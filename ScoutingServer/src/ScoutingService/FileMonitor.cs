//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Data.Entity;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Org.USFirst.Frc.Team4911.ScoutingService.DatabaseAccess;
using Org.USFirst.Frc.Team4911.ScoutingService.ScoutingDataModel;

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    internal class FileMonitor : WorkerThread
    {
        private readonly ConcurrentDictionary<string, DateTime> filesSeen = new ConcurrentDictionary<string, DateTime>();
        private readonly ConcurrentDictionary<string, Task<bool>> filesWaitingToBeProcessed = new ConcurrentDictionary<string, Task<bool>>();

        private readonly TraceListener listener;
        private readonly HashAlgorithm hashAlgorithm = new MD5CryptoServiceProvider();
        private readonly string root;

        private FileSystemWatcher fileSystemWatcher;
        private long running;

        internal FileMonitor(string directory, TraceListener listener)
        {
            this.listener = listener;
            this.root = directory;
        }

        public override void Start()
        {
            this.WatchForFiles();
        }

        public override void Stop()
        {
            Interlocked.Exchange(ref this.running, 0);

            if (this.fileSystemWatcher != null)
            {
                this.fileSystemWatcher.EnableRaisingEvents = false;
                this.fileSystemWatcher.Changed -= this.OnChanged;
                this.fileSystemWatcher.Created -= this.OnChanged;
                this.fileSystemWatcher.Dispose();
            }

            this.fileSystemWatcher = null;
        }

        internal void WatchForFiles()
        {
            Interlocked.Exchange(ref this.running, 1);

            try
            {
                try
                {
                    ThreadManager.ThreadInitialize.Release();
                }
                catch
                {
                    // If the thread is restarted, this could throw an exception but just ignore.
                }

                if (this.fileSystemWatcher == null)
                {
                    this.fileSystemWatcher = new FileSystemWatcher
                    {
                        InternalBufferSize = 64 * 1024,
                        Path = this.root,
                        Filter = "*.json",
                        IncludeSubdirectories = true,
                        NotifyFilter = NotifyFilters.FileName | NotifyFilters.LastWrite | NotifyFilters.Size
                    };
                }

                // Add event handlers.
                this.fileSystemWatcher.Changed += this.OnChanged;
                this.fileSystemWatcher.Created += this.OnChanged;
                //this.fileSystemWatcher.Deleted += new FileSystemEventHandler(OnChanged);
                //this.fileSystemWatcher.Renamed += new RenamedEventHandler(OnRenamed);

               // Begin watching.
                this.fileSystemWatcher.EnableRaisingEvents = true;

                while (Interlocked.Read(ref this.running) == 1)
                {
                    var filesToProcess = new ConcurrentQueue<KeyValuePair<string, ScoutingDataMatch>>();

                    // Find all tasks that have completed so files can be uploaded to database
                    foreach (var kv in this.filesWaitingToBeProcessed)
                    {
                        if (!kv.Value.IsCompleted || (kv.Value.IsFaulted || kv.Value.IsCanceled))
                        {
                            continue;
                        }

                        if (!kv.Value.Result)
                        {
                            continue;
                        }

                        Task<bool> task;
                        this.filesWaitingToBeProcessed.TryRemove(kv.Key, out task);
                        DateTime foo;
                        this.filesSeen.TryRemove(kv.Key, out foo);

                        // create matchd data only if data has not changed since last loaded
                        try

                        {
                            var matchData = this.CreateScoutingMatchData(kv.Key);
                            if (matchData != null)
                            {
                                filesToProcess.Enqueue(new KeyValuePair<string, ScoutingDataMatch>(kv.Key, matchData));
                            }
                        }
                        catch (Exception ex)
                        {
                            this.listener.WriteLine("Faild to process " + kv.Key + Environment.NewLine + ex.ToString(), "ScoutingService.Warning");
                        }
                    }

                    var processList = new List<KeyValuePair<string, ScoutingDataMatch>>();
                    do
                    {
                        KeyValuePair<string, ScoutingDataMatch> item;
                        if (filesToProcess.TryDequeue(out item))
                        {
                            processList.Add(item);
                        }
                    }
                    while (!filesToProcess.IsEmpty);

                    if (processList.Count > 0)
                    {
                        ThreadPool.QueueUserWorkItem(this.UpLoadMatchData, processList.ToArray());
                    }

                    Thread.Sleep(1000);
                }
            }
            catch (ThreadAbortException)
            {
                // Another thread has signalled that this worker thread must terminate.  
                // Typically, this occurs when the main service thread receives 
                // a service stop command.
            }
            catch (Exception ex)
            {
                this.listener.WriteLine(ex.ToString(), "ScoutingService.Warning");
            }
        }

        private void UpLoadMatchData(object state)
        {
            var matches = (KeyValuePair<string, ScoutingDataMatch>[]) state;
            using (var db = new ScoutingEntities())
            {
                try
                {
                    // Load all maches as updates don't map to primary key
                    db.ScoutingDataMatches.Load();

                    foreach (var kv  in matches)
                    {
                        var match = kv.Value;

                        var scoutingMatch = db.ScoutingDataMatches.Local.FirstOrDefault(s =>
                                match.Season == s.Season &&
                                match.EventCode == s.EventCode &&
                                match.MatchLevel == s.MatchLevel &&
                                match.MatchNumber == s.MatchNumber &&
                                match.Station == s.Station &&
                                match.DeviceId == s.DeviceId
                        );

                        if (scoutingMatch != null)
                        {
                            db.ScoutingDataMatches.Remove(scoutingMatch);
                            db.ScoutingDataMatches.Add(match);
                        }
                        else
                        {
                            db.ScoutingDataMatches.Add(match);
                        }

                        ////this.db.ScoutingDataFileLogs.Add(new ScoutingDataFileLog
                        ////{
                        ////    File = tuple.Item2,
                        ////    Md5Hash = tuple.Item3,
                        ////    TimeStamp = DateTime.Now
                        ////});
                    }

                    this.listener.WriteLine("Loading " + matches.Length + " matches");
                    db.BulkSaveChanges();
                }
                catch (Exception ex)
                {
                    this.listener.WriteLine(ex.ToString(), "ScoutingService.Warning");
                    // throw ex;
                }
            }
        }

        private ScoutingDataMatch CreateScoutingMatchData(string path)
        {
            var data = File.ReadAllText(path);
            var hashCurrent = hashAlgorithm.ComputeHash(Encoding.UTF8.GetBytes(data));

            // Check for MD5 hash file to see if data has changed
            var md5Path = path + ".md5";
            if (File.Exists(md5Path))
            {
                var hashPrevious = File.ReadAllBytes(md5Path);

                // Skip files that have been read already.
                if (hashCurrent.SequenceEqual(hashPrevious))
                {
                    return null;
                }
            }

            try
            {
                File.WriteAllBytes(md5Path, hashCurrent);
            }
            catch (Exception ex)
            {
                this.listener.WriteLine(ex.ToString(), "ScoutingService.Warning");
            }

            this.listener.WriteLine("Reading file " + path);
            var matchData = JsonConvert.DeserializeObject<ScoutingData>(data);
            var match = new ScoutingDataMatch
            {
                Season = 2017,
                EventCode = matchData.EventCode,
                MatchLevel = matchData.TournamentLevel,
                MatchNumber = matchData.MatchNumber,
                TeamNumber = matchData.TeamNumber,
                Station = matchData.Station,
                ScoutName = matchData.ScoutName,
                ScoutingTeam = matchData.ScoutingTeamName,
                DeviceId = matchData.DeviceId
            };

            var auto = new ScoutingDataAutonomou
            {
                AutoMobilityPoints = matchData.MatchData.AutonomousPeriod.AutoMobilityPoints ? (byte)1 : (byte)0,
            };

            match.ScoutingDataAutonomous.Add(auto);

            var scoutingDataPreGame = new ScoutingDataPreGame
            {
                PreGameHasFuel = matchData.MatchData.PreGame.HasFuel ? (byte)1 : (byte)0,
                PreGameHasGear = matchData.MatchData.PreGame.HasGear ? (byte)1 : (byte)0,
                PreGameUsesOwnRope = matchData.MatchData.PreGame.UsesOwnRope ? (byte)1 : (byte)0,
                PreGameRopeTouchPadPosition = matchData.MatchData.PreGame.RopeTouchPadPosition,
                PreGameRobotPosition = matchData.MatchData.PreGame.RobotPosition,
            };

            match.ScoutingDataPreGames.Add(scoutingDataPreGame);

            var scoutingDataTeleop = new ScoutingDataTeleop
            {
                TeleopPlayedDefense = matchData.MatchData.TeleopPeriod.PlayedDefense ? (byte)1 : (byte)0,
                GearAttemptCount = matchData.MatchData.TeleopPeriod.GearAttemptCount,
                ShotAttemptCount = matchData.MatchData.TeleopPeriod.ShotAttemptCount,
            };

            match.ScoutingDataTeleops.Add(scoutingDataTeleop);

            var scoutingDataEndGame = new ScoutingDataEndGame
            {
                ClimbingAttempted = matchData.MatchData.EndGame.Attempted ? (byte)1 : (byte)0,
                ClimbingSucceeded = matchData.MatchData.EndGame.Succeeded ? (byte)1 : (byte)0,
                ClimbingTimeInSeconds = matchData.MatchData.EndGame.TimeInSeconds,
                ClimbingTouchPadPosition = matchData.MatchData.EndGame.TouchPadPosition
            };

            match.ScoutingDataEndGames.Add(scoutingDataEndGame);

            if (matchData.MatchData.AutonomousPeriod.GearAttempts != null &&
                matchData.MatchData.AutonomousPeriod.GearAttempts.Count > 0)
            {
                var autoGearAttempt = new ScoutingDataGearAttempt
                {
                    AutonomousMode = (byte)1,
                    GearPegPosition = matchData.MatchData.AutonomousPeriod.GearAttempts[0].GearPegPosition,
                    GearResult = matchData.MatchData.AutonomousPeriod.GearAttempts[0].GearResult
                };

                match.ScoutingDataGearAttempts.Add(autoGearAttempt);
            }

            if (matchData.MatchData.AutonomousPeriod.ShotAttempts != null && 
                matchData.MatchData.AutonomousPeriod.ShotAttempts.Count > 0)
            {
                var autoShotAttempt = new ScoutingDataShotAttempt
                {
                    AutonomousMode = (byte)1,
                    ShotsMade= matchData.MatchData.AutonomousPeriod.ShotAttempts[0].ShotsMade,
                    ShotsMissed= matchData.MatchData.AutonomousPeriod.ShotAttempts[0].ShotsMissed,
                    ShotLocation = matchData.MatchData.AutonomousPeriod.ShotAttempts[0].ShotLocation,
                    ShotDuration= matchData.MatchData.AutonomousPeriod.ShotAttempts[0].ShotDuration
                };

                match.ScoutingDataShotAttempts.Add(autoShotAttempt);
            }

            if (matchData.MatchData.AutonomousPeriod.HopperAttempts != null &&
                matchData.MatchData.AutonomousPeriod.HopperAttempts.Count > 0)
            {
                var autoHopperAttempt = new ScoutDataHopperAttempt()
                {
                    AutonomousMode= (byte)1,
                    Activated = matchData.MatchData.AutonomousPeriod.HopperAttempts[0].Activated ? (byte)1 : (byte)0,
                    HopperLocation= matchData.MatchData.AutonomousPeriod.HopperAttempts[0].HopperLocation
                };

                match.ScoutDataHopperAttempts.Add(autoHopperAttempt);
            }

            if (matchData.MatchData.TeleopPeriod.GearAttempts != null)
            {
                foreach (var attempt in matchData.MatchData.TeleopPeriod.GearAttempts)
                {
                    var gearAttempt = new ScoutingDataGearAttempt
                    {
                        AutonomousMode = 0,
                        GearPegPosition = attempt.GearPegPosition,
                        GearResult = attempt.GearResult,
                        WasDefended = attempt.WasDefended ? (byte)1 : (byte)0
                    };

                    match.ScoutingDataGearAttempts.Add(gearAttempt);
                }
            }

            if (matchData.MatchData.TeleopPeriod.ShotAttempts != null)
            {
                var attemptCount = 0;
                foreach (var attempt in matchData.MatchData.TeleopPeriod.ShotAttempts)
                {
                    var shotAttempt = new ScoutingDataShotAttempt
                    {
                        AutonomousMode = 0,
                        ShotsMade= attempt.ShotsMade,
                        ShotsMissed = attempt.ShotsMissed,
                        ShotLocation = attempt.ShotLocation,
                        ShotDuration = attempt.ShotDuration,
                        WasDefended = attempt.WasDefended ? (byte)1 : (byte)0,
                        ShotAttempt = attemptCount++
                    };

                    match.ScoutingDataShotAttempts.Add(shotAttempt);
                }
            }

            if (matchData.MatchData.TeleopPeriod.HopperAttempts != null)
            {
                foreach (var hopperAttempt in matchData.MatchData.TeleopPeriod.HopperAttempts)
                {
                    var autoHopperAttempt = new ScoutDataHopperAttempt()
                    {
                        AutonomousMode = (byte)0,
                        Activated = hopperAttempt.Activated ? (byte)1 : (byte)0,
                        HopperLocation = hopperAttempt.HopperLocation
                    };

                    match.ScoutDataHopperAttempts.Add(autoHopperAttempt);
                }
            }

            return match;
        }



        ////internal void ManageMd5Hashes(string path)
        ////{
        ////    var data = File.ReadAllText(path);
        ////    var hashCurrent = hashAlgorithm.ComputeHash(Encoding.UTF8.GetBytes(data));

        ////    // Check for MD5 hash file to see if data has changed
        ////    var md5Path = path + ".md5";
        ////    if (File.Exists(md5Path))
        ////    {
        ////        var hashData = File.ReadAllBytes(md5Path);
        ////        var hashPrevious = hashAlgorithm.ComputeHash(hashData);

        ////        // this has been read!
        ////        if (hashCurrent.SequenceEqual(hashPrevious))
        ////        {
        ////            return;
        ////        }
        ////    }

        ////    try
        ////    {
        ////        File.WriteAllBytes(md5Path, hashCurrent);
        ////    }
        ////    catch (Exception ex)
        ////    {
        ////        this.listener.WriteLine(ex.ToString(), "ScoutingService.Warning");
        ////    }



        ////    var dbHashes =
        ////        this.db.ScoutingDataFileLogs.Local.Where(f => f.File == path)
        ////            .OrderByDescending(f => f.TimeStamp)
        ////            .ToList();

        ////    if (dbHashes.Count > 0)
        ////    {
        ////        if (dbHashes.Any(f => f.Md5Hash.SequenceEqual(hashCurrent)))
        ////        {
        ////            return;
        ////        }
        ////    }
        ////}


        internal void OnChanged(object source, FileSystemEventArgs fileSystemEventArgs)
        {
            if (!this.filesSeen.TryAdd(fileSystemEventArgs.FullPath, DateTime.Now))
            {
                return;
            }

            this.filesWaitingToBeProcessed[fileSystemEventArgs.FullPath] = Task.Run(() => this.WaitForFileWriteComplete(fileSystemEventArgs.FullPath));
        }

        internal async Task<bool> WaitForFileWriteComplete(string path)
        {
            for (var retries = 60; retries > 0; retries--)
            {
                try
                {
                    using (File.Open(path, FileMode.Open, FileAccess.Read, FileShare.None))
                    {
                        return true;
                    }
                }
                catch (IOException)
                {
                }
                catch (Exception ex)
                {
                    this.listener.WriteLine("Failed to load " + path + Environment.NewLine + ex, "ScoutingService.Warning");
                }

                if (retries > 0)
                {
                    await Task.Delay(TimeSpan.FromSeconds(1.0));
                }
            }

            this.listener.WriteLine("Failed to load " + path, "ScoutingService.Warning");

            return false;
        }
    }
}

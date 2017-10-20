//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.IO;
using Newtonsoft.Json;
using Org.USFirst.Frc.Team4911.FrcEvents;
using Org.USFirst.Frc.Team4911.FrcEvents.Models;
using Org.USFirst.Frc.Team4911.ScoutingService.ScoutingDataModel;

namespace Org.USFirst.Frc.Team4911.TestDataGenerator
{
    class Program
    {
        enum GameStategy
        {
            Gears,
            Fuel,
            GearsAndDefense,
        }

        private const string UserName = @"";
        private const string Key = @"";

        private static readonly Random RandomNumber = new Random(997);

        static void Main(string[] args)
        {
            var client = new FrcEventsClient(UserName, Key);
            var schedule = client.GetEventSchedule(2016, "WAMOU", TournamentLevel.qual).Result.ToList();

            var teams = new Dictionary<int, int>();
            GetTeams(schedule, teams);

            var i = 1;
            foreach (var match in schedule)
            {
                var redTeams = match.Teams.Where(t => t.Station.StartsWith("red", true, CultureInfo.InvariantCulture))
                        .Select(t => t.TeamNumber)
                        .ToList();
                var blueTeams = match.Teams.Where(t => t.Station.StartsWith("blue", true, CultureInfo.InvariantCulture))
                        .Select(t => t.TeamNumber)
                        .ToList();

                var teamStrategies = GetStrategy(teams, redTeams);
                foreach (var team in GetStrategy(teams, blueTeams))
                {
                    teamStrategies.Add(team.Key, team.Value);
                }

                foreach (var scoutingteam in new[] { "CyberKnights", /*"CPR"*/ })
                {
                    foreach (var team in teamStrategies)
                    {
                        DriveStation driveStation;
                        Enum.TryParse(match.Teams.First(t => t.TeamNumber == team.Key).Station, out driveStation);

                        TouchPadPosition touchPadPosition = (TouchPadPosition)(i++ % 3);
                        GearPegPosition gearPegPosition = (GearPegPosition)(i % 3);
                        var data = CreateScoutingData(
                            teams[team.Key],
                            team.Key,
                            match.MatchNumber,
                            scoutingteam,
                            driveStation,
                            touchPadPosition,
                            gearPegPosition,
                            team.Value);

                        var sjson = JsonConvert.SerializeObject(data);
                        var filename = string.Format(
                            CultureInfo.InvariantCulture,
                            "c:\\cyberknights\\test\\{0}_{1}_{2}_{3}_{4}_{5}_{6}_{7}.json",
                            data.EventCode,
                            data.MatchNumber,
                            data.TournamentLevel,
                            data.Station,
                            data.TeamNumber,
                            data.DeviceId,
                            data.ScoutName,
                            data.ScoutingTeamName);

                        File.WriteAllText(filename, sjson);

                    }
                }
            }
            // Now update database!
        }

        private static Dictionary<int, GameStategy> GetStrategy(Dictionary<int, int> teams, IList<int> alliance)
        {
            var strategies = new Dictionary<int, GameStategy>();

            var keys = new List<Tuple<int, int>>
            {
                new Tuple<int, int>(teams[alliance[0]], alliance[0]),
                new Tuple<int, int>(teams[alliance[1]], alliance[1]),
                new Tuple<int, int>(teams[alliance[2]], alliance[2]),
            }.OrderByDescending(e => e.Item1).ToList();

            if (keys[0].Item1 >= 80 && keys[1].Item1 >= 60)
            {
                strategies.Add(keys[0].Item2, GameStategy.Fuel);
                strategies.Add(keys[1].Item2, GameStategy.Fuel);
                strategies.Add(keys[2].Item2, GameStategy.GearsAndDefense);
            }
            else if (keys[2].Item1>= 60 && keys[1].Item1 >= 60)
            {
                strategies.Add(keys[0].Item2, GameStategy.Gears);
                strategies.Add(keys[1].Item2, GameStategy.Gears);
                strategies.Add(keys[2].Item2, GameStategy.Gears);
            }
            else
            {
                strategies.Add(keys[0].Item2, GameStategy.Fuel);
                strategies.Add(keys[1].Item2, GameStategy.Gears);
                strategies.Add(keys[2].Item2, GameStategy.GearsAndDefense);
            }

            return strategies;
        }
        
        private static void GetTeams(IEnumerable<ScheduledMatch> prevSchedule, Dictionary<int, int> teams)
        {
            foreach (var match in prevSchedule)
            {
                foreach (var team in match.Teams)
                {
                    if (!teams.ContainsKey(team.TeamNumber))
                    {
                        teams.Add(team.TeamNumber, RandomNumber.Next(15, 100));
                    }
                }
            }
        }

        private static ScoutingData CreateScoutingData(
            int offensivePowerRating,
            int teamNumber,
            int matchNumber,
            string scoutingTeam,
            DriveStation driveStation,
            TouchPadPosition touchPadPosition,
            GearPegPosition gearPegPosition,
            GameStategy strategy)
        {
            var autoGears = RandomNumber.Next(0, 100) >= 5;
            var autoShots = RandomNumber.Next(0, 100) >= 20;
            var autoHopper = autoShots && offensivePowerRating > 90 && RandomNumber.Next(0, 6) >= 2;
            var autoMobilityPoints = autoHopper || RandomNumber.Next(0, 100) >= 60;
            var climbs = offensivePowerRating > 40 && RandomNumber.Next(0, 100) >= 25;
            var isDefended = offensivePowerRating > 80 && RandomNumber.Next(0, 2) == 1;

            var data = new MatchData
            {
                PreGame = GetPreGame(autoGears, autoShots, climbs, touchPadPosition),
                AutonomousPeriod =  GetAutonomousPeriod(offensivePowerRating, autoGears, autoShots, autoHopper, autoMobilityPoints, gearPegPosition),
                TeleopPeriod = GetTeleopPeriod(strategy, offensivePowerRating, isDefended, autoHopper),
                EndGame = GetEndGame(climbs, touchPadPosition)
            };

            var qualitative = new Qualitative
            {
                AutoTargeting = false,
                BallCapacity = BallCapacity.Fifty.ToString(),
                DeadBot = false,

                BallPickupCanPickUpOffGroud = true,
                BallPickupFeederAbility = Ability.Fair.ToString(),
                BallPickupSpeed = Speed.Medium.ToString(),

                DefenseGoesInForbiddenAreas = true,

                DrivingSpeed = Speed.Fast.ToString(),

                GearPlacementAccuracy = Ability.Good.ToString(),
                GearPlacementCanPickupOffFloor = true,
                GearPlacementSpeed = Speed.Slow.ToString(),

                SmartnessIsHelpful = true,
                SmartnessWorksAgainstAlliance = true,

                PilotDropsRopeInTimelyManner = true,
                PilotGearPlacementSpeed = Speed.Medium.ToString(),
                PilotGearRetrievalSpeed = Speed.Slow.ToString(),

                Notes = "Impressive speed"
            };

            var scoutingData = new ScoutingData
            {
                TeamNumber = teamNumber,
                Station = driveStation.ToString(),
                TimeStamp = DateTime.UtcNow,
                ScoutName = "scout@" + driveStation,
                MatchData = data,
                //Qualitative = qualitative,
                EventCode = "DEMO",
                TournamentLevel = "qual",
                MatchNumber = matchNumber,
                DeviceId = scoutingTeam + "Device_" + driveStation,
                ScoutingTeamName = scoutingTeam
            };

            return scoutingData;
        }

        private static PreGame GetPreGame(bool autoGears, bool autoShots, bool climbs, TouchPadPosition touchPadPosition)
        {
            var preGame = new PreGame
            {
                HasFuel = autoShots,
                HasGear = autoGears,
                UsesOwnRope = climbs,
                HasPilot = false,
                RopeTouchPadPosition = touchPadPosition.ToString(),
                RobotPosition = "unknown"
            };

            return preGame;
        }

        private static AutonomousPeriod GetAutonomousPeriod(
            int offensivePowerRating,
            bool autoGears,
            bool autoShots,
            bool autoHopper,
            bool autoMobilityPoints,
            GearPegPosition gearPegPosition)
        {
            var auto = new AutonomousPeriod
            {
                AutoMobilityPoints = autoMobilityPoints
            };

            if (autoGears)
            {
                var gearResult = RandomNumber.Next(0, 100) > 25
                     ? GearResult.Success
                     : GearResult.Failed;

                var gearPosition = gearResult == GearResult.Success
                    ? gearPegPosition
                    : GearPegPosition.None;

                auto.GearAttempts = new List<GearAttempt>
                {
                    new GearAttempt
                    {
                        GearResult = gearResult.ToString(),
                        GearPegPosition = gearPosition.ToString()
                    }
                };
            }

            if (autoHopper)
            {
                auto.HopperAttempts = new List<HopperAttempt>();
                auto.HopperAttempts.Add(
                    new HopperAttempt
                    {
                        Activated = true,
                        HopperLocation = RandomNumber.Next(1, 6).ToString()
                    });
            }

            if (autoShots)
            {
                var fuelCount = autoHopper ? 60 : 10;
                var shotsMade = autoHopper
                    ? RandomNumber.Next(-12, 1) + fuelCount
                    : RandomNumber.Next(-5, 1) + fuelCount;

                var shotsMissed = fuelCount - shotsMade;

                var shotMode = autoHopper ? ShotMode.High : RandomNumber.Next(0, 100) > 30 ? ShotMode.High : ShotMode.Low;

                var shotLocation = autoHopper
                    ? ShotLocation.Medium :
                        shotMode == ShotMode.Low ? ShotLocation.Close :
                            RandomNumber.Next(0, 100) > 50 ? ShotLocation.Close : ShotLocation.Medium;

                var shotDuration = autoHopper ? 10 : 5;

                auto.ShotAttempts = new List<ShotAttempt>
                {
                    new ShotAttempt
                    {
                        ShotsMade = shotsMade,
                        ShotsMissed= shotsMissed,
                        ShotLocation = shotLocation.ToString(),
                        ShotMode = shotMode.ToString(),
                        ShotDuration = shotDuration,
                    },
                };
            };

            return auto;
        }

        private static TeleopPeriod GetTeleopPeriod(
            GameStategy strategy,
            int offensivePowerRating,
            bool isDefended,
            bool autoHopper)
        {
            bool telepGears = strategy == GameStategy.Gears || strategy == GameStategy.GearsAndDefense;
            bool telepShots = strategy == GameStategy.Fuel;
            bool teleopDoesDefense = strategy == GameStategy.GearsAndDefense;

            var teleopPeriod = new TeleopPeriod();

            var gearCollectionAttempts = teleopDoesDefense ? RandomNumber.Next(1, 4) : RandomNumber.Next(1, 5);

            int fuelCollectionAttempts;

            if (telepGears && offensivePowerRating > 90)
            {
                fuelCollectionAttempts = Math.Max(1, 4 - gearCollectionAttempts);
            }
            else
            {
                fuelCollectionAttempts = RandomNumber.Next(2, 5);
            }


            if (telepGears)
            {
                var gearAttempts = new List<GearAttemptTeleop>();

                for (var i = 0; i < gearCollectionAttempts; i++)
                {
                    var wasDefended = isDefended && RandomNumber.Next(0, 100) > 25;
                    var result = wasDefended ? RandomNumber.Next(0, 100) >= 80 : RandomNumber.Next(0, 100) >= 30;
                    var gearPoistion = (GearPegPosition)RandomNumber.Next(0, 3);

                    var gearAttempt = new GearAttemptTeleop
                    {
                        GearResult = result ? GearResult.Success.ToString() : GearResult.Failed.ToString(),
                        GearPegPosition = result ? gearPoistion.ToString() : GearPegPosition.None.ToString(),
                        WasDefended = wasDefended
                    };

                    gearAttempts.Add(gearAttempt);
                };

                teleopPeriod.GearAttempts = gearAttempts;
                teleopPeriod.GearAttemptCount = gearAttempts.Count;
            }

            if (autoHopper && RandomNumber.Next(0, 10) > 7)
            {
                teleopPeriod.HopperAttempts = new List<HopperAttempt>();
                teleopPeriod.HopperAttempts.Add(
                    new HopperAttempt
                    {
                        Activated = true,
                        HopperLocation = RandomNumber.Next(1, 6).ToString()
                    });
            }
            
            if (telepShots || telepGears && offensivePowerRating > 90)
            {
                var shotAttempts = new List<ShotAttemptTeleop>();

                for (var i = 0; i < fuelCollectionAttempts; i++)
                {
                    var fuelCount = i == 0 && RandomNumber.Next(0, 100) > 70 ? 50 : 30;

                    var shotMode = autoHopper ? ShotMode.High : RandomNumber.Next(0, 100) > 20 ? ShotMode.High : ShotMode.Low;

                    var shotsMade = isDefended
                        ? RandomNumber.Next(-12, 1) + fuelCount
                        : RandomNumber.Next(-5, 1) + fuelCount;

                    var shotsMissed = fuelCount - shotsMade;

                    var shotLocation = autoHopper
                        ? ShotLocation.Medium :
                            shotMode == ShotMode.Low ? ShotLocation.Close :
                                RandomNumber.Next(0, 100) > 50 ? ShotLocation.Close : ShotLocation.Medium;

                    var shotDuration = fuelCount / RandomNumber.Next(1,6);

                    var shotAttempt = new ShotAttemptTeleop
                    {
                        ShotsMade = shotsMade,
                        ShotsMissed = shotsMissed,
                        ShotLocation = shotLocation.ToString(),
                        ShotMode = shotMode.ToString(),
                        ShotDuration = shotDuration
                    };

                    shotAttempts.Add(shotAttempt);
                }

                teleopPeriod.ShotAttempts = shotAttempts;
                teleopPeriod.ShotAttemptCount = shotAttempts.Count;
            }

            return teleopPeriod;
        }

        private static EndGame GetEndGame(bool climbs, TouchPadPosition touchPadPosition)
        {
            if (climbs)
            {
                var succeeded = RandomNumber.Next(0, 100) > 10;
                var endGame = new EndGame()
                {
                    Attempted = true,
                    Succeeded = succeeded,
                    TimeInSeconds = succeeded ? RandomNumber.Next(3, 31) : 0,
                    TouchPadPosition = touchPadPosition.ToString()
                };

                return endGame;
            }

            return new EndGame()
            {
                Attempted = false,
                Succeeded = false,
                TimeInSeconds = 0,
                TouchPadPosition = TouchPadPosition.None.ToString()
            };

        }
    }
}

//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    internal class ThreadManager : WorkerThread
    {
        public static Semaphore ThreadInitialize = new Semaphore(0, 1);
        private readonly TraceListener listener;
        private long running;

        internal ThreadManager(Dictionary<WorkerThread, Thread> workerThreads, TraceListener listener)
        {
            this.WorkerThreads = workerThreads;
            this.listener = listener;
        }

        internal Dictionary<WorkerThread, Thread> WorkerThreads { get; }

        public override void Start()
        {
            Interlocked.Exchange(ref this.running, 1);

            try
            {
                var workerThreadTypes = new WorkerThread[this.WorkerThreads.Count];
                this.WorkerThreads.Keys.CopyTo(workerThreadTypes, 0);

                while (Interlocked.Read(ref this.running) == 1)
                {
                    try
                    {
                        foreach (var workerThreadType in workerThreadTypes)
                        {
                             var workerThread = this.WorkerThreads[workerThreadType];

                            if (workerThread == null || 
                                workerThread.ThreadState == System.Threading.ThreadState.Stopped)
                            {
                                if (workerThread == null)
                                {
                                    ThreadInitialize.WaitOne(5000, false);
                                }

                                //var obj = Activator.CreateInstance(workerThreadType.GetType());
                                this.WorkerThreads[workerThreadType] = new Thread(workerThreadType.Start);

                                this.WorkerThreads[workerThreadType].Start();
                            }
                        }

                        Thread.Sleep(5000);
                    }

                    catch (ThreadAbortException)
                    {
                        throw;
                    }

                    catch (Exception ex)
                    {
                        this.listener.WriteLine(ex.ToString(), "ScoutingService.Warning");
                    }
                }
            }
            catch (ThreadAbortException)
            {
            }
        }

        public override void Stop()
        {
            Interlocked.Exchange(ref this.running, 0);

            foreach (var worker in this.WorkerThreads.Keys)
            {
                worker.Stop();
            }

            Thread[] threads = new Thread[this.WorkerThreads.Count];
            this.WorkerThreads.Values.CopyTo(threads, 0);

            foreach (var thread in threads)
            {
                if (thread != null && thread.IsAlive)
                {
                    thread.Abort();
                }
            }
        }
    }
}
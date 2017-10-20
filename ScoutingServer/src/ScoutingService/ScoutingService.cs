//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using System.Configuration;
using System.Diagnostics;
using System.IO;
using System.ServiceProcess;
using System.Threading;

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    partial class ScoutingService
    {
        private readonly ThreadManager threadManager;
        private readonly TraceListener listener;
        private Thread managerThread;

        public ScoutingService()
        {
            ////InitializeComponent();
            this.listener = new ConsoleTraceListener { Name = "ScoutingServiceTraceListener" };
            Trace.Listeners.Add(this.listener);

            var dir = ConfigurationManager.AppSettings.Get("BluetoothFolder") ?? @"C:\SteamWorks\";

            if (!Directory.Exists(dir))
            {
                Directory.CreateDirectory(dir);
            }

            var bluetoothListener = new BluetoothListener(dir, this.listener);
            var fileMonitor = new FileMonitor(dir, this.listener);

            var workerThreads = new Dictionary<WorkerThread, Thread>()
            {
                { bluetoothListener, null },
                { fileMonitor, null }
            };

            this.threadManager = new ThreadManager(workerThreads, this.listener);
            //this.ServiceName = "ScoutingService";
        }

        internal void Start()
        {
            this.Register();
        }

        ////protected override void OnStart(string[] args)
        ////{
        ////    this.Register();
        ////}

        ////protected override void OnStop()
        ////{
        ////    this.UnRegister();
        ////}

        internal void Stop()
        {
            this.UnRegister();
        }

        private void Register()
        {
            this.managerThread = new Thread(new ThreadStart(this.threadManager.Start));
            this.managerThread.Start();
        }

        private void UnRegister()
        {
            this.threadManager.Stop();
            if ((this.managerThread != null) && (this.managerThread.IsAlive))
            {
                if (!this.managerThread.Join(1000))
                {
                    this.managerThread.Abort();
                }
            }
        }

    ////    protected override void OnShutdown()
    ////    {
    ////        this.listener.Dispose();
    ////    }
    }
}

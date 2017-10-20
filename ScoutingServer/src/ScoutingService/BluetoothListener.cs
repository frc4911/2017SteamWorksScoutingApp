//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Diagnostics;
using System.Threading;
using Org.USFirst.Frc.Team4911.ScoutingService.BluetoothServer;

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    internal class BluetoothListener : WorkerThread
    {
        private BluetoothFileServer bluetoothFileServer;
        private readonly string root;
        private long running;
        private readonly TraceListener listener;

        internal BluetoothListener(string directory, TraceListener listener)
        {
            this.listener = listener;
            this.root = directory;
        }

        public override void Start()
        {
            this.ListenToEvents();
        }

        public override void Stop()
        {
            Interlocked.Exchange(ref this.running, 0);
        }

        internal void ListenToEvents()
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
                this.bluetoothFileServer = new BluetoothFileServer(s => { listener.WriteLine(s); }, this.root, this.listener);
                this.bluetoothFileServer.Listen();

                while (Interlocked.Read(ref this.running) == 1)
                {
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
    }
}

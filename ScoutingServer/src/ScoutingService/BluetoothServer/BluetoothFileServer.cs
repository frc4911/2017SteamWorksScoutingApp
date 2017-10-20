//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Diagnostics;
using System.IO;
using System.Threading;
using InTheHand.Net.Sockets;

namespace Org.USFirst.Frc.Team4911.ScoutingService.BluetoothServer
{
    /// <summary>
    /// An implementation of a bluetooth server that can recieve files and folders from a client.
    /// </summary>
    public class BluetoothFileServer : IDisposable
    {
        private readonly string root;
        private readonly Action<string> updateHandler;
        InTheHand.Net.Sockets.BluetoothListener listener;
        private TraceListener traceListener;
        private bool disposed;

        public BluetoothFileServer(Action<string> handler, string root, TraceListener traceListener = null)
        {
            this.updateHandler = handler;
            this.root = root;
            this.traceListener = traceListener ?? new DefaultTraceListener();
        }

        public void Listen()
        {
            if (!Directory.Exists(this.root))
            {
                Directory.CreateDirectory(this.root);
            }

            this.updateHandler("Listening For Clients. Root Directory: " + this.root);

            // begin listening for devices
            // all callers will have to be paired ahead of time
            this.listener = new InTheHand.Net.Sockets.BluetoothListener(new Guid("2d31ac7d-0d4a-48dd-8136-2f6a9b71a3f4"));
            this.listener.Start();
            this.listener.BeginAcceptBluetoothClient(this.BluetoothListenerAcceptClientCallback, this.listener);
        }

        private void BluetoothListenerAcceptClientCallback(IAsyncResult result)
        {
            using (var client = this.GetClient(result))
            {
                this.updateHandler("Connected To: " + client.RemoteMachineName);

                DataStream stream = new DataStream(client.GetStream());
                MessageHandler handler = new MessageHandler(this.root, stream, this.traceListener);
                this.KeepAlive(client, handler);

                // read forever
                while (true)
                {
                    this.traceListener.WriteLine("Accept Callback: Reading Message");
                    ProtocolMessage msg = handler.Read();
                    if (msg != null)
                    {
                        if (msg is StopProtocolMessage)
                        {
                            this.traceListener.WriteLine("Recieved Stop Message exiting.");
                            return;
                        }

                        this.updateHandler(msg.ToString());
                    }

                    if (msg == null)
                    {
                        break;
                    }
                }
            }
        }

        // There is some weird bluetooth issues that require
        // the connection to be active in both directions.
        private void KeepAlive(BluetoothClient client, MessageHandler handler)
        {
            new Thread(() =>
            {
                Thread.CurrentThread.IsBackground = true;
                KeepAliveProtocolMessage keepAliveProtocolMessage = new KeepAliveProtocolMessage(handler, this.traceListener);
                while (client.Connected)
                {
                    try
                    {
                        keepAliveProtocolMessage.Send();
                        Thread.Sleep(500);
                    }
                    catch (IOException)
                    {
                        return;
                    }
                }
            }).Start();
        }

        private BluetoothClient GetClient(IAsyncResult result)
        {
            if (result == null || result.AsyncState == null || !(result.AsyncState is InTheHand.Net.Sockets.BluetoothListener))
            {
                throw new ArgumentNullException(nameof(result));
            }

            var blueToothListener = (InTheHand.Net.Sockets.BluetoothListener)result.AsyncState;

            // continue listening for other broadcasting devices
            blueToothListener.BeginAcceptBluetoothClient(this.BluetoothListenerAcceptClientCallback, blueToothListener);

            // create a connection to the device that's just been found
            BluetoothClient client = blueToothListener.EndAcceptBluetoothClient(result);
            return client;
        }

        public void Stop()
        {
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public void Dispose()
        {
            this.Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (this.disposed)
            {
                return;
            }

            if (disposing)
            {
                // Free managed objects here. 
                this.listener.Stop();
            }

            // Free any unmanaged objects here. 
            this.disposed = true;
        }
    }
}
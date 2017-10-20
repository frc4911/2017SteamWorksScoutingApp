//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;

namespace Org.USFirst.Frc.Team4911.BluetoothServer
{
    // File Pusher Protocol
    // The file pusher protocol is a simple order dependent protocol for copying files
    // over bluetooth. Files and directories are relative to a virtual root on the 
    // recieving server. 
    //
    // 1 byte - Message Type
    // variable - Dependent on message type
    //
    // Message Types:
    // 1 - File
    // 2 - Directory
    // 3 - Stop
    // 4 - Keep Alive
    // 5 - File Ack
    internal class MessageHandler
    {
        internal DataStream Stream;
        internal string RootPath;
        private readonly TraceListener listener;

        public MessageHandler(string root, DataStream stream, TraceListener listener)
        {
            this.RootPath = root;
            this.Stream = stream;
            this.listener = listener;
        }

        public ProtocolMessage Read()
        {
            Console.WriteLine("Message Handler Reading Message");
            try
            {
                int type = Stream.ReadInt1();
                switch (type)
                {
                    case FileProtocolMessage.TYPE:
                        return new FileProtocolMessage(this, this.listener).Read();
                    case DirectoryProtocolMessage.TYPE:
                        return new DirectoryProtocolMessage(this, this.listener).Read();
                    case StopProtocolMessage.TYPE:
                        return new StopProtocolMessage(this, this.listener).Read();
                    default:
                        Console.WriteLine("Unknown Message Type: " + type);
                        return null;
                }
            } catch (EndOfStreamException)
            {
                // no message left read
                return null;
            }
        }
    }

    abstract class ProtocolMessage
    {
        protected ProtocolMessage(MessageHandler handler, TraceListener traceListener)
        {
            this.Handler = handler;
            this.TraceListener = traceListener;
        }

        protected MessageHandler Handler { get; }

        public TraceListener TraceListener { get; private set; }
    }

    // File Message Type
    // 1 byte - Message Type (FILE = 0x01)
    // 2 byte - Filename Length
    // n byte - Filename
    // 2 byte - Container Name (Target Directory) Length
    // n byte - Container Name (Target Directory)
    // 1 byte - Compression: 0 = None
    // 4 byte - File length
    // n byte - File contents
    // 8 byte - CRC
    internal class FileProtocolMessage : ProtocolMessage
    {
        internal const int TYPE = 0x1;        
        private int compression;
        private string containerName;
        private long crc;
        private string filename;
        private int length;

        public FileProtocolMessage(MessageHandler handler, TraceListener traceListener) 
            : base(handler, traceListener)
        {
        }

        public FileProtocolMessage Read()
        {
            this.TraceListener.WriteLine("Reading File");
            DataStream buffer = Handler.Stream;
            this.filename = buffer.ReadUTF8();
            this.TraceListener.WriteLine("Filename: {0}", filename);
            this.containerName = buffer.ReadUTF8();
            this.TraceListener.WriteLine("ContainerName: {0}", containerName);
            // the client will use "/" to indicate writing files into
            // the root of the remote container, but Path.Combine
            // doesn't like it and instead of combining just replaces
            // the left-hand side with nothing, meaning files get writen
            // to C:\, so we will remove the leading slash
            if (this.containerName.StartsWith("/"))
            {
                this.containerName = this.containerName.Substring(1);
            }

            this.compression = buffer.ReadInt1();
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "Compression: {0}", compression));
            this.length = buffer.ReadInt32();
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "File Length: {0}", length));

            string tempRootFilePath = Environment.GetEnvironmentVariable("tmp");

            if (string.IsNullOrEmpty(tempRootFilePath))
            {
                tempRootFilePath = @"C:\tmp";
                Directory.CreateDirectory(tempRootFilePath);
                Environment.SetEnvironmentVariable("tmp", tempRootFilePath);
            }

            string containerPath = Path.Combine(Handler.RootPath, containerName);
            Directory.CreateDirectory(containerPath);

            string tempFile = Path.GetFullPath(Path.Combine(tempRootFilePath, containerName, filename));
            string destinationFile = Path.GetFullPath(Path.Combine(Handler.RootPath, containerPath, filename));
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "Writing to Temp File : {0}", tempFile));

            FileStream fout = new FileStream(tempFile, FileMode.OpenOrCreate);            
            buffer.ReadBytes(fout, this.length);
            fout.Close();

            if (File.Exists(destinationFile))
            {
                File.Delete(destinationFile);
            }

            File.Move(tempFile, destinationFile);

            this.crc = buffer.ReadInt64();
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "CRC: {0}", crc));
            this.TraceListener.WriteLine("Sending File Ack");
            new FileAckProtocolMessage(Handler, this.TraceListener).Send();
            return this;
        }

        public override string ToString() {
            return "Type: FILE" + Environment.NewLine +
            "  Filename: " + filename + Environment.NewLine +
            "  Container: " + containerName + Environment.NewLine + 
            "  Compression: " + compression + Environment.NewLine +
            "  File Length: " + length + Environment.NewLine  + 
            "  CRC: " + crc;
        }
    }

    // Directory Message Type (UN-USED)
    // 1 byte - Message Type (DIRECTORY = 0x02)
    // 2 byte - Directory Name Length
    // n byte - Directory Name
    // 2 byte - Child Count
    internal class DirectoryProtocolMessage : ProtocolMessage
    {
        internal const int TYPE = 0x2;
        private string containerName;
        private int childCount;

        public DirectoryProtocolMessage(MessageHandler handler, TraceListener traceListener) 
            : base(handler, traceListener)
        {            
            this.Children = new List<ProtocolMessage>();
        }

        private List<ProtocolMessage> Children { get; }

        public DirectoryProtocolMessage Read()
        {
            this.TraceListener.WriteLine("Reading Directory");
            DataStream buffer = Handler.Stream;
            this.containerName = buffer.ReadUTF8();
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "Container Name: {0}", containerName));
            this.childCount = buffer.ReadInt16();
            this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "Child Count [{0}]: {1}", containerName, childCount));
            for (int i = 0; i < childCount; i++)
            {
                this.TraceListener.WriteLine(string.Format(CultureInfo.InvariantCulture, "Reading Child [{0}]: {1}/{2}", containerName, (i+1), childCount));
                this.Children.Add(Handler.Read());
            }
            return this;
        }

        public override string ToString()
        {
            return "Type: DIRECTORY" + Environment.NewLine +
            "  Container: " + containerName + Environment.NewLine +
            "  ChildCount: " + childCount;            
        }
    }

    // Stop Message Type
    // 1 byte - Message Type (STOP = 0x03)
    internal class StopProtocolMessage : ProtocolMessage
    {
        internal const int TYPE = 0x3;
       
        public StopProtocolMessage(MessageHandler handler, TraceListener traceListener) 
            : base(handler, traceListener)
        {   
        }

        public StopProtocolMessage Read()
        {
            return this;
        }

        public override string ToString()
        {
            return "Type: STOP";
        }
    }

    internal class KeepAliveProtocolMessage : ProtocolMessage
    {
        internal const int TYPE = 0x4;

        public KeepAliveProtocolMessage(MessageHandler handler, TraceListener traceListener) 
            : base(handler, traceListener)
        {
        }

        public KeepAliveProtocolMessage Send()
        {
            Handler.Stream.WriteInt1(TYPE);
            Handler.Stream.Flush();
            return this;
        }
    }

    internal class FileAckProtocolMessage : ProtocolMessage
    {
        internal const int TYPE = 0x5;

        public FileAckProtocolMessage(MessageHandler handler, TraceListener traceListener) 
            : base(handler, traceListener)
        {
        }

        public FileAckProtocolMessage Send()
        {
            Handler.Stream.WriteInt1(TYPE);
            Handler.Stream.Flush();
            return this;
        }
    }
}

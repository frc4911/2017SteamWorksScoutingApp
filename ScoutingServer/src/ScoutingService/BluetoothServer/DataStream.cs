//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

namespace Org.USFirst.Frc.Team4911.ScoutingService.BluetoothServer
{
    /// <summary>
    /// Handles reading data from a stream written by Java in Network Byte Order.
    /// This also internally handles any buffering needed to ensure multi-byte reads
    /// only return when the full payload has been read.
    /// </summary>
    internal class DataStream : IDisposable
    { 
        private readonly Stream stream;
        private byte[] buffer = new byte[1024];
        private bool disposed;

        public DataStream(Stream stream)
        {
            this.stream = stream;
        }

        private byte[] ReadExactly(int count)
        {
            if (count >= this.buffer.Length)
            {
                this.buffer = new byte[count * 2];
            }

            int offset = 0;
            while (offset < count)
            {
                int read = this.stream.Read(this.buffer, offset, count - offset);
                Console.WriteLine("\tRead {0} @ {1}/{2}: [{3}]", read, (offset + read), count, string.Join(" ", this.buffer.Take(count).ToArray()));
                if (read == 0)
                {
                    throw new EndOfStreamException();
                }

                offset += read;
            }

            Debug.Assert(offset == count);
            return this.buffer;
        }

        public int ReadInt1()
        {
            return this.ReadExactly(1)[0];
        }

        public void WriteInt1(int v)
        {
            this.stream.WriteByte((byte) v);
        }

        public int ReadInt16()
        {
            byte[] bytes = this.ReadExactly(2);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(bytes, 0, 2);
            }

            int v = BitConverter.ToInt16(bytes, 0);
            return v;
        }

        public int ReadInt32()
        {
            byte[] bytes = this.ReadExactly(4);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(bytes, 0, 4);
            }

            int v = BitConverter.ToInt32(bytes, 0);
            return v;
        }

        public long ReadInt64()
        {
            byte[] bytes = this.ReadExactly(8);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(bytes, 0, 8);
            }

            long v = BitConverter.ToInt64(bytes, 0);
            return v;
        }

        public string ReadUTF8()
        {
            int len = this.ReadInt16();
            var s = Encoding.UTF8.GetString(this.ReadExactly(len), 0, len);
            return s;
        }

        public byte[] ReadBytes(int len)
        {
            byte[] sub = new byte[len];
            Array.Copy(this.ReadExactly(len), 0, sub, 0, len);
            return sub;
        }

        public void ReadBytes(Stream destinationStream, int len)
        {
            int offset = 0;
            while (offset < len)
            {
                int read = this.stream.Read(this.buffer, 0, Math.Min(this.buffer.Length, len - offset));
                destinationStream.Write(this.buffer, 0, read);
                if (read == 0)
                {
                    throw new EndOfStreamException();
                }

                offset += read;
            }

            Debug.Assert(offset == len);
        }

        public void Dispose()
        {
            this.Dispose(true);
            GC.SuppressFinalize(this);
        }

        internal void Flush()
        {
            this.stream.Flush();
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
                this.stream.Dispose();
            }

            // Free any unmanaged objects here. 
            this.disposed = true;
        }
    }
}

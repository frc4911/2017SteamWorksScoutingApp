//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Org.USFirst.Frc.Team4911.ScoutingService.Test
{
    [TestClass]
    public class UnitTest1
    {
        ////[TestMethod]
        ////public void FileMonitor_LoadFile()
        ////{
        ////    var fm = new FileMonitor(@"C:\cyberknights\qualitative.json", new DefaultTraceListener());
        ////    fm.LoadFile((object) @"C:\cyberknights\qualitative.json");
        ////}

        ////[TestMethod]
        ////public void FileMonitor_LoadFile2()
        ////{
        ////    var listener = new ConsoleTraceListener { Name = "ScoutingServiceTraceListener" };
        ////    Trace.Listeners.Add(listener);

        ////    var fm = new FileMonitor(@"C:\cyberknights\qualitative.json", listener);
        ////    Directory.CreateDirectory(@"C:\tmp\");
        ////    using (var file = File.Open(@"C:\tmp\qualitative.json", FileMode.OpenOrCreate, FileAccess.Write, FileShare.None))
        ////    {
        ////        ThreadPool.QueueUserWorkItem(fm.LoadFile, @"C:\tmp\qualitative.json");
        ////        Thread.Sleep(TimeSpan.FromSeconds(3.0));

        ////        var buffer = Encoding.UTF8.GetBytes("This is a test");
        ////        file.Write(buffer, 0, buffer.Length);                
        ////    }

        ////    Thread.Sleep(TimeSpan.FromSeconds(10.0));
        ////}
    }
}

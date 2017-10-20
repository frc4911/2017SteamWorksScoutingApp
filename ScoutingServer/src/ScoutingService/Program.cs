//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.ServiceProcess;
using System.Threading;

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    public class Program
    {
        static void Main(string[] args)
        {
            // Pass in arguments when debugging.
            ////if (args.Length > 0)
            ////{
                var scoutingService = new ScoutingService();
                scoutingService.Start();
                Thread.Sleep(Timeout.Infinite);
            ////scoutingService.Stop();
            ////    return;
            ////}

            // More than one user Service may run within the same process. To add
            // another service to this process, change the following line to
            // create a second service object. For example,
            //
            //   ServicesToRun = new ServiceBase[] {new Service1(), new MySecondUserService()};
            //
            ////var servicesToRun = new ServiceBase[] { new ScoutingService() };
            ////ServiceBase.Run(servicesToRun);
        }
    }
}

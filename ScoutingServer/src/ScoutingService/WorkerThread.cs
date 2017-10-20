//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

namespace Org.USFirst.Frc.Team4911.ScoutingService
{
    /// <summary>
    /// Abstract class for worker threads
    /// </summary>
    public abstract class WorkerThread
    {
        /// <summary>
        /// The Start method is used to start the worker thread.
        /// </summary>
		public abstract void Start();

        /// <summary>
        /// The Stop method is used to stop the worker thread.
        /// </summary>
		public abstract void Stop();
    }
}

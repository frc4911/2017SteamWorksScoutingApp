//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------
using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class Season
    {
        /// <summary>
        /// Gets or sets the total number of official frc events (district, regional, etc) scheduled for the given season
        /// </summary>
        [JsonProperty(PropertyName = "eventCount")]
        public int EventCount { get; set; }

        /// <summary>
        /// Gets or sets the name of the FRC game for the given season
        /// </summary>
        [JsonProperty(PropertyName = "gameName")]
        public int GameName { get; set; }

        /// <summary>
        /// Gets or sets the scheduled date and time of the FRC kickoff event
        /// </summary>
        [JsonProperty(PropertyName = "kickoff")]
        public DateTime Kickoff { get; set; }

        /// <summary>
        /// Gets or sets the lowest assigned rookie team number for the given season
        /// </summary>
        [JsonProperty(PropertyName = "rookieStart")]
        public int RookieStart { get; set; }

        /// <summary>
        /// Gets or sets the total number of registered frc teams competing in the season
        /// </summary>
        [JsonProperty(PropertyName = "teamCount")]
        public int TeamCount { get; set; }

        /// <summary>
        /// Gets or sets the array of FRC Championships 
        /// </summary>
        [JsonProperty(PropertyName = "FRCChampionships")]
        public IEnumerable<FRCChampionship> FRCChampionships { get; set; }

        public class FRCChampionship
        {
            /// <summary>
            /// Gets or sets the name of the Championship event
            /// </summary>
            [JsonProperty(PropertyName = "name")]
            public string Name { get; set; }

            /// <summary>
            /// Gets or sets the start date of the Championship event
            /// </summary>
            [JsonProperty(PropertyName = "startDate")]
            public DateTime StartDate { get; set; }

            /// <summary>
            /// Gets or sets the location of the Championship event
            /// </summary>
            [JsonProperty(PropertyName = "location")]
            public string Location { get; set; }
        }
    }
}

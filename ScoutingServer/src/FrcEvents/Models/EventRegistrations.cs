//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    /// <summary>
    /// Represents the registrations of teams in a particular season at particular events.
    /// </summary>
    public class RegistrationListing
    {
        /// <summary>
        /// Gets or sets the number of results returned
        /// </summary>
        [JsonProperty(PropertyName = "count")]
        public int Count { get; set; }

        /// <summary>
        /// Gets or sets the array of <see cref="T:RegistrationRecord"/>
        /// </summary>
        [JsonProperty(PropertyName = "registrations")]
        public IEnumerable<RegistrationRecord> Registrations { get; set; }
    }

    public class RegistrationRecord
    {
        /// <summary>
        /// Gets or sets the cteam number of the team
        /// </summary>
        [JsonProperty(PropertyName = "teamNumber")]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Gets or sets the array of strings representing the event codes where the team is registered.
        /// </summary>
        [JsonProperty(PropertyName = "events")]
        public IEnumerable<string> Events { get; set; }

    }
}

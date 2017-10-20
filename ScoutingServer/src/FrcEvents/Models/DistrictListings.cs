//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class District
    {
        /// <summary>
        /// Gets or sets district code (alpha)
        /// </summary>
        [JsonProperty(PropertyName = "code")]
        public string Code { get; set; }

        /// <summary>
        /// Gets or sets the name of the district
        /// </summary>
        [JsonProperty(PropertyName = "name")]
        public string Name { get; set; }
    }

    public class DistrictListings
    {
        /// <summary>
        /// Gets or sets number of districts
        /// </summary>
        [JsonProperty(PropertyName = "districtCount")]
        public int DistrictCount { get; set; }

        /// <summary>
        /// Gets or sets the current page of results
        /// </summary>
        [JsonProperty(PropertyName = "districts")]
        public IEnumerable<District> Districts { get; set; }
    }
}

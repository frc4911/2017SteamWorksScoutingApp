//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class AllianceListing
    {
        [JsonProperty(PropertyName = "count")]
        public int Count { get; set; }

        [JsonProperty(PropertyName = "Alliances")]
        public IEnumerable<Alliance> Alliances { get; set; }
    }

    public class Alliance
    {
        [JsonProperty(PropertyName = "name")]
        public string Name { get; set; }

        [JsonProperty(PropertyName = "number")]
        public int Number { get; set; }

        [JsonProperty(PropertyName = "captain")]
        public int Captain { get; set; }

        [JsonProperty(PropertyName = "round1")]
        public int Round1 { get; set; }

        [JsonProperty(PropertyName = "round2")]
        public int Round2 { get; set; }

        [JsonProperty(PropertyName = "round3", Required = Required.AllowNull)]
        public int? Round3 { get; set; }

        [JsonProperty(PropertyName = "backup", Required = Required.AllowNull)]
        public int? Backup { get; set; }

        [JsonProperty(PropertyName = "backupReplaced", Required = Required.AllowNull)]
        public int? BackupReplaced { get; set; }
    }
}
//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class RankingsListing
    {
        [JsonProperty(PropertyName = "Rankings")]
        public IEnumerable<TeamRanking> Rankings { get; set; }
    }

    /// <summary>
    /// Represents a team ranking detail from a particular event in a particular season.
    /// </summary>
    public class TeamRanking
    {
        /// <summary>
        /// Gets or sets the team number of the team in the record.
        /// </summary>
        [JsonProperty(PropertyName = "teamNumber")]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Gets or sets the current rank (1-n) of the team in the qualification rounds of the event, where n is the number of teams at the event.
        /// </summary>
        [JsonProperty(PropertyName = "rank")]
        public int Rank { get; set; }

        /// <summary>
        /// Gets or sets the 
        /// </summary>
        [JsonProperty(PropertyName = "matchesPlayed")]
        public int MatchesPlayed { get; set; }

        /// <summary>
        /// Gets or sets the total number of matches the team has won in the qualification rounds of the event.
        /// </summary>
        [JsonProperty(PropertyName = "wins")]
        public int Wins { get; set; }

        /// <summary>
        /// Gets or sets the 
        /// </summary>
        [JsonProperty(PropertyName = "losses")]
        public int Losses { get; set; }

        /// <summary>
        /// Gets or sets the total number of matches the team has tied in the qualification rounds of the event.
        /// </summary>
        [JsonProperty(PropertyName = "ties")]
        public int Ties { get; set; }

        /// <summary>
        /// Gets or sets the total number of times the team has been disqualified from a match in the qualification rounds of the event.
        /// </summary>
        [JsonProperty(PropertyName = "dq")]
        public int Dq { get; set; }

        /// <summary>
        /// Gets or sets the 
        /// </summary>
        [JsonProperty(PropertyName = "qualAverage")]
        public float QualAverage { get; set; }

        /// <summary>
        /// Gets or sets the first order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder1")]
        public float SortOrder1 { get; set; }
        /// <summary>
        /// Gets or sets the second order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder2")]
        public float SortOrder2 { get; set; }
        /// <summary>
        /// Gets or sets the third order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder3")]
        public float SortOrder3 { get; set; }
        /// <summary>
        /// Gets or sets the forth order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder4")]
        public float SortOrder4 { get; set; }

        /// <summary>
        /// Gets or sets the fifth order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder5")]
        public float SortOrder5 { get; set; }

        /// <summary>
        /// Gets or sets the sixth order of sort in accordance with the Game Manual
        /// </summary>
        [JsonProperty(PropertyName = "sortOrder6")]
        public float SortOrder6 { get; set; }
    }
}

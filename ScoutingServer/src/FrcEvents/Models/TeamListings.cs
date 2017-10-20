//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class Team
    {
        /// <summary>
        /// Gets or sets the code of associated district if team is a district team.
        /// </summary>
        [JsonProperty(PropertyName = "districtCode", Required = Required.AllowNull)]
        public string DistrictCode { get; set; }

        /// <summary>
        /// Gets or sets the city of team
        /// </summary>
        [JsonProperty(PropertyName = "city")]
        public string City { get; set; }

        /// <summary>
        /// Gets or sets the state (physical) of team
        /// </summary>
        [JsonProperty(PropertyName = "stateProv")]
        public string StateProv { get; set; }

        /// <summary>
        /// Gets or sets the country code of team
        /// </summary>
        [JsonProperty(PropertyName = "country")]
        public string Country { get; set; }

        /// <summary>
        /// Gets or sets the team number of the team
        /// </summary>
        [JsonProperty(PropertyName = "teamNumber")]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Gets or sets the official full length name of the team
        /// </summary>
        [JsonProperty(PropertyName = "nameFull")]
        public string FullName { get; set; }

        /// <summary>
        /// Gets or sets the shortened version of the team name
        /// </summary>
        [JsonProperty(PropertyName = "nameShort")]
        public string ShortName { get; set; }

        /// <summary>
        /// Gets or sets the team rookie year
        /// </summary>
        [JsonProperty(PropertyName = "rookieYear")]
        public int RookieYear { get; set; }

        /// <summary>
        /// Gets or sets the name of team's robot
        /// </summary>
        [JsonProperty(PropertyName = "robotName")]
        public string RobotName { get; set; }

        /// <summary>
        /// Gets or sets the (NEW 2017) name of the team's school or affilited group
        /// </summary>
        [JsonProperty(PropertyName = "schoolName")]
        public string SchoolName { get; set; }

        /// <summary>
        /// Gets or sets the (NEW 2017) event code of the team's home FIRST Championship event
        /// </summary>
        [JsonProperty(PropertyName = "homeCMP")]
        public string HomeChampionship { get; set; }
    }

    public class TeamListings
    {
        /// <summary>
        /// Gets or sets the current page of results
        /// </summary>
        [JsonProperty(PropertyName = "pageCurrent")]
        public int PageCurrent { get; set; }

        /// <summary>
        /// Gets or sets the total pages of results
        /// </summary>
        [JsonProperty(PropertyName = "pageTotal")]
        public int PageTotal { get; set; }

        /// <summary>
        /// Gets or sets the number of teams on page of results
        /// </summary>
        [JsonProperty(PropertyName = "teamCountPage")]
        public int TeamCountPage { get; set; }

        /// <summary>
        /// Gets or sets the total number of teams matching the request requirements
        /// </summary>
        [JsonProperty(PropertyName = "teamCountTotal")]
        public int TeamCountTotal { get; set; }

        /// <summary>
        /// Gets or sets the 
        /// </summary>
        [JsonProperty(PropertyName = "teams")]
        public IEnumerable<Team> Teams { get; set; }
    }
}

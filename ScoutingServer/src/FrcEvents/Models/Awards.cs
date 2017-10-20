//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{

    public class AwardListings
    {
        [JsonProperty(PropertyName = "Awards")]
        public IEnumerable<Award> Awards { get; set; }
    }


    public class Award
    {
        /// <summary>
        /// Gets or sets the identification number used by FIRST for the award.
        /// </summary>
        [JsonProperty(PropertyName = "awardId")]
        public string AwardId { get; set; }

        /// <summary>
        /// Gets or sets the team Id used by FIRST.
        /// </summary>
        /// <remarks>
        /// This return is for use by FIRST IT, and it not useful to public API users
        /// </remarks>
        [JsonProperty(PropertyName = "teamId")]
        public int TeamId { get; set; }

        /// <summary>
        /// Gets or sets the event Id used by FIRST.
        /// </summary>
        /// <remarks>
        /// This return is for use by FIRST IT, and it not useful to public API users
        /// </remarks>
        [JsonProperty(PropertyName = "eventId")]
        public int EventId { get; set; }

        /// <summary>
        /// Gets or sets the event division Id used by FIRST.
        /// </summary>
        /// <remarks>
        /// This return is for use by FIRST IT, and it not useful to public API users
        /// </remarks>
        [JsonProperty(PropertyName = "eventDivisionId", Required = Required.AllowNull)]
        public int? EventDivisionId { get; set; }

        /// <summary>
        /// Gets or sets the name of award presented.
        /// </summary>
        [JsonProperty(PropertyName = "name")]
        public string Name{ get; set; }

        /// <summary>
        /// Gets or sets the team number of winning team if the award is presented to a team.
        /// </summary>
        [JsonProperty(PropertyName = "teamNumber")]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Gets or sets the full official team name of winning team if the award is presented to a team.
        /// </summary>
        [JsonProperty(PropertyName = "fullTeamName")]
        public string FullTeamName { get; set; }

        /// <summary>
        /// Gets or sets the series value for an award.  
        /// If award is presented multiple times (such as Dean's List Finalist) the series value 
        /// will increment with each assignment. If you have filtered your results 
        /// (such as by using teamNumber) the series value may not be sequential.
        /// </summary>
        [JsonProperty(PropertyName = "series")]
        public int Series { get; set; }

        /// <summary>
        /// Gets or sets the name of person if if award is presented to an individual.
        /// </summary>
        [JsonProperty(PropertyName = "person", Required = Required.AllowNull)]
        public int? Person { get; set; }
    }



    public class AwardListingListings
    {
        /// <summary>
        /// Gets or sets identification number used by FIRST for the award.
        /// </summary>
        [JsonProperty(PropertyName = "awards")]
        public IEnumerable<AwardListing> AwardListings { get; set; }
    }

/// <summary>
/// The award listings API returns a listing of the various awards that 
/// can be distributed in the requested season.
/// <para>
/// This is especially useful in order to avoid having to use the name field 
/// of the event awards API to know which award was won. Instead the awardId 
/// field can be matched between the two APIs.
/// </para>
/// </summary>
public class AwardListing
    {
        /// <summary>
        /// Gets or sets identification number used by FIRST for the award.
        /// </summary>
        [JsonProperty(PropertyName = "awardId")]
        public int AwardId { get; set; }

        /// <summary>
        /// Gets or sets the type of event at which the award is presented 
        /// (usually either None, Regional, DistrictEvent, DistrictChampionship, ChampionshipSubdivision, ChampionshipDivision or Championship.
        /// </summary>
        [JsonProperty(PropertyName = "eventType")]
        public string EventType { get; set; }

        /// <summary>
        /// Gets or sets the name of the award.
        /// </summary>
        [JsonProperty(PropertyName = "description")]
        public string Description { get; set; }

        /// <summary>
        /// Gets or sets whether the award is inteded awarded to a person or a team.
        /// This does not mean a teamNumber wouldn't be specified in the event 
        /// awards API, as the winner could also be affiliated with a team.
        /// </summary>
        [JsonProperty(PropertyName = "forPerson")]
        public bool ForPerson { get; set; }
    }
}

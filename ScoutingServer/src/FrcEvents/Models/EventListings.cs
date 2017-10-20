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
    public class EventListings
    {
        /// <summary>
        /// Gets or sets the total number of events included in the response
        /// </summary>
        [JsonProperty(PropertyName = "eventCount")]
        public int EventCount { get; set; }

        /// <summary>
        /// Gets or sets the collection of <see cref="T:Events"/>
        /// </summary>
        [JsonProperty(PropertyName = "Events")]
        public IEnumerable<Event> Events { get; set; }
    }

    public class Event
    {
        /// <summary>
        /// Gets or sets the alphanumeric event code unique to the event, and used to make additional api calls regarding the specific event
        /// </summary>
        [JsonProperty(PropertyName = "code")]
        public string Code { get; set; }

        /// <summary>
        /// Gets or sets the alphanumeric event code of the parent event, if the event is the child of another
        /// </summary>
        [JsonProperty(PropertyName = "divisionCode", Required = Required.AllowNull)]
        public string DivisionCode { get; set; }

        /// <summary>
        /// Gets or sets the official name of the event
        /// </summary>
        [JsonProperty(PropertyName = "name")]
        public string Name { get; set; }

        /// <summary>
        /// Gets or sets the type of event (Regional/DistrictEvent/DistrictChampionship/ChampionshipSubdivision/ChampionshipDivision/Championship/OffSeason/OffSeasonWithAzureSync) type of event
        /// </summary>
        [JsonProperty(PropertyName = "type")]
        public string EventType { get; set; }

        /// <summary>
        /// Gets or sets the code of the associated district if the even is district event or district championship
        /// </summary>
        [JsonProperty(PropertyName = "districtCode", Required = Required.AllowNull)]
        public string DistrictCode { get; set; }

        /// <summary>
        /// Gets or sets the name of the venue
        /// </summary>
        [JsonProperty(PropertyName = "venue")]
        public string Venue { get; set; }

        /// <summary>
        /// Gets or sets the city of event
        /// </summary>
        [JsonProperty(PropertyName = "city")]
        public string City { get; set; }

        /// <summary>
        /// Gets or sets the state of event
        /// </summary>
        [JsonProperty(PropertyName = "stateProv")]
        public string StateProv { get; set; }

        /// <summary>
        /// Gets or sets the country code of event
        /// </summary>
        [JsonProperty(PropertyName = "country")]
        public string Country { get; set; }

        /// <summary>
        /// Gets or sets the scheduled start date of the event
        /// </summary>
        [JsonProperty(PropertyName = "dateStart")]
        public DateTime DateStart { get; set; }

        /// <summary>
        /// Gets or sets the scheduled end date of the event
        /// </summary>
        [JsonProperty(PropertyName = "dateEnd")]
        public DateTime DateEnd { get; set; }

        /// <summary>
        /// Gets or sets the (NEW 2017) official website of event
        /// </summary>
        [JsonProperty(PropertyName = "website", Required = Required.AllowNull)]
        public string Website { get; set; }

        /// <summary>
        /// Gets or sets the (NEW 2017) address of the venue
        /// </summary>
        [JsonProperty(PropertyName = "address", Required = Required.AllowNull)]
        public string Address { get; set; }
    }
}

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
    public class ScheduleListing
    {
        /// <summary>
        /// Gets or sets collection of <see cref="T:ScheduledMatch"/>.
        /// /// </summary>
        [JsonProperty(PropertyName = "Schedule")]
        public IEnumerable<ScheduledMatch> Schedule { get; set; }
    }

    public class ScheduledMatch
    {
        /// <summary>
        /// Gets or sets the human "readable" format of the match (should not be used as a "filter" as it is subject to change and meant for "display only".)
        /// </summary>
        [JsonProperty(PropertyName = "description")]
        public string Description { get; set; }

        /// <summary>
        /// Gets or sets the field scheduled to host the match (at most events this will always be "primary", but may be "secondary" if the event has multiple competition fields)
        /// </summary>
        [JsonProperty(PropertyName = "field")]
        public string Field { get; set; }

        /// <summary>
        /// Gets or sets the tournament level of the match in the record ("Qualification" or "Playoff")
        /// </summary>
        [JsonProperty(PropertyName = "tournamentLevel")]
        public string TournamentLevel { get; set; }

        /// <summary>
        /// Gets or sets the match number of the match in the record
        /// </summary>
        [JsonProperty(PropertyName = "matchNumber")]
        public int MatchNumber { get; set; }

        /// <summary>
        /// Gets or sets the scheduled start date/time of the match in the record
        /// </summary>
        [JsonProperty(PropertyName = "startTime")]
        public DateTime StartTime { get; set; }

        /// <summary>
        /// Gets or sets the teams participating in the match
        /// </summary>
        [JsonProperty(PropertyName = "Teams")]
        public IEnumerable<ScheduledTeam> Teams { get; set; }
    }

    public class ScheduledTeam
    {
        /// <summary>
        /// Gets or sets the team number of team in the record on an alliance.
        /// </summary>
        [JsonProperty(PropertyName = "teamNumber")]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Gets or sets staion used by team in the match.
        /// </summary>
        /// <remarks>
        /// format is red or blue + station number (1/2/3) of the team on an alliance
        /// </remarks>
        [JsonProperty(PropertyName = "station")]
        public string Station { get; set; }

        /// <summary>
        /// Gets or sets whether or not the team will be playing as a surrogate in the match. 
        /// </summary>
        /// <remarks>
        /// For details about the significance of surrogate rounds, please see the official FRC Game Manual.
        /// </remarks>
        [JsonProperty(PropertyName = "surrogate")]
        public bool Surrogate { get; set; }
    }

    public class HybridScedule
    {
        /// <summary>
        /// Gets or sets the actual time that the match started.
        /// </summary>
        [JsonProperty(PropertyName = "actualStartTime")]
        public DateTime ActualStartTime { get; set; }

        /// <summary>
        /// Gets or sets the the human "readable" format of the match (should not be used as a "filter" as it is subject to change and meant for "display only").
        /// </summary>
        [JsonProperty(PropertyName = "description")]
        public string Description { get; set; }

        /// <summary>
        /// Gets or sets the tournament level of the result ("Qualification" or "Playoff")
        /// </summary>
        [JsonProperty(PropertyName = "tournamentLevel")]
        public string TournamentLevel { get; set; }

        /// <summary>
        /// Gets or sets match number of the record.
        /// </summary>
        [JsonProperty(PropertyName = "matchNumber")]
        public int MatchNumber { get; set; }

        /// <summary>
        /// Gets or sets the time that the final results were shown to the audience.
        /// </summary>
        [JsonProperty(PropertyName = "postResultTime")]
        public DateTime PostResultTime { get; set; }

        /// <summary>
        /// Gets or sets the final score for the red alliance
        /// </summary>
        [JsonProperty(PropertyName = "scoreRedFinal")]
        public int ScoreRedFinal { get; set; }

        /// <summary>
        /// Gets or sets the final foul points for the red alliance (subtracts from red final score)
        /// </summary>
        [JsonProperty(PropertyName = "scoreRedFoul")]
        public int ScoreRedFoul { get; set; }

        /// <summary>
        /// Gets or sets the final auto score for the red alliance
        /// </summary>
        [JsonProperty(PropertyName = "scoreRedAuto")]
        public int ScoreRedAuto { get; set; }

        /// <summary>
        /// Gets or sets the final score for the blue alliance
        /// </summary>
        [JsonProperty(PropertyName = "scoreBlueFinal")]
        public int ScoreBlueFinal { get; set; }

        /// <summary>
        /// Gets or sets the final foul points for the blue alliance (subtracts from blue final score)
        /// </summary>
        [JsonProperty(PropertyName = "scoreBlueFoul")]
        public int ScoreBlueFoul { get; set; }

        /// <summary>
        /// Gets or sets the final auto score for the blue alliance
        /// </summary>
        [JsonProperty(PropertyName = "scoreBlueAuto")]
        public int ScoreBlueAuto { get; set; }

        /// <summary>
        /// Gets or sets the teams in the match.
        /// </summary>
        [JsonProperty(PropertyName = "teams")]
        public IEnumerable<ScheduledTeam> Teams { get; set; }
    }
}

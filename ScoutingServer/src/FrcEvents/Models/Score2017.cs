//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class MatchScorce2017Listings
    {
        /// <summary>
        /// Gets or sets the collection of alliance scores.
        /// </summary>
        [JsonProperty(PropertyName = "MatchScores")]
        public IEnumerable<MatchScores2017> MatchScores { get; set; }
    }

    public class MatchScores2017
    {
        /// <summary>
        /// Gets or sets the type of match
        /// </summary>        
        [JsonProperty(PropertyName = "matchLevel")]
        public string MatchLevel { get; set; }

        /// <summary>
        /// Gets or sets the match number within the tournament level
        /// </summary>        
        [JsonProperty(PropertyName = "matchNumber")]
        public int MatchNumber { get; set; }

        /// <summary>
        /// Gets or sets the collection of alliance scores.
        /// </summary>
        [JsonProperty(PropertyName = "Alliances")]
        public IEnumerable<Score2017> Alliances { get; set; }
    }
    
    /// <summary>
    /// Represents the match result.
    /// </summary>
    public class Score2017
    {
        /// <summary>
        /// Gets or sets the the alliance name
        /// </summary>        
        [JsonProperty(PropertyName = "alliance")]
        public string Alliance { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 1 (station 1) (Unknown, None, Mobility)
        /// </summary>        
        [JsonProperty(PropertyName = "robot1Auto")]
        public string Robot1Auto { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 2 (station 2) (Unknown, None, Mobility)
        /// </summary>        
        [JsonProperty(PropertyName = "robot2Auto")]
        public string Robot2Auto { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 3 (station 3) (Unknown, None, Mobility)
        /// </summary>        
        [JsonProperty(PropertyName = "robot3Auto")]
        public string Robot3Auto { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Fuel scored
        /// </summary>        
        [JsonProperty(PropertyName = "autoFuelLow")]
        public int AutoFuelLow { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Fuel scored
        /// </summary>        
        [JsonProperty(PropertyName = "autoFuelHigh")]
        public int AutoFuelHigh { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 1 was engaged in Auto
        /// </summary>        
        [JsonProperty(PropertyName = "rotor1Auto")]
        public bool Rotor1Auto { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 2 was engaged in Auto
        /// </summary>        
        [JsonProperty(PropertyName = "rotor2Auto")]
        public bool Rotor2Auto { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 1 was engaged
        /// </summary>        
        [JsonProperty(PropertyName = "rotor1Engaged")]
        public bool Rotor1Engaged { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 2 was engaged
        /// </summary>        
        [JsonProperty(PropertyName = "rotor2Engaged")]
        public bool Rotor2Engaged { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 3 was engaged
        /// </summary>        
        [JsonProperty(PropertyName = "rotor3Engaged")]
        public bool Rotor3Engaged { get; set; }

        /// <summary>
        /// Gets or sets whether or not the Rotor 4 was engaged
        /// </summary>        
        [JsonProperty(PropertyName = "rotor4Engaged")]
        public bool Rotor4Engaged { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Fuel scored
        /// </summary>        
        [JsonProperty(PropertyName = "teleopFuelLow")]
        public int TeleopFuelLow { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Fuel scored
        /// </summary>        
        [JsonProperty(PropertyName = "teleopFuelHigh")]
        public int TeleopFuelHigh { get; set; }

        /// <summary>
        /// Gets or sets the state of the touchpad nearest the scoring table (None or ReadyForTakeoff)
        /// </summary>        
        [JsonProperty(PropertyName = "touchpadNear")]
        public string TouchpadNear { get; set; }

        /// <summary>
        /// Gets or sets the state of the touchpad nearest the alliance station (None or ReadyForTakeoff)
        /// </summary>        
        [JsonProperty(PropertyName = "touchpadMiddle")]
        public string TouchpadMiddle { get; set; }

        /// <summary>
        /// Gets or sets the state of the touchpad furthest from the scoring table (None or ReadyForTakeoff)
        /// </summary>        
        [JsonProperty(PropertyName = "touchpadFar")]
        public string TouchpadFar { get; set; }

        /// <summary>
        /// Gets or sets whether or not ranking point was achieved (Qual Only)
        /// </summary>        
        [JsonProperty(PropertyName = "kPaRankingPointAchieved")]
        public bool KiloPaRankingPointAchieved { get; set; }

        /// <summary>
        /// Gets or sets whether or not ranking point was achieved (Qual Only)
        /// </summary>        
        [JsonProperty(PropertyName = "rotorRankingPointAchieved")]
        public bool RotorRankingPointAchieved { get; set; }

        /// <summary>
        /// Gets or sets the quantity of fouls assigned to the alliance
        /// </summary>        
        [JsonProperty(PropertyName = "foulCount ")]
        public int FoulCount { get; set; }

        /// <summary>
        /// Gets or sets the quantity of tech fouls assigned to the alliance
        /// </summary>        
        [JsonProperty(PropertyName = "techFoulCount")]
        public int TechFoulCount { get; set; }

        /// <summary>
        /// Gets or sets the total points earned in Auto
        /// </summary>        
        [JsonProperty(PropertyName = "autoPoints")]
        public int AutoPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantitiy of points earned in Auto for Mobility
        /// </summary>        
        [JsonProperty(PropertyName = "autoMobilityPoints")]
        public int AutoMobilityPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Auto for Engaging Rotors
        /// </summary>        
        [JsonProperty(PropertyName = "autoRotorPoints")]
        public int AutoRotorPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Auto for Fuel (kPa)
        /// </summary>        
        [JsonProperty(PropertyName = "autoFuelPoints")]
        public int AutoFuelPoints { get; set; }

        /// <summary>
        /// Gets or sets the total points earned in Teleop
        /// </summary>        
        [JsonProperty(PropertyName = "teleopPoints")]
        public int TeleopPoints { get; set; }
        
        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Engaging Rotors
        /// </summary>        
        [JsonProperty(PropertyName = "teleopRotorPoints")]
        public int TeleopRotorPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop  for Fuel (kPa)
        /// </summary>        
        [JsonProperty(PropertyName = "teleopFuelPoints")]
        public int TeleopFuelPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Ready for Takeoff
        /// </summary>        
        [JsonProperty(PropertyName = "teleopTakeoffPoints")]
        public int TeleopTakeoffPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for reaching the pressure threshold (Playoffs Only)
        /// </summary>        
        [JsonProperty(PropertyName = "kPaBonusPoints")]
        public int KilokPaBonusPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Engaging all Rotors (Playoffs Only)
        /// </summary>        
        [JsonProperty(PropertyName = "rotorBonusPoints")]
        public int RotorBonusPoints { get; set; }

        /// <summary>
        /// Gets or sets the manual adjustment points entered
        /// </summary>        
        [JsonProperty(PropertyName = "adjustPoints")]
        public int AdjustPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Fouls committed by the opposite alliance
        /// </summary>        
        [JsonProperty(PropertyName = "foulPoints")]
        public int FoulPoints { get; set; }

        /// <summary>
        /// Gets or sets the total points earned by the alliance in the Match
        /// </summary>        
        [JsonProperty(PropertyName = "totalPoints")]
        public int TotalPoints { get; set; }
    }
}

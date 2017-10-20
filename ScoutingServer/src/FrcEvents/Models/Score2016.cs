//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents.Models
{
    public class MatchScorce2016Listings
    {
        /// <summary>
        /// Gets or sets the collection of alliance scores.
        /// </summary>
        [JsonProperty(PropertyName = "MatchScores")]
        public IEnumerable<MatchScores2016> MatchScores { get; set; }
    }

    public class MatchScores2016
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
        /// Gets or sets the group from which the audience will be selecting their defense
        /// </summary>        
        [JsonProperty(PropertyName = "AudienceGroup ")]
        public string AudienceGroup { get; set; }

        /// <summary>
        /// Gets or sets the collection of alliance scores.
        /// </summary>
        [JsonProperty(PropertyName = "Alliances")]
        public IEnumerable<Score2016> Alliances { get; set; }
    }


    /// <summary>
    /// Represents the match result.
    /// </summary>
    public class Score2016
    {
        /// <summary>
        /// Gets or sets the the alliance name
        /// </summary>        
        [JsonProperty(PropertyName = "alliance")]
        public string Alliance { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 1 (station 1) (Unknown, None, Reached, Crossed)
        /// </summary>        
        [JsonProperty(PropertyName = "robot1Auto")]
        public string Robot1Auto { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 3 (station 3)
        /// </summary>        
        [JsonProperty(PropertyName = "robot2Auto")]
        public string Robot2Auto { get; set; }

        /// <summary>
        /// Gets or sets the Auto action of robot 2 (station 2)
        /// </summary>        
        [JsonProperty(PropertyName = "robot3Auto")]
        public string Robot3Auto { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Boulders scored
        /// </summary>        
        [JsonProperty(PropertyName = "autoBouldersLow")]
        public int AutoBouldersLow { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Boulders scored
        /// </summary>        
        [JsonProperty(PropertyName = "autoBouldersHigh")]
        public int AutoBouldersHigh { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Boulders scored
        /// </summary>        
        [JsonProperty(PropertyName = "teleopBouldersLow")]
        public int TeleopBouldersLow { get; set; }

        /// <summary>
        /// Gets or sets the quantity of Boulders scored
        /// </summary>        
        [JsonProperty(PropertyName = "teleopBouldersHigh")]
        public int TeleopBouldersHigh { get; set; }

        /// <summary>
        /// Gets or sets the final robot positions on the Tower face (Unknown, None, Challenged, Scaled, Both)
        /// </summary>        
        [JsonProperty(PropertyName = "towerFaceA")]
        public string TowerFaceA { get; set; }

        /// <summary>
        /// Gets or sets the final robot positions on the Tower face (Unknown, None, Challenged, Scaled, Both)
        /// </summary>        
        [JsonProperty(PropertyName = "towerFaceB")]
        public string TowerFaceB { get; set; }

        /// <summary>
        /// Gets or sets the final robot positions on the Tower face (Unknown, None, Challenged, Scaled, Both)
        /// </summary>        
        [JsonProperty(PropertyName = "towerFaceC")]
        public string TowerFaceC { get; set; }

        /// <summary>
        /// Gets or sets the final strength of the Tower at the end of the Match (that Alliance's tower)
        /// </summary>        
        [JsonProperty(PropertyName = "towerEndStrength")]
        public int TowerEndStrength { get; set; }

        /// <summary>
        /// Gets or sets the whether or not the tower was captured (true/false)
        /// </summary>        
        [JsonProperty(PropertyName = "teleopTowerCaptured")]
        public bool TeleopTowerCaptured { get; set; }

        /// <summary>
        /// Gets or sets the whether or not the defenses were breached (true/false
        /// </summary>        
        [JsonProperty(PropertyName = "teleopDefensesBreached")]
        public bool TeleopDefensesBreached { get; set; }

        /// <summary>
        /// Gets or sets the quantity (up to 2) of crossings of this defense
        /// </summary>        
        [JsonProperty(PropertyName = "position1crossings")]
        public int Position1Crossings { get; set; }

        /// <summary>
        /// Gets or sets the defense being crossed in position 2
        /// </summary>        
        [JsonProperty(PropertyName = "position2")]
        public string Position2 { get; set; }

        /// <summary>
        /// Gets or sets the quantity (up to 2) of crossings of this defense
        /// </summary>        
        [JsonProperty(PropertyName = "position2crossings")]
        public int Position2Crossings { get; set; }

        /// <summary>
        /// Gets or sets the defense being crossed in position 3
        /// </summary>        
        [JsonProperty(PropertyName = "position3")]
        public string Position3 { get; set; }

        /// <summary>
        /// Gets or sets the quantity (up to 2) of crossings of this defense
        /// </summary>        
        [JsonProperty(PropertyName = "position3crossings")]
        public int Position3Crossings { get; set; }

        /// <summary>
        /// Gets or sets the defense being crossed in position 4
        /// </summary>        
        [JsonProperty(PropertyName = "position4")]
        public string Position4 { get; set; }

        /// <summary>
        /// Gets or sets the quantity (up to 2) of crossings of this defense
        /// </summary>        
        [JsonProperty(PropertyName = "position4crossings")]
        public int Position4Crossings { get; set; }

        /// <summary>
        /// Gets or sets the defense being crossed in position 5
        /// </summary>        
        [JsonProperty(PropertyName = "position5")]
        public string Position5 { get; set; }

        /// <summary>
        /// Gets or sets the quantity (up to 2) of crossings of this defense
        /// </summary>        
        [JsonProperty(PropertyName = "position5crossings")]
        public string Position5Crossings { get; set; }

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
        /// Gets or sets the quantitiy of points earned in Auto for Reaching Defenses
        /// </summary>        
        [JsonProperty(PropertyName = "autoReachPoints")]
        public int AutoReachPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Auto for Crossing Defenses
        /// </summary>        
        [JsonProperty(PropertyName = "autoCrossingPoints")]
        public int AutoCrossingPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Auto for Boulders
        /// </summary>        
        [JsonProperty(PropertyName = "autoBoulderPoints")]
        public int AutoBoulderPoints { get; set; }

        /// <summary>
        /// Gets or sets the total points earned in Teleop
        /// </summary>        
        [JsonProperty(PropertyName = "teleopPoints")]
        public int TeleopPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Crossing Defenses
        /// </summary>        
        [JsonProperty(PropertyName = "teleopCrossingPoints")]
        public int TeleopCrossingPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Boulders
        /// </summary>        
        [JsonProperty(PropertyName = "teleopBoulderPoints")]
        public int TeleopBoulderPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Challenging the Tower
        /// </summary>        
        [JsonProperty(PropertyName = "teleopChallengePoints")]
        public int  TeleopChallengePoints { get; set; }

        /// <summary>
        /// Gets or sets the Quantity of points earned in Teleop for Scaling the Tower
        /// </summary>        
        [JsonProperty(PropertyName = "teleopScalePoints")]
        public int TeleopScalePoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Breaching the Defenses (Playoffs Only)
        /// </summary>        
        [JsonProperty(PropertyName = "breachPoints")]
        public int BreachPoints { get; set; }

        /// <summary>
        /// Gets or sets the quantity of points earned in Teleop for Capturing the Tower (Playoffs Only)
        /// </summary>        
        [JsonProperty(PropertyName = "capturePoints")]
        public int CapturePoints { get; set; }

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

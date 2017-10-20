using System;
using System.Collections.Generic;
using System.ComponentModel;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.ScoutingService.ScoutingDataModel
{
    public enum GearResult
    {
        None = 0,
        Success,
        Failed,
        RanOutOfTime,
        Abandoned
    }

    public enum GearPegPosition
    {
        None,
        G1,
        G2,
        G3
    }

    public enum ShotSpeed
    {
        Slow,  // 0-1 per second
        Average, // 2-3 per second
        Quick, // 4-5 per second
        Fast // 5+ or more per second
    }

    public enum ShotAccuracy
    {
        Failed,  // missed all shots
        Poor, // 0%-39% %
        Average, // 40%-79%
        Great // 80%+
    }

    public enum FuelAmount
    {
        Small, // 5-20
        Medium, // 20-35
        Large  // 35-50
    }

    /// <summary>
    /// Some predefined grid locations
    /// </summary>
    public enum ShotLocation
    {
        Close,
        Medium,
        Far
    }

    public enum ShotMode
    {
        High,
        Low
    }

    public enum TouchPadPosition
    {
        None,
        T1,
        T2,
        T3
    }

    public enum DriveStation
    {
        Red1,
        Red2,
        Red3,
        Blue1,
        Blue2,
        Blue3
    }

    public enum Speed
    {
        Fast,
        Medium,
        Slow
    }

    public enum Ability
    {
        Poor,
        Fair,
        Good,
    }

    public enum BallCapacity
    {
        Ten,
        Thirty,
        Fifty
    }

    public enum DriverTrainType
    {
        WestCoast,
        Meccanum,
        Butterfly
    }

    public enum HopperPosition
    {
        None,
        One,
        Two,
        Three,
        Four,
        Five
    }


    public class HopperAttempt
    {
        [JsonProperty(Required = Required.Always)]
        public bool Activated { get; set; }

        public string HopperLocation { get; set; }
    }

    public class GearAttempt
    {
        [JsonProperty(Required = Required.Always)]
        public string GearResult { get; set; }
        
        [JsonProperty(Required = Required.Always)]
        public string GearPegPosition { get; set; }
    }

    public class GearAttemptTeleop : GearAttempt
    {
        public GearAttemptTeleop()
        {
            this.TimeStamp = DateTime.Now;
        }

        [JsonProperty(Required = Required.AllowNull)]
        public bool WasDefended { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public DateTime TimeStamp { get; set; }
    }

    public class ShotAttempt
    {
        [JsonProperty(Required = Required.Always)]
        public int ShotsMade { get; set; }

        [JsonProperty(Required = Required.Always)]
        public int ShotsMissed  { get; set; }

        [JsonProperty(Required = Required.Always)]
        public string ShotLocation { get; set; }

        [JsonProperty(Required = Required.Always)]
        public string ShotMode { get; set; }

        /// <summary>
        /// Duration in seconds
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public int ShotDuration { get; set; }
    }

    public class ShotAttemptTeleop : ShotAttempt
    {
        public ShotAttemptTeleop()
        {
            this.TimeStamp = DateTime.Now;
        }

        [JsonProperty(Required = Required.AllowNull)]
        public bool WasDefended { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public DateTime TimeStamp { get; set; }
    }
    
    public class PreGame
    {
        /// <summary>
        /// Does robot start with a gear?
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public bool HasGear { get; set; }

        /// <summary>
        /// Does robot start with fuel?
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public bool HasFuel { get; set; }

        /// <summary>
        /// Does team provide pilot?
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public bool HasPilot { get; set; }

        /// <summary>
        /// Does robot use it's own rope?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public bool UsesOwnRope { get; set; }

        /// <summary>
        /// What touch pad position is rope place at?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public string RopeTouchPadPosition { get; set; }

        /// <summary>
        /// What position does robot start from?
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string RobotPosition { get; set; }
        
    }

    public class AutonomousPeriod
    {
        /// <summary>
        /// Does robot cross baseline by t=0 for 5 points?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]   
        public bool AutoMobilityPoints { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList<GearAttempt> GearAttempts { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList<ShotAttempt> ShotAttempts { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList<HopperAttempt> HopperAttempts { get; set; }
    }

    public class TeleopPeriod
    {
        /// <summary>
        /// Did robot play defense during teleop period?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public bool PlayedDefense { get; set; }

        /// <summary>
        /// Count of attempts to load gears at airship.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public int GearAttemptCount { get; set; }

        /// <summary>
        /// Count of attempts to shot balls.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public int ShotAttemptCount { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList< GearAttemptTeleop> GearAttempts { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList<ShotAttemptTeleop> ShotAttempts { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public IList<HopperAttempt> HopperAttempts { get; set; }
    }

    public class EndGame
    {
        /// <summary>
        /// Did robot attempt to climb?
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public bool Attempted { get; set; }

        /// <summary>
        /// Did robot succeed in climbing?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public bool Succeeded { get; set; }

        /// <summary>
        /// How long did it take to climb?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public int TimeInSeconds { get; set; }

        /// <summary>
        /// What touch pad position did they climb at?
        /// </summary>
        [JsonProperty(Required = Required.AllowNull)]
        public string TouchPadPosition { get; set; }
    }

    public class Qualitative
    {
        [JsonProperty(Required = Required.AllowNull)]
        public bool DeadBot { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool NoShow { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool Veers { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool HitsPartner { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool Stuck { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool WrongWay { get; set; }

        [DefaultValue(0)]
        [JsonProperty(Required = Required.AllowNull)]
        public int FoulCount { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool AutoTargeting { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string BallCapacity { get; set; }

        #region DrivingSkills

        [JsonProperty(Required = Required.AllowNull)]
        public string DrivingDriverTrainType { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string DrivingSpeed { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool DrivingIsReckless { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool DrivingIsAimless { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool DrivingDrivesIntoPartner { get; set; }
        #endregion

        #region BallPickupSkills
        [JsonProperty(Required = Required.AllowNull)]
        public string BallPickupSpeed { get; set; }

        // Todo: Is this really needed?

        [JsonProperty(Required = Required.AllowNull)]
        public bool BallPickupCanPickUpOffGroud { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string BallPickupFeederAbility { get; set; }
        #endregion

        #region GearPlacementAbility
        [JsonProperty(Required = Required.AllowNull)]
        public string GearPlacementSpeed { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string GearPlacementAccuracy { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool GearPlacementCanPickupOffFloor { get; set; }
        #endregion

        #region DefenseAbility
        [JsonProperty(Required = Required.AllowNull)]
        public string DefenseAsTargetedBumper { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string DefenseAsCrasher { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string DefenseSpeed { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool DefenseIsRisky { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool DefenseGoesInForbiddenAreas { get; set; }
        #endregion

        #region Smartness
        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsPurposeful { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsConfused { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsAimless { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsHelpful { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessWorksAgainstAlliance { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsDangerous { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessIsReckless { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool SmartnessGetsInWayOfPartners { get; set; }
        #endregion

        #region Pilot
        [JsonProperty(Required = Required.AllowNull)]
        public string PilotGearRetrievalSpeed { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public string PilotGearPlacementSpeed { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool PilotFailedToTurnCrank { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool PilotDropsRopeInTimelyManner { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool PilotIsHelpfulWithSpottingToDrivers { get; set; }
        #endregion

        #region HumanPlayer
        [JsonProperty(Required = Required.AllowNull)]
        public int HumanPlayerFromTeam { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool HumanPlayerMadeMistakeDeliveringGear { get; set; }

        [JsonProperty(Required = Required.AllowNull)]
        public bool HumanPlayerMadeMistakeDeliveringFuel { get; set; }
        #endregion

        [JsonProperty(Required = Required.AllowNull)]
        public string Notes { get; set; }
    }

    public class MatchData
    {
        /// <summary>
        /// The pre game data on how a team sets up for a match.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public PreGame PreGame { get; set; }

        /// <summary>
        /// The autonomous period data, the first 15s of the match
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public AutonomousPeriod AutonomousPeriod { get; set; }

        /// <summary>
        /// the teleop period data, from 15s to about 30s before match is over
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public TeleopPeriod TeleopPeriod { get; set; }

        /// <summary>
        /// The end game period is typically the last 30s 
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public EndGame EndGame { get; set; }
    }

    public class ScoutingData
    {
        /// <summary>
        /// The FRC event code for the event.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string EventCode { get; set; }

        /// <summary>
        /// The number of the match being played.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public int MatchNumber { get; set; }

        /// <summary>
        /// The level: "qual" or "playoff"
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string TournamentLevel { get; set; }

        /// <summary>
        /// The drive station the scout is observing:  "red1", "red2", "red3", "blue1", "blue2", or "blue3"
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string Station { get; set; }

        /// <summary>
        /// The number of the team being scouted.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public int TeamNumber { get; set; }

        /// <summary>
        /// Unique device identofoer for the device produceing the data.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string DeviceId{ get; set; }
        /// <summary>
        /// Name of scout
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string ScoutName { get; set; }

        /// <summary>
        /// NAme of team scout is from.
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public string ScoutingTeamName { get; set; }

        /// <summary>
        /// Timestamp of when the data was first created.
        /// </summary>
        public DateTime TimeStamp { get; set; }

        /// <summary>
        /// The match data 
        /// </summary>
        [JsonProperty(Required = Required.Always)]
        public MatchData MatchData { get; set; }

        /// <summary>
        ///  The qualitative data if any.
        /// </summary>
        public Qualitative Qualitative { get; set; }
    }
}
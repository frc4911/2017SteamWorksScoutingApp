//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Org.USFirst.Frc.Team4911.ScoutingService.DatabaseAccess
{
    using System;
    using System.Collections.Generic;
    
    public partial class ScoutingDataQualitative
    {
        public int Id { get; set; }
        public int ScoutingDataId { get; set; }
        public byte DeadBot { get; set; }
        public byte NoShow { get; set; }
        public Nullable<byte> Veers { get; set; }
        public Nullable<byte> HitsPartner { get; set; }
        public Nullable<byte> Stuck { get; set; }
        public Nullable<byte> WrongWay { get; set; }
        public int FoulCount { get; set; }
        public Nullable<byte> AutoTargeting { get; set; }
        public string BallCapacity { get; set; }
        public Nullable<byte> DrivingDriverTrainType { get; set; }
        public Nullable<byte> DrivingSpeed { get; set; }
        public Nullable<byte> DrivingIsReckless { get; set; }
        public Nullable<byte> DrivingIsAimless { get; set; }
        public Nullable<byte> DrivingDrivesIntoPartner { get; set; }
        public string BallPickupSpeed { get; set; }
        public Nullable<byte> BallPickupCanPickUpOffGroud { get; set; }
        public string BallPickupFeederAbility { get; set; }
        public string GearPlacementSpeed { get; set; }
        public string GearPlacementAccuracy { get; set; }
        public Nullable<byte> GearPlacementCanPickupOffFloor { get; set; }
        public string DefenseAsTargetedBumper { get; set; }
        public string DefenseAsCrasher { get; set; }
        public string DefenseSpeed { get; set; }
        public Nullable<byte> DefenseIsRisky { get; set; }
        public Nullable<byte> DefenseGoesInForbiddenAreas { get; set; }
        public Nullable<byte> SmartnessIsPurposeful { get; set; }
        public Nullable<byte> SmartnessIsConfused { get; set; }
        public Nullable<byte> SmartnessIsAimless { get; set; }
        public Nullable<byte> SmartnessIsHelpful { get; set; }
        public Nullable<byte> SmartnessWorksAgainstAlliance { get; set; }
        public Nullable<byte> SmartnessIsDangerous { get; set; }
        public Nullable<byte> SmartnessIsReckless { get; set; }
        public Nullable<byte> SmartnessGetsInWayOfPartners { get; set; }
        public int PilotOneFromTeam { get; set; }
        public Nullable<int> PilotTwoFromTeam { get; set; }
        public string PilotGearRetrievalSpeed { get; set; }
        public string PilotGearPlacementSpeed { get; set; }
        public Nullable<byte> PilotFailedToTurnCrank { get; set; }
        public Nullable<byte> PilotDropsRopeInTimelyManner { get; set; }
        public Nullable<byte> PilotIsHelpfulWithSpottingToDrivers { get; set; }
        public int HumanPlayerFromTeam { get; set; }
        public Nullable<byte> HumanPlayerMadeMistakeDeliveringGear { get; set; }
        public Nullable<byte> HumanPlayerMadeMistakeDeliveringFuel { get; set; }
        public string Notes { get; set; }
    
        public virtual ScoutingDataMatch ScoutingDataMatch { get; set; }
    }
}
package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Qualitative metrics.
 */

class Qualitative {
    @SerializedName("DeadBot") boolean deadBot;
    @SerializedName("NoShow") boolean noShow;
    @SerializedName("Veers") boolean veers;
    @SerializedName("HitsPartner") boolean hitsPartner;
    @SerializedName("Stuck") boolean stuck;
    @SerializedName("WrongWay") boolean wrongWay;

    // Todo: Is this needed?
    @SerializedName("FoulCount") int foulCount;
    @SerializedName("AutoTargeting") boolean autoTargeting;
    @SerializedName("BallCapacity") BallCapacity ballCapacity;

    // DrivingSkills
    @SerializedName("DrivingDriverTrainType") DriverTrainType drivingDriverTrainType;
    @SerializedName("DrivingSpeed") Speed drivingSpeed;
    @SerializedName("DrivingIsReckless") boolean drivingIsReckless;
    @SerializedName("DrivingIsAimless") boolean drivingIsAimless;
    @SerializedName("DrivingDrivesIntoPartner") boolean drivingDrivesIntoPartner;

    // BallPickupSkills
    @SerializedName("BallPickupSpeed") String ballPickupSpeed;

    // Todo: Is this really needed?
    @SerializedName("BallPickupCanPickUpOffGroud") boolean ballPickupCanPickUpOffGroud;
    @SerializedName("BallPickupFeederAbility") Ability ballPickupFeederAbility;

    // GearPlacementAbility
    @SerializedName("GearPlacementSpeed") Speed gearPlacementSpeed;
    @SerializedName("GearPlacementAccuracy") Ability gearPlacementAccuracy;
    @SerializedName("GearPlacementCanPickupOffFloor") boolean gearPlacementCanPickupOffFloor;

    // DefenseAbility
    @SerializedName("DefenseAsTargetedBumper") String defenseAsTargetedBumper;
    @SerializedName("DefenseAsCrasher") String defenseAsCrasher;
    @SerializedName("DefenseSpeed") Speed defenseSpeed;
    @SerializedName("DefenseIsRisky") boolean defenseIsRisky;
    @SerializedName("DefenseGoesInForbiddenAreas") boolean defenseGoesInForbiddenAreas;

    // Smartness
    @SerializedName("SmartnessIsPurposeful") boolean smartnessIsPurposeful;
    @SerializedName("SmartnessIsConfused") boolean smartnessIsConfused;
    @SerializedName("SmartnessIsAimless") boolean smartnessIsAimless;
    @SerializedName("SmartnessIsHelpful") boolean smartnessIsHelpful;
    @SerializedName("SmartnessWorksAgainstAlliance") boolean smartnessWorksAgainstAlliance;
    @SerializedName("SmartnessIsDangerous") boolean smartnessIsDangerous;
    @SerializedName("SmartnessIsReckless") boolean smartnessIsReckless;
    @SerializedName("SmartnessGetsInWayOfPartners") boolean smartnessGetsInWayOfPartners;

    // Pilot
    @SerializedName("PilotGearRetrievalSpeed") Speed pilotGearRetrievalSpeed;
    @SerializedName("PilotGearPlacementSpeed") Speed pilotGearPlacementSpeed;
    @SerializedName("PilotFailedToTurnCrank") boolean pilotFailedToTurnCrank;
    @SerializedName("PilotDropsRopeInTimelyManner") boolean pilotDropsRopeInTimelyManner;
    @SerializedName("PilotIsHelpfulWithSpottingToDrivers") boolean pilotIsHelpfulWithSpottingToDrivers;

    // HumanPlayer
    @SerializedName("HumanPlayerFromTeam") int humanPlayerFromTeam;
    @SerializedName("HumanPlayerMadeMistakeDeliveringGear") boolean humanPlayerMadeMistakeDeliveringGear;
    @SerializedName("HumanPlayerMadeMistakeDeliveringFuel") boolean humanPlayerMadeMistakeDeliveringFuel;

    @SerializedName("Notes") String Notes;
}


CREATE TABLE [dbo].[ScoutingDataQualitative]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
    DeadBot TINYINT NOT NULL DEFAULT 0,
    NoShow TINYINT NOT NULL DEFAULT 0,
    Veers TINYINT NULL DEFAULT NULL,
    HitsPartner TINYINT NULL DEFAULT NULL,
    Stuck TINYINT NULL DEFAULT NULL,
    WrongWay TINYINT NULL DEFAULT NULL,
    FoulCount INT NOT NULL DEFAULT 0,
    AutoTargeting TINYINT NULL DEFAULT 0,
    BallCapacity NVARCHAR(50) NULL DEFAULT NULL,
        
	-- DrivingSkills
    DrivingDriverTrainType TINYINT NULL DEFAULT NULL,
    DrivingSpeed TINYINT NULL DEFAULT NULL,
    DrivingIsReckless TINYINT NULL DEFAULT NULL,
    DrivingIsAimless TINYINT NULL DEFAULT NULL,
    DrivingDrivesIntoPartner TINYINT NULL DEFAULT NULL,
        
    -- BallPickupSkills
    BallPickupSpeed NVARCHAR(50) NULL DEFAULT NULL,
    --Todo: Is this really needed?
    BallPickupCanPickUpOffGroud TINYINT NULL DEFAULT NULL,
    BallPickupFeederAbility NVARCHAR(50) NULL DEFAULT NULL,
        
    -- GearPlacementAbility
    GearPlacementSpeed NVARCHAR(50) NULL DEFAULT NULL,
    GearPlacementAccuracy NVARCHAR(50) NULL DEFAULT NULL,
    GearPlacementCanPickupOffFloor TINYINT NULL DEFAULT NULL,
        
    -- DefenseAbility
    DefenseAsTargetedBumper NVARCHAR(50) NULL DEFAULT NULL,
    DefenseAsCrasher NVARCHAR(50) NULL DEFAULT NULL,
    DefenseSpeed NVARCHAR(50) NULL DEFAULT NULL,
    DefenseIsRisky TINYINT NULL DEFAULT NULL,
    DefenseGoesInForbiddenAreas TINYINT NULL DEFAULT NULL,
        
    -- Smartness
    SmartnessIsPurposeful TINYINT NULL DEFAULT NULL,
    SmartnessIsConfused TINYINT NULL DEFAULT NULL,
    SmartnessIsAimless TINYINT NULL DEFAULT NULL,
    SmartnessIsHelpful TINYINT NULL DEFAULT NULL,
    SmartnessWorksAgainstAlliance TINYINT NULL DEFAULT NULL,
    SmartnessIsDangerous TINYINT NULL DEFAULT NULL,
    SmartnessIsReckless TINYINT NULL DEFAULT NULL,
    SmartnessGetsInWayOfPartners TINYINT NULL DEFAULT NULL,
        
    -- Pilot
    PilotOneFromTeam INT NOT NULL,
    PilotTwoFromTeam INT NULL DEFAULT NULL,
  
	PilotGearRetrievalSpeed NVARCHAR(50) NULL DEFAULT NULL,
    PilotGearPlacementSpeed NVARCHAR(50) NULL DEFAULT NULL,
    PilotFailedToTurnCrank TINYINT NULL DEFAULT NULL,
    PilotDropsRopeInTimelyManner TINYINT NULL DEFAULT NULL,
    PilotIsHelpfulWithSpottingToDrivers TINYINT NULL DEFAULT NULL,
        
    -- HumanPlayer
    HumanPlayerFromTeam INT NOT NULL ,
    HumanPlayerMadeMistakeDeliveringGear TINYINT NULL DEFAULT NULL,
    HumanPlayerMadeMistakeDeliveringFuel TINYINT NULL DEFAULT NULL,
        
    Notes NVARCHAR(2048) NULL DEFAULT NULL,

    CONSTRAINT [PK_ScoutingDataQualitative] PRIMARY KEY ([Id]), 
    CONSTRAINT [FK_ScoutingDataQualitative_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id])  ON DELETE CASCADE, 
    CONSTRAINT [CK_ScoutingDataQualitative_Id] CHECK ([ScoutingDataId] >= 1)
)

CREATE TABLE [dbo].[ScoutingDataGearAttempts]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[AutonomousMode] TINYINT NOT NULL,
	[GearResult] NVARCHAR(50) NOT NULL,
	[GearPegPosition] NVARCHAR(50) NOT NULL,
	[WasDefended] TINYINT DEFAULT NULL,

	CONSTRAINT [PK_ScoutingDataGearAttempts] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutingDataGearAttempts_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
    CONSTRAINT [CK_ScoutingDataGearAttempts_Id] CHECK ([ScoutingDataId] >= 1)
)

CREATE TABLE [dbo].[ScoutingDataShotAttempts]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[AutonomousMode] TINYINT NOT NULL,
	[ShotAttempt] INT NOT NULL,
	[ShotsMade] INT NOT NULL,
	[ShotsMissed] INT NOT NULL,
	[ShotDuration] INT NOT NULL,
	[ShotLocation] NVARCHAR(50) NOT NULL,
	[WasDefended] TINYINT DEFAULT NULL,

	CONSTRAINT [PK_ScoutingDataShotAttempts] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutingDataShotAttempts_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
    CONSTRAINT [CK_ScoutingDataShotAttempts_Id] CHECK ([ScoutingDataId] >= 1)
)
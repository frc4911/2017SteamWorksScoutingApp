CREATE TABLE [dbo].[ScoutingDataTeleop]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[TeleopPlayedDefense] TINYINT NULL DEFAULT NULL,
	[GearAttemptCount] INT NULL DEFAULT 0,
	[ShotAttemptCount] INT NULL DEFAULT 0,

    CONSTRAINT [PK_ScoutingDataTeleop] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutingDataTeleop_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
    CONSTRAINT [CK_ScoutingDataTeleop_Id] CHECK ([ScoutingDataId] >= 1)
)

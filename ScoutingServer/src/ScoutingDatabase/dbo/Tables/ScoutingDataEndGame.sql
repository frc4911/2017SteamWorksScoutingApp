CREATE TABLE [dbo].[ScoutingDataEndGame]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[ClimbingAttempted]  TINYINT NOT NULL DEFAULT 0,
	[ClimbingSucceeded]  TINYINT NOT NULL DEFAULT 0,
	[ClimbingTimeInSeconds] INT NULL DEFAULT NULL,
	[ClimbingTouchPadPosition] NVARCHAR(50) NULL DEFAULT NULL,

    CONSTRAINT [PK_ScoutingDataEndGame] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutingDataEndGame_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
	CONSTRAINT [CK_ScoutingDataEndGame_Id] CHECK ([ScoutingDataId] >= 1)
)

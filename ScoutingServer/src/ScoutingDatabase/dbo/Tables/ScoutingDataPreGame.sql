CREATE TABLE [dbo].[ScoutingDataPreGame]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[PreGameHasGear] TINYINT NOT NULL,
	[PreGameHasFuel] TINYINT NOT NULL,
	[PreGameHasPilot] TINYINT NOT NULL,
	[PreGameUsesOwnRope] TINYINT NOT NULL,
	[PreGameRopeTouchPadPosition] NVARCHAR(50) NULL,
	[PreGameRobotPosition] NVARCHAR(50) NOT NULL,

    CONSTRAINT [FK_ScoutingDataPreGame_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
    CONSTRAINT [PK_ScoutingDataPreGame] PRIMARY KEY ([Id]), 
    CONSTRAINT [CK_ScoutingDataPreGame_Id] CHECK ([ScoutingDataId] >= 1)
)

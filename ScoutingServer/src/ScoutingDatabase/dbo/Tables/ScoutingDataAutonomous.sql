CREATE TABLE [dbo].[ScoutingDataAutonomous]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[AutoMobilityPoints] INT NOT NULL DEFAULT 0,

    CONSTRAINT [PK_ScoutingDataAutonomous] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutingDataAutonomous_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
	CONSTRAINT [CK_ScoutingDataAutonomous_Id] CHECK ([ScoutingDataId] >= 1)
)

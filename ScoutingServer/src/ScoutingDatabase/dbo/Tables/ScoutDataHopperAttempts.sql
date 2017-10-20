CREATE TABLE [dbo].[ScoutDataHopperAttempts]
(
	[Id] INT NOT NULL IDENTITY,
	[ScoutingDataId] INT NOT NULL,
	[AutonomousMode] TINYINT NOT NULL,
	[Activated] TINYINT NOT NULL,
	[HopperLocation] NVARCHAR(50) NOT NULL,
		
	CONSTRAINT [PK_ScoutDataHopperAttempts] PRIMARY KEY ([Id]),
    CONSTRAINT [FK_ScoutDataHopperAttempts_To_ScoutingDataMatches] FOREIGN KEY ([ScoutingDataId]) REFERENCES [ScoutingDataMatches]([Id]) ON DELETE CASCADE, 
	CONSTRAINT [CK_ScoutDataHopperAttempts_Id] CHECK ([ScoutingDataId] >= 1)
)

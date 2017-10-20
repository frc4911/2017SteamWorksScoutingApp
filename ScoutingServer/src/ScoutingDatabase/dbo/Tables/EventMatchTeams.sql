CREATE TABLE [dbo].[EventMatchTeams]
(
	[Season] int NOT NULL,
    [EventCode] nvarchar(50)   NOT NULL,
	[MatchNumber] int NOT NULL,
	[TeamNumber] int NOT NULL,
	[Station] nvarchar(50) NOT NULL,
	[Dq] BIT NOT NULL
	CONSTRAINT [PK_EventMatchTeams] PRIMARY KEY CLUSTERED ([Season], [EventCode] ASC, [MatchNumber], [TeamNumber])
)

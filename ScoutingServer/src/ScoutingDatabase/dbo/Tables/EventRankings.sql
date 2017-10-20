CREATE TABLE [dbo].[EventRankings]
(
	[Season]		INT NOT NULL,
    [EventCode]		NVARCHAR (50) NOT NULL,
	[TeamNumber]	INT NOT NULL,
	[DqCount]		INT NOT NULL,
	[MatchesPlayed] INT NOT NULL,
	[QualAverage]	FLOAT NOT NULL,
	[Rank]			INT NOT NULL,
	[Wins]			INT NOT NULL,
	[Losses]		INT NOT NULL,
	[Ties]			INT NOT NULL,
	[SortOrder1]	FLOAT NOT NULL,
	[SortOrder2]	FLOAT NOT NULL,
	[SortOrder3]	FLOAT NOT NULL,
	[SortOrder4]	FLOAT NOT NULL,
	[SortOrder5]	FLOAT NOT NULL,
	[SortOrder6]	FLOAT NOT NULL
    CONSTRAINT [PK_SeasonEventsCodeTeamNumber] PRIMARY KEY CLUSTERED ([Season], [EventCode] ASC, [TeamNumber])
)

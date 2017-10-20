CREATE TABLE [dbo].[EventMatchResults]
(
	[Season] int NOT NULL,
    [EventCode] nvarchar(50)   NOT NULL,
	[TournamentLevel] nvarchar(50) NOT NULL,
	[MatchNumber] int NOT NULL,
	[ActualStartTime] datetime NOT NULL,
	[PostResultTime] datetime NOT NULL,
	[ScoreRedFinal] int NOT NULL,
	[ScoreRedFoul] int NOT NULL,
	[ScoreRedAuto] int NOT NULL,
	[ScoreBlueFinal] int NOT NULL,
	[ScoreBlueFoul] int NOT NULL,
	[ScoreBlueAuto] int NOT NULL,
	CONSTRAINT [PK_EventMatchResultsEventCode] PRIMARY KEY CLUSTERED ([Season], [EventCode] ASC,  [TournamentLevel], MatchNumber)
)

GO
CREATE INDEX [IX_EventMatchResults_MatchNumber] ON [dbo].[EventMatchResults] (MatchNumber)

GO
CREATE INDEX [IX_EventMatchResults_EventCode_TournamnetLevel] ON [dbo].[EventMatchResults] ([EventCode], [TournamentLevel])

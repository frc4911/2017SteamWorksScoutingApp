CREATE PROCEDURE [dbo].[Insert_EventRanking]
	@Season int,
    @EventCode nvarchar(max),
	@TeamNumber int,
    @DqCount int,
	@MatchesPlayed int,
    @QualAverage float,
    @Rank int,
    @Wins int,
    @Losses	int,
    @Ties int,
    @SortOrder1	float,
    @SortOrder2	float,
    @SortOrder3	float,
    @SortOrder4	float,
    @SortOrder5	float,
    @SortOrder6	float
AS
	SET NOCOUNT ON;
	MERGE INTO [dbo].[EventRankings] AS T
	USING (VALUES(@Season, @EventCode, @TeamNumber, @DqCount, @MatchesPlayed, @QualAverage, @Rank, @Wins, @Losses, @Ties, @SortOrder1, @SortOrder2, @SortOrder3, @SortOrder4, @SortOrder5, @SortOrder6)) 
		AS S (Season, EventCode, TeamNumber, DqCount, MatchesPlayed, QualAverage, [Rank], Wins, Losses, Ties, SortOrder1, SortOrder2, SortOrder3, SortOrder4, SortOrder5, SortOrder6)
	ON T.Season = S.Season AND T.EventCode = S.EventCode AND T.TeamNumber = S.TeamNumber 
	WHEN MATCHED THEN
		UPDATE SET
			DqCount = S.DqCount, 
			MatchesPlayed = S.MatchesPlayed, 
			QualAverage = S.QualAverage, 
			[Rank] = S.[Rank], 
			Wins = S.Wins, 
			Losses = S.Losses, 
			Ties = S.Ties, 
			SortOrder1 = S.SortOrder1, 
			SortOrder2 = S.SortOrder2, 
			SortOrder3 = S.SortOrder3, 
			SortOrder4 = S.SortOrder4, 
			SortOrder5 = S.SortOrder5, 
			SortOrder6 = S.SortOrder6
	WHEN NOT MATCHED THEN
		INSERT (Season, EventCode, TeamNumber, DqCount, MatchesPlayed, QualAverage, [Rank], Wins, Losses, Ties, SortOrder1, SortOrder2, SortOrder3, SortOrder4, SortOrder5, SortOrder6)
		VALUES (S.Season, S.EventCode, S.TeamNumber, S.DqCount, S.MatchesPlayed, S.QualAverage, S.[Rank], S.Wins, S.Losses, S.Ties, S.SortOrder1, S.SortOrder2, S.SortOrder3, S.SortOrder4, S.SortOrder5, S.SortOrder6);
RETURN 0

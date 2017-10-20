CREATE PROCEDURE [dbo].[Update_EventRanking]
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
	EXECUTE Insert_EventRanking @Season, @EventCode, @TeamNumber, @DqCount, @MatchesPlayed, @QualAverage, @Rank, @Wins, @Losses, @Ties, @SortOrder1, @SortOrder2, @SortOrder3, @SortOrder4, @SortOrder5, @SortOrder6
RETURN 0

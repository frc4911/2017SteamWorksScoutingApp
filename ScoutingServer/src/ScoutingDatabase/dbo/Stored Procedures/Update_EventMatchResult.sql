CREATE PROCEDURE [dbo].[Update_EventMatchResult]
	@Season int,
    @EventCode nvarchar(max),
	@ActualStartTime datetime,
	@TournamentLevel nvarchar(max),
	@MatchNumber int,
	@PostResultTime datetime,
	@ScoreRedFinal int,
	@ScoreRedFoul int,
	@ScoreRedAuto int,
	@ScoreBlueFinal int,
	@ScoreBlueFoul int,
	@ScoreBlueAuto int
AS
	EXECUTE [dbo].[Insert_MatchEventResult] @Season, @EventCode, @ActualStartTime, @TournamentLevel, @MatchNumber, @PostResultTime, @ScoreRedFinal, @ScoreRedFoul, @ScoreRedAuto, @ScoreBlueFinal, @ScoreBlueFoul, @ScoreBlueAuto
RETURN 0

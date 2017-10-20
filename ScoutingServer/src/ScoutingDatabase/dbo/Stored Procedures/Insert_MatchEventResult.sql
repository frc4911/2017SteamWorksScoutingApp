CREATE PROCEDURE [dbo].[Insert_MatchEventResult]
	@Season int,
    @EventCode nvarchar(50),
	@ActualStartTime datetime,
	@TournamentLevel nvarchar(50),
	@MatchNumber int,
	@PostResultTime datetime,
	@ScoreRedFinal int,
	@ScoreRedFoul int,
	@ScoreRedAuto int,
	@ScoreBlueFinal int,
	@ScoreBlueFoul int,
	@ScoreBlueAuto int
AS
	SET NOCOUNT ON;
	MERGE INTO [dbo].[EventMatchResults] AS T
	USING (VALUES (@Season, @EventCode, @ActualStartTime, @TournamentLevel, @MatchNumber, @PostResultTime, @ScoreRedFinal, @ScoreRedFoul, @ScoreRedAuto, @ScoreBlueFinal, @ScoreBlueFoul, @ScoreBlueAuto) ) 
		AS S (Season, EventCode, ActualStartTime, TournamentLevel, MatchNumber, PostResultTime, ScoreRedFinal, ScoreRedFoul, ScoreRedAuto, ScoreBlueFinal, ScoreBlueFoul, ScoreBlueAuto)
	ON T.Season = S.Season AND T.EventCode = S.EventCode AND  T.TournamentLevel = S.TournamentLevel AND T.MatchNumber = S.MatchNumber
	WHEN MATCHED THEN
		UPDATE SET
			ActualStartTime = S.ActualStartTime,
			PostResultTime = S.PostResultTime,
			ScoreRedFinal = S.ScoreRedFinal,
			ScoreRedFoul = S.ScoreRedFoul,
			ScoreRedAuto = S.ScoreRedAuto,
			ScoreBlueFinal = S.ScoreBlueFinal,
			ScoreBlueFoul = S.ScoreBlueFoul,
			ScoreBlueAuto = S.ScoreBlueAuto
	WHEN NOT MATCHED THEN
		INSERT (Season, EventCode, ActualStartTime, TournamentLevel, MatchNumber, PostResultTime, ScoreRedFinal, ScoreRedFoul, ScoreRedAuto, ScoreBlueFinal, ScoreBlueFoul, ScoreBlueAuto)
		VALUES (S.Season, S.EventCode, S.ActualStartTime, S.TournamentLevel, S.MatchNumber, S.PostResultTime, S.ScoreRedFinal, S.ScoreRedFoul, S.ScoreRedAuto, S.ScoreBlueFinal, S.ScoreBlueFoul, S.ScoreBlueAuto);	
RETURN 0

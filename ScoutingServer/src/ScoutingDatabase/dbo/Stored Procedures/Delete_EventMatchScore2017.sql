CREATE PROCEDURE [dbo].[Delete_EventMatchScore2017]
	@EventCode nvarchar(max),
	@TournamentLevel nvarchar(max),
	@MatchNumber int
AS
	DELETE [dbo].[EventMatchScores2017] 
	WHERE 
		[EventCode] = @EventCode AND
		[TournamentLevel] = @TournamentLevel AND
		[MatchNumber] = @MatchNumber
RETURN 0

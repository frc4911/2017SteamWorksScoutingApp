CREATE PROCEDURE [dbo].[Delete_EventMatchResult]
	@Season int,
    @EventCode nvarchar(max) ,
	@TournamentLevel nvarchar(max),
	@MatchNumber int
AS
	DELETE FROM [dbo].[EventMatchResults]
	WHERE 
		[Season] = @Season AND
		[EventCode] = @EventCode AND
		[TournamentLevel] = @TournamentLevel AND
		[MatchNumber] = @MatchNumber
RETURN 0

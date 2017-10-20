CREATE PROCEDURE [dbo].[Delete_EventMatchTeam]
	@Season int,
    @EventCode nvarchar(max),
	@MatchNumber int,
	@TeamNumber int
AS
	SET NOCOUNT ON;
	DELETE FROM [dbo].[EventMatchTeams] 
	WHERE 
		[Season] = @Season AND
		[EventCode] = @EventCode AND
		[MatchNumber] = @MatchNumber AND
		[TeamNumber] = @TeamNumber
RETURN 0

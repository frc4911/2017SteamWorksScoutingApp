CREATE PROCEDURE [dbo].[Update_EventMatchTeam]
	@Season int,
    @EventCode nvarchar(max),
	@MatchNumber int,
	@TeamNumber int,
	@Station nvarchar(max),
	@Dq BIT
AS
	EXECUTE Insert_EventMatchTeam @Season, @EventCode, @MatchNumber, @TeamNumber, @Station, @Dq
RETURN 0

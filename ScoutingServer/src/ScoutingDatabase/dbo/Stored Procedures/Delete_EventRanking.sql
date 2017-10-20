CREATE PROCEDURE [dbo].[Delete_EventRanking]
	@Season int,
	@EventCode nvarchar(max),
	@TeamNumber int
AS
	SET NOCOUNT ON;
	DELETE FROM [dbo].[EventRankings] 
	WHERE [Season] = @Season AND [EventCode] = @EventCode AND [TeamNumber] = @TeamNumber
RETURN 0

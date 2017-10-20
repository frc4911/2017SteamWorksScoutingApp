CREATE PROCEDURE [dbo].[Delete_Event]
	@Season int,
	@EventCode nvarchar(max)
AS
	SET NOCOUNT ON;
	DELETE FROM [dbo].[Events] 
	WHERE [Season] = @Season AND [EventCode] = @EventCode
RETURN 0

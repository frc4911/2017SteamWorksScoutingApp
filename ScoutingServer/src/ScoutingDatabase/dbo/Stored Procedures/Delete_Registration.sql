CREATE PROCEDURE [dbo].[Delete_Registration]
	@Season int,
	@TeamNumber int
AS
	SET NOCOUNT ON;
	DELETE FROM [dbo].[Registrations] 
	WHERE [Season] = @Season AND [TeamNumber] = @TeamNumber
RETURN 0

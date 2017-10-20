CREATE PROCEDURE Delete_Team 
	@TeamNumber int = 0
AS
BEGIN
	SET NOCOUNT ON;
	DELETE FROM [dbo].[Teams] 
	WHERE [TeamNumber] = @TeamNumber
END
RETURN 0
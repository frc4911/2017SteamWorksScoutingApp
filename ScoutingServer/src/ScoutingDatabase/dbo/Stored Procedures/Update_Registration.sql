CREATE PROCEDURE [dbo].[Update_Registration]
	@Season int,
	@TeamNumber int,
    @EventCode nvarchar(max)
AS
	EXECUTE Insert_Registration @Season, @TeamNumber, @EventCode
RETURN 0

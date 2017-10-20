CREATE PROCEDURE [dbo].[Delete_District]
	@Season int,
	@DistrictCode nvarchar(max)
AS
	SET NOCOUNT ON;
	DELETE FROM [dbo].[Districts] 
	WHERE [Season] = @Season AND [DistrictCode] = @DistrictCode
RETURN 0

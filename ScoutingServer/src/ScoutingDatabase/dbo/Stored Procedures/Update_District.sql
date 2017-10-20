CREATE PROCEDURE [dbo].[Update_District]
	@Season int,
	@DistrictCode nvarchar(max),
	@Name nvarchar(max)
AS
	EXECUTE Insert_District @Season, @DistrictCode, @Name
RETURN 0
CREATE PROCEDURE [dbo].[Insert_District]
	@Season int,
	@DistrictCode nvarchar(max),
	@Name nvarchar(max)
AS
	SET NOCOUNT ON;
	MERGE INTO [dbo].[Districts] AS T
	USING (VALUES(@Season, @DistrictCode, @Name) ) 
	AS S (Season, DistrictCode, Name)
	ON T.Season = S.Season AND T.DistrictCode = S.DistrictCode
	WHEN MATCHED THEN
		UPDATE SET
			Name = S.Name
	WHEN NOT MATCHED THEN
		INSERT (Season, DistrictCode, Name)
		VALUES (S.Season, S.DistrictCode, S.Name);
RETURN 0

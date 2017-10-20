CREATE PROCEDURE [dbo].[Insert_Team] 
(
	@TeamNumber int,
	@ShortName nvarchar(max),
	@FullName nvarchar(max),
	@City nvarchar(max),
	@StateProv nvarchar(max),
	@Country nvarchar(max),
	@DistrictCode nvarchar(max),
	@RookieYear int,
	@RobotName nvarchar(max),
	@WebSite nvarchar(max),
	@SchoolName nvarchar(max),
	@HomeCMP nvarchar(max)
)
AS
BEGIN
	SET NOCOUNT ON;
	MERGE INTO [dbo].[Teams] AS T
	USING (
		VALUES(@TeamNumber, @ShortName, @FullName, @City, @StateProv, @Country, @DistrictCode, @RookieYear, @RobotName, @WebSite, @SchoolName, @HomeCMP)
		) AS S (TeamNumber, ShortName, FullName, City, StateProv, Country, DistrictCode, RookieYear, RobotName, WebSite, SchoolName, HomeCMP)
	ON T.TeamNumber = S.TeamNumber 
	WHEN MATCHED THEN
		UPDATE SET
			ShortName = S.ShortName,
			FullName = S.FullName,
			City = S.City,
			StateProv = S.StateProv,
			Country = S.Country,
			DistrictCode = S.DistrictCode,
			RookieYear = S.RookieYear,
			RobotName = S.RobotName,
			WebSite = S.WebSite,
			SchoolName = S.SchoolName,
			HomeCMP = S.HomeCMP
	WHEN NOT MATCHED THEN
		INSERT (TeamNumber, ShortName, FullName, City, StateProv, Country, DistrictCode, RookieYear, RobotName, WebSite, SchoolName, HomeCMP)
		VALUES (S.TeamNumber, S.ShortName, S.FullName, S.City, S.StateProv, S.Country, S.DistrictCode, S.RookieYear, S.RobotName, S.WebSite, S.SchoolName, S.HomeCMP);
END
RETURN 0


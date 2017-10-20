CREATE PROCEDURE [dbo].[Update_Team] 
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
	EXECUTE Insert_Team @TeamNumber, @ShortName, @FullName, @City, @StateProv, @Country, @DistrictCode, @RookieYear, @RobotName, @WebSite, @SchoolName, @HomeCMP
END
RETURN 0

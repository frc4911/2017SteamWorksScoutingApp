CREATE PROCEDURE [dbo].[Update_Event]
(
	@Season int,
	@EventCode nvarchar(max),
	@DistrictCode nvarchar(max),
	@DivisionCode nvarchar(max),
	@Name nvarchar(max),
	@Type nvarchar(max),
	@Venue nvarchar(max),
	@Address nvarchar(max),
	@City nvarchar(max),
	@Country nvarchar(max),
	@StateProv nvarchar(max),
	@DateStart datetime,
	@DateEnd datetime,
	@WebSite nvarchar(max)
)
AS
BEGIN
	EXECUTE Insert_Event @Season, @EventCode, @DistrictCode, @DivisionCode, @Name, @Type, @Venue, @Address, @City, @StateProv, @Country, @DateStart, @DateEnd, @WebSite
END
RETURN 0


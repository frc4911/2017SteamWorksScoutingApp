CREATE PROCEDURE [dbo].[Insert_Event]
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
	SET NOCOUNT ON;
	MERGE INTO [dbo].[Events] AS T
	USING (
		VALUES (@Season, @EventCode, @DistrictCode, @DivisionCode, @Name, @Type, @Venue, @Address, @City, @StateProv, @Country, @DateStart, @DateEnd, @WebSite)
		) AS S (Season, EventCode, DistrictCode, DivisionCode, Name, [Type], Venue, [Address], City, StateProv, Country, DateStart, DateEnd, WebSite)
	ON T.Season = S.Season AND T.EventCode = S.EventCode
		WHEN MATCHED THEN
		UPDATE SET
			[DistrictCode]	= S.[DistrictCode],
			[DivisionCode]	= S.[DivisionCode],
			[Name]			= S.[Name],
			[Type]			= S.[Type],
			[Venue]			= S.[Venue],
			[Address]		= S.[Address],
			[City]			= S.[City],
			[StateProv]		= S.[StateProv],
			[Country]		= S.[Country],
			[DateStart]		= S.[DateStart],
			[DateEnd]		= S.[DateEnd],
			[WebSite]		= S.[WebSite]
	WHEN NOT MATCHED THEN
		INSERT (Season, EventCode, DistrictCode, DivisionCode, Name, [Type], Venue, [Address], City, StateProv, Country, DateStart, DateEnd, WebSite)
		VALUES (S.Season, S.EventCode, S.DistrictCode, S.DivisionCode, S.Name, S.[Type], S.Venue, S.[Address], S.City, S.StateProv, S.Country, S.DateStart, S.DateEnd, S.WebSite);
END
RETURN 0

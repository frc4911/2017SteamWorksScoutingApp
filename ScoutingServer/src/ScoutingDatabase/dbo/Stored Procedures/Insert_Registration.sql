CREATE PROCEDURE [dbo].[Insert_Registration]
	@Season int,
	@TeamNumber int,
    @EventCode nvarchar(max)
AS
	SET NOCOUNT ON;
	MERGE INTO [dbo].[Registrations] AS T
	USING (VALUES(@Season, @TeamNumber, @EventCode)) AS S (Season, TeamNumber, EventCode)
	ON T.Season = S.Season AND T.TeamNumber = S.TeamNumber AND T.EventCode = S.EventCode
	WHEN NOT MATCHED THEN
		INSERT (Season, TeamNumber, EventCode)
		VALUES (S.Season, S.TeamNumber, S.EventCode);
RETURN 0


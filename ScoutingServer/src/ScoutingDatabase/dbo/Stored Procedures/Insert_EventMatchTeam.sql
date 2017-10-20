CREATE PROCEDURE [dbo].[Insert_EventMatchTeam]
	@Season int,
    @EventCode nvarchar(max),
	@MatchNumber int,
	@TeamNumber int,
	@Station nvarchar(max),
	@Dq BIT
AS
	SET NOCOUNT ON;
	MERGE INTO [dbo].[EventMatchTeams] AS T
	USING (VALUES(@Season, @EventCode, @MatchNumber, @TeamNumber, @Station, @Dq) ) 
		AS S (Season, EventCode, MatchNumber, TeamNumber, Station, Dq)
	ON T.Season = S.Season AND T.EventCode = S.EventCode AND T.TeamNumber = S.TeamNumber AND  S.MAtchNumber = T.MatchNumber
	WHEN MATCHED THEN
		UPDATE SET
			Station = S.Station,
			Dq = S.Dq
	WHEN NOT MATCHED THEN
		INSERT (Season, EventCode, MatchNumber, TeamNumber, Station, Dq)
		VALUES (S.Season, S.EventCode, S.MatchNumber, S.TeamNumber, S.Station, S.Dq);
RETURN 0

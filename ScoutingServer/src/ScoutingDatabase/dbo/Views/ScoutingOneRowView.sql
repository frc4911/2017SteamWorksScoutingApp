CREATE VIEW [dbo].[ScoutingOneRowView]
AS
WITH GearAttempts AS (
	SELECT 
		ScoutingDataId,
		SUM(CASE WHEN AutonomousMode = 1 THEN 1 ELSE 0 END) AS AutonomousGearAttempts,
		SUM(CASE WHEN AutonomousMode = 1 AND GearResult = 'Success' THEN 1 ELSE 0 END) AS AutonomousGearSucceded,
		SUM(CASE WHEN AutonomousMode = 0 THEN 1 ELSE 0 END) AS TeleopGearAttempts,
		SUM(CASE WHEN AutonomousMode = 0 AND GearResult = 'Success' THEN 1 ELSE 0 END) AS TeleopGearSucceded
	FROM
		dbo.ScoutingDataGearAttempts
	GROUP BY 
		ScoutingDataId
),
ShotAttempts AS (
	SELECT 
		ScoutingDataId,
		SUM(CASE WHEN AutonomousMode = 1 THEN 1 ELSE 0 END) AS AutonomousShotAttempts,
		SUM(CASE WHEN AutonomousMode = 1 THEN ShotsMade ELSE 0 END) AS AutonomousShotsMade,
		SUM(CASE WHEN AutonomousMode = 1 THEN ShotsMissed ELSE 0 END) AS AutonomousShotsMissed,
		SUM(CASE WHEN AutonomousMode = 0 THEN 1 ELSE 0 END) AS TeleopShotAttempts,
		SUM(CASE WHEN AutonomousMode = 0 THEN ShotsMade ELSE 0 END) AS TeleopShotsMade,
		SUM(CASE WHEN AutonomousMode = 0 THEN ShotsMissed ELSE 0 END) AS TeleopShotsMissed
	FROM
		dbo.ScoutingDataShotAttempts
	GROUP BY 
		ScoutingDataId
),
HopperAttempts AS (
  	SELECT 
		ScoutingDataId,
		SUM(CASE WHEN AutonomousMode = 1 THEN 1 ELSE 0 END) AS AutonomousHopperAttempts,
		COUNT(CASE WHEN AutonomousMode = 0 THEN 1 ELSE 0 END) AS TeleopHopperAttempts
	FROM
		dbo.ScoutingDataShotAttempts
	GROUP BY 
		ScoutingDataId
)
SELECT      
	dbo.ScoutingDataMatches.MatchNumber, 
	dbo.ScoutingDataMatches.TeamNumber, 
	CASE WHEN dbo.ScoutingDataAutonomous.AutoMobilityPoints = 0 THEN 0 ELSE 5 END AS BaseLinePoints, 
	CASE WHEN AutonomousGearAttempts = NULL THEN 0 ELSE AutonomousGearAttempts END AS AutonomousGearAttempts,
	CASE WHEN AutonomousGearSucceded = NULL THEN 0 ELSE AutonomousGearSucceded END AutonomousGearSucceded,
	CASE WHEN TeleopGearAttempts = NULL THEN 0 ELSE TeleopGearAttempts END AS TeleopGearAttempts,
	CASE WHEN TeleopGearSucceded = NULL THEN 0 ELSE TeleopGearSucceded END AS TeleopGearSucceded,
	CASE WHEN AutonomousShotAttempts = NULL THEN 0 ELSE AutonomousShotAttempts END AS AutonomousShotAttempts,
	CASE WHEN AutonomousShotsMade = NULL THEN 0 ELSE AutonomousShotsMade END AS AutonomousShotsMade,
	CASE WHEN AutonomousShotsMissed = NULL THEN 0 ELSE AutonomousShotsMissed END AS AutonomousShotsMissed,
	CASE WHEN TeleopShotAttempts = NULL THEN 0 ELSE TeleopShotAttempts END AS TeleopShotAttempts,
	CASE WHEN TeleopShotsMade = NULL THEN 0 ELSE TeleopShotsMade END AS TeleopShotsMade,
	CASE WHEN TeleopShotsMissed = NULL THEN 0 ELSE TeleopShotsMissed END AS TeleopShotsMissed,
	dbo.ScoutingDataTeleop.TeleopPlayedDefense,
	CASE WHEN AutonomousHopperAttempts = NULL THEN 0 ELSE AutonomousHopperAttempts END AS AutonomousHopperAttempts,
	CASE WHEN TeleopHopperAttempts = NULL THEN 0 ELSE TeleopHopperAttempts END AS TeleopHopperAttempts,
	dbo.ScoutingDataEndGame.ClimbingSucceeded, 
    dbo.ScoutingDataEndGame.ClimbingAttempted, 
	dbo.ScoutingDataEndGame.ClimbingTimeInSeconds, 
	dbo.ScoutingDataEndGame.ClimbingTouchPadPosition,
	dbo.ScoutingDataMatches.EventCode, 
	dbo.ScoutingDataMatches.ScoutName, 
	dbo.ScoutingDataMatches.Station, 
	dbo.ScoutingDataMatches.ScoutingTeam, 
	dbo.ScoutingDataMatches.DeviceId, 
	dbo.ScoutingDataMatches.MatchLevel
FROM        
	dbo.ScoutingDataMatches INNER JOIN
    dbo.ScoutingDataPreGame ON dbo.ScoutingDataMatches.Id = dbo.ScoutingDataPreGame.ScoutingDataId INNER JOIN
    dbo.ScoutingDataAutonomous ON dbo.ScoutingDataMatches.Id = dbo.ScoutingDataAutonomous.ScoutingDataId INNER JOIN
    dbo.ScoutingDataTeleop ON dbo.ScoutingDataMatches.Id = dbo.ScoutingDataTeleop.ScoutingDataId INNER JOIN
    dbo.ScoutingDataEndGame ON dbo.ScoutingDataMatches.Id = dbo.ScoutingDataEndGame.ScoutingDataId LEFT OUTER JOIN
	GearAttempts ON dbo.ScoutingDataMatches.Id = GearAttempts.ScoutingDataId LEFT OUTER JOIN
	ShotAttempts ON dbo.ScoutingDataMatches.Id = ShotAttempts.ScoutingDataId LEFT OUTER JOIN
	HopperAttempts ON dbo.ScoutingDataMatches.Id = HopperAttempts.ScoutingDataId
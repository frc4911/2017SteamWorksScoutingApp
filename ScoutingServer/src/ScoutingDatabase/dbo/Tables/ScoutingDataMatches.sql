CREATE TABLE [dbo].[ScoutingDataMatches]
(
	[Id] INT NOT NULL IDENTITY,
    [DeviceId] NVARCHAR(50) NOT NULL,
	[Season] INT NOT NULL,
    [EventCode] NVARCHAR(50) NOT NULL,
    [MatchNumber] INT NOT NULL,
    [TeamNumber] INT NOT NULL,
	[MatchLevel] NVARCHAR(50) NOT NULL,
	[Station] NVARCHAR(50) NOT NULL,
    [ScoutName] NVARCHAR(50) NOT NULL,
	[ScoutingTeam] NVARCHAR(50) NOT NULL
	
    CONSTRAINT [PK_ScoutingDataMatches] PRIMARY KEY (Id), 
    -- CONSTRAINT [AK_ScoutingDataMatches_Season_EventCode_MatchNumber_TeamNumber_ScoutName_MatchLevel] UNIQUE ([Season], [EventCode], [MatchNumber], [MatchLevel], [Station]), 
  --  CONSTRAINT [CK_ScoutingDataMatches_Season_EventCode_TeamNumber_MatchNumber_ScoutName_MatchLevel] CHECK (
		--[Season] >= 2016 AND 
		--LEN([EventCode]) >= 3 AND 
		--[TeamNumber] > 1 AND 
		--[MatchNumber] > 1 AND 
		--LEN([MatchLevel]) >= 1 AND 
		--LEN([Station]) >= 1)
)

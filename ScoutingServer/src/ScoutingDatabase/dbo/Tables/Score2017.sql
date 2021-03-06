﻿CREATE TABLE [dbo].[Score2017]
(
	Alliance  nvarchar(50),
	Robot1Auto  nvarchar(50),
	Robot2Auto  nvarchar(50),
	Robot3Auto  nvarchar(50),
	AutoFuelLow int,
	AutoFuelHigh int,
	Rotor1Auto tinyint,
	Rotor2Auto tinyint,
	Rotor1Engaged tinyint,
	Rotor2Engaged tinyint,
	Rotor3Engaged tinyint,
	Rotor4Engaged tinyint,
TeleopFuelLow int,
TeleopFuelHigh int,
TouchpadNear nvarchar(50),
TouchpadMiddle nvarchar(50),
TouchpadFar nvarchar(50),
KiloPaRankingPointAchieved tinyint,
RotorRankingPointAchieved tinyint,
FoulCount int,
TechFoulCount int,
AutoPo ints int,
AutoMobilityPoints int,
AutoRotorPoints int,
AutoFuelPoints int,
TeleopPoints int,
TeleopRotorPoints int,
TeleopFuelPoints int,
TeleopTakeoffPoints int,
KilokPaBonusPoints int,
RotorBonusPoints int,
AdjustPoints int,
FoulPoints int,
TotalPoints int
)

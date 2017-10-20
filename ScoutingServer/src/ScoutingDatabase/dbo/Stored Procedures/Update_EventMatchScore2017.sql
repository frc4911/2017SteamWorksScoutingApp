﻿CREATE PROCEDURE [dbo].[Update_EventMatchScore2017]
(
    @EventCode nvarchar(max),
	@TournamentLevel nvarchar(max),
	@MatchNumber int,
    @Alliance nvarchar(max),
    @Robot1Auto nvarchar(max),
    @Robot2Auto nvarchar(max),
    @Robot3Auto nvarchar(max),
    @AutoFuelLow int,
    @AutoFuelHigh int,
    @Rotor1Auto BIT,
    @Rotor2Auto BIT,
    @Rotor1Engaged BIT,
    @Rotor2Engaged BIT,
    @Rotor3Engaged BIT,
    @Rotor4Engaged BIT,
    @TeleopFuelLow int,
    @TeleopFuelHigh int,
    @TouchpadNear nvarchar(max),
    @TouchpadMiddle nvarchar(max),
    @TouchpadFar nvarchar(max),
    @KiloPaRankingPointAchieved BIT,
    @RotorRankingPointAchieved BIT,
    @FoulCount int,
    @TechFoulCount int,
    @AutoPoints int,
    @AutoMobilityPoints int,
    @AutoRotorPoints int,
    @AutoFuelPoints int,
    @TeleopPoints int,
    @TeleopRotorPoints int,
    @TeleopFuelPoints int,
    @TeleopTakeoffPoints int,
    @KilokPaBonusPoints int,
    @RotorBonusPoints int,
    @AdjustPoints int,
    @FoulPoints int,
    @TotalPoints int
)
AS
BEGIN
	EXECUTE Insert_EventMatchScore2017 
		@EventCode,
		@TournamentLevel,
		@MatchNumber,
		@Alliance,
		@Robot1Auto,
		@Robot2Auto,
		@Robot3Auto,
		@AutoFuelLow,
		@AutoFuelHigh,
		@Rotor1Auto,
		@Rotor2Auto,
		@Rotor1Engaged,
		@Rotor2Engaged,
		@Rotor3Engaged,
		@Rotor4Engaged,
		@TeleopFuelLow,
		@TeleopFuelHigh,
		@TouchpadNear,
		@TouchpadMiddle,
		@TouchpadFar,
		@KiloPaRankingPointAchieved,
		@RotorRankingPointAchieved,
		@FoulCount,
		@TechFoulCount,
		@AutoPoints,
		@AutoMobilityPoints,
		@AutoRotorPoints,
		@AutoFuelPoints,
		@TeleopPoints,
		@TeleopRotorPoints,
		@TeleopFuelPoints,
		@TeleopTakeoffPoints,
		@KilokPaBonusPoints,
		@RotorBonusPoints,
		@AdjustPoints,
		@FoulPoints,
		@TotalPoints
END
RETURN 0

package com.example.angwy.scouting;

import org.junit.Test;
import org.usfirst.frc.team4911.scouting.datamodel.AutonomousPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.DriveStation;
import org.usfirst.frc.team4911.scouting.datamodel.EndGame;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;
import org.usfirst.frc.team4911.scouting.datamodel.GearPegPosition;
import org.usfirst.frc.team4911.scouting.datamodel.GearResult;
import org.usfirst.frc.team4911.scouting.datamodel.LocationPosition;
import org.usfirst.frc.team4911.scouting.datamodel.PreGame;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttemptTeleop;
import org.usfirst.frc.team4911.scouting.datamodel.ShotMode;
import org.usfirst.frc.team4911.scouting.datamodel.TeleopPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.TouchPadPosition;

import com.google.gson.Gson;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataModelTests {

    @Test
    public void scoutingDataModel_isCorrect() throws Exception {
        ScoutingData data = new ScoutingData("DEMO", 1, DriveStation.Red1, 4911, "Device_1", "scout@Red1", "Cyberknights");
        PreGame pregame = data.getMatchData().getPreGame();

        AutonomousPeriod autonomousPeriod = data.getMatchData().getAutonomousPeriod();
        data.getMatchData().setAutonomousPeriod(autonomousPeriod);

        ShotAttempt shotAttempt = new ShotAttempt();
        shotAttempt.setShotMode(ShotMode.High);
        shotAttempt.setShotsScored(10);
        shotAttempt.setShotLocation(LocationPosition.getInvalidPosition());
        autonomousPeriod.getShotAttempts().add(shotAttempt);

        GearAttempt gearAttempt = new GearAttempt();
        gearAttempt.setGearResult(GearResult.Success);
        gearAttempt.setGearPegPosition(GearPegPosition.G1);
        autonomousPeriod.getGearAttempts().add(gearAttempt);

        TeleopPeriod teleopPeriod = new TeleopPeriod();
        data.getMatchData().setTeleopPeriod(teleopPeriod);

        GearAttemptTeleop gearAttemptTeleop = new GearAttemptTeleop();
        gearAttemptTeleop.setGearResult(GearResult.PilotError);
        gearAttemptTeleop.setWasDefended(true);
        teleopPeriod.getGearAttempts().add(gearAttemptTeleop);

        gearAttemptTeleop = new GearAttemptTeleop();
        gearAttemptTeleop.setGearResult(GearResult.Success);
        gearAttemptTeleop.setWasDefended(false);
        teleopPeriod.getGearAttempts().add(gearAttemptTeleop);
        teleopPeriod.setGearAttemptCount(teleopPeriod.getGearAttempts().size());

        ShotAttemptTeleop shotAttemptTeleop = new ShotAttemptTeleop();
        shotAttemptTeleop.setWasDefended(false);
        shotAttemptTeleop.setShotLocation(LocationPosition.getInvalidPosition());
        shotAttemptTeleop.setShotsScored(30);
        shotAttemptTeleop.setShotMode(ShotMode.High);
        teleopPeriod.getShotAttempts().add(shotAttemptTeleop);
        shotAttemptTeleop = new ShotAttemptTeleop();
        shotAttemptTeleop.setWasDefended(true);
        shotAttemptTeleop.setShotLocation(LocationPosition.getInvalidPosition());
        shotAttemptTeleop.setShotsScored(40);
        shotAttemptTeleop.setShotMode(ShotMode.High);
        teleopPeriod.getShotAttempts().add(shotAttemptTeleop);
        teleopPeriod.setShotAttemptCount(teleopPeriod.getShotAttempts().size());

        EndGame endGame = new EndGame();
        data.getMatchData().setEndGame(endGame);

        Gson gson = new Gson();
        String json = gson.toJson(data);

        assertTrue(true);
    }
}
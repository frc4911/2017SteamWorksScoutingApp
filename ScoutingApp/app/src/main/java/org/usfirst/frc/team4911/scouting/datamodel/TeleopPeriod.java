package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the teleop phase of the match.
 */

public class TeleopPeriod {
    // Did the bot temporarily disconnect during the match?
    @SerializedName("Disconnected") private boolean disconnected = false;

    // Did the bot suffer a match-ending technical failure during the match
    @SerializedName("BotDied") private boolean botDied = false;

    // Was the bot damaged during the match?
    @SerializedName("BrokenBot") private boolean damagedBot = false;

    // Did robot play defense during teleop period?
    // Note that this is a redundant field that's here to preserve backwards compatibility
    @SerializedName("PlayedDefense") private boolean playedDefense = false;

    // How good was their defence?
    @SerializedName("DefenceRating") private Ability defenceRating = Ability.None;

    // Count of attempts to load gears at airship.
    @SerializedName("GearAttemptCount") private int gearAttemptCount = 0;

    // Count of attempts to shot balls.
    @SerializedName("ShotAttemptCount") private int ShotAttemptCount = 0;

    // The list of gear attempts made during teleop
    @SerializedName("GearAttempts") private List<GearAttemptTeleop> gearAttempts;

    // List of shot attempts made during teleop
    @SerializedName("ShotAttempts") private List<ShotAttemptTeleop> shotAttempts;

    /// How fast does the robot move?
    @SerializedName("DrivingSpeed") private Speed drivingSpeed = Speed.None;

    public TeleopPeriod() {
        this.setGearAttempts(new ArrayList<GearAttemptTeleop>());
        this.setShotAttempts(new ArrayList<ShotAttemptTeleop>());
    }

    public List<GearAttemptTeleop> getGearAttempts() {
        return gearAttempts;
    }

    public List<ShotAttemptTeleop> getShotAttempts() {
        return shotAttempts;
    }

    public void setGearAttemptCount(int gearAttemptCount) {
        this.gearAttemptCount = gearAttemptCount;
    }

    public void setGearAttempts(List<GearAttemptTeleop> gearAttempts) {
        this.gearAttempts = gearAttempts;
        this.setGearAttemptCount(gearAttempts.size());
    }

    public boolean isDisconnected() {
        return this.disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public boolean isBotDied() {
        return this.botDied;
    }

    public void setBotDied(boolean botDied) {
        this.botDied = botDied;
    }

    public boolean isDamagedBot() {
        return this.damagedBot;
    }

    public void setDamagedBot(boolean brokenBot) {
        this.damagedBot = brokenBot;
    }

    public Ability getDefenceRating() {
        return this.defenceRating;
    }

    public void setDefenceRating(Ability defenceRating) {
        this.playedDefense = defenceRating != Ability.None;
        this.defenceRating = defenceRating;
    }

    public void setShotAttemptCount(int shotAttemptCount) {
        ShotAttemptCount = shotAttemptCount;
    }

    private void setShotAttempts(List<ShotAttemptTeleop> shotAttempts) {
        this.shotAttempts = shotAttempts;
        this.setShotAttemptCount(shotAttempts.size());
    }

    public Speed getDrivingSpeed() {
        return this.drivingSpeed;
    }

    public void setDrivingSpeed(Speed drivingSpeed) {
        this.drivingSpeed = drivingSpeed;
    }

    /**
     * Last-ditch validator to protect us against bad data during teleop.
     * @return
     */
    public boolean isDataBad() {
        return (defenceRating != Ability.None && !playedDefense);
    }
}
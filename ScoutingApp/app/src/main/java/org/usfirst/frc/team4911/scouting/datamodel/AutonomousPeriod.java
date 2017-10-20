package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for the autonomous period.
 */

public class AutonomousPeriod implements DataValidator {

    // Indicates if the robot even *has* auto capability
    @SerializedName("HasAuto") private boolean hasAuto = false;

    // Does robot cross baseline by t=0 for 5 points?
    @SerializedName("AutoMobilityPoints") private boolean autoMobilityPoints = false;

    // Records the attempts to load a gear.
    @SerializedName("GearAttempts") private List<GearAttempt> gearAttempts;

    // Records the attempts to shot fuel.
    @SerializedName("ShotAttempts") private List<ShotAttempt> shotAttempts;

    public AutonomousPeriod() {
        this.setGearAttempts(new ArrayList<GearAttempt>());
        this.setShotAttempts(new ArrayList<ShotAttempt>());
    }

    public boolean hasAuto() {
        return hasAuto;
    }

    public void setHasAuto(boolean hasAuto) {
        this.hasAuto = hasAuto;
    }

    public boolean autoMobilityPoints() {
        return autoMobilityPoints;
    }

    public void setAutoMobilityPoints(boolean autoMobilityPoints) {
        this.autoMobilityPoints = autoMobilityPoints;
    }

    public List<GearAttempt> getGearAttempts() {
        return gearAttempts;
    }

    private void setGearAttempts(List<GearAttempt> gearAttempts) {
        if (gearAttempts.size() > 0) {
            setHasAuto(true);
            setAutoMobilityPoints(true);
        }

        this.gearAttempts = gearAttempts;
    }

    public List<ShotAttempt> getShotAttempts() {
        return shotAttempts;
    }

    private void setShotAttempts(List<ShotAttempt> shotAttempts) {
        if (shotAttempts.size() > 0) {
            setHasAuto(true);
        }

        this.shotAttempts = shotAttempts;
    }
    
    @Override
    public boolean isDataBad() {
        return (!hasAuto && (autoMobilityPoints || gearAttempts.size() == 1 || shotAttempts.size() == 1)
                || (!autoMobilityPoints && gearAttempts.size() > 0)
                || gearAttempts.size() < 0
                || gearAttempts.size() > 1
                || shotAttempts.size() < 0
                || shotAttempts.size() > 1);
    }
}

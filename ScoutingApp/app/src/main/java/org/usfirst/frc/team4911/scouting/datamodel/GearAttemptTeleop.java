package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Extends the gear attempt class to include teleop-relevant data.
 */

public class GearAttemptTeleop {
    // Indicates the outcome of the geat attempt
    @SerializedName("GearResult") private GearResult gearResult = GearResult.None;

    // Indicates if the bot was defended against during the attempt
    @SerializedName("WasDefended") private boolean wasDefended = false;

    public GearResult getGearResult() {
        return gearResult;
    }

    public void setGearResult(GearResult gearResult) {
        this.gearResult = gearResult;
    }

    public boolean isWasDefended() {
        return wasDefended;
    }

    public void setWasDefended(boolean wasDefended) {
        this.wasDefended = wasDefended;
    }

    public boolean isDataBad() {
        return gearResult == GearResult.None;
    }
}

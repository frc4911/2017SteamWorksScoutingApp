package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Extends the shot attempt class to include teleop-specific information.
 */

public class ShotAttemptTeleop extends ShotAttempt {
    // Indicates whether or not the bot was defended during the shot attempt
    @SerializedName("WasDefended") private boolean wasDefended = false;

    public ShotAttemptTeleop() {}

    public ShotAttemptTeleop(ShotAttempt attempt) {
        this.setShotsScored(attempt.getShotsScored());
        this.setShotLocation(attempt.getShotLocation());
        this.setShotMode(attempt.getShotMode());
    }

    public boolean isWasDefended() {
        return this.wasDefended;
    }

    public void setWasDefended(boolean wasDefended) {
        this.wasDefended = wasDefended;
    }
}

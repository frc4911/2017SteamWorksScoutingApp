package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Describes a shot attempt.
 */

public class ShotAttempt implements DataValidator {
    // Best guess at the number of shots scored
    @SerializedName("ShotsScored") private int shotsScored = 0;

    // Where the bot shot from, normalized [x,y] coordinates in feet from boiler
    @SerializedName("ShotPosition") private LocationPosition shotLocation =
            LocationPosition.getInvalidPosition();

    // Whether they shot low or high
    @SerializedName("ShotMode") private ShotMode shotMode = ShotMode.None;

    public int getShotsScored() {
        return shotsScored;
    }

    public void setShotsScored(int shotsScored) {
        this.shotsScored = shotsScored;
    }

    public ShotMode getShotMode() {
        return shotMode;
    }

    public void setShotMode(ShotMode shotMode) {
        this.shotMode = shotMode;
    }

    public LocationPosition getShotLocation() {
        return shotLocation;
    }

    public void setShotLocation(LocationPosition shotLocation) {
        this.shotLocation = shotLocation;
    }

    @Override
    public boolean isDataBad() {
        return shotLocation.equals(LocationPosition.getInvalidPosition())
                || shotMode == ShotMode.None
                || shotsScored < 0;
    }
}

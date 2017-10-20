package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Models a gear attempt.
 */

public class GearAttempt implements DataValidator {
    // Indicates the outcome of the geat attempt
    @SerializedName("GearResult") private GearResult gearResult = GearResult.None;

    // Indicates where the attempt happened
    @SerializedName("GearPegPosition") private GearPegPosition gearPegPosition = GearPegPosition.None;

    public GearPegPosition getGearPegPosition() {
        return gearPegPosition;
    }

    public void setGearPegPosition(GearPegPosition gearPegPosition) {
        this.gearPegPosition = gearPegPosition;
    }

    public GearResult getGearResult() {
        return gearResult;
    }

    public void setGearResult(GearResult gearResult) {
        this.gearResult = gearResult;
    }

    @Override
    public boolean isDataBad() {
        return  gearResult == GearResult.None || gearPegPosition == GearPegPosition.None;
    }
}

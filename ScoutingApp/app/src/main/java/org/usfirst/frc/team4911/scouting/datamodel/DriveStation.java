package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * Enum for drive stations. R1 is the red drive station furthest from the red boiler, R3 is closest
 * to the red boiler. B1 is furthest from the blue boiler, B3 is closest to the blue boiler.
 */

public enum DriveStation {
    Red1(false),
    Red2(false),
    Red3(false),
    Blue1(true),
    Blue2(true),
    Blue3(true);

    private boolean isBlue;

    private DriveStation(boolean isBlue) {
        this.isBlue = isBlue;
    }

    public boolean isBlue() {
        return isBlue;
    }

    public boolean isRed() {
        return !isBlue;
    }
}

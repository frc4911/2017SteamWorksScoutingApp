package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * All the different things that can happen with a gear.
 */

public enum GearResult {
    None(0), Success(1), RobotError(2), PilotError(3);

    private final int value;

    GearResult(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GearResult fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return Success;
            case 2:
                return RobotError;
            case 3:
                return PilotError;
            default:
                return null;
        }
    }
}

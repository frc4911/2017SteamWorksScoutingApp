package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * Enum for driving speed.
 */

public enum Speed {
    None(0), Fast(1), Medium(2), Slow(3);

    private final int value;

    Speed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Speed fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return Fast;
            case 2:
                return Medium;
            case 3:
                return Slow;
            default:
                return null;
        }
    }
}
package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * Gear peg position.
 */

public enum GearPegPosition {
    None(0), G1(1), G2(2), G3(3);

    private final int value;

    GearPegPosition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GearPegPosition fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return G1;
            case 2:
                return G2;
            case 3:
                return G3;
            default:
                return null;
        }
    }
}

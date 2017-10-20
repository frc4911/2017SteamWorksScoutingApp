package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * The ability categories we use to rate thinge. E.G. defence.
 */

public enum Ability {
    None(0), Poor(1), Fair(2), Good(3);

    private final int value;

    Ability(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Ability fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return Poor;
            case 2:
                return Fair;
            case 3:
                return Good;
            default:
                return null;
        }
    }
}
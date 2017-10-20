package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * All the kinds of foul we care about
 */

public enum Card {
    None(0), HumanYellow(1), RobotYellow(2), HumanRed(3), RobotRed(4);

    private final int value;

    Card(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Card fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return HumanYellow;
            case 2:
                return RobotYellow;
            case 3:
                return HumanRed;
            case 4:
                return HumanYellow;
            default:
                return null;
        }
    }
}
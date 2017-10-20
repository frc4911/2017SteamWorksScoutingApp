package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * All the climb outcomes of interest to our scouts
 */

public enum ClimbOutcome {
    Success(0), SucceededButPoorClimber(1), ClimbTooSlow(2), SlidDownRope(3),
    DidNotGrabRopeInTime(4), RopeBroke(5), PilotDroppedRopeLate(6), NoAttempt(7);

    private final int value;

    ClimbOutcome(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ClimbOutcome fromInt(int x) {
        switch(x) {
            case 0:
                return Success;
            case 1:
                return SucceededButPoorClimber;
            case 2:
                return ClimbTooSlow;
            case 3:
                return SlidDownRope;
            case 4:
                return DidNotGrabRopeInTime;
            case 5:
                return RopeBroke;
            case 6:
                return PilotDroppedRopeLate;
            case 7:
                return NoAttempt;
            default:
                return null;
        }
    }
}

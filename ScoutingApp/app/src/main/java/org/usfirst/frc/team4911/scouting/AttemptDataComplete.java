package org.usfirst.frc.team4911.scouting;

/**
 * Interface to be implemented by all the event fragments that is called when the save buttons on
 * auto and teleop are pressed to make sure that there's no incomplete data.
 */

interface AttemptDataComplete {

    /**
     * Called when the save button on the containing fragment (either auto or teleop) is pressed to
     * indicate whether or not there's partial data.
     *
     * @return True if the event data is incomplete, false if it can be safely saved.
     */
    boolean isAttemptDataIncomplete();
}

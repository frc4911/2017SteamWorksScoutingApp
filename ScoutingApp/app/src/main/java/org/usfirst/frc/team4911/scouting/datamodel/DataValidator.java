package org.usfirst.frc.team4911.scouting.datamodel;

/**
 * Interface for the data model validation method. Should be implemented by every object in this
 * model that corresponds to something we want to save.
 */

interface DataValidator {
    /**
     * Sanity-check method that tells us if there's anything extremely wrong with the data in the
     * object that implements this. This, and all similar methods, are intended as a last line of
     * defence against corrupt data and should be used in tandem with nice, feedback-soliciting
     * validation that has lots of popups that say exactly what's wrong.
     * @return True if the data contains errors that preclude sending it to the server, false
     * otherwise.
     */
    boolean isDataBad();
}

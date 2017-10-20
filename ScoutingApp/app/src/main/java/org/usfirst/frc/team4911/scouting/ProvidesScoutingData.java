package org.usfirst.frc.team4911.scouting;

import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;

/**
 * Interface marking that a fragment requires a reference to the scouting data object for things such as alliance color.
 */

interface ProvidesScoutingData {
    ScoutingData getScoutingData();
}

package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Model where we keep the pre-game data.
 */

public class PreGame {
    // Indicates if they were a no-show or not
    @SerializedName("NoShow") private boolean noShow = true;

    public boolean isNoShow() {
        return this.noShow;
    }

    public void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }
}

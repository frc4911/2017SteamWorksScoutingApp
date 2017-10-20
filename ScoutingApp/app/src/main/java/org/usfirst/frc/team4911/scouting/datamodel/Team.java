package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Models a team.
 */

public class Team {
    @SerializedName("TeamNumber") private int teamNumber;
    @SerializedName("TeamName") private String teamName;

    public Team(String teamName, int teamNumber) {
        this.setTeamName(teamName);
        this.setTeamNumber(teamNumber);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    /**
     * Returns a nicely formatted string containing name and number suitable for display.
     * @return Returns a nicely formatted string containing name and number suitable for display.
     */
    public String getDisplayName() {
        return getTeamNumber() + "  " + getTeamName();
    }

}

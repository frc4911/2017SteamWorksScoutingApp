package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Collection for teams.
 */

public class Teams {
    @SerializedName("Teams") private List<Team> teams;
}

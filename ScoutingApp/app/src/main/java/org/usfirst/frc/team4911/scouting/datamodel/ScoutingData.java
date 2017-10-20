package org.usfirst.frc.team4911.scouting.datamodel;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

/**
 * Top-level class that stores all scouted data for a given match.
 */

public class ScoutingData {
    public static final String MY_SCOUTING_PREFS = "MyScoutingPrefs";
    public static final String EventCodeKey = "eventCodeKey";
    public static final String ScoutNameKey = "scoutNameKey";
    public static final String ScoutTeamKey = "scoutTeamKey";
    public static final String DriveStationKey = "driveStationKey";
    public static final String AppInstanceIdKey = "appInstanceKey";

    @SerializedName("EventCode")
    private String eventCode;
    @SerializedName("MatchNumber")
    private int matchNumber;
    @SerializedName("Station")
    private DriveStation station;
    @SerializedName("TeamNumber")
    private int teamNumber;
    @SerializedName("DeviceId")
    private String deviceId;
    @SerializedName("ScoutName")
    private String scoutName;
    @SerializedName("ScoutingTeamName")
    private String scoutingTeamName;
    @SerializedName("TimeStamp")
    private String timeStamp;
    @SerializedName("MatchData")
    private MatchData matchData;
    @SerializedName("Qualitative")
    private Qualitative qualitative;

    public ScoutingData(
            String eventCode,
            int matchNumber,
            DriveStation station,
            int teamNumber,
            String deviceId,
            String scoutName,
            String scoutingTeamName) {
        this.eventCode = eventCode;
        this.matchNumber = matchNumber;
        this.station = station;
        this.teamNumber = teamNumber;
        this.deviceId = deviceId;
        this.scoutName = scoutName;
        this.scoutingTeamName = scoutingTeamName;
        this.timeStamp = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance()
                .getTime());
        this.matchData = new MatchData();
        //this.qualitative = new Qualitative();
    }

    public ScoutingData(
            String eventCode,
            DriveStation station,
            String deviceId,
            String scoutName,
            String scoutingTeamName) {
        this(eventCode, -1, station, -1, deviceId, scoutName, scoutingTeamName);
    }

    public MatchData getMatchData() {
        return this.matchData;
    }

    public String getEventCode() {
        return eventCode;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DriveStation getStation() {
        return station;
    }

    public String getScoutName() {
        return scoutName;
    }

    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }

    public String getScoutingTeamName() {
        return scoutingTeamName;
    }

    /**
     * Create a new ScoutingData instance with the given team and match numbers loading the rest
     * from the SharedPreferences.
     *
     * @param teamNumber  team number for the scouting data
     * @param matchNumber match number for the scouting data
     * @param preferences preferences object to load the rest from
     *
     * @return
     */
    public static ScoutingData create(int teamNumber, int matchNumber, SharedPreferences preferences) {
        String eventCode = preferences.getString(EventCodeKey, "");
        String scoutName = preferences.getString(ScoutNameKey, "");
        String scoutingTeamName = preferences.getString(ScoutTeamKey, "");
        String drive_Station = preferences.getString(DriveStationKey, "");
        DriveStation station = !drive_Station.isEmpty() ? DriveStation.valueOf(drive_Station) : DriveStation.Blue1;
        String deviceId = preferences.getString(AppInstanceIdKey, UUID.randomUUID().toString());

        return new ScoutingData(eventCode, matchNumber, station, teamNumber, deviceId, scoutName, scoutingTeamName);
    }

    /**
     * Create a new ScoutingData instance with default  team and match numbers (-1 & -1) loading the
     * rest from the SharedPreferences. Note that the created object will be invalid if isValid() is
     * called on it.
     *
     * @param preferences preferences object to load the rest from.
     */
    public static ScoutingData create(SharedPreferences preferences) {
        return create(-1, -1, preferences);
    }

    /**
     * Store this scouting data into the given shared preferences
     *
     * @param prefs
     */
    public void store(SharedPreferences prefs) {
        if (!setupComplete()) {
            throw new IllegalStateException("ScoutingData object is not valid.");
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EventCodeKey, eventCode);
        editor.putString(ScoutNameKey, scoutName);
        editor.putString(ScoutTeamKey, scoutingTeamName);
        editor.putString(DriveStationKey, station.toString());
        editor.putString(AppInstanceIdKey, deviceId);
        editor.commit();
    }

    /**
     * Validates that all data is set, including team number and match number
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return (eventCode != null && !eventCode.isEmpty())
                && (scoutName != null && !scoutName.isEmpty())
                && (scoutingTeamName != null && !scoutingTeamName.isEmpty())
                && (station != null)
                && (deviceId != null && !deviceId.isEmpty())
                && teamNumber > 0
                && matchNumber > 0;
    }

    /**
     * Checks to see if the setup has been completed and stored in the shared preferences
     *
     * @param prefs
     *
     * @return true if setup is complete, false otherwise
     */
    public static boolean setupComplete(SharedPreferences prefs) {
        ScoutingData data = create(prefs);
        return data.setupComplete();
    }

    private boolean setupComplete() {
        return (eventCode != null && !eventCode.isEmpty())
                && (scoutName != null && !scoutName.isEmpty())
                && (scoutingTeamName != null && !scoutingTeamName.isEmpty())
                && (station != null)
                && (deviceId != null && !deviceId.isEmpty());
    }

    public void initMatch(int matchNumber, int teamNumber) {
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
    }

    /**
     * Last-defence valdiator for the scouting metadata.
     * @return True if there's something wrong with the top-level scouting data; false otherwise.
     */
    public boolean isDataBad() {
        return TextUtils.isEmpty(eventCode)
                || matchNumber <= 0
                || teamNumber <= 0
                || TextUtils.isEmpty(deviceId)
                || TextUtils.isEmpty(scoutName)
                || TextUtils.isEmpty(scoutingTeamName)
                || TextUtils.isEmpty(timeStamp);
    }
}
package org.usfirst.frc.team4911.scouting.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne_ on 4/2/2017.
 *
 * Custom view for our team spinner. If anyone ever figures out how to make this extend spinner
 * or another more relevant class instead of linearlayout, please let me know.
 */

public class TeamSpinnerView extends LinearLayout {
    private Context context;

    private Spinner spinnerTeamNumber;

    private List<String> teamDisplayNames;
    private List<Team> teams;

    public TeamSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the context object for use later
        this.context = context;
        setupLayout();
    }

    /**
     * Does the work of initialising the layout.
     */
    private void setupLayout() {
        // Inflate the XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.team_spinner_layout, this);

        spinnerTeamNumber = (Spinner) findViewById(R.id.spinner_team_spinner);
    }

    /**
     * Loads all the teams for a given event. Must be called before interacting with the spinner.
     *
     * @param eventCode EventCode of event whose teams we want to load.
     */
    public void Init(String eventCode) {
        teams = getTeams(eventCode);

        if (teamDisplayNames == null) {
            teamDisplayNames = new ArrayList<>();
        }

        for (int i = 0; i < teams.size(); i++)
        {
            teamDisplayNames.add(teams.get(i).getDisplayName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.team_spinner, teamDisplayNames);
        spinnerTeamNumber.setAdapter(adapter);
    }

    /**
     * Returns the selected team number.
     *
     * @return The number of the team that's currently selected by the spinner.
     */
    public int getSelectedTeamNumber() {
        return teams.get(spinnerTeamNumber.getSelectedItemPosition()).getTeamNumber();
    }

    /**
     * Sets the selected team given their team number.
     *
     * @param teamNumber The number of the team to display in the spinner.
     */
    public void setSelectedTeam(int teamNumber) {
        int teamIndex = getTeamIndex(teamNumber);
        spinnerTeamNumber.setSelection(teamIndex);;
    }

    /**
     * Gets the spinner index of a given team.
     * @param teamNumber The team to calculate the spinner position for.
     * @return The index in the spinner of that team.
     */
    private int getTeamIndex(int teamNumber)
    {
        String teamDisplayName = "";

        // First, we need to determine the display name of the given team
        for (int i=0; i < teams.size(); i++) {
            if (teams.get(i).getTeamNumber() == teamNumber) {
                teamDisplayName = teams.get(i).getDisplayName();
                break;
            }
        }

        int index = 0;

        // And now we get the index of the team in the spinner
        for (int i=0; i < spinnerTeamNumber.getCount(); i++){
            if (spinnerTeamNumber.getItemAtPosition(i).toString().equals(teamDisplayName)){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Loads the team lists.
     *
     * @param eventCode The event to load teams for
     * @return A list containing all the teams competing at the given event.
     */
    private List<Team> getTeams(String eventCode)
    {
        List<Team> teams = new ArrayList<Team>();

        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader reader;

        try {
            String file = eventCode + "_teams.json";
            inputStream = getContext().getAssets().open(file);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            Gson gson = new Gson();
            teams = gson.fromJson(reader, new TypeToken<List<Team>>(){}.getType());
        }
        catch (IOException e) {
        }

        if (teams.isEmpty())
        {
            teams.add(new Team("Unknown", 9999));
        }

        return teams;
    }
}

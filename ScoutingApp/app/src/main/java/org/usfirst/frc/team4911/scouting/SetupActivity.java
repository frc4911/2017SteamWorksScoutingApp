package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.usfirst.frc.team4911.scouting.datamodel.DriveStation;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;

import android.support.v7.app.AlertDialog;

public class SetupActivity extends AppCompatActivity {

    private Spinner spinner_eventName;
    private EditText edit_scoutName;
    private Spinner spinner_scoutTeam;
    private Spinner spinner_driveStation;
    private Button button_savePreferences;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        spinner_eventName = (Spinner) findViewById(R.id.spinner_event_name);
        edit_scoutName = (EditText) findViewById(R.id.editText_ScoutName);

        spinner_scoutTeam = (Spinner) findViewById(R.id.spinner_scout_team);
        spinner_driveStation = (Spinner) findViewById(R.id.spinner_drive_station);
        button_savePreferences = (Button) findViewById(R.id.button_Save_Preverences);

        Resources res = getResources();
        sharedPreferences = getSharedPreferences(ScoutingData.MY_SCOUTING_PREFS, Context.MODE_PRIVATE);
        ScoutingData data = ScoutingData.create(sharedPreferences);

        edit_scoutName.setText(data.getScoutName());
        final String appInstanceId = data.getDeviceId();

        final String[] eventCodes = res.getStringArray(R.array.pnw_eventcodes_array);
        spinner_eventName.setAdapter(createArrayAdapter(R.array.pnw_events_array));
        selectValue(spinner_eventName, eventCodes, data.getEventCode());

        final String[] scoutTeams = res.getStringArray(R.array.scoutteams_array);
        spinner_scoutTeam.setAdapter(createArrayAdapter(R.array.scoutteams_array));
        selectValue(spinner_scoutTeam, scoutTeams, data.getScoutingTeamName());

        final String[] driveStations = res.getStringArray(R.array.drive_stations_array);
        spinner_driveStation.setAdapter(createArrayAdapter(R.array.drive_stations_array));
        selectValue(spinner_driveStation,  driveStations, data.getStation().toString());


        button_savePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventCode = eventCodes[spinner_eventName.getSelectedItemPosition()];
                String scoutName = edit_scoutName.getText().toString();
                String scoutTeam = (String) spinner_scoutTeam.getSelectedItem();
                String driveStation = (String) spinner_driveStation.getSelectedItem();

                if (scoutName.length() < 2) {
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("Please enter your name.")
                            .setTitle("Missing Info")
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //dismiss the dialog
                                        }
                                    })
                            .setCancelable(true)
                            .create().show();
                } else {
                    ScoutingData data = new ScoutingData(eventCode, DriveStation.valueOf(driveStation), appInstanceId, scoutName, scoutTeam);
                    data.store(sharedPreferences);
                    finish();
                }
            }
        });
    }

    /**
     * Sets the spinner to the selected value by using the values array to find the correct index
     * to set the spinner at. The spinner's adapter holds readable strings, but the data objects
     * store shortend codes, so we can't simply lookup the code in the adapter. Instead we use
     * the fact that the readable strings and codes are paired arrays.
     */
    private void selectValue(Spinner spinner, String[] values, String selected) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(selected)) {
                spinner.setSelection(i % values.length);
                break;
            }
        }

    }

    private SpinnerAdapter createArrayAdapter(int array_resource_id) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array_resource_id, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
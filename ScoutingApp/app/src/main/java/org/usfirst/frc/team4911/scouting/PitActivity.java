package org.usfirst.frc.team4911.scouting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.usfirst.frc.team4911.scouting.datamodel.MatchData;
import org.usfirst.frc.team4911.scouting.ui.ExampleCustomView;

// Someday (soon?) this will allow us to take pictures. Right now it's just a stub (with an custom example view!)
public class PitActivity extends AppCompatActivity {

    ExampleCustomView exampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit);

        // This is where you extract the custom view, and then initialize whatever data you need there
        exampleView = (ExampleCustomView) findViewById(R.id.example_view);
        exampleView.setMatchData(new MatchData());
    }
}

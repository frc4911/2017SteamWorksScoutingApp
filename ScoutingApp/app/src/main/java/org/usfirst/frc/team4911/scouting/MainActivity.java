package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;

// This 'class A extentds B' thing is the inheritance I mentioned yesterday.
// It's basically a way of passing on features of classes to other classes.
// eg: MainActivity can do everything AppCompatActivity can do and more.
// It helps keep code cleaner and easier to read (stop laughing!) by making
// sure that all info that's specific to general activity management can live
// in AppCompatActivity and the only code in MainActivity is what's specific
// to our application.
public class MainActivity extends AppCompatActivity {
    // A pattern you're going to see A LOT in Android is the code counterparts
    // of UI elements defined as fields like this. That's so all the
    // different methods can read them easily.
    // Also, in case you don't know, the 'private' tells Java that this field
    // is only visible to methods inside the class.
    private ListView listView;

    // This array contains the values that will be the items of our list.
    private static final String SCOUT_MATCH = "Scout Match";
    private static final String SCOUT_PIT = "Scout Pit";
    private static final String BLUETOOTH_SYNC = "Bluetooth Sync";
    private static final String USB_SYNC = "USB Sync";
    private static final String SETUP = "Scout Setup";
    private String[] values = new String[]{SCOUT_MATCH, SCOUT_PIT, BLUETOOTH_SYNC, USB_SYNC, SETUP};

    // The onCreate of the main activity. This is the first thing that runs
    // when the app is started.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The super is an inheritance thing. What it means is it's calling the constructor of
        // the class it inherits from (in this case AppCompatActivity) and passing it
        // savedInstanceState.
        super.onCreate(savedInstanceState);

        // I don't really know what this does but it's got something to do with initialising the
        // view associated with this activity (ie: what's defined in the XML) and it's important.
        setContentView(R.layout.activity_main);

        // This is where we bind the listView at the top of the class to the listView we defined in
        // the XML. Programming is all about making sure that bits get hooked up to each other.
        listView = (ListView) findViewById(R.id.list);

        // (I got this comment from the example I googled to tell me how to do a ListView. IDK how
        // good it is but here you go. Anyway, this is an arrayAdapter. I think what it does is
        // take an array and make it into something that can fill a ListView).
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Fourth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assigns the adapter to the ListView that's the menu. Like I said, programming is all
        // about making sure the bits hook up to each other.
        listView.setAdapter(adapter);

        // And set the OnClickListener! OnClickListeners are a VERY BIG DEAL. They're what makes
        // this happen when users press buttons. Also, what you're seeing here is a method being
        // defined as an argument that gets passed to another method. Don't think about it too much.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // This is here because lots of cases in the switch/case block use an intent and the
                // IDE complains if you try to create a new one in each case block.
                Intent intent;

                // Position is the menu item the user touched. They're counted from the top
                // down (I think). This is where we see which one got clicked and decide what
                // to do based on it.
                String viewToShow = (String) parent.getItemAtPosition(position);
                switch (viewToShow) {
                    case SCOUT_MATCH:
                        // Start the ScoutMatch activity
                        intent = new Intent(MainActivity.this, ScoutMatchActivity.class);
                        startActivity(intent);
                        break;
                    case SCOUT_PIT:
                        // Start the pit scouting activity
                        intent = new Intent(MainActivity.this, PitActivity.class);
                        startActivity(intent);
                        break;
                    case BLUETOOTH_SYNC:
                        // Start the bluetooth sync activity
                        intent = new Intent(MainActivity.this, BluetoothSyncActivity.class);
                        startActivity(intent);
                        break;
                    case USB_SYNC:
                        // Start the bluetooth sync activity
                        intent = new Intent(MainActivity.this, USBSyncActivity.class);
                        startActivity(intent);
                        break;
                    case SETUP:
                        // Start the setup activity
                        intent = new Intent(MainActivity.this, SetupActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        if (!ScoutingData.setupComplete(getSharedPreferences(ScoutingData.MY_SCOUTING_PREFS, Context.MODE_PRIVATE))) {
            startActivity( new Intent(MainActivity.this, SetupActivity.class));
        }
    }
}

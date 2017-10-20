package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.usfirst.frc.team4911.scouting.datamodel.AutonomousPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.EndGame;
import org.usfirst.frc.team4911.scouting.datamodel.PreGame;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.TeleopPeriod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

// This is the single activity we care about the most.
public class ScoutMatchActivity extends AppCompatActivity implements
        ProvidesScoutingData,
        PreGameFragment.OnStartClickedListener,
        ScoutAutoFragment.OnAutoPeriodObjectCreatedListener,
        ScoutTeleOpFragment.OnTeleopPeriodObjectCreatedListener,
        EndGameFragment.OnReviewClickedListener,
        ReviewFragment.ReviewFragmentInteractionListener {

    private MatchTeamHeaderFragment matchTeamHeaderFragment;
    private FrameLayout frameLayout;
    /**
     * The custom ViewPager that will host the section contents.
     */
    public CustomViewPager mViewPager;

    /**
     * The {@link ScoutingData} object that stores all scouted data collected from this match.
     */
    private ScoutingData scoutingData;

    // One thing I should probably mention is that it's important to keep track of when UI stuff
    // gets bound to code. Here in the activity class we initialise all the views and stuff that
    // belong to the activity specifically and not any of the fragments. Think of it as being the
    // stuff that's defined in activity_scout_match.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scout_match);

        this.frameLayout = (FrameLayout) findViewById(R.id.fragment_container_match_team);
        this.frameLayout.setVisibility(View.INVISIBLE);

        if (this.matchTeamHeaderFragment == null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_match_team, this.matchTeamHeaderFragment = MatchTeamHeaderFragment.newInstance());
            fragmentTransaction.commit();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences(ScoutingData.MY_SCOUTING_PREFS, Context.MODE_PRIVATE);
        this.scoutingData = ScoutingData.create(sharedPreferences);
    }

    @Override
    public ScoutingData getScoutingData() {
        return scoutingData;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages. This is the thing that holds all the different pages
     * and handles us switching between them.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // This is the method that creates the different fragments for each page.
            // Notice that it's pretty similar to the OnClickListener of the menu in
            // MainActivity?
            switch (position) {
                case  0:
                    return PreGameFragment.newInstance();
                case 1:
                    return ScoutAutoFragment.newInstance();
                case 2:
                    return ScoutTeleOpFragment.newInstance();
                case 3:
                    return EndGameFragment.newInstance();
                case 4:
                    return ReviewFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages. IDK why we need to keep track of the number like this but
            // we do so we're doing it. This method is probably called by something at a deeper
            // layer of Android.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // From the name of this method I think it gets the titles of the pages in the
            // fragment. IDK what a lot of this code does in detail. I just guess.
            switch (position) {
                case 0:
                    return "Pre-Match";
                case 1:
                    return "Autonomous";
                case 2:
                    return "Tele-Op";
                case 3:
                    return "Post-Match";
                case 4:
                    return "Review";
            }
            return null;
        }
    }

    /**
     * Method that gets called when a match is started.
     * @param matchNumber the match number of the match that's starting.
     * @param teamNumber Team number of the team being scouted in this match.
     * @param preGame The pre-game data object for this team for this match.
     */
    @Override public void onStartClicked(int matchNumber, int teamNumber, PreGame preGame) {
        // Create the new scouting data object
        this.scoutingData.initMatch(matchNumber, teamNumber);
        this.scoutingData.getMatchData().setPreGame(preGame);

        mViewPager.setCurrentItem(1);
        this.frameLayout.setVisibility(View.VISIBLE);
        this.matchTeamHeaderFragment.setMatchTeam(
                this.scoutingData.getEventCode(),
                this.scoutingData.getScoutName(),
                this.scoutingData.getMatchNumber(),
                this.scoutingData.getTeamNumber(),
                this.scoutingData.getStation().isBlue());
    }

    /**
     * Fragment listener for the scoutautofragment. Adds the auto period to the matchData object
     * when it's created.
     * @param autonomousPeriod The autonomous period object to add to the matchdata
     */
    @Override
    public void onAutoPeriodObjectCreated(AutonomousPeriod autonomousPeriod) {
        scoutingData.getMatchData().setAutonomousPeriod(autonomousPeriod);
        mViewPager.setCurrentItem(2);
    }

    /** Same thing but for teleop.
     * @param teleopPeriod The teleop period object to add to matchdata.
     */
    @Override
    public void onTeleopPeriodObjectCreated(TeleopPeriod teleopPeriod) {
        scoutingData.getMatchData().setTeleopPeriod(teleopPeriod);
        mViewPager.setCurrentItem(3);
    }

    /**
     * Likewise for end-game
     * @param endGame The endgame object to save
     */
    @Override
    public void onReviewClicked(EndGame endGame) {
        scoutingData.getMatchData().setEndGame(endGame);
        mViewPager.setCurrentItem(4);
    }

    /**
     * Liostener for the 'save' button in the Review fragment. If you're looking for the place where
     * data actually gets written then you've found it.
     *
     * @param scoutingData The socuting data object to save for Bluetooth transfer.
     */
    @Override
    public void onSaveClickedListener(ScoutingData scoutingData) {
        // Serialise the scouting data object and save it to a file
        Gson gson = new GsonBuilder().create();
        String serialisedScoutingData = gson.toJson(scoutingData);

        String fileName = String.format(Locale.getDefault(),
                "%1$s_%2$s_%3$s_%4$d_%5$s_%6$s_%7$s.json", scoutingData.getEventCode(),
                scoutingData.getMatchNumber(), scoutingData.getStation(),
                scoutingData.getTeamNumber(), scoutingData.getScoutName(),
                scoutingData.getScoutingTeamName(), scoutingData.getDeviceId());

        SaveDataToFile(fileName, serialisedScoutingData);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Actually does the work of saving the data to a file.
     * @param fileName The name of the file to save to.
     * @param data The data to save.
     */
    private void SaveDataToFile(String fileName, String data) {
        CharSequence text;

        if (this.isExternalStorageWritable()) {
            try {
                File directory = getScoutingDataStorageDir();
                File dataFileHandle = new File(directory, fileName);

                FileOutputStream outputStream = new FileOutputStream(dataFileHandle);
                outputStream.write(data.getBytes());
                outputStream.close();

                text = "File written: " + dataFileHandle.getPath();
            } catch (IOException e) {
                text = "unable to write file because of an exception: " + e.toString();
            }
        }
        else {
            text = "External storage not writeable";
        }

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Checks if external storage is available for read and write
     * @return True if the external storage is writeable, false otherwise.
     */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Gets the directory where the scouting app will store its output files.
     * @return The path to the directory where scouting data is stored.
     */
    public static File getScoutingDataStorageDir() {
        File directoryPath = new File(Environment.getExternalStorageDirectory(),
                "ScoutingData");

        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        }

        return directoryPath;
    }
}

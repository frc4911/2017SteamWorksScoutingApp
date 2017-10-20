package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.AutonomousPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoutAutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoutAutoFragment extends Fragment implements
        ProvidesScoutingData,
        ShotAttemptFragment.OnShotAttemptCreatedListener,
        GearAttemptFragment.OnGearAttemptCreatedListener
{

    OnAutoPeriodObjectCreatedListener mListener;

    CheckBox checkBoxNoShotAttempts;
    CheckBox checkBoxNoGearAttempts;

    ToggleButton toggleButtonCrossedBaseline;
    ToggleButton toggleButtonNoAuto;

    TextView shotAttemptLabel;
    TextView gearAttemptLabel;

    List<ShotAttempt> shotAttempts;
    List<GearAttempt> gearAttempts;
    private ScoutingData scoutingData;

    public ScoutAutoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScoutAutoFragment.
     */
    public static ScoutAutoFragment newInstance() {
        return new ScoutAutoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShotAttemptFragment shotAttemptFragment =
                (ShotAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_auto_shooting);

        if (shotAttemptFragment == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_auto_shooting,
                            ShotAttemptFragment.newInstance());
            fragmentTransaction.commit();
        }

        GearAttemptFragment gearAttemptFragment =
                (GearAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_auto_gear);

        if (gearAttemptFragment == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_auto_gear,
                            GearAttemptFragment.newInstance());
            fragmentTransaction.commit();
        }

        // We need to make VERY SURE that rotating won't mess up our list
        if (shotAttempts == null) {
            shotAttempts = new ArrayList<>();
        }

        if (gearAttempts == null) {
            gearAttempts = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // See note in the OnCreateView method of PreGameFragment
        View view = inflater.inflate(R.layout.fragment_scout_auto, container, false);

        checkBoxNoShotAttempts = (CheckBox) view.findViewById(R.id.checkbox_scout_auto_no_shots);
        checkBoxNoGearAttempts = (CheckBox) view.findViewById(R.id.checkbox_scout_auto_no_gears);

        toggleButtonCrossedBaseline =
                (ToggleButton) view.findViewById(R.id.toggle_button_auto_crossed_baseline);
        toggleButtonNoAuto =
                (ToggleButton) view.findViewById(R.id.toggle_button_auto_no_auto);

        toggleButtonCrossedBaseline.setOnCheckedChangeListener(crossedBaselineOnCheckedListener);
        toggleButtonNoAuto.setOnCheckedChangeListener(noAutoOnCheckedListener);

        shotAttemptLabel = (TextView) view.findViewById(R.id.label_shots_attempted_auto);
        gearAttemptLabel = (TextView) view.findViewById(R.id.label_gears_attempted_auto);

        Button save = (Button) view.findViewById(R.id.button_auto_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoDataSaver saver = new AutoDataSaver();
                validateAutoData(saver);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAutoPeriodObjectCreatedListener) {
            mListener = (OnAutoPeriodObjectCreatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAutoPeriodObjectCreatedListener");
        }

        if (context instanceof ProvidesScoutingData) {
            scoutingData = ((ProvidesScoutingData) context).getScoutingData();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ProvidesScoutingData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onShotAttemptCreated(ShotAttempt shotAttempt) {
        // We don't want to record more than one shot attempt in auto
        if (shotAttempts.size() >= 1) {
            HelperMethods.showSimpleDialog("Invalid shot attempt",
                    "You can only have one shot attempt in auto", getContext());
            return;
        }

        shotAttempts.add(shotAttempt);
        String newLabel = getString(R.string.label_attempts_recorded) + shotAttempts.size();
        shotAttemptLabel.setText(newLabel);
        checkBoxNoShotAttempts.setChecked(false);
        toggleButtonNoAuto.setChecked(false);
    }

    @Override
    public void onGearAttemptCreated(GearAttempt gearAttempt) {
        // We don't want to record more than one gear attempt in auto either
        if (gearAttempts.size() >= 1) {
            HelperMethods.showSimpleDialog("Invalid gear attempt",
                    "You can only have one gear attempt in auto", getContext());
            return;
        }

        gearAttempts.add(gearAttempt);
        String newLabel = getString(R.string.label_attempts_recorded) + gearAttempts.size();
        gearAttemptLabel.setText(newLabel);
        checkBoxNoGearAttempts.setChecked(false);
        toggleButtonNoAuto.setChecked(false);
        toggleButtonCrossedBaseline.setChecked(true);
    }

    /**
     * OnCheckListener for the crossed baseline button that un-checks the 'no auto' button.
     */
    private ToggleButton.OnCheckedChangeListener crossedBaselineOnCheckedListener =
            new ToggleButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggleButtonNoAuto.setChecked(false);
                    }
                }
            };

    /**
     * OnCheckListener for the no auto button that un-checks the 'crossed baseline' button.
     */
    private ToggleButton.OnCheckedChangeListener noAutoOnCheckedListener =
            new ToggleButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (shotAttempts.size() > 0 || gearAttempts.size() > 0) {
                            toggleButtonNoAuto.setChecked(false);
                        } else {
                            toggleButtonCrossedBaseline.setChecked(false);
                        }
                    }
                }
            };

    /**
     * Makes sure there's no partially complete attempts and prompts the user to complete if there
     * are.
     * @param onValid The method to call if validation succeeds.
     */
    private void validateAutoData(Runnable onValid) {
        ShotAttemptFragment shotAttemptFragment =
                (ShotAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_auto_shooting);

        GearAttemptFragment gearAttemptFragment =
                (GearAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_auto_gear);

        if (shotAttemptFragment.isAttemptDataIncomplete()) {
            HelperMethods.showSimpleDialog("Incomplete Shot Attempt",
                    "Please complete the current shot attempt", getContext());
        } else if (gearAttemptFragment.isAttemptDataIncomplete()) {
            HelperMethods.showSimpleDialog("Incomplete Gear Attempt",
                    "Please complete the current gear attempt", getContext());
        } else {
            onValid.run();
        }
    }

    @Override
    public ScoutingData getScoutingData() {
        return scoutingData;
    }

    /**
     * Passes the autonomous period data object up to whoever is listening. Activities that contain
     * this fragment should implement this.
     */
    interface OnAutoPeriodObjectCreatedListener {
        void onAutoPeriodObjectCreated(AutonomousPeriod autonomousPeriod);
    }

    /**
     * Making this a separate class instead of something defined in-line stopped a very annoying
     * bug where clicking the 'save' button would throw an exception complaining about casting
     * auto to teleop fragments. I have NO idea why this worked.
     */
    private class AutoDataSaver implements Runnable {
        public void run() {
            // Create the autonomous period object and hand it on up to the top level activity
            AutonomousPeriod autonomousPeriod = new AutonomousPeriod();
            autonomousPeriod.setAutoMobilityPoints(toggleButtonCrossedBaseline.isChecked());
            autonomousPeriod.getGearAttempts().addAll(gearAttempts);
            autonomousPeriod.getShotAttempts().addAll(shotAttempts);
            autonomousPeriod.setHasAuto(!toggleButtonNoAuto.isChecked());

            // Clear the shot and gear attempt lists
            shotAttempts.clear();
            gearAttempts.clear();

            // And call up to the activity with the data model
            if (mListener != null) {
                mListener.onAutoPeriodObjectCreated(autonomousPeriod);
            }
        }
    }
}

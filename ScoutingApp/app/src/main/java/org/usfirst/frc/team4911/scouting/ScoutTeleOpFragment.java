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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.Ability;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttemptTeleop;
import org.usfirst.frc.team4911.scouting.datamodel.Speed;
import org.usfirst.frc.team4911.scouting.datamodel.TeleopPeriod;
import org.usfirst.frc.team4911.scouting.ui.GearAttemptTeleOpView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoutTeleOpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoutTeleOpFragment extends Fragment implements
        ProvidesScoutingData,
        ShotAttemptTeleOpFragment.OnShotAttemptTeleopCreatedListener {

    OnTeleopPeriodObjectCreatedListener mListener;

    CheckBox checkBoxNoShotAttempts;
    CheckBox checkBoxNoGearAttempts;

    ToggleButton toggleButtonDisconnected;
    ToggleButton toggleButtonBotDied;
    ToggleButton toggleButtonBotDamaged;

    TextView shotAttemptLabel;
    TextView gearAttemptLabel;

    RadioButton radioDrivingSpeedNone;
    RadioButton radioDrivingSpeedSlow;
    RadioButton radioDrivingSpeedMedium;
    RadioButton radioDrivingSpeedFast;

    RadioGroup radioGroupDefence;

    List<ShotAttemptTeleop> shotAttempts;
    List<GearAttemptTeleop> gearAttempts;

    GearAttemptTeleOpView gearAttemptTeleOpView;

    private ScoutingData scoutingData;

    public ScoutTeleOpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScoutTeleOpFragment.
     */
    public static ScoutTeleOpFragment newInstance() {
        return new ScoutTeleOpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShotAttemptTeleOpFragment shotAttemptFragment =
                (ShotAttemptTeleOpFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_tele_op_shooting);

        if (shotAttemptFragment == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_tele_op_shooting,
                            ShotAttemptTeleOpFragment.newInstance());
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
        // See note in OnCreateView of PreGameFragment
        View view = inflater.inflate(R.layout.fragment_scout_tele_op, container, false);

        gearAttemptTeleOpView = (GearAttemptTeleOpView) view.findViewById(R.id.gear_attempt_teleop_view);

        shotAttemptLabel = (TextView) view.findViewById(R.id.label_shots_attempted_teleop);
        gearAttemptLabel = (TextView) view.findViewById(R.id.label_gears_attempted_teleop);

        checkBoxNoShotAttempts = (CheckBox) view.findViewById(R.id.checkbox_scout_teleop_no_shots);
        checkBoxNoGearAttempts = (CheckBox) view.findViewById(R.id.checkbox_scout_teleop_no_gears);

        toggleButtonDisconnected = (ToggleButton) view.findViewById(R.id.togglebutton_teleop_disconnected);
        toggleButtonBotDied = (ToggleButton) view.findViewById(R.id.togglebutton_teleop_dead_bot);
        toggleButtonBotDamaged = (ToggleButton) view.findViewById(R.id.togglebutton_teleop_damaged_bot);

        this.radioDrivingSpeedNone = (RadioButton) view.findViewById(R.id.radio_driving_speed_none);
        this.radioDrivingSpeedSlow = (RadioButton) view.findViewById(R.id.radio_driving_speed_slow);
        this.radioDrivingSpeedMedium = (RadioButton) view.findViewById(R.id.radio_driving_speed_medium);
        this.radioDrivingSpeedFast = (RadioButton) view.findViewById(R.id.radio_driving_speed_fast);

        radioGroupDefence = (RadioGroup) view.findViewById(R.id.radio_group_defence_rating);

        Button buttonGearTeleOpSave = (Button) view.findViewById(R.id.button_scout_tele_op_save_gear_attempt);
        buttonGearTeleOpSave.setOnClickListener(saveGearAttemptTeleop);

        Button save = (Button) view.findViewById(R.id.button_tele_op_save);
        save.setOnClickListener(saveTeleop);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTeleopPeriodObjectCreatedListener) {
            mListener = (OnTeleopPeriodObjectCreatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTeleopPeriodObjectCreatedListener");
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
    public void onShotAttemptTeleopCreated(ShotAttemptTeleop shotAttemptTeleop) {
        shotAttempts.add(shotAttemptTeleop);
        String newLabel = getString(R.string.label_attempts_recorded) + shotAttempts.size();
        shotAttemptLabel.setText(newLabel);
        checkBoxNoShotAttempts.setChecked(false);
    }

    View.OnClickListener saveTeleop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateTeleOpData(() -> {
                TeleopPeriod teleopPeriod = new TeleopPeriod();

                // Add to existing collection - don't replace
                teleopPeriod.getShotAttempts().addAll(shotAttempts);
                teleopPeriod.getGearAttempts().addAll(gearAttempts);

                int checkedButton = radioGroupDefence.getCheckedRadioButtonId();

                switch (checkedButton) {
                    case R.id.radio_defence_rating_none:
                        teleopPeriod.setDefenceRating(Ability.None);
                        break;
                    case R.id.radio_defence_rating_poor:
                        teleopPeriod.setDefenceRating(Ability.Poor);
                        break;
                    case R.id.radio_defence_rating_fair:
                        teleopPeriod.setDefenceRating(Ability.Fair);
                        break;
                    case R.id.radio_defence_rating_good:
                        teleopPeriod.setDefenceRating(Ability.Good);
                        break;
                }

                teleopPeriod.setDisconnected(toggleButtonDisconnected.isChecked());
                teleopPeriod.setBotDied(toggleButtonBotDied.isChecked());
                teleopPeriod.setDamagedBot(toggleButtonBotDamaged.isChecked());
                teleopPeriod.setDrivingSpeed(
                        radioDrivingSpeedSlow.isChecked() ? Speed.Slow :
                        radioDrivingSpeedMedium.isChecked() ? Speed.Medium :
                        radioDrivingSpeedFast.isChecked() ? Speed.Fast : Speed.None);

                if (mListener != null) {
                    mListener.onTeleopPeriodObjectCreated(teleopPeriod);
                }

                restoreDefaults();
            });
        }
    };

    View.OnClickListener saveGearAttemptTeleop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateTeleOpGearAttempt(() -> {
                GearAttemptTeleop gearAttemptTeleop = gearAttemptTeleOpView.getGearAttemptTeleOp();
                gearAttempts.add(gearAttemptTeleop);

                String message = getString(R.string.label_attempts_recorded) + gearAttempts.size();
                gearAttemptLabel.setText(message);
                checkBoxNoGearAttempts.setChecked(false);

                gearAttemptTeleOpView.restoreDefaults();
            });
        }
    };

    /**
     * Checks a gear attempt and displays any appropriate error messages before saving.
     * @param onValid The method to call if validation succeeds.
     */
    private void validateTeleOpGearAttempt(Runnable onValid) {
        if (gearAttemptTeleOpView.isGearAttemptEmpty()) {
            HelperMethods.showSimpleDialog("Empty Attempt", "Cannot save empty attempt", getContext());
        } else if (gearAttemptTeleOpView.isDefendedCheckedWithNoAttempt()) {
            HelperMethods.showSimpleDialog("Incomplete Gear Attempt", "Please clear or complete the data", getContext());
        } else if (gearAttemptTeleOpView.isFailureReasonMissing()) {
            HelperMethods.showSimpleDialog("Attempt Failure", "Why did the attempt fail?", getContext());
        } else {
            onValid.run();
        }
    }

    /**
     * Makes sure there's no partially complete attempts and prompts the user to complete
     * if there are.
     * @param onValid The method to call if validation succeeds.
     */
    private void validateTeleOpData(Runnable onValid) {
        ShotAttemptTeleOpFragment shotAttemptFragment =
                (ShotAttemptTeleOpFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_tele_op_shooting);

        if (shotAttemptFragment.isAttemptDataIncomplete()) {
            HelperMethods.showSimpleDialog("Incomplete Shot Attempt",
                    "Please complete the current shot attempt", getContext());
        } else if (gearAttemptTeleOpView.isDefendedCheckedWithNoAttempt()) {
            HelperMethods.showSimpleDialog("Incomplete Gear Attempt", "Please clear or complete the data", getContext());
        } else if (gearAttemptTeleOpView.isFailureReasonMissing()) {
            HelperMethods.showSimpleDialog("Attempt Failure", "Why did the attempt fail?", getContext());
        } else {
            onValid.run();
        }
    }

    /**
     * Helper method that clears all saved data
     */
    private void restoreDefaults() {
        // Clear the shot and gear attempt lists
        shotAttempts.clear();
        gearAttempts.clear();

        // Restore the toggle buttons
        toggleButtonBotDamaged.setChecked(false);
        toggleButtonBotDied.setChecked(false);
        toggleButtonDisconnected.setChecked(false);

        radioGroupDefence.check(R.id.radio_defence_rating_none);

        this.radioDrivingSpeedSlow.setChecked(false);
        this.radioDrivingSpeedMedium.setChecked(false);
        this.radioDrivingSpeedFast.setChecked(false);
        this.radioDrivingSpeedNone.setChecked(false);

        gearAttemptTeleOpView.restoreDefaults();
    }

    @Override
    public ScoutingData getScoutingData() {
        return scoutingData;
    }

    /**
     * Passes the autonomous period data object up to whoever is listening. Activities that contian
     * this fragment should implement this.
     */
    interface OnTeleopPeriodObjectCreatedListener {
        void onTeleopPeriodObjectCreated(TeleopPeriod teleopPeriod);
    }
}

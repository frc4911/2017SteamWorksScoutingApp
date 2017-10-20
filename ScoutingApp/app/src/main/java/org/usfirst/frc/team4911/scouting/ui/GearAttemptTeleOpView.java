package org.usfirst.frc.team4911.scouting.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.GearResult;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;

/**
 * Created by Anne_ on 3/31/2017.
 *
 * Custom view for teleop gear attempts.
 */

public class GearAttemptTeleOpView extends LinearLayout {
    private Context context;

    ToggleButton toggleButtonSuccess;
    ToggleButton toggleButtonFailure;
    ToggleButton toggleButtonPilotError;
    ToggleButton toggleButtonRobotError;
    CheckBox checkBoxDefended;

    public GearAttemptTeleOpView(Context context) {
        super(context);
        this.context = context;
        setupLayout();
    }

    /**
     * Constructor for the teleop gear custom view
     * @param context The context of the activity it runs in
     */
    public GearAttemptTeleOpView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the context object for use later
        this.context = context;

        // Set up all of the layouts
        setupLayout();
    }

    /**
     * Does all the work of setting up the layout.
     */
    private void setupLayout() {
        // Inflate the XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gear_attempt_teleop, this);

        // Initialize all of the other view objects now that the layout is inflated
        checkBoxDefended = (CheckBox) findViewById(R.id.checkbox_gear_attempt_tele_op_defended);

        toggleButtonSuccess = (ToggleButton) findViewById(R.id.togglebutton_gear_attempt_tele_op_success);
        toggleButtonSuccess.setOnCheckedChangeListener(successOnCheckedListener);

        toggleButtonFailure = (ToggleButton) findViewById(R.id.togglebutton_gear_attempt_tele_op_fail);
        toggleButtonFailure.setOnCheckedChangeListener(failedOnCheckedListener);

        toggleButtonPilotError = (ToggleButton) findViewById(R.id.togglebutton_gear_attempt_tele_op_human_error);
        toggleButtonPilotError.setOnCheckedChangeListener(humanErrorOnCheckedListener);

        toggleButtonRobotError = (ToggleButton) findViewById(R.id.togglebutton_gear_attempt_tele_op_robot_error);
        toggleButtonRobotError.setOnCheckedChangeListener(robotErrorOnCheckedListener);

        Button clear = (Button) findViewById(R.id.button_gear_attempt_tele_op_clear);
        clear.setOnClickListener((View v) -> restoreDefaults());
    }

    /**
     * State change listener for the 'success' button.
     */
    private CompoundButton.OnCheckedChangeListener successOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (toggleButtonFailure.isChecked()) {
                            toggleButtonFailure.setChecked(false);
                        }

                        if (toggleButtonRobotError.isChecked()) {
                            toggleButtonRobotError.setChecked(false);
                        }

                        if (toggleButtonPilotError.isChecked()) {
                            toggleButtonPilotError.setChecked(false);
                        }
                    }
                }
            };

    /**
     * State change listener for the 'failed' button
     */
    private CompoundButton.OnCheckedChangeListener failedOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && toggleButtonSuccess.isChecked()) {
                        toggleButtonSuccess.setChecked(false);
                    }
                }
            };

    /**
     * State change listener for the 'human error' button
     */
    private CompoundButton.OnCheckedChangeListener humanErrorOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!toggleButtonFailure.isChecked()) {
                            toggleButtonFailure.setChecked(true);
                        }

                        if (toggleButtonSuccess.isChecked()) {
                            toggleButtonSuccess.setChecked(false);
                        }

                        if (toggleButtonRobotError.isChecked()) {
                            toggleButtonRobotError.setChecked(false);
                        }
                    }
                }
            };

    /**
     * State change listener for the 'robot error' button
     */
    private CompoundButton.OnCheckedChangeListener robotErrorOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!toggleButtonFailure.isChecked()) {
                            toggleButtonFailure.setChecked(true);
                        }

                        if (toggleButtonSuccess.isChecked()) {
                            toggleButtonSuccess.setChecked(false);
                        }

                        if (toggleButtonPilotError.isChecked()) {
                            toggleButtonPilotError.setChecked(false);
                        }
                    }
                }
            };

    /**
     * Checks whether or not the gear attempt is empty
     *
     * @return True if the attempt is empty and contains no data; false otherwise
     */
    public boolean isGearAttemptEmpty() {
        return !checkBoxDefended.isChecked()
                && !toggleButtonSuccess.isChecked()
                && !toggleButtonFailure.isChecked()
                && !toggleButtonPilotError.isChecked()
                && !toggleButtonRobotError.isChecked();
    }

    /**
     * Used to check whether or not a gear attempt is complete.
     *
     * @return true if there's data, false otherwise.
     */
    public boolean isDefendedCheckedWithNoAttempt() {
        return checkBoxDefended.isChecked()
                && !toggleButtonSuccess.isChecked()
                && !toggleButtonFailure.isChecked();
    }

    /**
     * Call this before calling getGearAttempt to be sure that nothing important is missing.
     *
     * @return 'true' if the failure reason is missing and you should display a popup complaining
     * about it, false if the attempt is ready to be saved.
     */
    public boolean isFailureReasonMissing() {
        return (toggleButtonFailure.isChecked() && !toggleButtonPilotError.isChecked()
                && !toggleButtonRobotError.isChecked());
    }

    /**
     * Blanket validator that gives a 'yes/no' answer. This might be all we need TBH.
     * @return true if the attempt is complete and can be saved, false otherwise.
     */
    public boolean isAttemptComplete() {
        return !isGearAttemptEmpty()
                && !isDefendedCheckedWithNoAttempt()
                && !isFailureReasonMissing();
    }

    /**
     * Returns the gear attempt containing the data in the view. Be careful to call the right
     * validators before you use it.
     *
     * And call {@link GearAttemptTeleOpView#restoreDefaults()} after getting your data in order to
     * be sure that you've cleared all info from the previous attempt.
     *
     * @return A gear attempt object containing the data that's currently in the UI elements.
     */
    public GearAttemptTeleop getGearAttemptTeleOp() {
        GearResult result;

        if (toggleButtonFailure.isChecked()) {
            result = toggleButtonPilotError.isChecked() ? GearResult.PilotError :
                    GearResult.RobotError;
        } else {
            result = GearResult.Success;
        }

        GearAttemptTeleop gearAttemptTeleop = new GearAttemptTeleop();
        gearAttemptTeleop.setGearResult(result);
        gearAttemptTeleop.setWasDefended(checkBoxDefended.isChecked());

        return gearAttemptTeleop;
    }

    /**
     * Restores all view components to their default values.
     */
    public void restoreDefaults() {
        toggleButtonSuccess.setChecked(false);
        toggleButtonFailure.setChecked(false);
        toggleButtonPilotError.setChecked(false);
        toggleButtonRobotError.setChecked(false);
        checkBoxDefended.setChecked(false);
    }
}

package org.usfirst.frc.team4911.scouting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.GearPegPosition;
import org.usfirst.frc.team4911.scouting.datamodel.GearResult;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;

/**
 * Code for the fragment which handles recording gear events.
 * Use the {@link GearAttemptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GearAttemptFragment extends Fragment implements
        RecordLocationFragment.OnLocationSelectedListener, AttemptDataComplete {

    private OnGearAttemptCreatedListener mListener;

    GearPegPosition gearPegPosition = GearPegPosition.None;

    private ToggleButton toggleButtonGearSuccess;
    private ToggleButton toggleButtonGearFailed;
    private ToggleButton toggleButtonRobotError;
    private ToggleButton toggleButtonPilotError;

    private ScoutingData scoutingData;

    public GearAttemptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GearAttemptFragment.
     */
    public static GearAttemptFragment newInstance() {
        return new GearAttemptFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    /**
     * Part of binding the parent fragment interaction listener to this method.
     *
     * @param fragment The parent fragment whose listener we're binding to.
     */
    private void onAttachToParentFragment(Fragment fragment) {
        try {
            mListener = (OnGearAttemptCreatedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnGearAttemptCreatedListener");
        }

        if (fragment instanceof ProvidesScoutingData) {
            scoutingData = ((ProvidesScoutingData) fragment).getScoutingData();
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement ProvidesScoutingData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gear_attempt, container, false);

        toggleButtonGearSuccess =
                (ToggleButton) view.findViewById(R.id.togglebutton_record_gear_success);
        toggleButtonGearFailed =
                (ToggleButton) view.findViewById(R.id.togglebutton_record_gear_fail);
        toggleButtonRobotError =
                (ToggleButton) view.findViewById(R.id.togglebutton_record_gear_robot_error);
        toggleButtonPilotError =
                (ToggleButton) view.findViewById(R.id.togglebutton_record_gear_human_error);

        toggleButtonGearSuccess.setOnCheckedChangeListener(gearSuccessOnCheckedListener);
        toggleButtonGearFailed.setOnCheckedChangeListener(gearFailedOnCheckedListener);
        toggleButtonPilotError.setOnCheckedChangeListener(humanErrorOnCheckedListener);
        toggleButtonRobotError.setOnCheckedChangeListener(robotErrorOnCheckedListener);

        Button save = (Button) view.findViewById(R.id.btn_gear_record_save);
        save.setOnClickListener(saveGearAttempt);

        Button clear = (Button) view.findViewById(R.id.btn_gear_record_clear);
        clear.setOnClickListener(clearOnClickListener);

        return view;
    }

    /**
     * Handles touch events on the location map.
     */
    @Override
    public boolean onLocationSelected(Pair<Float, Float> normalisedTouchPoint) {
        boolean isBlueAlliance = scoutingData.getStation().isBlue();
        gearPegPosition = LocationMappingHelpers.GetGearPegPosition(normalisedTouchPoint,
                isBlueAlliance);

        String message;

        if (gearPegPosition == GearPegPosition.None) {
            message = "Please select a valid gear peg position";
        } else {
            message = "Gear peg " + gearPegPosition.toString() + " selected.";
        }

        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        return gearPegPosition != GearPegPosition.None;
    }

    /**
     * Called by parent fragment on save.
     *
     * @return 'true' if there's no incomplete data, false otherwise.
     */
    @Override
    public boolean isAttemptDataIncomplete() {
        boolean positionNotSet =
                (toggleButtonGearSuccess.isChecked() || toggleButtonGearFailed.isChecked())
                && gearPegPosition == GearPegPosition.None;
        boolean noFailureReason = (toggleButtonGearFailed.isChecked() && !toggleButtonPilotError.isChecked()
                && !toggleButtonRobotError.isChecked());

        return positionNotSet || noFailureReason;
    }

    /**
     * OnClickListener for the save button that records the current gear event.
     */
    private View.OnClickListener saveGearAttempt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // We need to make sure that the data we're collecting is good
            // before we try to save it.
            validateUiState(() -> {
                GearResult result;

                if (toggleButtonGearFailed.isChecked()) {
                    result = toggleButtonPilotError.isChecked() ? GearResult.PilotError :
                            GearResult.RobotError;
                } else {
                    result = GearResult.Success;
                }

                GearAttempt gearAttempt = new GearAttempt();
                gearAttempt.setGearResult(result);
                gearAttempt.setGearPegPosition(gearPegPosition);

                // We also validate the object itself
                if (gearAttempt.isDataBad()) {
                    HelperMethods.showSimpleDialog("Corrupt Data",
                            "Check that all the fields have values that make sense", v);
                    return;
                }

                // Call the parent activity and pass it the gear attempt
                if (mListener != null) {
                    mListener.onGearAttemptCreated(gearAttempt);
                }

                restoreDefaults();
            });
        }
    };

    /**
     * OnClickListener for the clear button that wipes all current values.
     */
    private View.OnClickListener clearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            restoreDefaults();
        }
    };

    /**
     * State change listener for the 'success' button.
     */
    private CompoundButton.OnCheckedChangeListener gearSuccessOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (toggleButtonGearFailed.isChecked()) {
                            toggleButtonGearFailed.setChecked(false);
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
    private CompoundButton.OnCheckedChangeListener gearFailedOnCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && toggleButtonGearSuccess.isChecked()) {
                        toggleButtonGearSuccess.setChecked(false);
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
                        if (!toggleButtonGearFailed.isChecked()) {
                            toggleButtonGearFailed.setChecked(true);
                        }

                        if (toggleButtonGearSuccess.isChecked()) {
                            toggleButtonGearSuccess.setChecked(false);
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
                        if (!toggleButtonGearFailed.isChecked()) {
                            toggleButtonGearFailed.setChecked(true);
                        }

                        if (toggleButtonGearSuccess.isChecked()) {
                            toggleButtonGearSuccess.setChecked(false);
                        }

                        if (toggleButtonPilotError.isChecked()) {
                            toggleButtonPilotError.setChecked(false);
                        }
                    }
                }
            };

    /**
     * Validator for the gear attempt data that gets called before the data saves.
     *
     * @param onValid method to call if the UI state is valid.
     */
    private void validateUiState(Runnable onValid) {
        if ((toggleButtonGearSuccess.isChecked() || toggleButtonGearFailed.isChecked())
                && gearPegPosition == GearPegPosition.None) {
            int resourceIdOfMapToDraw = scoutingData.getStation().isRed() ?
                    R.drawable.gear_locations_red : R.drawable.gear_locations_blue;
            RecordLocationFragment.show(resourceIdOfMapToDraw, "Peg Position For Gear Attempt", getChildFragmentManager())
                    .setOnLocationSelectionDone(() -> validateUiState(onValid));
        } else if (toggleButtonGearFailed.isChecked() && !toggleButtonPilotError.isChecked()
                && !toggleButtonRobotError.isChecked()) {
            HelperMethods.showSimpleDialog("Attempt Failure", "Why did the attempt fail?", getContext());
        } else {
            onValid.run();
        }
    }

    /**
     * Clears the current gearResult object and restores all the appropriate defaults.
     */
    protected void restoreDefaults() {
        toggleButtonGearSuccess.setChecked(false);
        toggleButtonGearFailed.setChecked(false);
        toggleButtonPilotError.setChecked(false);
        toggleButtonRobotError.setChecked(false);
        gearPegPosition = GearPegPosition.None;
    }

    /**
     * All activities containing this fragment must implement this interface.
     */
    interface OnGearAttemptCreatedListener {
        void onGearAttemptCreated(GearAttempt gearAttempt);
    }
}

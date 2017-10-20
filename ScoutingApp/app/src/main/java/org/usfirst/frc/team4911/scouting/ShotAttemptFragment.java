package org.usfirst.frc.team4911.scouting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.LocationPosition;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.ShotMode;

/**
 * A simple {@link Fragment} subclass.
 * Contains all data interfaces necessary to collect information about a shooting event.
 * Use the {@link ShotAttemptFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link ShotAttemptFragment.OnShotAttemptCreatedListener} interface
 * to handle interaction events.
 */
public class ShotAttemptFragment extends Fragment implements
        ProvidesScoutingData, AttemptDataComplete,
        RecordLocationFragment.OnLocationSelectedListener,
        RecordLocationFragment.OnLocationSelectionDoneListener {
    private OnShotAttemptCreatedListener mListener;

    private LocationPosition shotLocation = LocationPosition.getInvalidPosition();

    private ToggleButton toggleButtonShotLow;
    private ToggleButton toggleButtonShotHigh;
    private EditText editTextShotsScored;
    private ScoutingData scoutingData;

    public ShotAttemptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment ShotAttemptFragment.
     */
    public static ShotAttemptFragment newInstance() {
        return new ShotAttemptFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    private void onAttachToParentFragment(Fragment fragment) {
        try {
            mListener = (OnShotAttemptCreatedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnShotAttemptCreatedListener");
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
        View view = inflater.inflate(R.layout.fragment_shot_attempt, container, false);

        toggleButtonShotLow = (ToggleButton) view.findViewById(R.id.togglebutton_shot_low);
        toggleButtonShotLow.setOnCheckedChangeListener(onShotLowCheckedListener);

        toggleButtonShotHigh = (ToggleButton) view.findViewById(R.id.togglebutton_shot_high);
        toggleButtonShotHigh.setOnCheckedChangeListener(onShotHighCheckedListener);

        editTextShotsScored = (EditText) view.findViewById(R.id.edit_text_shots_scored);

        Button save = (Button) view.findViewById(R.id.button_shot_attempt_save);
        save.setOnClickListener(saveShotAttempt);

        Button clear = (Button) view.findViewById(R.id.button_shot_attempt_clear);
        clear.setOnClickListener(clearOnClickListener);

        return view;
    }

    /**
     * Handles touch events on the location map.
     */
    @Override
    public boolean onLocationSelected(Pair<Float, Float> normalisedTouchPoint) {
        boolean isBlueAlliance = scoutingData.getStation().isBlue();

        shotLocation = LocationMappingHelpers.GetShootingPosition(normalisedTouchPoint,
                isBlueAlliance);

        String message;

        boolean invalidPosition = shotLocation.equals(LocationPosition.getInvalidPosition());

        if (invalidPosition) {
            message = "Please select a point in the shooting area";
        } else {
            message = "X: " + shotLocation.getXInFeet() + " Y: " + shotLocation.getYInFeet();
        }

        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        return !invalidPosition;
    }


    /**
     * Handles presses of the 'done' button on the record location dialog.
     */
    @Override
    public void onLocationSelectionDone() {
    }

    /**
     * Tells the parent whether or not there's incomplete data
     */
    @Override
    public boolean isAttemptDataIncomplete() {
        boolean isMissingShotCount = false;
        boolean invalidShotsScored = false;
        boolean missingFuelTarget = false;
        boolean missingLocation = false;

        String shotsScoredString = editTextShotsScored.getText().toString();

        if (shotsScoredString.equals("")) {
            if (toggleButtonShotHigh.isChecked()
                    || toggleButtonShotLow.isChecked()
                    || !shotLocation.equals(LocationPosition.getInvalidPosition())) {
                isMissingShotCount = true;
            }
        } else {
            int shotsScored = shotsScoredString.isEmpty() ? 0 : Integer.parseInt(shotsScoredString);
            invalidShotsScored = shotsScored < 0;

            missingFuelTarget = (!invalidShotsScored
                    && !(toggleButtonShotHigh.isChecked() || toggleButtonShotLow.isChecked()));
            missingLocation = shotLocation.equals(LocationPosition.getInvalidPosition());
        }

        return isMissingShotCount || invalidShotsScored || missingFuelTarget || missingLocation;
    }

    /**
     * Listener for state changes of the 'low' button
     */
    private ToggleButton.OnCheckedChangeListener onShotLowCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && toggleButtonShotHigh.isChecked()) {
                        toggleButtonShotHigh.setChecked(false);
                    }
                }
            };

    /**
     * Listener for state changes of the 'high' button
     */
    private ToggleButton.OnCheckedChangeListener onShotHighCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && toggleButtonShotLow.isChecked()) {
                        toggleButtonShotLow.setChecked(false);
                    }
                }
            };

    /**
     * OnClickListener for the clear button that restores all attempt values to their defaults
     */
    private View.OnClickListener clearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            restoreDefaults();
        }
    };

    /**
     * OnTouchListener for the save button.
     */
    private View.OnClickListener saveShotAttempt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateUiState(() -> {
                String shotsScoredString = editTextShotsScored.getText().toString();
                int shotsScored = shotsScoredString.isEmpty() ? 0 : Integer.parseInt(shotsScoredString);

                ShotAttempt shotAttempt = new ShotAttempt();
                shotAttempt.setShotsScored(shotsScored);
                shotAttempt.setShotLocation(shotLocation);
                shotAttempt.setShotMode(toggleButtonShotHigh.isChecked() ?
                        ShotMode.High : ShotMode.Low);

                // Do the last-defence check of the data
                if (shotAttempt.isDataBad()) {
                    HelperMethods.showSimpleDialog("Corrupt Data",
                            "Check that all the fields have values that make sense", v);
                    return;
                }

                // Pass the shot event up to whoever is listening for it
                if (mListener != null) {
                    mListener.onShotAttemptCreated(shotAttempt);
                }

                restoreDefaults();
            });
        }
    };

    /**
     * Validator that gets called before creating a shot attempt that makes sure all the values are
     * correct.
     *
     * @param onValid method to call if the UI state is valid.
     */
    private void validateUiState(Runnable onValid) {
        String shotsScoredString = editTextShotsScored.getText().toString();
        int shotsScored = shotsScoredString.isEmpty() ? 0 : Integer.parseInt(shotsScoredString);

        // We want 0 to be an actively valid value. It's "" that's now invalid.
        if (shotsScoredString.equals("")) {
            if ((toggleButtonShotHigh.isChecked()
                    || toggleButtonShotLow.isChecked()
                    || !shotLocation.equals(LocationPosition.getInvalidPosition()))) {
                HelperMethods.showSimpleDialog("Invalid shots scored",
                        "Please enter the number of shots scored", getContext());
            }
        } else if (shotsScored < 0) {
            HelperMethods.showSimpleDialog("Invalid shots scored",
                    "Number of shots can't be less than 0", getContext());
        } else if (!(toggleButtonShotHigh.isChecked() || toggleButtonShotLow.isChecked())) {
            HelperMethods.showSimpleDialog("Missing Fuel Target",
                    "Please record if shots where HIGH or LOW.", getContext());
        } else if (shotLocation.equals(LocationPosition.getInvalidPosition())) {
            int resourceIdOfMapToDraw = scoutingData.getStation().isRed() ?
                    R.drawable.shootingzone_red : R.drawable.shootingzone_blue;
            RecordLocationFragment.show(resourceIdOfMapToDraw, "Shot Attempt", getChildFragmentManager())
                    .setOnLocationSelectionDone(() -> validateUiState(onValid));
        } else {
            // nothing is wrong so run it
            onValid.run();
        }
    }

    /**
     * Restores the default values of all fields
     */
    protected void restoreDefaults() {
        toggleButtonShotHigh.setChecked(false);
        toggleButtonShotLow.setChecked(false);
        editTextShotsScored.setText("");
        editTextShotsScored.setHint(getString(R.string.string_no_shot_attempt));
        shotLocation = LocationPosition.getInvalidPosition();
    }

    @Override
    public ScoutingData getScoutingData() {
        return scoutingData;
    }

    /**
     * Callback method invoked in the parent activity or fragment when a new shot attempt is
     * created.
     */
    interface OnShotAttemptCreatedListener {
        void onShotAttemptCreated(ShotAttempt shotAttempt);
    }
}

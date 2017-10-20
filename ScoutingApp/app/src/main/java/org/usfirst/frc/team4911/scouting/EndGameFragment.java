package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.Card;
import org.usfirst.frc.team4911.scouting.datamodel.ClimbOutcome;
import org.usfirst.frc.team4911.scouting.datamodel.EndGame;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGameFragment extends Fragment {
    OnReviewClickedListener mListener;

    private RadioGroup radioGroupClimbAttemptPart1;
    private RadioGroup radioGroupClimbAttemptPart2;

    private CheckBox checkBoxNonTechFoul;
    private CheckBox checkBoxTechFoul;

    private ToggleButton toggleButtonHumanYellow;
    private ToggleButton toggleButtonHumanRed;
    private ToggleButton toggleButtonRobotYellow;
    private ToggleButton toggleButtonRobotRed;
    private ToggleButton toggleButtonNoFoul;

    private EditText editTextClimbTime;

    private Card card = Card.None;

    public EndGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EndGameFragment.
     */
    public static EndGameFragment newInstance() {
        return new EndGameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // See note in the OnCreateView method of PreGameFragment
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        radioGroupClimbAttemptPart1 = (RadioGroup) view.findViewById(R.id.radio_group_climb_part_1);
        radioGroupClimbAttemptPart1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                if (checkedRadioButton != null && checkedRadioButton.isChecked()) {
                    radioGroupClimbAttemptPart2.clearCheck();
                }
            }
        });

        radioGroupClimbAttemptPart2 = (RadioGroup) view.findViewById(R.id.radio_group_climb_part_2);
        radioGroupClimbAttemptPart2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                if (checkedRadioButton != null && checkedRadioButton.isChecked()) {
                    radioGroupClimbAttemptPart1.clearCheck();
                }
            }
        });

        editTextClimbTime = (EditText) view.findViewById(R.id.edit_text_end_game_climb_time);

        checkBoxNonTechFoul = (CheckBox) view.findViewById(R.id.checkbox_end_game_non_tech_foul);
        checkBoxNonTechFoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonNoFoul.setChecked(false);
                }
            }
        });

        checkBoxTechFoul = (CheckBox) view.findViewById(R.id.checkbox_end_game_tech_foul);
        checkBoxTechFoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonNoFoul.setChecked(false);
                }
            }
        });

        toggleButtonHumanYellow = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_human_yellow);
        toggleButtonHumanYellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonHumanRed.setChecked(false);
                    toggleButtonRobotYellow.setChecked(false);
                    toggleButtonRobotRed.setChecked(false);
                    toggleButtonNoFoul.setChecked(false);
                    card = Card.HumanYellow;
                }
            }
        });

        toggleButtonHumanRed = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_human_red);
        toggleButtonHumanRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonHumanYellow.setChecked(false);
                    toggleButtonRobotYellow.setChecked(false);
                    toggleButtonRobotRed.setChecked(false);
                    toggleButtonNoFoul.setChecked(false);
                    card = Card.HumanRed;
                }
            }
        });

        toggleButtonRobotYellow = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_robot_yellow);
        toggleButtonRobotYellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonHumanYellow.setChecked(false);
                    toggleButtonHumanRed.setChecked(false);
                    toggleButtonRobotRed.setChecked(false);
                    toggleButtonNoFoul.setChecked(false);
                    card = Card.RobotYellow;
                }
            }
        });

        toggleButtonRobotRed = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_robot_red);
        toggleButtonRobotRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButtonHumanYellow.setChecked(false);
                    toggleButtonHumanRed.setChecked(false);
                    toggleButtonRobotYellow.setChecked(false);
                    toggleButtonNoFoul.setChecked(false);
                    card = Card.RobotRed;
                }
            }
        });

        toggleButtonNoFoul = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_no_foul);
        toggleButtonNoFoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxTechFoul.setChecked(false);
                    checkBoxNonTechFoul.setChecked(false);
                    toggleButtonHumanYellow.setChecked(false);
                    toggleButtonHumanRed.setChecked(false);
                    toggleButtonRobotYellow.setChecked(false);
                    toggleButtonRobotRed.setChecked(false);
                    card = Card.None;
                }
            }
        });

        Button buttonSaveClimbAttempt = (Button) view.findViewById(R.id.button_climb_attempt_save);
        buttonSaveClimbAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndGame endGame = getEndGameData();

                if (endGame.isDataBad()) {
                    HelperMethods.showSimpleDialog("Corrupt Climbing Data",
                            "Check that all the fields have values that make sense", v);
                }
            }
        });

        Button goToReview = (Button) view.findViewById(R.id.button_end_game_review);
        goToReview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EndGame endGame = getEndGameData();

                // And then the end game model
                if (endGame.isDataBad()) {
                    HelperMethods.showSimpleDialog("Corrupt Data",
                            "Check that all the fields have values that make sense", v);
                    return;
                }

                if (mListener != null) {
                    mListener.onReviewClicked(endGame);
                }

                restoreDefaults();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReviewClickedListener) {
            mListener = (OnReviewClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSaveAndClearClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private EndGame getEndGameData() {
        EndGame endGame = new EndGame();

        endGame.setNonTechFoul(checkBoxNonTechFoul.isChecked());
        endGame.setTechFoul(checkBoxTechFoul.isChecked());
        endGame.setCard(card);

        String climbTimeAsString = editTextClimbTime.getText().toString();
        int climbTime = climbTimeAsString.isEmpty() ? 0 : Integer.parseInt(climbTimeAsString);

        endGame.setTimeInSeconds(climbTime);

        if (radioGroupClimbAttemptPart1.getCheckedRadioButtonId() != -1) {
            switch (radioGroupClimbAttemptPart1.getCheckedRadioButtonId()) {
                case (R.id.radio_button_climb_success):
                    endGame.setClimbOutcome(ClimbOutcome.Success);
                    break;
                case (R.id.radio_button_climb_too_slow):
                    endGame.setClimbOutcome(ClimbOutcome.ClimbTooSlow);
                    break;
                case (R.id.radio_button_climb_slid_down_rope):
                    endGame.setClimbOutcome(ClimbOutcome.SlidDownRope);
                    break;
                case (R.id.radio_button_climb_grab_fail):
                    endGame.setClimbOutcome(ClimbOutcome.DidNotGrabRopeInTime);
                    break;
                default:
                    break;
            }
        } else if (radioGroupClimbAttemptPart2.getCheckedRadioButtonId() != -1) {
            switch (radioGroupClimbAttemptPart2.getCheckedRadioButtonId()) {
                case R.id.radio_button_poor_climber:;
                    endGame.setClimbOutcome(ClimbOutcome.SucceededButPoorClimber);
                    break;
                case R.id.radio_button_climb_rope_broke:
                    endGame.setClimbOutcome(ClimbOutcome.RopeBroke);
                    break;
                case R.id.radio_button_climb_pilot_drop_late:
                    endGame.setClimbOutcome(ClimbOutcome.PilotDroppedRopeLate);
                    break;
                case R.id.radio_button_climb_no_attempt:
                    endGame.setClimbOutcome(ClimbOutcome.NoAttempt);
                    break;
                default:
                    break;
            }
        } else {
            HelperMethods.showSimpleDialog("Something went horribly wrong", "Go tell Anne or Johan", getContext());
            endGame.setClimbOutcome(ClimbOutcome.NoAttempt);
        }

        return endGame;
    }

    /**
     * Clears the end-game data
     */
    private void restoreDefaults() {
        radioGroupClimbAttemptPart1.clearCheck();
        radioGroupClimbAttemptPart2.clearCheck();
    }

    /**
     * Passes the end-game data object created when the start match button is clicked up to
     * whoever's listening.
     */
    interface OnReviewClickedListener {
        void onReviewClicked(EndGame endGame);
    }
}

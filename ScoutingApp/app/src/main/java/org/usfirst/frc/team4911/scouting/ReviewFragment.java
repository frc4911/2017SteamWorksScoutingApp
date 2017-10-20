package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.Ability;
import org.usfirst.frc.team4911.scouting.datamodel.AutonomousPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.Card;
import org.usfirst.frc.team4911.scouting.datamodel.ClimbOutcome;
import org.usfirst.frc.team4911.scouting.datamodel.EndGame;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.PreGame;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttemptTeleop;
import org.usfirst.frc.team4911.scouting.datamodel.Speed;
import org.usfirst.frc.team4911.scouting.datamodel.TeleopPeriod;
import org.usfirst.frc.team4911.scouting.ui.AutoReviewView;
import org.usfirst.frc.team4911.scouting.ui.DividerItemDecoration;
import org.usfirst.frc.team4911.scouting.ui.GearAttemptAdapter;
import org.usfirst.frc.team4911.scouting.ui.GearAttemptTeleopReviewListView;
import org.usfirst.frc.team4911.scouting.ui.ShotAttemptAdapter;
import org.usfirst.frc.team4911.scouting.ui.ShotAttemptTeleopAdapter;
import org.usfirst.frc.team4911.scouting.ui.TeamSpinnerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Review page fragment.
 * Activities that contain this fragment must implement the
 * {@link ReviewFragment.ReviewFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {
    private ReviewFragmentInteractionListener mListener;

    //Setup
    private EditText editTextScoutName;
    private EditText editTextMatchNumber;
    private TeamSpinnerView teamSpinnerView;
    private CheckBox checkBoxNoShow;

    // Auto
    private AutoReviewView autoReviewView;

    // TeleOp
    private GearAttemptTeleopReviewListView gearAttemptTeleopReviewListView;
    private ShotAttemptTeleopAdapter shotAttemptTeleopAdapter;
    private Spinner spinnerDrivingSpeed;
    private Spinner spinnerDefenceRating;
    private CheckBox checkBoxDisconnected;
    private CheckBox checkBoxBotDied;
    private CheckBox checkBoxBotDamaged;

    //End Game
    private EditText editTextClimbTime;
    private Spinner spinnerClimbingOutcome;
    private Spinner spinnerCard;
    private CheckBox checkBoxNonTechFoul;
    private CheckBox checkBoxTechFoul;

    // Review
    private ToggleButton toggleButtonMistake;

    // Data
    private ScoutingData scoutingData;
    private List<ShotAttemptTeleop> shotAttemptTeleopList = new ArrayList<>();

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReviewFragment.
     */
    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        // Setup
        editTextScoutName = (EditText) view.findViewById(R.id.edit_text_review_scout_name);
        editTextMatchNumber = (EditText) view.findViewById(R.id.edit_text_review_match_number);
        teamSpinnerView = (TeamSpinnerView) view.findViewById(R.id.team_spinner_review);
        teamSpinnerView.Init(scoutingData.getEventCode());
        checkBoxNoShow = (CheckBox) view.findViewById(R.id.checkbox_review_setup_no_show);

        // Auto
        autoReviewView = (AutoReviewView) view.findViewById(R.id.auto_review);
        int resourceIdOfMapToDraw = scoutingData.getStation().isRed() ? R.drawable.gear_locations_red : R.drawable.gear_locations_blue;
        autoReviewView.loadLaunchPadMap(resourceIdOfMapToDraw);

        // TeleOp
        gearAttemptTeleopReviewListView = (GearAttemptTeleopReviewListView) view.findViewById(R.id.gear_attempt_teleop_view);
        RecyclerView recyclerViewShotAttemptTeleOp = (RecyclerView) view.findViewById(R.id.recycler_view_review_teleop_shot);
        shotAttemptTeleopAdapter = new ShotAttemptTeleopAdapter(shotAttemptTeleopList);

        RecyclerView.LayoutManager shotLayoutTeleOpManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewShotAttemptTeleOp.setLayoutManager(shotLayoutTeleOpManager);
        recyclerViewShotAttemptTeleOp.setItemAnimator(new DefaultItemAnimator());
        recyclerViewShotAttemptTeleOp.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewShotAttemptTeleOp.setAdapter(shotAttemptTeleopAdapter);

        spinnerDrivingSpeed = (Spinner) view.findViewById(R.id.spinner_review_driving_speed);
        spinnerDrivingSpeed.setAdapter(new ArrayAdapter<>(getContext(), R.layout.team_spinner, Speed.values()));
        spinnerDefenceRating = (Spinner) view.findViewById(R.id.spinner_review_defence_rating);
        spinnerDefenceRating.setAdapter(new ArrayAdapter<>(getContext(), R.layout.team_spinner, Ability.values()));
        checkBoxDisconnected = (CheckBox) view.findViewById(R.id.checkbox_review_teleop_disconnected);
        checkBoxBotDied = (CheckBox) view.findViewById(R.id.checkbox_review_teleop_bot_died);
        checkBoxBotDamaged = (CheckBox) view.findViewById(R.id.checkbox_review_teleop_bot_damaged);

        // End Game
        editTextClimbTime = (EditText) view.findViewById(R.id.edit_text_review_climb_time);
        spinnerClimbingOutcome = (Spinner) view.findViewById(R.id.spinner_review_climbing_outcome);
        spinnerClimbingOutcome.setAdapter(new ArrayAdapter<>(getContext(), R.layout.team_spinner, ClimbOutcome.values()));
        spinnerCard = (Spinner) view.findViewById(R.id.spinner_review_card);
        spinnerCard.setAdapter(new ArrayAdapter<>(getContext(), R.layout.team_spinner, Card.values()));
        checkBoxNonTechFoul = (CheckBox) view.findViewById(R.id.checkbox_review_end_game_non_tech_foul);
        checkBoxTechFoul = (CheckBox) view.findViewById(R.id.checkbox_review_end_game_tech_foul);

        // Review
        Button getData = (Button) view.findViewById(R.id.button_review_get_scouting_data);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUiValues();
            }
        });

        toggleButtonMistake = (ToggleButton) view.findViewById(R.id.togglebutton_end_game_mistake);

        Button saveData = (Button) view.findViewById(R.id.button_review_save_data_to_file);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDataSaver saver = new ReviewDataSaver();
                saver.run();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReviewFragmentInteractionListener) {
            mListener = (ReviewFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ReviewFragmentInteractionListener");
        }

        if (context instanceof ProvidesScoutingData) {
            scoutingData = ((ProvidesScoutingData) context).getScoutingData();
        } else {
            throw new RuntimeException(context.toString() + " must implement ProvidesScoutingData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This is the method that goes and gets the scouting data. It's here because I couldn't think
     * of an elegant way to make the data load automatically. Doing it when OnCreateView is called
     * doesn't work because that method gets called when you first swipe to the end-game fragment
     * which is before all the data there gets filled in.
     */
    public void updateUiValues() {
        // Setup
        editTextScoutName.setText(scoutingData.getScoutName());
        editTextMatchNumber.setText(String.valueOf(scoutingData.getMatchNumber()));
        checkBoxNoShow.setChecked(scoutingData.getMatchData().getPreGame().isNoShow());
        teamSpinnerView.setSelectedTeam(scoutingData.getTeamNumber());

        // Auto
        autoReviewView.setValues(scoutingData.getMatchData().getAutonomousPeriod());

        // Teleop
        spinnerDrivingSpeed.setSelection(scoutingData.getMatchData().getTeleopPeriod().getDrivingSpeed().getValue());
        spinnerDefenceRating.setSelection(scoutingData.getMatchData().getTeleopPeriod().getDefenceRating().getValue());
        checkBoxDisconnected.setChecked(scoutingData.getMatchData().getTeleopPeriod().isDisconnected());
        checkBoxBotDied.setChecked(scoutingData.getMatchData().getTeleopPeriod().isBotDied());
        checkBoxBotDamaged.setChecked(scoutingData.getMatchData().getTeleopPeriod().isDamagedBot());
        gearAttemptTeleopReviewListView.setAttemptList(scoutingData.getMatchData().getTeleopPeriod().getGearAttempts());
        loadShotAttemptTeleopData();

        // End Game
        editTextClimbTime.setText(String.valueOf(scoutingData.getMatchData().getEndGame().getTimeInSeconds()));
        spinnerClimbingOutcome.setSelection(scoutingData.getMatchData().getEndGame().getClimbOutcome().getValue());
        spinnerCard.setSelection(scoutingData.getMatchData().getEndGame().getCard().getValue());
        checkBoxNonTechFoul.setChecked(scoutingData.getMatchData().getEndGame().isNonTechFoul());
        checkBoxTechFoul.setChecked(scoutingData.getMatchData().getEndGame().isTechFoul());
    }

    /**
     * Guess
     */
    private void loadShotAttemptTeleopData() {
        shotAttemptTeleopList.clear();
        shotAttemptTeleopList.addAll(scoutingData.getMatchData().getTeleopPeriod().getShotAttempts());
        shotAttemptTeleopAdapter.notifyDataSetChanged();
    }

    /**
     * Thread class for the method that updates and saves the matchdata
     */
    private class ReviewDataSaver implements Runnable {
        public void run() {
            String matchNum = editTextMatchNumber.getText().toString();
            int matchNumber = matchNum.isEmpty() ? 0 : Integer.parseInt(matchNum);

            scoutingData.setScoutName(editTextScoutName.getText().toString());
            scoutingData.setMatchNumber(matchNumber);
            scoutingData.setTeamNumber(teamSpinnerView.getSelectedTeamNumber());

            // This is a bit weird in that the bad data gets propagated to the actual model even if
            // it's bad. This is non-ideal and we should probably do something about it.
            if (scoutingData.isDataBad()) {
                HelperMethods.showSimpleDialog("Bad scouting metadata", "Check your name, match number, or team number", getContext());
                return;
            }

            // Pre-game
            PreGame preGame = new PreGame();
            preGame.setNoShow(checkBoxNoShow.isChecked());

            // Auto
            AutonomousPeriod autonomousPeriod = autoReviewView.getAuto();

            if (autonomousPeriod.isDataBad()) {
                HelperMethods.showSimpleDialog("Bad auto data", "Something is wrong with your auto data", getContext());
                return;
            }

            // TeleOp
            TeleopPeriod teleopPeriod = scoutingData.getMatchData().getTeleopPeriod();
            teleopPeriod.setGearAttempts(gearAttemptTeleopReviewListView.getAttemptList());
            teleopPeriod.setDrivingSpeed(Speed.fromInt(spinnerDrivingSpeed.getSelectedItemPosition()));
            teleopPeriod.setDefenceRating(Ability.fromInt(spinnerDefenceRating.getSelectedItemPosition()));
            teleopPeriod.setDisconnected(checkBoxDisconnected.isChecked());
            teleopPeriod.setBotDied(checkBoxBotDied.isChecked());
            teleopPeriod.setDamagedBot(checkBoxBotDamaged.isChecked());

            if (teleopPeriod.isDataBad()) {
                HelperMethods.showSimpleDialog("Bad teleop data", "Something is wrong with your teleop data", getContext());
                return;
            }

            // End Game
            EndGame endGame = scoutingData.getMatchData().getEndGame();
            endGame.setTimeInSeconds(Integer.parseInt(editTextClimbTime.getText().toString()));
            endGame.setClimbOutcome(ClimbOutcome.fromInt(spinnerClimbingOutcome.getSelectedItemPosition()));
            endGame.setCard(Card.fromInt(spinnerCard.getSelectedItemPosition()));
            endGame.setNonTechFoul(checkBoxNonTechFoul.isChecked());
            endGame.setTechFoul(checkBoxTechFoul.isChecked());

            if (endGame.isDataBad()) {
                HelperMethods.showSimpleDialog("Bad end Game data", "Something is wrong with yout end game data", getContext());
                return;
            }

            // Now do all the writes
            scoutingData.getMatchData().setPreGame(preGame);
            scoutingData.getMatchData().setTeleopPeriod(teleopPeriod);
            scoutingData.getMatchData().setAutonomousPeriod(autonomousPeriod);
            scoutingData.getMatchData().setEndGame(endGame);

            // And finally add the mistake flag and save the data
            scoutingData.getMatchData().setMadeMistake(toggleButtonMistake.isChecked());

            if (mListener != null) {
                mListener.onSaveClickedListener(scoutingData);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    @FunctionalInterface
     interface ReviewFragmentInteractionListener {
        void onSaveClickedListener(ScoutingData scoutingData);
    }
}

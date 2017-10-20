package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import org.usfirst.frc.team4911.scouting.datamodel.PreGame;
import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.ui.TeamSpinnerView;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * Use the {@link PreGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreGameFragment extends Fragment {

    OnStartClickedListener mListener;

    private EditText etxtMatchNum;
    private TeamSpinnerView teamSpinnerView;
    private ToggleButton toggleButtonNoShow;

    public PreGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PreGameFragment.
     */
    public static PreGameFragment newInstance() {
        return new PreGameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Remember when we talked about activity lifecycle and how fragments have their own lifecycle
    // only it's a bit different? This is an example of how they're different. When we're dealing
    // with activities, all the layout initialisation stuff happens in OnCreate. With fragments it
    // it generally happens in OnCreateView. That's because OnCreate for fragments runs when the
    // OnCreate of the activity they belong to runs, but OnCreateView is what runs before the
    // fragment is shown to the user. In this case, OnCreateView runs when the user is scrolling
    // through the tabs and has reached one which has the relevant fragment in it.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // First we inflate the view. This allows us to access UI items from code.
        View view = inflater.inflate(R.layout.fragment_pre_game, container, false);

        this.etxtMatchNum = (EditText) view.findViewById(R.id.etxt_pre_game_match_num);
        this.toggleButtonNoShow = (ToggleButton) view.findViewById(R.id.toggleButton_pre_game_no_show);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ScoutingData.MY_SCOUTING_PREFS, Context.MODE_PRIVATE);
        String eventCode = sharedPreferences.getString(ScoutingData.EventCodeKey, "practice");

        this.teamSpinnerView = (TeamSpinnerView) view.findViewById(R.id.team_spinner_pre_game);
        this.teamSpinnerView.Init(eventCode);

        Button btnSaveData = (Button) view.findViewById(R.id.btn_pre_game_start_game);
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First we validate that the UI state is good
                PreGameDataSaver saver = new PreGameDataSaver();
                validateUiState(saver);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartClickedListener) {
            mListener = (OnStartClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Checks that all the user-inputted values make sense.
     *
     * @param onValid method to call if the UI state is valid.
     */
    private void validateUiState(Runnable onValid) {
        int teamNumber = teamSpinnerView.getSelectedTeamNumber();

        String matchNum = etxtMatchNum.getText().toString();
        int matchNumber = matchNum.isEmpty() ? 0 : Integer.parseInt(matchNum);

        if (teamNumber <= 0 || 9999 < teamNumber) {
            HelperMethods.showSimpleDialog("Invalid Team Number",
                    "Please provide a valid team number.", getContext());
        } else if (matchNumber <= 0 || 150 <= matchNumber) {
            HelperMethods.showSimpleDialog("Invalid Match Number",
                    "Please provide a valid match number.", getContext());
        } else {
            onValid.run();
        }
    }

    /**
     * Passes the necessary parameters up to the scoutmatchactivity.
     */
    interface OnStartClickedListener {
        void onStartClicked(int matchNumber, int teamNumber, PreGame preGame);
    }

    /**
     * I got this error again
     * java.lang.ClassCastException: org.usfirst.frc.team4911.scouting.PreGameFragment$2 cannot be cast to org.usfirst.frc.team4911.scouting.ScoutTeleOpFragment$2
     * so I added this since that made the equivalent error go away for teleop.
     */
    private class PreGameDataSaver implements Runnable {
        public void run() {
            int teamNumber = teamSpinnerView.getSelectedTeamNumber();

            String matchNum = etxtMatchNum.getText().toString();
            int matchNumber = matchNum.isEmpty() ? 0 : Integer.parseInt(matchNum);

            PreGame preGame = new PreGame();
            preGame.setNoShow(toggleButtonNoShow.isChecked());

            if (mListener != null) {
                mListener.onStartClicked(matchNumber, teamNumber, preGame);
            }
        }
    }
}

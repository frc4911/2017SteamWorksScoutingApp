package org.usfirst.frc.team4911.scouting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.usfirst.frc.team4911.scouting.datamodel.ScoutingData;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttemptTeleop;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShotAttemptTeleOpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShotAttemptTeleOpFragment extends Fragment
        implements ProvidesScoutingData, AttemptDataComplete,
        ShotAttemptFragment.OnShotAttemptCreatedListener {

    OnShotAttemptTeleopCreatedListener mListener;

    private CheckBox checkBoxWasDefended;
    private ScoutingData scoutingData;

    public ShotAttemptTeleOpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShotAttemptTeleOpFragment.
     */
    public static ShotAttemptTeleOpFragment newInstance() {
        return new ShotAttemptTeleOpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());

        // This is what it looks like when I try to do composition with fragments.
        ShotAttemptFragment shotAttemptFragment =
                (ShotAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_shot_attempt_teleop);

        if (shotAttemptFragment == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_shot_attempt_teleop,
                            ShotAttemptFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    private void onAttachToParentFragment(Fragment fragment) {
        try {
            mListener = (OnShotAttemptTeleopCreatedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnShotAttemptTeleopCreatedListener");
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
        View view = inflater.inflate(R.layout.fragment_shot_attempt_tele_op, container, false);
        checkBoxWasDefended = (CheckBox) view.findViewById(R.id.checkbox_shot_attempt_teleop_defended);
        return view;
    }

    @Override
    public void onShotAttemptCreated(ShotAttempt shotAttempt) {
        // Create a teleop shot event based on this and pass it on up.
        ShotAttemptTeleop shotAttemptTeleop = new ShotAttemptTeleop(shotAttempt);
        shotAttemptTeleop.setWasDefended(checkBoxWasDefended.isChecked());

        if (mListener != null) {
            mListener.onShotAttemptTeleopCreated(shotAttemptTeleop);
        }

        ShotAttemptFragment shotAttemptFragment =
                (ShotAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_shot_attempt_teleop);

        shotAttemptFragment.restoreDefaults();
        checkBoxWasDefended.setChecked(false);
    }

    /**
     * Tells the parent whether or not there's incomplete data
     */
    @Override
    public boolean isAttemptDataIncomplete() {
        ShotAttemptFragment shotAttemptFragment =
                (ShotAttemptFragment) getChildFragmentManager()
                        .findFragmentById(R.id.fragment_container_shot_attempt_teleop);

        return shotAttemptFragment.isAttemptDataIncomplete();
    }

    @Override
    public ScoutingData getScoutingData() {
        return scoutingData;
    }

    /**
     * Callback method invoked in the parent activity or fragment when a new teleop shot attempt is
     * created.
     */
    public interface OnShotAttemptTeleopCreatedListener {
        void onShotAttemptTeleopCreated(ShotAttemptTeleop shotAttemptTeleop);
    }
}

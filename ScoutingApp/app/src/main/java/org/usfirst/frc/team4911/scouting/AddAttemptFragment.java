package org.usfirst.frc.team4911.scouting;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;
import org.usfirst.frc.team4911.scouting.ui.GearAttemptTeleOpView;

/**
 * If you implement this class and *don't* set the listener then very bad things will happen
 * to handle interaction events.
 * Use the {@link AddAttemptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAttemptFragment extends DialogFragment {
    private static final String SAVE_ON_CLICK_LISTENER = "saveOnClickListener";

    private AddAttemptFragmentInteractionListener saveListener;
    GearAttemptTeleOpView attemptView;

    public AddAttemptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddAttemptFragment.
     */
    public static AddAttemptFragment newInstance() {
        return new AddAttemptFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_attempt, container, false);
        LinearLayout linearLayoutAddAttemptContainer = (LinearLayout) view.findViewById(R.id.linear_layout_add_attempt);

        // If anyone ever works out how to pass views to fragments, do let me know.
        attemptView = new GearAttemptTeleOpView(getContext());
        linearLayoutAddAttemptContainer.addView(attemptView);

        Button buttonSave = (Button) view.findViewById(R.id.button_add_attempt_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (saveListener != null) {
                    if (!attemptView.isAttemptComplete()) {
                        Toast toast = Toast.makeText(getContext(), "Incomplete attempt", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    } else {
                        GearAttemptTeleop attempt = attemptView.getGearAttemptTeleOp();
                        saveListener.onSaveButtonPressed(attempt);
                        dismiss();
                    }
                }
            }
        });

        Button buttonCancel = (Button) view.findViewById(R.id.button_add_attempt_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setSaveOnClickListener(AddAttemptFragmentInteractionListener saveListener) {
        this.saveListener = saveListener;
    }

    /**
     * Interface for the save click listener. Note that this gets passed in by the caller instead
     * of the usual bind at runtime. Stop me if that's really hacky.
     */
    @FunctionalInterface
    public interface AddAttemptFragmentInteractionListener {
        void onSaveButtonPressed(GearAttemptTeleop attempt);
    }
}

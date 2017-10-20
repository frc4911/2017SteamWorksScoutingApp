package org.usfirst.frc.team4911.scouting.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.AutonomousPeriod;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;
import org.usfirst.frc.team4911.scouting.datamodel.GearPegPosition;
import org.usfirst.frc.team4911.scouting.datamodel.GearResult;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne_ on 4/6/2017.
 *
 * Review view for autonomous.
 */

public class AutoReviewView extends LinearLayout {
    private Context context;

    private CheckBox checkBoxHasAuto;
    private CheckBox checkBoxCrossedBaseline;
    private ShotAttemptAdapter shotAttemptAdapter;

    private ImageView imageViewLaunchPad;
    private Spinner spinnerLocation;
    private Spinner spinnerResult;

    private List<ShotAttempt> shotAttemptList = new ArrayList<>();

    public AutoReviewView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the context object for use later
        this.context = context;

        // Set up all of the layouts
        setupLayout();
    }

    private void setupLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_auto_review, this);

        checkBoxHasAuto = (CheckBox) findViewById(R.id.checkbox_view_auto_review_has_auto);
        checkBoxHasAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    checkBoxCrossedBaseline.setChecked(false);
                    spinnerLocation.setSelection(GearPegPosition.None.getValue());
                    spinnerResult.setSelection(GearResult.None.getValue());
                }
            }
        });

        checkBoxCrossedBaseline = (CheckBox) findViewById(R.id.checkbox_view_auto_review_crossed_baseline);
        checkBoxCrossedBaseline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxHasAuto.setChecked(true);
                }
            }
        });

        RecyclerView recyclerViewShotAttempt = (RecyclerView) findViewById(R.id.recycler_view_auto_review_shot);
        shotAttemptAdapter = new ShotAttemptAdapter(shotAttemptList);

        RecyclerView.LayoutManager shotLayoutAutoManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerViewShotAttempt.setLayoutManager(shotLayoutAutoManager);
        recyclerViewShotAttempt.setItemAnimator(new DefaultItemAnimator());
        recyclerViewShotAttempt.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewShotAttempt.setAdapter(shotAttemptAdapter);
        imageViewLaunchPad = (ImageView) findViewById(R.id.image_view_auto_review_launchpad);

        spinnerLocation = (Spinner) findViewById(R.id.spinner_auto_review_gear_location);
        spinnerLocation.setAdapter(new ArrayAdapter<>(context, R.layout.team_spinner, GearPegPosition.values()));

        spinnerResult = (Spinner) findViewById(R.id.spinner_auto_review_gear_result);
        spinnerResult.setAdapter(new ArrayAdapter<>(context, R.layout.team_spinner, GearResult.values()));
    }

    /**
     * Displays the launchpad with the given resource ID. You need to call this with red/blue info.
     * @param launchPadMapId The resource ID of the launchpad image to load
     */
    public void loadLaunchPadMap(int launchPadMapId) {
        imageViewLaunchPad.setImageBitmap(BitmapFactory.decodeResource(getResources(), launchPadMapId));
    }

    public void setValues(AutonomousPeriod auto) {
        checkBoxHasAuto.setChecked(auto.hasAuto());
        checkBoxCrossedBaseline.setChecked(auto.autoMobilityPoints());

        if (auto.getGearAttempts().size() == 1) {
            spinnerLocation.setSelection(auto.getGearAttempts().get(0).getGearPegPosition().getValue());
            spinnerResult.setSelection(auto.getGearAttempts().get(0).getGearResult().getValue());
        } else {
            spinnerLocation.setSelection(GearPegPosition.None.getValue());
            spinnerResult.setSelection(GearResult.None.getValue());
        }

        shotAttemptList.clear();
        shotAttemptList.addAll(auto.getShotAttempts());
        shotAttemptAdapter.notifyDataSetChanged();
    }

    public AutonomousPeriod getAuto() {
        AutonomousPeriod auto = new AutonomousPeriod();
        auto.setHasAuto(checkBoxHasAuto.isChecked());
        auto.setAutoMobilityPoints(checkBoxCrossedBaseline.isChecked());

        auto.getShotAttempts().clear();
        auto.getShotAttempts().addAll(shotAttemptList);

        // Gear attempts are a little fancy
        GearPegPosition location = GearPegPosition.fromInt(spinnerLocation.getSelectedItemPosition());
        GearResult result = GearResult.fromInt(spinnerResult.getSelectedItemPosition());

        if (location != GearPegPosition.None && result != GearResult.None) {
            GearAttempt attempt = new GearAttempt();
            attempt.setGearPegPosition(location);
            attempt.setGearResult(result);

            List<GearAttempt> attempts = new ArrayList<>();
            attempts.add(attempt);
            auto.getGearAttempts().clear();
            auto.getGearAttempts().addAll(attempts);
        }

        return auto;
    }
}

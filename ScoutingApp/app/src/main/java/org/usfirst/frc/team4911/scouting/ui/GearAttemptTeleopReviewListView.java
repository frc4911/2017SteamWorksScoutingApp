package org.usfirst.frc.team4911.scouting.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.usfirst.frc.team4911.scouting.AddAttemptFragment;
import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne_ on 4/4/2017.
 *
 * Custom view for the teleop gear attempt review list.
 */

public class GearAttemptTeleopReviewListView extends LinearLayout implements
        AddAttemptFragment.AddAttemptFragmentInteractionListener {
    private Context context;

    private GearAttemptTeleopAdapter adapter;
    private List<GearAttemptTeleop> attemptList = new ArrayList<>();
    private AddAttemptFragment.AddAttemptFragmentInteractionListener isThisEvenGoingToWork = (AddAttemptFragment.AddAttemptFragmentInteractionListener) this;

    // This constructor is called implicitly when the view is created. The context is essentially
    // the activity from which this was created (so you can use it for getting resources or other
    // things). The AttributeSet is the set of attributes that are defined in the XML layout, and
    // are used to configure how the view looks
    public GearAttemptTeleopReviewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupLayout();
    }

    private void setupLayout() {
        // Inflate the XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gear_attempt_teleop_review_list_layout, this);

        // Do the needful
        RecyclerView recyclerViewGearAttemptTeleOp = (RecyclerView) findViewById(R.id.recycler_view_gear_attempt_teleop_review_list);
        adapter = new GearAttemptTeleopAdapter(attemptList);

        RecyclerView.LayoutManager mLayoutTeleOpManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerViewGearAttemptTeleOp.setLayoutManager(mLayoutTeleOpManager);
        recyclerViewGearAttemptTeleOp.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGearAttemptTeleOp.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewGearAttemptTeleOp.setAdapter(adapter);

        Button buttonAdd = (Button) findViewById(R.id.button_gear_attempt_teleop_review_list_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAttemptFragment addAttemptFragment = AddAttemptFragment.newInstance();
                addAttemptFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "Enter attempt");
                addAttemptFragment.setSaveOnClickListener(isThisEvenGoingToWork);
            }
        });

        Button buttonDelete = (Button) findViewById(R.id.button_gear_attempt_teleop_review_list_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmDelete = new AlertDialog.Builder(context)
                        //set message, title, and icon
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int position = adapter.getSelectedItemPosition();
                                attemptList.remove(position);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                confirmDelete.show();
            }
        });
    }

    /**
     * Getter for the attempt list
     * @return The list of gear attempts displayed in this view
     */
    public List<GearAttemptTeleop> getAttemptList() {
        return attemptList;
    }

    /**
     * Sets the attempt list.
     */
    public void setAttemptList(List<GearAttemptTeleop> attemptList) {
        this.attemptList.clear();
        this.attemptList.addAll(attemptList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveButtonPressed(GearAttemptTeleop attempt) {
        this.attemptList.add(attempt);
        adapter.notifyDataSetChanged();
    }
}

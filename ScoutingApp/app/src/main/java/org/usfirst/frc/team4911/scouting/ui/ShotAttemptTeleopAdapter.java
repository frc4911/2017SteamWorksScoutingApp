package org.usfirst.frc.team4911.scouting.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttemptTeleop;

import java.util.List;

/**
 * Created by Anne_ on 4/3/2017.
 *
 * Adapter for teleop shot attempts.
 */

public class ShotAttemptTeleopAdapter extends RecyclerView.Adapter<ShotAttemptTeleopAdapter.MyViewHolder> {
    private List<ShotAttemptTeleop> attemptList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewShotsScored;
        TextView textViewPosition;
        TextView textViewShotMode;
        TextView textViewDefended;

        MyViewHolder(View view) {
            super(view);
            textViewShotsScored = (TextView) view.findViewById(R.id.text_view_teleop_shot_list_scored);
            textViewPosition = (TextView) view.findViewById(R.id.text_view_teleop_shot_list_position);
            textViewShotMode = (TextView) view.findViewById(R.id.text_view_teleop_shot_list_mode);
            textViewDefended = (TextView) view.findViewById(R.id.text_view_teleop_shot_list_defended);
        }
    }

    public ShotAttemptTeleopAdapter(List<ShotAttemptTeleop> attemptList) {
        this.attemptList = attemptList;
    }

    @Override
    public ShotAttemptTeleopAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teleop_shot_list_item, parent, false);
        return new ShotAttemptTeleopAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShotAttemptTeleopAdapter.MyViewHolder holder, int position) {
        ShotAttemptTeleop attempt = attemptList.get(position);

        String scoredMessage = "Shots Scored: " + attempt.getShotsScored();
        holder.textViewShotsScored.setText(scoredMessage);

        String positionMessage = "Position: " + attempt.getShotLocation().toString();
        holder.textViewPosition.setText(positionMessage);

        String modeMessage = "Shot Mode: " + attempt.getShotMode();
        holder.textViewShotMode.setText(modeMessage);

        String defendedMessage = "Defended: " + (attempt.isWasDefended() ? "yes" : "no");
        holder.textViewDefended.setText(defendedMessage);
    }

    @Override
    public int getItemCount() {
        return attemptList.size();
    }
}

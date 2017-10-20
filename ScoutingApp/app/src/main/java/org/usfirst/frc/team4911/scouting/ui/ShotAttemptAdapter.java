package org.usfirst.frc.team4911.scouting.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.ShotAttempt;

import java.util.List;

/**
 * Created by Anne_ on 4/3/2017.
 *
 * Adapter for auto shot attempts.
 */

public class ShotAttemptAdapter extends RecyclerView.Adapter<ShotAttemptAdapter.MyViewHolder> {
    private List<ShotAttempt> attemptList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewShotsScored;
        TextView textViewPosition;
        TextView textViewShotMode;

        MyViewHolder(View view) {
            super(view);
            textViewShotsScored = (TextView) view.findViewById(R.id.text_view_auto_shot_list_scored);
            textViewPosition = (TextView) view.findViewById(R.id.text_view_auto_shot_list_position);
            textViewShotMode = (TextView) view.findViewById(R.id.text_view_auto_shot_list_mode);
        }
    }

    public ShotAttemptAdapter(List<ShotAttempt> attemptList) {
        this.attemptList = attemptList;
    }

    @Override
    public ShotAttemptAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_attempt_list_item, parent, false);
        return new ShotAttemptAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShotAttemptAdapter.MyViewHolder holder, int position) {
        ShotAttempt attempt = attemptList.get(position);

        String scoredMessage = "Shots Scored: " + attempt.getShotsScored();
        holder.textViewShotsScored.setText(scoredMessage);

        String positionMessage = "Position: " + attempt.getShotLocation().toString();
        holder.textViewPosition.setText(positionMessage);

        String modeMessage = "Shot Mode: " + attempt.getShotMode();
        holder.textViewShotMode.setText(modeMessage);
    }

    @Override
    public int getItemCount() {
        return attemptList.size();
    }
}

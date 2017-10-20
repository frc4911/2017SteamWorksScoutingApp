package org.usfirst.frc.team4911.scouting.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttempt;

import java.util.List;

/**
 * Created by Anne_ on 4/3/2017.
 *
 * Adapter for the gear attempt class.
 */

public class GearAttemptAdapter extends RecyclerView.Adapter<GearAttemptAdapter.MyViewHolder> {
    private List<GearAttempt> attemptList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewResult;
        TextView textViewPosition;

        MyViewHolder(View view) {
            super(view);
            textViewResult = (TextView) view.findViewById(R.id.text_view_gear_list_result);
            textViewPosition = (TextView) view.findViewById(R.id.text_view_gear_list_position);
        }
    }

    public GearAttemptAdapter(List<GearAttempt> attemptList) {
        this.attemptList = attemptList;
    }

    @Override
    public GearAttemptAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gear_list_item, parent, false);
        return new GearAttemptAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GearAttemptAdapter.MyViewHolder holder, int position) {
        GearAttempt attempt = attemptList.get(position);

        String resultMessage = "Result: " + attempt.getGearResult().toString();
        holder.textViewResult.setText(resultMessage);

        String defendedMessage = "Position: " + attempt.getGearPegPosition().toString();
        holder.textViewPosition.setText(defendedMessage);
    }

    @Override
    public int getItemCount() {
        return attemptList.size();
    }
}

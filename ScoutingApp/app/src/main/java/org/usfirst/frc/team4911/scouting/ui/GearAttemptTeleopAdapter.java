package org.usfirst.frc.team4911.scouting.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop;

import java.util.List;

/**
 * Created by Anne_ on 4/2/2017.
 *
 * Array adapter for {@link org.usfirst.frc.team4911.scouting.datamodel.GearAttemptTeleop} objects
 */

class GearAttemptTeleopAdapter extends RecyclerView.Adapter<GearAttemptTeleopAdapter.MyViewHolder> {
    private List<GearAttemptTeleop> attemptList;
    private int selectedItemPosition = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutContainer;
        TextView textViewResult;
        TextView textViewDefended;

        MyViewHolder(View view) {
            super(view);
            linearLayoutContainer = (LinearLayout) view.findViewById(R.id.linear_layout_teleop_gear_list_background);
            textViewResult = (TextView) view.findViewById(R.id.text_view_teleop_gear_list_result);
            textViewDefended = (TextView) view.findViewById(R.id.text_view_teleop_gear_list_defended);
        }
    }

    GearAttemptTeleopAdapter(List<GearAttemptTeleop> attemptList) {
        this.attemptList = attemptList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teleop_gear_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GearAttemptTeleop attempt = attemptList.get(position);

        if(selectedItemPosition == position){
            // Here I am just highlighting the background
            holder.itemView.setBackgroundColor(Color.parseColor("#ff33cc"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FF99CC"));
        }

        String resultMessage = "Result: " + attempt.getGearResult().toString();
        holder.textViewResult.setText(resultMessage);

        String defendedMessage = "Defended: " + (attempt.isWasDefended() ? "yes" : "no");
        holder.textViewDefended.setText(defendedMessage);

        holder.linearLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedItemPosition);
                selectedItemPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedItemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attemptList.size();
    }

    /**
     * Gets the position of the selected item.
     * @return The position of the selected item.
     */
    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }
}

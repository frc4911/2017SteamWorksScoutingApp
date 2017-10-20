package org.usfirst.frc.team4911.scouting;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;

public class MatchTeamHeaderFragment extends Fragment {

    private LinearLayout linearLayoutMatch;
    private TextView textViewEvent;
    private TextView textViewScout;
    private TextView textViewMatch;
    private TextView textViewTeam;
    private String[] eventNames;
    private String[] eventCodes;

    public static MatchTeamHeaderFragment newInstance() {
        return new MatchTeamHeaderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // First we inflate the view. This allows us to access UI items from code.
        View v = inflater.inflate(R.layout.fragement_match_team_header, container, false);
        this.linearLayoutMatch = (LinearLayout) v.findViewById(R.id.layout_match_header);
        this.textViewEvent = (TextView) v.findViewById(R.id.textView_match_header_event);
        this.textViewScout = (TextView) v.findViewById(R.id.textView_match_header_scout);
        this.textViewMatch = (TextView) v.findViewById(R.id.textView_match_header_match);
        this.textViewTeam = (TextView) v.findViewById(R.id.textView_match_header_team);

        Resources res = getResources();
        this.eventNames = res.getStringArray(R.array.pnw_events_array);
        this.eventCodes = res.getStringArray(R.array.pnw_eventcodes_array);
        return v;
    }

    public void setMatchTeam(String eventCode,  String scoutName, int match, int team, boolean isBlue) {
        String eventName = "unknown";

        for (int i = 0; i < this.eventCodes.length; i++) {
            if (eventCodes[i].equals(eventCode))
            {
                eventName = eventNames[i];
                break;
            }
        }

        this.textViewEvent.setText(eventName);
        this.textViewScout.setText(scoutName);
        this.textViewMatch.setText(String.format("Match %d", match));
        this.textViewTeam.setText(String.format("Team %d", team));
        this.linearLayoutMatch.setBackgroundColor(isBlue ? BLUE : RED);
    }
}
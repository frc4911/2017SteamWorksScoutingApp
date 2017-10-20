package org.usfirst.frc.team4911.scouting.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.usfirst.frc.team4911.scouting.R;
import org.usfirst.frc.team4911.scouting.datamodel.MatchData;


public class ExampleCustomView extends LinearLayout {

    private Context context;
    private int defaultValue;
    private EditText counter;

    // This is added here as an example of the data model you could be writing to, this could
    // very well be any generic data container (teleop, auto, end game, etc)
    private MatchData matchData;

    // This constructor is called implicitly when the view is created. The context is essentially
    // the activity from which this was created (so you can use it for getting resources or other
    // things). The AttributeSet is the set of attributes that are defined in the XML layout, and
    // are used to configure how the view looks
    public ExampleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the context object for use later
        this.context = context;

        // Set up all of the layouts
        setupLayout(attrs);
    }

    private void setupLayout(AttributeSet attrs) {
        // Inflate the XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.example_layout, this);

        // Initialize all of the other view objects now that the layout is inflated
        counter = (EditText) findViewById(R.id.counter_edit_text);
        Button negativeButton = (Button) findViewById(R.id.negative_button);
        Button positiveButton = (Button) findViewById(R.id.positive_button);
        Button saveButton = (Button) findViewById(R.id.save_button);

        // Set up the click listener on the negative button, adding validation to make sure it doesn't
        // go below zero
        negativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.valueOf(counter.getText().toString());
                if (currentValue > 0) {
                    currentValue--;
                }
                counter.setText(String.valueOf(currentValue));
            }
        });

        // Set up the click listener on the positive button
        positiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText(String.valueOf(Integer.valueOf(counter.getText().toString()) + 1));
            }
        });

        // Set up the click listener on the save button
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is where we would extract the data from the view (such as the counter value) and
                // write it into the backend data model. This could include some validation and potentially popup
                // a toast.
                //
                // If the data successfully writes, we can then reset the view back to the defaultValue
                counter.setText(String.valueOf(defaultValue));
            }
        });

        // Convert the attribute set to a typed array
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExampleCustomView, 0, 0);

        // Retrieve the values from the typed array, using them however you want. In this case, we're
        // just using it to initialize the view so we can use it locally
        try {
            defaultValue = a.getInt(R.styleable.ExampleCustomView_defaultValue, 0);
            counter.setText(String.valueOf(defaultValue));
        } finally {
            a.recycle();
        }
    }

    // This is used to pass in the data object to which you're writing
    public void setMatchData(MatchData matchData) {
        this.matchData = matchData;
    }
}

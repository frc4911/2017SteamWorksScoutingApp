package org.usfirst.frc.team4911.scouting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Dialog fragment subclass which displays a given field and records a normalised location
 * where it was pressed. Use the {@link RecordLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link OnLocationSelectedListener} interface
 * to handle interaction events.
 */
public class RecordLocationFragment extends DialogFragment {

    OnLocationSelectionDoneListener mOnLocationSelectionDoneListener;
    OnLocationSelectedListener mOnLocationSelectedListener;

    // The keys we use to store argument parameters
    private static final String ARG_RESOURCE_ID = "resourceId";

    // The variable we store the ID in once we've gone through the whole argument passing process.
    private int mapImageResourceId;

    // Object for interacting with the image of the map shown on screen.
    private ImageView map;

    // tracks if the user has selected a valid location and blocks the dismissal of the dialog
    private boolean validLocationSelected = false;

    public RecordLocationFragment() {
        // Required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resourceId The resource ID of the picture to draw.
     * @return A new instance of fragment RecordLocationFragment.
     */
    public static RecordLocationFragment newInstance(int resourceId) {
        RecordLocationFragment fragment = new RecordLocationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESOURCE_ID, resourceId);
        fragment.setArguments(args);
        return fragment;
    }

    public static RecordLocationFragment show(int resourceIdOfMap, String name, FragmentManager fragmentManager) {
        RecordLocationFragment fieldMapFragment = RecordLocationFragment.newInstance(resourceIdOfMap);
        fieldMapFragment.show(fragmentManager, name);
        return fieldMapFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mapImageResourceId = getArguments().getInt(ARG_RESOURCE_ID);
        }

        onAttachToParentFragment(getParentFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_location, container, false);

        map = (ImageView) view.findViewById(R.id.fragment_map);
        map.setImageBitmap(getEditableFieldCanvas());
        map.setOnTouchListener(mapTouchEventListener);

        Button close = (Button) view.findViewById(R.id.btn_fragment_close);
        close.setOnClickListener(doneButtonListener);

        TextView txtName = (TextView) view.findViewById(R.id.txt_location_name);
        txtName.setText(getTag());

        return view;
    }

    private void onAttachToParentFragment(Fragment fragment) {
        if (fragment instanceof OnLocationSelectedListener) {
            if (mOnLocationSelectedListener == null) {
                mOnLocationSelectedListener = (OnLocationSelectedListener) fragment;
            } else {
                final OnLocationSelectedListener existingListener = mOnLocationSelectedListener;
                mOnLocationSelectedListener = (pair) -> {
                    boolean valid = false;
                    try {
                        valid = existingListener.onLocationSelected(pair);
                    } finally {
                        valid = valid && ((OnLocationSelectedListener) fragment).onLocationSelected(pair);
                    }
                    return valid;
                };
            }
        }

        if (fragment instanceof OnLocationSelectionDoneListener) {
            if (mOnLocationSelectionDoneListener == null) {
                mOnLocationSelectionDoneListener = (OnLocationSelectionDoneListener) fragment;
            } else {
                final OnLocationSelectionDoneListener existingListener = mOnLocationSelectionDoneListener;
                mOnLocationSelectionDoneListener = () -> {
                    try {
                        existingListener.onLocationSelectionDone();
                    } finally {
                        ((OnLocationSelectionDoneListener) fragment).onLocationSelectionDone();
                    }
                };
            }
        }
    }


    /**
     * OnTouchListener for the map which draws an event position marker on the screen
     * when the screen is touched.
     */
    private View.OnTouchListener mapTouchEventListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_UP) {
                return true;
            }

            drawMapWithMarker(event.getX(), event.getY());

            // if there is no listener then we can't determine if the click was valid or not
            if (mOnLocationSelectedListener == null) {
                return true;
            }

            // This is where we call the map touch event listener in the parent class to pass
            // the data back to it.
            validLocationSelected = mOnLocationSelectedListener.onLocationSelected(
                    getNormalisedCoordinates(event.getX(), event.getY()));

            return true;
        }
    };

    /**
     * OnClickListener for the button that records the location and closes the dialog.
     */
    private View.OnClickListener doneButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validLocationSelected) {
                dismiss();
                if (mOnLocationSelectionDoneListener != null) {
                    mOnLocationSelectionDoneListener.onLocationSelectionDone();
                }
            }
        }
    };

    /**
     * Draws the map with the marker.
     *
     * @param scrX x coordinate of said marker.
     * @param scrY y coordinate of said marker.
     */
    private void drawMapWithMarker(float scrX, float scrY) {
        Bitmap image = getEditableFieldCanvas();
        Canvas canvas = new Canvas(image);
        Drawable d = getActivity().getDrawable(R.drawable.marker_shape);

        if (d != null) {
            d.setBounds(getDrawableBounds(scrX, scrY, d));
            d.draw(canvas);
        }
        map.setImageBitmap(image);
    }

    /**
     * Gets the field canvas as an editable bitmap.
     *
     * @return FIeld canvas as an editable bitmap.
     */
    private Bitmap getEditableFieldCanvas() {
        Bitmap originalBitmap;
        originalBitmap = BitmapFactory.decodeResource(getResources(), mapImageResourceId);
        return originalBitmap.copy(Bitmap.Config.RGB_565, true);
    }

    /**
     * This is the method we need so that the touch point marker draws itself centered at
     * the touch point like you want it to instead of randomly off the the side.
     *
     * @param centerX The X coordinate of the point you want to draw the shape around.
     * @param centerY The Y coordinate of the point you want to draw the shape around.
     * @param d       A drawable representing the shape you want to draw.
     * @return A rectangle containing the bounds of the shape such that it will draw itself with the
     * given x and y coordinates at the center of said shape.
     */
    private Rect getDrawableBounds(float centerX, float centerY, Drawable d) {
        int halfWidth = d.getIntrinsicWidth() / 2;
        int halfHeight = d.getIntrinsicHeight() / 2;

        int centerXasInt = (int) centerX;
        int centerYAsInt = (int) centerY;

        int left = centerXasInt - halfWidth;
        int top = centerYAsInt - halfHeight;
        int right = centerXasInt + halfWidth;
        int bottom = centerYAsInt + halfHeight;

        return new Rect(left, top, right, bottom);
    }

    /**
     * Gets the touchpoint X and Y coordinates normalised against the size of the image.
     * This means we don't have to worry about image size when working out what important location
     * on our image the touched point corresponds to.
     *
     * @param x raw X coordinate of the touched point.
     * @param y raw Y coordinate of the touched point.
     * @return A pair containing the X and Y coordinates of the touched point normalised against the
     * size of the image.
     */
    private Pair<Float, Float> getNormalisedCoordinates(float x, float y) {
        float normX = x / map.getWidth();
        float normY = y / map.getHeight();

        return new Pair<>(normX, normY);
    }

    public void setOnLocationSelectionDone(OnLocationSelectionDoneListener listener) {
        mOnLocationSelectionDoneListener = listener;
    }

    public void setOnLocationSelected(OnLocationSelectedListener listener) {
        mOnLocationSelectedListener = (OnLocationSelectedListener) listener;
    }


    /**
     * Interface that allows the object that created this fragment to react to events where
     * the map got clicked.  Needs to be implemented by all parent fragments of this class.
     * Returns 'true' if it's OK to close the location dialog, false if it should be kept open.
     */
    @FunctionalInterface
    public interface OnLocationSelectedListener {
        /**
         * Called when the user clicks the map selecting a location
         *
         * @param normalisedTouchPoint the point that was touched
         * @return true if the point is a valid location for selection, false otherwise
         */
        boolean onLocationSelected(Pair<Float, Float> normalisedTouchPoint);
    }

    /**
     * Interface that allows the object that created this fragment to react to the done button being
     * clicked. Note this is only called when a valid location has already been selected.
     */
    @FunctionalInterface
    public interface OnLocationSelectionDoneListener {
        void onLocationSelectionDone();
    }
}

package org.usfirst.frc.team4911.scouting;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Here's where assorted helper methods go.
 */
public class HelperMethods {
    /**
     * Shows a very simple pop-up dialog box suitable for informing the user of bad data.
     *
     * @param title The title of the view to display.
     * @param message The message to display in the box.
     * @param v       Parent view of the box.
     */
    public static void showSimpleDialog(String title, String message, View v) {
        showSimpleDialog(title, message, v.getContext());
    }

    public static void showSimpleDialog(String title, String message, Context context) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setTitle(title);
        dlgAlert.setMessage(message);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);

        dlgAlert.create().show();
    }
}

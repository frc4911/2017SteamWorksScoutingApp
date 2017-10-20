package org.usfirst.frc.team4911.scouting.bluetoothfilepusher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;

/**
 * Created by nathanieltroutman on 3/18/17.
 */

public class BluetoothMessageHandler {
    public static final String TAG = "BluetoothPusher";

    private final Handler handler;

    public BluetoothMessageHandler(Handler handler) {
        this.handler = handler;
    }

    public void sendError(String s, Exception e) {
        Log.e(TAG, s, e);
        // Share the sent message with the UI activity.
        Message msg = handler.obtainMessage(BluetoothPusherService.StatusMessageTypes.ERROR);
        Bundle bundle = new Bundle();
        bundle.putString("msg", s);
        bundle.putString("error", e.getMessage());
        msg.setData(bundle);
        msg.sendToTarget();
    }

    public void sendLog(String s) {
        Log.i(TAG, s);
        // Share the sent message with the UI activity.
        Message msg = handler.obtainMessage(BluetoothPusherService.StatusMessageTypes.LOG);
        Bundle bundle = new Bundle();
        bundle.putString("msg", s);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    public void sendFileSuccessMessage(File file) {
        // Share the sent message with the UI activity.
        Message msg = handler.obtainMessage(BluetoothPusherService.StatusMessageTypes.SEND_SUCCESS);
        Bundle bundle = new Bundle();
        bundle.putString("file", file.getName());
        msg.setData(bundle);
        msg.sendToTarget();
    }

    public void sendFileFailedMessage(File file) {
        // Share the sent message with the UI activity.
        Message msg = handler.obtainMessage(BluetoothPusherService.StatusMessageTypes.SEND_FAILED);
        Bundle bundle = new Bundle();
        bundle.putString("file", file.getName());
        msg.setData(bundle);
        msg.sendToTarget();
    }
}

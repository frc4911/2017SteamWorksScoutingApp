package org.usfirst.frc.team4911.scouting.bluetoothfilepusher;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import java.io.IOException;
import java.util.UUID;

/**
 * This is a bluetooth service that allows for pushing files and directories over bluetooth to
 * receiving server.
 */
public class BluetoothPusherService {
    // Uniquely identifies the CyberKnight Pusher Service to the receiving device. These must match on the
    // client and server for  succesfully Bluetooth RFComm connection to be made.
    public static final UUID CYBERKNIGHT_BLUETOOTH_PUSHER_UUID = UUID.fromString("2d31ac7d-0d4a-48dd-8136-2f6a9b71a3f4");

    public static final String TAG = "BluetoothPusher";
    private BluetoothMessageHandler mHandler;

    /**
     * Defines constants used when transmitting messages between the
     * service and the UI via a Handler.
     */
    public interface StatusMessageTypes {
        int SEND_SUCCESS = 0;
        int SEND_FAILED = 1;
        int LOG = 2;
        int ERROR = 3;
    }


    /**
     * @param handler a handler service to publish notifications over such as
     *                status updates during file pushes.
     */
    public BluetoothPusherService(Handler handler) {
        this.mHandler = new BluetoothMessageHandler(handler);
    }


    public ConnectedDevice connect(BluetoothDevice mDevice) {
        try {
            mHandler.sendLog("Attempting to connect to: " + mDevice.getName());
            ConnectedDevice pusher = new ConnectedDevice(mDevice, mHandler);
            return pusher;
        } catch (IOException | RuntimeException e) {
            mHandler.sendError("Failed to connect to: " + mDevice.getName(), e);
            throw new BluetoothConnectionException("Error connecting to device: " + mDevice.getName(), e);
        }
    }


}


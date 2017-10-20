package org.usfirst.frc.team4911.scouting.bluetoothfilepusher;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Set;


/**
 * https://gist.github.com/timnew/7908603
 */
public class BluetoothDeviceManager implements BluetoothDevicePicker {
    private static String TAG = "BluetoothDeviceManager";

    private Context context;

    public BluetoothDeviceManager(Context context) {
        this.context = context;
    }

    /**
     * Finds a device to connect to with the given name
     */
    public void findDevice(String targetDeviceName, BluetoothDevicePickResultHandler handler) {
        Log.i(TAG, "Finding device with name: " + targetDeviceName);
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                Log.d(TAG, "Found: " + deviceName + " - " + deviceHardwareAddress);

                if (deviceName.equals(targetDeviceName)) {
                    handler.onDevicePicked(device);
                    break;
                }
            }
        } else {
            Log.d(TAG, "No bound devices!");
            handler.onNoDevicePicked();
        }
    }

    public void pickDevice(BluetoothDevicePickResultHandler handler) {
        context.registerReceiver(new BluetoothDeviceManagerReceiver(handler), new IntentFilter(ACTION_DEVICE_SELECTED));

        context.startActivity(new Intent(ACTION_LAUNCH)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(EXTRA_NEED_AUTH, false)
                .putExtra(EXTRA_FILTER_TYPE, FILTER_TYPE_ALL)
                .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
    }

    public interface BluetoothDevicePickResultHandler {
        void onDevicePicked(BluetoothDevice device);

        void onNoDevicePicked();
    }

    private static class BluetoothDeviceManagerReceiver extends BroadcastReceiver {

        private final BluetoothDevicePickResultHandler handler;

        public BluetoothDeviceManagerReceiver(BluetoothDevicePickResultHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(this);

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (device != null) {
                handler.onDevicePicked(device);
            } else {
                handler.onNoDevicePicked();
            }
        }
    }
}

package org.usfirst.frc.team4911.scouting.bluetoothfilepusher;

public class BluetoothConnectionException extends RuntimeException {
    public BluetoothConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BluetoothConnectionException(String message) {
        super(message);
    }
}

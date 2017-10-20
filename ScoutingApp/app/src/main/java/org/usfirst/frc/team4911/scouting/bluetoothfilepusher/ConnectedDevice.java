package org.usfirst.frc.team4911.scouting.bluetoothfilepusher;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;

/**
 * This represents a bluetooth server ready to receive files.
 */
public class ConnectedDevice implements AutoCloseable {
    /**
     * Constants for message types for the internal protocol
     */
    private interface FilePushMessage {
        byte TYPE_FILE = 1;
        byte TYPE_DIRECTORY = 2;
        byte TYPE_STOP = 3;
        byte TYPE_KEEP_ALIVE = 4;
        byte TYPE_FILE_ACK = 5;

        byte COMPRESSION_NONE = 0;
    }


    private static final String TAG = BluetoothPusherService.TAG;

    private BluetoothSocket socket;
    private AtomicBoolean sendingInProgress;
    private AtomicBoolean waitOnFileAck;
    private BluetoothDevice device;
    private InputStream inStream;
    private DataOutputStream outStream;
    private byte[] writeBuffer; // mmBuffer store for the stream
    private byte[] readBuffer; // mmBuffer store for the stream
    private Thread sendingThread;
    private Thread listeningThread;

    private BluetoothMessageHandler handler;

    public ConnectedDevice(BluetoothDevice device, BluetoothMessageHandler handler) throws IOException {
        this.device = device;
        this.handler = handler;

        writeBuffer = new byte[1024];
        readBuffer = new byte[1024];

        // only a single file/directory can be sent a time
        sendingInProgress = new AtomicBoolean(false);

        // we use this lock to force the file write to wait until
        // the receiving server has acknowledged the entire file
        // was received.
        waitOnFileAck = new AtomicBoolean(false);
    }

    private void openSocket() throws IOException {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try {
            handler.sendLog("Trying connection 1/3...");

            this.socket = device.createRfcommSocketToServiceRecord(BluetoothPusherService.CYBERKNIGHT_BLUETOOTH_PUSHER_UUID);
            this.socket.connect();
        } catch (IOException e) {
            try {
                handler.sendLog("Trying connection 2/3...");
                this.socket = device.createInsecureRfcommSocketToServiceRecord(BluetoothPusherService.CYBERKNIGHT_BLUETOOTH_PUSHER_UUID);
                this.socket.connect();
            } catch (IOException e2) {
                try {
                    handler.sendLog("Trying connection 3/3...");
                    this.socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                    this.socket.connect();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e3) {
                    throw new IOException("Failed to connect", e3);
                }
            }
        }

        handler.sendLog("Connected socket to: " + socket.getRemoteDevice().getName());
        inStream = socket.getInputStream();
        if (inStream == null) {
            handler.sendLog("Unable to get input stream.");
            throw new BluetoothConnectionException("Unable to get input stream.");
        }
        if (socket.getOutputStream() == null) {
            handler.sendLog("Unable to get output stream.");
            throw new BluetoothConnectionException("Unable to get output stream.");
        }
        outStream = new DataOutputStream(socket.getOutputStream());

        startListening();
    }


    /**
     * Starts the pusher listening for responses from the server.
     * (Currently the server never responds with anything)
     */
    private void startListening() {
        listeningThread = new Thread() {
            public void run() {
                handler.sendLog("Connected to " + device.getName() + " and listening...");
                int numBytes; // bytes returned from read()

                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    try {
                        // Read from the InputStream.
                        int messageType = inStream.read();
                        Log.d(TAG, "Received Message Type: " + messageType);
                        switch (messageType) {
                            case FilePushMessage.TYPE_KEEP_ALIVE:
                                break;
                            case FilePushMessage.TYPE_FILE_ACK:
                                // let the waiting writeFile() thread continue
                                //mmWaitOnFileAckLock.release();
                                waitOnFileAck.set(false);
                                break;
                        }
                    } catch (IOException e) {
                        if (sendingInProgress.get()) {
                            handler.sendError("Input stream was disconnected", e);
                        }
                        break;
                    }
                }
            }
        };
        listeningThread.start();
    }

    public boolean canSend() {
        return !sendingInProgress.get();
    }

    /**
     * Sends a file or directory to the connected device. On success or failure publishes a message to the
     * handler associated with the pusher.
     */
    public void send(final File path) {
        Log.d(TAG, "Starting to Send File: " + path);
        if (!canSend()) {
            throw new IllegalStateException("A directory is already being sent, cannot send more than one at a time.");
        }


        sendingInProgress.compareAndSet(false, true);

        sendingThread = new Thread() {
            public void run() {
                try {
                    openSocket();
                    handler.sendLog("Sending File/Directory: " + path);
                    if (path.isDirectory()) {
                        writeDirectory(path, new File("/"));
                    } else {
                        writeFile(path, null);
                    }
                    handler.sendFileSuccessMessage(path);
                    sendingInProgress.set(false);
                } catch (IOException e) {
                    handler.sendError("Error occurred when sending data", e);
                    handler.sendFileFailedMessage(path);
                } finally {
                    close();
                }
            }
        };
        sendingThread.start();

    }


    // TODO Break out messages to seperate classes

    // File Pusher Protocol
    // The file pusher protocol is a simple order dependent protocol for copying files
    // over bluetooth. Files and directories are relative to a virtual root on the
    // recieving server.
    //
    // 1 byte - Message Type
    // variable - Dependent on message type
    //
    // File Message Type
    // 1 byte - Message Type (FILE = 0x01)
    // 2 byte - Filename Length
    // n byte - Filename
    // 2 byte - Container Name (Target Directory) Length
    // n byte - Container Name (Target Directory)
    // 1 byte - Compression: 0 = None
    // 4 byte - File length
    // n byte - File contents
    // 8 byte - CRC
    //
    // Directory Message Type
    // 1 byte - Message Type (DIRECTORY = 0x02)
    // 2 byte - Directory Name Length
    // n byte - Directory Name
    // 2 byte - Child Count
    //
    // Stop Message Type
    // 1 byte - Message Type (STOP = 0x03)
    //
    // Keep Alive Message Type
    // 1 byte - Message Type (KEEP_ALIVE = 0x04)
    //
    // File Ack Message Type
    // 1 byte - Message Type (FILE_ACK = 0x05)
    //

    /**
     * Write a file over the connected socket
     */
    private void writeFile(File file, File destinationDirectory) throws IOException {
        Log.d(TAG, "Sending File: " + file);

        // Indicate we must wait for a file ack before we can send another file
        waitOnFileAck.set(true);

        // type
        outStream.writeByte(FilePushMessage.TYPE_FILE);

        outStream.writeUTF(file.getName());

        if (destinationDirectory != null) {
            outStream.writeUTF(destinationDirectory.getPath());
        } else {
            outStream.writeShort(0);
        }

        // compression
        outStream.writeByte(FilePushMessage.COMPRESSION_NONE);

        // file chunk
        outStream.writeInt((int) file.length());
        // copy the file to the stream
        CRC32 crc = new CRC32();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int totalWritten = 0;
            while (true) {
                int numBytes = inputStream.read(writeBuffer);
                if (numBytes == -1) {
                    break;
                }
                totalWritten += numBytes;
                crc.update(writeBuffer, 0, numBytes);
                outStream.write(writeBuffer, 0, numBytes);
            }
            outStream.writeLong(crc.getValue());
            outStream.flush();
            Log.d(TAG, "Sent Bytes: " + totalWritten);
            Log.d(TAG, "Sent CRC: " + crc.getValue());

        }

        // once the writes are complete to the stream we wait for an ack from the device
        try {
            Log.d(TAG, "Waiting on file ack");
            while (waitOnFileAck.get()) {
                Thread.sleep(10);
            }
            Log.d(TAG, "File ack received");
            handler.sendLog("....sent file: " + file.getName());
        } catch (InterruptedException e) {
            handler.sendError("Interrupted while waiting for FileAck", e);
        }
    }

    private void writeDirectory(File dir, File destinationContainer) throws IOException {
        Log.d(TAG, "Sending Directory: " + dir.getName() + " -> " + destinationContainer.getPath());
        handler.sendLog("Sending Directory: " + dir.getName());
        File[] children = dir.listFiles();
        outStream.writeByte(FilePushMessage.TYPE_DIRECTORY);
        outStream.writeUTF(destinationContainer.getPath());
        outStream.writeShort(children.length);

        handler.sendLog("...sub-files/directories to send: " + children.length);

        for (int index = 0; index < children.length; index++) {
            File subfile = children[index];
            handler.sendLog("...sending file " + (index + 1) + "/" + children.length);
            if (subfile.isFile()) {
                writeFile(subfile, destinationContainer);
            } else if (subfile.isDirectory()) {
                writeDirectory(subfile, new File(destinationContainer, subfile.getName()));
            }
        }
    }

    private void writeStop() throws IOException {
        Log.d(TAG, "Sending stop message");
        outStream.writeByte(FilePushMessage.TYPE_STOP);
        outStream.flush();
    }

    // Disconnect everything and perform an orderly shutdown
    public void close() {
        if (socket == null) {
            return;
        }

        if (!socket.isConnected()) {
            return;
        }
        Log.d(TAG, "Closing thread: " + socket.getRemoteDevice().getName());


        try {
            Log.d(TAG, "Writing stop message: " + socket.getRemoteDevice().getName());
            writeStop();
        } catch (IOException e) {
            handler.sendError("Could not write stop message.", e);

        }

        try {
            Log.d(TAG, "Flushing outpustream: " + socket.getRemoteDevice().getName());
            outStream.flush();
        } catch (IOException e) {
            handler.sendError("Could not flush the outpustream", e);
        }
        try {
            Log.d(TAG, "Closing Output: " + socket.getRemoteDevice().getName());
            outStream.close();
        } catch (IOException e) {
            handler.sendError("Could not close the Output", e);
        }
        try {
            Log.d(TAG, "Closing Input: " + socket.getRemoteDevice().getName());
            inStream.close();
        } catch (IOException e) {
            handler.sendError("Could not close the Input", e);
        }

        try {
            Log.d(TAG, "Closing socket: " + socket.getRemoteDevice().getName());
            socket.close();
        } catch (IOException e) {
            handler.sendError("Could not close the connection to socket", e);
        }
    }
}

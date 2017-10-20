package org.usfirst.frc.team4911.scouting;

import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class USBSyncActivity extends AppCompatActivity {
    private static final int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 4911;
    private static final String DESTINATION_FOLDER_PREFERENCE = "USB_DESTINATION_FOLDER";

    private static final String TAG = "CyberKnightUSBPusher";

    private TextView textView_log;
    private TextView textView_targetDevice;
    private Button button_Send;

    // only access this with its getter and setter
    private Uri __destinationFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_sync);

        this.textView_targetDevice = (TextView) findViewById(R.id.textView_TargetDevice);

        // initialize logging view to blank
        this.textView_log = (TextView) findViewById(R.id.textView_Log);
        this.textView_log.setText("");

        // to lazy to create another activity xml
        ((Button) findViewById(R.id.button_SelectDevice)).setText("Select Folder");

        // sending is disabled till we check the destinations validitiy
        this.button_Send = (Button) findViewById(R.id.button_Send);
        this.button_Send.setEnabled(false);

        this.ensureDestinationIsSelected();
    }

    private void ensureDestinationIsSelected() {
        // check to see if we have a stored desition
        String destination = getPreferences(MODE_PRIVATE).getString(DESTINATION_FOLDER_PREFERENCE, null);
        if (destination == null) {
            this.log("Please Select a Folder");
            return;
        }

        // check to see if we have write permissions
        Uri folder = Uri.parse(destination);
        for (UriPermission permission : getContentResolver().getPersistedUriPermissions()) {
            if (permission.getUri().equals(folder) && permission.isWritePermission()) {
                this.setDestinationFolder(folder);
                return;
            }
        }

        log("Cannot find permissions for: %s", folder.getPath());
        log("Please connect the USB Stick or Select a Folder");
    }

    protected void sendData() {
        try {
            DocumentFile rootDocument = DocumentFile.fromTreeUri(getApplicationContext(), getDestinationFolder());
            log("Copying files to: %s", rootDocument.getUri().getPath());

            if (!rootDocument.canWrite()) {
                this.log("Cannot write to USB Stick, is it plugged in?");
                return;
            }

            // read/write buffer
            byte[] bytes = new byte[1024];

            File[] files = ScoutMatchActivity.getScoutingDataStorageDir().listFiles();
            int filesWrittern = 0, filesSkipped = 0, total = files.length;
            log("Found %s files", files.length);

            for (File f : files) {
                // skip if it already exists
                if (rootDocument.findFile(f.getName()) != null) {
                    filesSkipped += 1;
                    continue;
                } else {
                    filesWrittern += 1;
                }


                DocumentFile child = rootDocument.createFile("application/json", f.getName());
                this.log("Writing file: %s", child.getName());
                try (OutputStream out = getContentResolver().openOutputStream(child.getUri());
                     InputStream input = new FileInputStream(f)) {

                    while (true) {
                        int numBytes = input.read(bytes);
                        if (numBytes == -1) {
                            break;
                        }
                        out.write(bytes, 0, numBytes);
                    }
                } catch (IOException e) {
                    this.log("Could not open file: %s", child.getName());
                }
            }
            this.log("Done! Copied %s, skipped %s", filesWrittern, filesSkipped);
        } catch (RuntimeException e) {
            this.log("Error: %s", e.getMessage(), e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ACTION_OPEN_DOCUMENT_TREE && data != null) {
            try {
                Uri folder = data.getData();

                // store the permissions so we don't have to re-prompt the user to select
                // the directory
                getContentResolver().takePersistableUriPermission(folder,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // store the selected directory
                getPreferences(MODE_PRIVATE).edit().putString(DESTINATION_FOLDER_PREFERENCE, folder.toString()).apply();

                // all is good to go
                button_Send.setEnabled(true);
            } catch (RuntimeException e) {
                this.log("Error: %s", e.getMessage(), e);
            }
        }
    }

    private void setDestinationFolder(Uri folder) {
        __destinationFolder = folder;
        textView_targetDevice.setText(folder.getPath());
        button_Send.setEnabled(true);
    }

    private Uri getDestinationFolder() {
        return __destinationFolder;
    }

    public void selectDeviceButton_Clicked(View v) {
        // ask the user to select a folder and request permissions for:
        // read, write, persist-permissions, directory-access
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TITLE, "ScoutingData");
        this.startActivityForResult(intent, REQUEST_ACTION_OPEN_DOCUMENT_TREE);
    }

    public void sendButton_Clicked(View v) {
        try {
            this.button_Send.setEnabled(false);
            sendData();
        } finally {
            this.button_Send.setEnabled(true);
        }
    }

    private void log(String line, Object... args) {
        String newText = new Date() + ": " + String.format(line, args) + "\n";

        if (args.length > 0 && args[args.length - 1] instanceof Throwable) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ((Throwable) args[args.length - 1]).printStackTrace(pw);
            newText += sw.toString();
        }

        this.textView_log.append(newText);
        Layout layout = this.textView_log.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(this.textView_log.getLineCount()) - this.textView_log.getHeight();
            this.textView_log.scrollTo(0, scrollAmount < 0 ? 0 : scrollAmount);
        }
    }
}
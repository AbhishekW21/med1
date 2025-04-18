package com.mad.womensafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView siren, location, Settings, currentlocation, Helpline, Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start background service
        Intent backgroundService = new Intent(getApplicationContext(), ScreenOnOffBackgroundService.class);
        startService(backgroundService);
        Log.d(ScreenOnOffReceiver.SCREEN_TOGGLE_TAG, "Activity onCreate");

        // Check and request permissions if needed
        checkPermissions();

        // Initialize UI components
        siren = findViewById(R.id.Siren);
        location = findViewById(R.id.send_Location);
        Settings = findViewById(R.id.Settings);
        currentlocation = findViewById(R.id.Currentlocation);
        Helpline = findViewById(R.id.helpline);
        Map = findViewById(R.id.map);

        // Set onClick listeners for the buttons
        siren.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Flashing.class)));
        location.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Instructions.class)));
        Settings.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SmsActivity.class)));
        currentlocation.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChoosenActivity.class)));
        Helpline.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HelplineActivity.class)));
        Map.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MapActivity.class)));
    }

    // Method to check and request necessary permissions
    private void checkPermissions() {
        int permissionCheckSms = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
        int permissionCheckLocation = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheckPhone = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
        int permissionCheckCamera = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        int permissionCheckContacts = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);

        if (permissionCheckSms != PackageManager.PERMISSION_GRANTED ||
                permissionCheckLocation != PackageManager.PERMISSION_GRANTED ||
                permissionCheckPhone != PackageManager.PERMISSION_GRANTED ||
                permissionCheckCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckContacts != PackageManager.PERMISSION_GRANTED) {
            // Request permissions
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS
                    }, 0);
        }

        // Special permission for devices with Android Q and above for background location access
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int permissionCheckBackgroundLocation = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            if (permissionCheckBackgroundLocation != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
            }
        }
    }

    // Handle the result of permissions request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            // Handle the result of regular permissions request
            if (grantResults.length > 0) {
                // Check if all requested permissions are granted
                boolean allPermissionsGranted = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }

                if (!allPermissionsGranted) {
                    Log.e("Permissions", "Not all required permissions are granted!");
                }
            }
        } else if (requestCode == 100) {
            // Handle the result for background location permission (Android Q and above)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Background location permission granted.");
            } else {
                Log.e("Permissions", "Background location permission denied.");
            }
        }
    }
}

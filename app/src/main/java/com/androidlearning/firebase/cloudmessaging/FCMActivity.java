package com.androidlearning.firebase.cloudmessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import android.Manifest;

import java.util.Objects;


public class FCMActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FCMActivity";

    private Button subscribe, read;

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fcmactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        askNotificationPermission();

        subscribe = findViewById(R.id.fcm_btn_subscribe);
        read = findViewById(R.id.fcm_btn_read);

        subscribe.setOnClickListener(this);
        read.setOnClickListener(this);

        /**
         * SDK lvl >= 26
         *
         * in order to check if channel is exist and subscribe to channel
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_LOW
            ));
        }

        if(getIntent().getExtras() != null) {
            for(String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.fcm_btn_subscribe) {
            subscribeToTopic();
        } else if(id == R.id.fcm_btn_read) {
            readFromTopic();
        }
    }

    private void subscribeToTopic() {
        FirebaseMessaging.getInstance()
                .subscribeToTopic(getString(R.string.default_notification_channel_name))
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed!";
                    if(!task.isSuccessful()) {
                        msg = "Failed!";
                    }
                    Log.d(TAG, "Subscribe to Topic: " + msg);
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                });
    }

    private void readFromTopic() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()) {
                        Log.e(TAG, "Fail to Register Token: " + Objects.requireNonNull(task.getException()).getMessage());
                        return;
                    }
                    String token = task.getResult();
                    Toast.makeText(this, "Token: " + token, Toast.LENGTH_LONG).show();
                });
    }
}
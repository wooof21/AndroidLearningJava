package com.androidlearning.broadcastreceiver;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

public class AirplaneModeReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_airplane_mode_receiver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        AirplaneModeReceiver receiver = new AirplaneModeReceiver();
        //dynamically register the receiver in the app code, for Android Api above 26
        //register receiver in manifest file wont work
        registerReceiver(receiver, intentFilter);
    }
}
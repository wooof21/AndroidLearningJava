package com.androidlearning.firebase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

public class FirebaseActivity extends AppCompatActivity {

    private RealtimeDBService dbService;
    private TextView tv, userEmailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firebase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.firebase_tv);
        userEmailTv = findViewById(R.id.firebase_user_email);

        dbService = new RealtimeDBService();
        dbService.writeMsgToDB("message", "Hello World");

        dbService.changeListener("message", tv);

        //Write custom object to real time DB

        User user = User.builder().name("john").email("john@gmail.com").build();
        dbService.writeMsgToDB("user", user);

        dbService.changeListener("user", userEmailTv);
    }
}
package com.androidlearning.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

public class MusicPlayServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button play, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_play_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        play = findViewById(R.id.music_service_play);
        play.setOnClickListener(this);

        stop = findViewById(R.id.music_service_stop);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Intent service = new Intent(getApplicationContext(), MusicPlayService.class);
        if(viewId == R.id.music_service_play) {
            startService(service);
        } else {
            stopService(service);
        }
    }
}
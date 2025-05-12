package com.androidlearning.meidiaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

//Handle multiple button click events
public class FrenchTeacherActivity extends AppCompatActivity implements View.OnClickListener {

    Button black, green, red, purple, yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_french_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        black = findViewById(R.id.f_t_btn_black);
        black.setOnClickListener(this);

        green = findViewById(R.id.f_t_btn_green);
        green.setOnClickListener(this);

        red = findViewById(R.id.f_t_btn_red);
        red.setOnClickListener(this);

        purple = findViewById(R.id.f_t_btn_purple);
        purple.setOnClickListener(this);

        yellow = findViewById(R.id.f_t_btn_yellow);
        yellow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.f_t_btn_black) {
            playSound(R.raw.f_t_black);
        } else if(viewId == R.id.f_t_btn_green) {
            playSound(R.raw.f_t_green);
        } else if(viewId == R.id.f_t_btn_red) {
            playSound(R.raw.f_t_red);
        } else if(viewId == R.id.f_t_btn_purple) {
            playSound(R.raw.f_t_purple);
        } else {
            playSound(R.raw.f_t_yellow);
        }
    }

    private void playSound(int resId) {
        MediaPlayer player = MediaPlayer.create(getApplicationContext(), resId);
        player.start();
    }
}
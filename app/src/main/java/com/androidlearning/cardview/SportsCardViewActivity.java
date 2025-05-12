package com.androidlearning.cardview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.model.Sport;

import java.util.List;

public class SportsCardViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SportsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sports_card_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.sports_card_recycler_view);

        List<Sport> sports = List.of(
                Sport.builder().sportImg(R.drawable.cardview_basketball).sportTitle("Basketball").build(),
                Sport.builder().sportImg(R.drawable.cardview_football).sportTitle("Football").build(),
                Sport.builder().sportImg(R.drawable.cardview_ping).sportTitle("Ping Pong").build(),
                Sport.builder().sportImg(R.drawable.cardview_tennis).sportTitle("Tennis").build(),
                Sport.builder().sportImg(R.drawable.cardview_volley).sportTitle("Volleyball").build()
        );

        adapter = new SportsAdapter(sports);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
package com.androidlearning.recyclerview.advanced.cardview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

public class RVCardViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PlanetCard> planetCards;
    private RVCardViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rvcard_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.advanced_rv_activity_cardview_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        planetCards = new ArrayList<>();
        PlanetCard card1 = PlanetCard.builder().planetName("Jupiter").distanceFromSun(778)
                .gravity(26).diameter(143000).build();
        planetCards.add(card1);
        PlanetCard card2 = PlanetCard.builder().planetName("Mars").distanceFromSun(228)
                .gravity(4).diameter(6800).build();
        planetCards.add(card2);
        PlanetCard card3 = PlanetCard.builder().planetName("Pluto").distanceFromSun(5900)
                .gravity(1).diameter(2320).build();
        planetCards.add(card3);
        PlanetCard card4 = PlanetCard.builder().planetName("Venus").distanceFromSun(108)
                .gravity(9).diameter(12750).build();
        planetCards.add(card4);
        PlanetCard card5 = PlanetCard.builder().planetName("Saturn").distanceFromSun(1429)
                .gravity(11).diameter(120000).build();
        planetCards.add(card5);
        PlanetCard card6 = PlanetCard.builder().planetName("Mercury").distanceFromSun(58)
                .gravity(4).diameter(4900).build();
        planetCards.add(card6);
        PlanetCard card7 = PlanetCard.builder().planetName("Neptune").distanceFromSun(4500)
                .gravity(12).diameter(50500).build();
        planetCards.add(card7);
        PlanetCard card8 = PlanetCard.builder().planetName("Uranus").distanceFromSun(2870)
                .gravity(9).diameter(52400).build();
        planetCards.add(card8);
        adapter = new RVCardViewAdapter(planetCards);

        recyclerView.setAdapter(adapter);
    }
}
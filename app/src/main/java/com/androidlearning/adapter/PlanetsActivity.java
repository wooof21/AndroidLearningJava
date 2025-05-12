package com.androidlearning.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.androidlearning.model.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    List<Planet> planets;
    PlanetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.planets_list_view);

        planets = new ArrayList<>(List.of(
                Planet.builder().planetName("Earth").moonCount("1 Moon").planetImg(R.drawable.earth).build(),
                Planet.builder().planetName("Mercury").moonCount("0 Moon").planetImg(R.drawable.mercury).build(),
                Planet.builder().planetName("Venus").moonCount("0 Moon").planetImg(R.drawable.venus).build(),
                Planet.builder().planetName("Mars").moonCount("2 Moons").planetImg(R.drawable.mars).build(),
                Planet.builder().planetName("Jupiter").moonCount("79 Moons").planetImg(R.drawable.jupiter).build(),
                Planet.builder().planetName("Saturn").moonCount("83 Moons").planetImg(R.drawable.saturn).build(),
                Planet.builder().planetName("Uranus").moonCount("27 Moons").planetImg(R.drawable.uranus).build(),
                Planet.builder().planetName("Neptune").moonCount("14 Moons").planetImg(R.drawable.neptune).build()
        ));

        adapter = new PlanetAdapter((ArrayList<Planet>) planets, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,
                "Planet Name: " + ((Planet)parent.getAdapter().getItem(position)).getPlanetName(),
                Toast.LENGTH_SHORT).show();
    }
}
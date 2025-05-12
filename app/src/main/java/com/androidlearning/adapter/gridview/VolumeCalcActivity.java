package com.androidlearning.adapter.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

public class VolumeCalcActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gridView;
    private ArrayList<Shape> shapeList;
    private VolCalcAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volume_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.vol_calc_grdiview);
        gridView.setOnItemClickListener(this);

        shapeList = new ArrayList<>(List.of(
                Shape.builder().shapeImgId(R.drawable.vol_calc_sphere).shapeName("Sphere").build(),
                Shape.builder().shapeImgId(R.drawable.vol_calc_cylinder).shapeName("Cylinder").build(),
                Shape.builder().shapeImgId(R.drawable.vol_calc_cube).shapeName("Cube").build(),
                Shape.builder().shapeImgId(R.drawable.vol_calc_prism).shapeName("Prism").build()
        ));

        adapter = new VolCalcAdapter(getApplicationContext(), shapeList);

        gridView.setAdapter(adapter);
        //can also be set in xml layout file
        gridView.setNumColumns(2);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String shapeName = ((Shape)parent.getAdapter().getItem(position)).getShapeName();
        Intent intent = new Intent(getApplicationContext(), VolCalcResultActivity.class);
        intent.putExtra("shapeName", shapeName);
        startActivity(intent);
    }
}
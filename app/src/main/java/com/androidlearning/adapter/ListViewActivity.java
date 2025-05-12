package com.androidlearning.adapter;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    ListView listView, customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        //1. AdapterView: ListView
        listView = findViewById(R.id.adapter_list_view);

        //2. Data sources
        String[] languages = {"C", "Java", "Node.js", "Python", "Kotlin"};

        //3. Adapter: act as a bridge between AdapterView and Data Source
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);

        listView.setAdapter(adapter);

        customListView = findViewById(R.id.adapter_list_view_custom);

        /**
         * Potential Problem with List.of()
         *
         *     Issue: List.of() creates an immutable list in Java. \
         *     If you attempt to modify this list later, it will throw an UnsupportedOperationException.
         *     If your data is mutable, consider using new ArrayList<>(...) instead.
         *
         *     Solution: Use ArrayList for mutable lists.
         */
        List<Pair<String, String>> customData = new ArrayList<>(
                List.of(Pair.create("C", "0%"),
                        Pair.create("Java", "100%"), Pair.create("Node.js", "60%"),
                        Pair.create("Android", "80%"))
        );
        CustomListVIewAdapter customAdapter = new CustomListVIewAdapter(getApplicationContext(), customData);
        customListView.setAdapter(customAdapter);
    }
}
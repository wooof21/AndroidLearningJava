package com.androidlearning.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.model.Grocery;

import java.util.List;

public class MarketAppActivity extends AppCompatActivity implements RecyclerViewItemClickListener{

    private RecyclerView recyclerView;

    private List<Grocery> groceries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_market_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.market_app_recycler_view);

        groceries = List.of(
                Grocery.builder().imgId(R.drawable.grocery_fruit).title("Fruits").description("Fresh Fruits from the Garden.").build(),
                Grocery.builder().imgId(R.drawable.grocery_vegitables).title("Vegetables").description("Fresh Vegetables.").build(),
                Grocery.builder().imgId(R.drawable.grocery_bread).title("Bakery").description("Bread, Wheat and Beans.").build(),
                Grocery.builder().imgId(R.drawable.grocery_beverage).title("Beverage").description("Juice, Tea, Coffee and Soda.").build(),
                Grocery.builder().imgId(R.drawable.grocery_milk).title("Milk").description("Milk, Shakes and Yogurt.").build(),
                Grocery.builder().imgId(R.drawable.grocery_popcorn).title("Snacks").description("Pop Corn, Donut and Drinks.").build());

        MarketAdapter adapter = new MarketAdapter(groceries);

        /**
         * LinearLayoutManager:
         *  handle the layout of the items in a liner manner
         *
         */
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);

        //link the customer click listener to adapter
        adapter.setItemClickListener(this);

    }

    @Override
    public void onItemClicked(View view, int position) {
        Toast.makeText(this,
                "Grocery: " + this.groceries.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
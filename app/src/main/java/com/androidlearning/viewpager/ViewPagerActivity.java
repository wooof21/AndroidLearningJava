package com.androidlearning.viewpager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.androidlearning.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.activity_view_pager);
        tabLayout = findViewById(R.id.activity_view_pager_tablayout);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        adapter.addFragments(fragment1, fragment2, fragment3);

        //set orientation in ViewPager2
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager.setAdapter(adapter);

        //connect TabLayout with ViewPager
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, pos) -> {
                    tab.setText("Fragment " + (pos + 1));
                });

        //attach the mediator to TabLayout and ViewPager
        mediator.attach();

    }
}
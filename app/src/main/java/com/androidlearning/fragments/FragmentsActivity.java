package com.androidlearning.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidlearning.R;

public class FragmentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button simple, another;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * is part of Jetpack's Edge-to-Edge API introduced in AndroidX to make apps fully
         * extend into system bars (status bar, navigation bar) by default.
         *
         * It automatically applies window insets handling, meaning:
         *
         *     Your app's content will extend behind the status bar and navigation bar.
         *     System bars (status & nav) will become transparent or translucent.
         *     It adds padding/margins dynamically to prevent content from being obscured by the system bars.
         */
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //If you want Edge-to-Edge but need to remove the bottom space,
            // Remove unwanted padding systemBars.bottom -> 0
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        simple = findViewById(R.id.fragment_activity_simple);
        simple.setOnClickListener(this);

        another = findViewById(R.id.fragment_activity_another);
        another.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Fragment simple = new SimpleFragment();
        Fragment another = new AnotherFragment();
        if(viewId == R.id.fragment_activity_simple) {
            loadFragments(simple);
        } else {
            loadFragments(another);
        }
    }

    private void loadFragments(Fragment fragment) {
        /**
         * responsible for all runtime management of fragments
         * including add, remove, hide, show and navigate between fragments
         *
         * primary purpose: to create and manage fragments transactions
         *
         * API >= 28 -> FragmentManager fm = getSupportFragmentManager();
         * API < 28 -> FragmentManager fm = getFragmentManager()
         */
        FragmentManager fm = getSupportFragmentManager();

        /**
         * a set of operations that involve adding, replacing, or removing fragments within the activity
         *
         * beginTransaction: creates a new transaction to change a fragment at runtime
         */
        FragmentTransaction ft = fm.beginTransaction();

        //Replace the framelayout with new fragment
        ft.replace(R.id.fragments_activity_framelayout, fragment);

        //commit transaction, save changes
        ft.commit();
    }

}
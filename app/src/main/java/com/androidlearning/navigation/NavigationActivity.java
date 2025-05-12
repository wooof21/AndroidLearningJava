package com.androidlearning.navigation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * Step 1: Navigation Graph
         *  - a resource file that contains all the destinations and actions
         *  - represents all the app's navigation paths
         *  - to create: resource folder(res) -> create <Android Resource Directory> - type: navigation
         *  - create Navigation Resource File
         *
         *  Step 2: NavHostFragment
         *  - a special fragment that hosts the navigation graph
         *  - typically included in the app's layout xml
         *  - responsible for inflating and displaying the appropriate destination as the user
         *      navigate through the app
         *  - when navigate from one fragment to another using NavController, the NavHostFragment
         *      will replace the displayed content with a specified fragment
         *
         *  Step 3: NavController
         *  - navigate between destinations
         */
    }
}
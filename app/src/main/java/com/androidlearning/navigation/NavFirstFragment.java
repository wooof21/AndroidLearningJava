package com.androidlearning.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidlearning.R;

public class NavFirstFragment extends Fragment {


    public NavFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_first, container, false);

        /**
         * NavController: responsible for handling navigation actions defined in the navigation graph
         * and controlling the backstack of destinations
         *
         * To obtain a reference to NavController:
         * - through NavHostFragment
         * - Navigation.findNavController(View)
         */
        Button btn = view.findViewById(R.id.nav_first_frag_btn);
        btn.setOnClickListener(v -> {
            /**
             * obtain a reference to NavController
             * commonly used in case where you want to trigger navigation from UI elements like a button
             *
             * .navigate(): trigger navigation actions to move between destinations
             * passing the action id defined in navigation graph
             */
            Navigation.findNavController(view).navigate(R.id.action_navFirstFragment_to_navSecondFragment);
        });

        return view;
    }
}
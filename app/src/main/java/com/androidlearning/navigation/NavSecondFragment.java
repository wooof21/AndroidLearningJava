package com.androidlearning.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidlearning.R;

public class NavSecondFragment extends Fragment {


    public NavSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_second, container, false);

        Button btn = view.findViewById(R.id.nav_second_frag_btn);
        btn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_navSecondFragment_to_navFirstFragment);
        });

        return view;
    }
}
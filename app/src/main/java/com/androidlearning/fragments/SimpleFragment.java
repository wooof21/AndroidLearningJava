package com.androidlearning.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidlearning.R;


public class SimpleFragment extends Fragment {

    private static final String TAG = "Simple Fragment";


    public SimpleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "OnAttach called");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
    }

    /**
     * used to inflate the fragments layout or construct UI elements programmatically
     *
     * called when a fragment is first attached to an activity or need to display its UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple, container, false);

        Button btn = view.findViewById(R.id.fragments_simple_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Simple Fragment", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
package com.androidlearning.databinding;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClickHandler {

    Context context;

    public void onBtnClicked(View view) {
        Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show();
    }
}

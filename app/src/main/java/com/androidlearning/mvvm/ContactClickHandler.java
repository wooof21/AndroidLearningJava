package com.androidlearning.mvvm;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidlearning.mvvm.view.AddContactActivity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class ContactClickHandler {

    private Context context;

    public void onFABClicked(View view) {
        Intent intent = new Intent(view.getContext(), AddContactActivity.class);
        context.startActivity(intent);
    }
}

package com.androidlearning.mvvm;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import com.androidlearning.mvvm.model.entity.Contact;
import com.androidlearning.mvvm.view.ContactActivity;
import com.androidlearning.mvvm.viewmodel.ContactViewModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddNewContactClickHandler {

    private Contact contact;
    private Context context;
    private ContactViewModel viewModel;

    public void onSubmitBtnClicked(View view) {
        if(contact.getName() == null || contact.getEmail() == null) {
            Toast.makeText(context, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, ContactActivity.class);
//        intent.putExtra("Contact", contact);

        //insert the contact into DB use ViewModel
        viewModel.addContact(contact);

        context.startActivity(intent);
    }
}

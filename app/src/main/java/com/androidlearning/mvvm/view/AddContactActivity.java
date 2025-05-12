package com.androidlearning.mvvm.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityAddContactBinding;
import com.androidlearning.mvvm.AddNewContactClickHandler;
import com.androidlearning.mvvm.model.entity.Contact;
import com.androidlearning.mvvm.viewmodel.ContactViewModel;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding binding;
    private AddNewContactClickHandler handler;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact);

        contact = Contact.builder().build();

        /**
         * Use ViewModel instance passed from AddNewContactActivity to AddNewContactClickHandler
         * and use it to insert into database
         */

        ContactViewModel viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        handler = new AddNewContactClickHandler(contact, this, viewModel);


        //link Data Binding
        binding.setContact(contact);
        binding.setAddContactClickHandler(handler);
    }
}
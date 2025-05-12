package com.androidlearning.mvvm.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityContactBinding;
import com.androidlearning.mvvm.ContactClickHandler;
import com.androidlearning.mvvm.ContactRecyclerViewAdapter;
import com.androidlearning.mvvm.SwipeToDeleteCallback;
import com.androidlearning.mvvm.model.database.ContactDB;
import com.androidlearning.mvvm.model.entity.Contact;
import com.androidlearning.mvvm.viewmodel.ContactViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

//https://www.digitalocean.com/community/tutorials/android-recyclerview-swipe-to-delete-undo
public class ContactActivity extends AppCompatActivity {

    //Data Source
    private ContactDB contactDB;
    private List<Contact> contacts;
    //Adapter
    private ContactRecyclerViewAdapter adapter;
    //Binding
    private ActivityContactBinding mainBinding;
    private ContactClickHandler handler;
    private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contacts = new ArrayList<>();

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact);

        handler = new ContactClickHandler(this);
        //link click handler to Data Binding
        mainBinding.setContactClickHandler(handler);

        //RecyclerView
        RecyclerView recyclerView = mainBinding.mvvmRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Database
        contactDB = ContactDB.getInstance(this);

        //ViewModel
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        //Insert a new contact(testing)
//        Contact c1 =  Contact.builder().name("Test_New").email("test_new@gmail.com").build();
        /**
         * no direct interaction with Database, use ViewModel to insert data to DB
         * while ViewModel call Repository to communicate with DB
         */
//        viewModel.addContact(c1);

        //Load all contacts from Database
        viewModel.getAllContacts().observe(this, contacts_list -> {
            //to clear the duplicate data whenever load all contacts from DB
            contacts.clear();

            contacts.addAll(contacts_list);

            //have to notify the RecyclerView about the data change to update UI
            adapter.setContacts(contacts);
        });

        //Adapter
        adapter = new ContactRecyclerViewAdapter(contacts);
        recyclerView.setAdapter(adapter);


        //swip to delete
        SwipeToDeleteCallback swipeCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                //swipe to left to delete the item
                Contact contact = contacts.get(position);
                //delete from DB
                deleteContact(contact, position);
                //a dummy view from the android framework
                View view = findViewById(android.R.id.content);
                //Show Snackbar to undo delete
                Snackbar snackbar = Snackbar
                        .make(view, "Contact was removed from the DB.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", v -> {
                    restoreItem(contact, position);
                    recyclerView.scrollToPosition(position);
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        /**
         * In order to set the ItemTouchHelper onto the RecyclerView,
         * the attachToRecyclerView method is used.
         * When the Snackbar action is clicked we restore the item in the RecyclerView
         * using the restoreItem method.
         */
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void deleteContact(Contact contact, int position) {
        viewModel.deleteContact(contact);
        adapter.notifyItemRemoved(position);
    }

    private void restoreItem(Contact contact, int position) {
        viewModel.addContact(contact);
        adapter.notifyItemInserted(position);
    }
}
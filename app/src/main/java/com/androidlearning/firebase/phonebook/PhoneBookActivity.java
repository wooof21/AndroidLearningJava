package com.androidlearning.firebase.phonebook;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityPhoneBookBinding;
import com.androidlearning.firebase.RealtimeDBService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookActivity extends AppCompatActivity {

    private List<PhoneBookUser> users;
    private RecyclerView recyclerView;
    private PhoneBookRVAdapter adapter;
    private ActivityPhoneBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        users = new ArrayList<>();

        binding = DataBindingUtil.setContentView(PhoneBookActivity.this, R.layout.activity_phone_book);

        //Initialize RecyclerView with DataBinding
        recyclerView = binding.phoneBookActivityRecyclerview;

        //Firebase
        RealtimeDBService dbService = new RealtimeDBService();

        //Load Data from Firebase Realtime DB into RecyclerView
        dbService.getDBReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**
                 * This is commonly used in Firebase Realtime DB to iterate
                 * through the child nodes or children of a specific snapshot
                 *
                 * snapshot.getChildren(): contain all immediate child nodes of the snapshot
                 */
                users.clear();
                for(DataSnapshot data : snapshot.getChildren()) {
                    PhoneBookUser user = data.getValue(PhoneBookUser.class);
                    users.add(user);
                }

                /**
                 * notify an adapter associated with a RecyclerView that the underlying
                 * dataset has changed
                 */
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter = new PhoneBookRVAdapter(this, users);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
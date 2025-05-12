package com.androidlearning.firebase.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.firebase.journal.model.pojo.Journal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {

    //FirebaseAuth
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;

    //Firestore
    private FirebaseFirestore db;
    private CollectionReference collectionRef;


    //Firebase Storage
    private StorageReference storageRef;

    private List<Journal> journals;

    private RecyclerView recyclerView;
    private JournalListAdapter adapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.journal_list_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, JournalAddActivity.class);
            startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Journal");

        recyclerView = findViewById(R.id.journal_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        journals = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    /**
                     * queryDocumentSnapshot: is an Object that represent a single Document retrieved from
                     * a Firestore query
                     *
                     * queryDocumentSnapshot --> Document
                     */
                    journals.clear();
                    for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Journal journal = snapshot.toObject(Journal.class);
                        journals.add(journal);
                    }

                    //Display in RecyclerView
                    adapter = new JournalListAdapter(JournalListActivity.this, journals);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                });
    }

    //Add the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle the menu select action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.journal_action_add) {
            if(user != null && auth != null) {
                Intent intent = new Intent(this, JournalAddActivity.class);
                startActivity(intent);
            }
        } else if(id == R.id.journal_action_signout) {
            if(user != null && auth != null) {
                auth.signOut();
                Intent intent = new Intent(this, JournalActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.androidlearning.firebase.firestore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button readBtn;
    private TextView textView;
    private EditText nameET;
    private EditText emailET;

    //Firebase Firestore
    private FirebaseFirestore firestore;
    private CollectionReference collectionRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firestore);

        saveBtn = findViewById(R.id.firestore_save_btn);
        updateBtn = findViewById(R.id.firestore_update_btn);
        deleteBtn = findViewById(R.id.firestore_delete_btn);
        readBtn = findViewById(R.id.firestore_read_btn);
        textView = findViewById(R.id.firestore_textview);
        nameET = findViewById(R.id.firestore_name_et);
        emailET = findViewById(R.id.firestore_email_et);

        /**
         * FirebaseFirestore: entry point for accessing a Firestore DB
         *  - used to obtain references to specific Documents and Collections,
         *    as well as to configure Firestore settings such as enabling offline data persistence
         *    or adjust security rules
         *
         * CollectionReference: represents a specific Collection within a Firestore DB
         *  - used to perform operations on the Documents within that collection, such as
         *    querying, adding and listening for changes
         *
         * DocumentReference: represents a specific Document in a Firestore DB
         *  - provide method to read, write, update and delete data within the specific Document
         */
        firestore = FirebaseFirestore.getInstance();
        collectionRef = firestore.collection("Users");

        saveBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        readBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.firestore_save_btn) {
            saveToDocument();
        } else if(id == R.id.firestore_read_btn) {
            getAllDocuments();
        } else if(id == R.id.firestore_update_btn) {
            //to pass the document ID to get the document reference
            // GbkAEzbD3XYbHnRQzAYI : test, test@gmail.com
            updateDocument("GbkAEzbD3XYbHnRQzAYI");
        } else {
            //pass Document ID to delete the specific Document
            deleteDocument("GbkAEzbD3XYbHnRQzAYI");
        }
    }

    private void saveToDocument() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        //an Object represent the data to store in the new Document
        Friend friend = Friend.builder().name(name).email(email).build();

        //call Collection Reference to add a new Document to the specified Collection
        collectionRef.add(friend).addOnSuccessListener(docRef -> {
            //when success, docRef contains the Document ID
            String docId = docRef.getId();
        });
    }

    private void getAllDocuments() {
        /**
         * queryDocumentSnapshots: contains the Documents in the Collection
         *
         * each queryDocumentSnapshot -> represent a Document in the Collection
         */
        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            StringBuilder sb = new StringBuilder();
            for(QueryDocumentSnapshot queryDocSnapshot: queryDocumentSnapshots) {
                //Transforming snapshot into Object
                Friend friend = queryDocSnapshot.toObject(Friend.class);
                sb.append(friend.toString() + " ,id=" + queryDocSnapshot.getId());
                sb.append(System.lineSeparator());
            }

            textView.setText(sb.toString());
        });
    }

    private void updateDocument(String docId) {
        docRef = collectionRef.document(docId);

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        docRef.update("name", name, "email", email);
    }

    private void deleteDocument(String docId) {
        docRef = collectionRef.document(docId);
        docRef.delete();
    }
}
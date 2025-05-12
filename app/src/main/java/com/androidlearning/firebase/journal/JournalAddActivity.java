package com.androidlearning.firebase.journal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.androidlearning.firebase.journal.model.pojo.Journal;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class JournalAddActivity extends AppCompatActivity implements View.OnClickListener {

    private Button save;
    private ImageView cameraBtn;
    private ProgressBar progressBar;
    private EditText titleET;
    private EditText thoughtET;
    private ImageView postIV;

    //Firebase (Firestore)
    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    //Firebase (Storage)
    private StorageReference storageRef;

    //FirebaseAuth
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private String userId, username;

    /**
     * To get image from gallery, display a thumbnail in the ImageView and prepare it for
     * uploading to Firebase Storage
     *
     * user Activity Result Launcher -> since startActivityForResult is deprecated
     *
     */
    private ActivityResultLauncher<String> takePhoto;
    private Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        save = findViewById(R.id.journal_add_save_btn);
        cameraBtn = findViewById(R.id.journal_add_camera_btn);
        progressBar = findViewById(R.id.journal_add_progressbar);
        titleET = findViewById(R.id.journal_add_post_title_et);
        thoughtET = findViewById(R.id.journal_add_post_thought_et);
        postIV = findViewById(R.id.journal_add_post_imageview);
        progressBar.setVisibility(View.INVISIBLE);
        save.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Journal");

        storageRef = FirebaseStorage.getInstance().getReference();


        /**
         * AndroidX
         *
         * to register an activity result callback, allows you to specify an activity
         * result contract, a predefined contract or a custom one that describes the type of
         * activity result you want to handle and how to handle it
         *
         * params: Contract, Callback
         *
         * GetContent: AndroidX predefined contract - used for opening a file picker or gallery picker
         * to select content such as images or files from the device
         */
        takePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    //set the select image into ImageView
                    postIV.setImageURI(uri);

                    //get the image uri
                    imgUri = uri;
                }
        );


    }

    /**
     * When working with FirebaseAuth, always get currentUser at onStart
     */
    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user != null) {
            userId = user.getUid();
            username = user.getDisplayName();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.journal_add_save_btn) {
            saveJournal();
        } else if(id == R.id.journal_add_camera_btn) {
            Log.i("TakePhoto", "camera click");
            //Get image from gallery - specify the input type of files
            takePhoto.launch("image/*");
        }
    }

    private void saveJournal() {
        String title = titleET.getText().toString().trim();
        String thought = thoughtET.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thought) && imgUri != null) {
            //Display the progress bar
            progressBar.setVisibility(View.VISIBLE);
            //the saving path of image in Firebase Storage:
            //......../journal_images/userId/imageName
            final StorageReference filePath = storageRef.child("journal_images")
                    .child(userId)
                    .child("img_post_" + Timestamp.now().getSeconds());

            //upload the image to Firebase Storage
            //UploadTask.TaskSnapshot taskSnapshot
            filePath.putFile(imgUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        //get the img download url and save it to Firebase DB
                        filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imgUrl = uri.toString();
                            Journal journal = Journal.builder()
                                    .title(title)
                                    .thoughts(thought)
                                    .imgUrl(imgUrl)
                                    .timeAdded(Timestamp.now())
                                    .username(username)
                                    .userId(userId).build();

                            collectionRef.add(journal)
                                    .addOnSuccessListener(documentReference -> {
                                        //hide the progress bar
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finish();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Post Upload Failed", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
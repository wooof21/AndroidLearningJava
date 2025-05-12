package com.androidlearning.firebase.journal;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class JournalSignUpActivity extends AppCompatActivity {

    private EditText emailET, passwordET, usernameET;
    private Button signup;

    /**
     * FirebaseAuth: to interact with Firebase Authentication system
     *
     * AuthStateListener: listen for changes in the user's authentication state, trigger
     * callback when a user sign in or sign out
     *
     * FirebaseUser: represent the current authenticated user in Firebase Authentication, used
     * to store information about the authenticated user once they have sign in
     */
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();



    }

    private void init() {
        emailET = findViewById(R.id.journal_signup_email);
        passwordET = findViewById(R.id.journal_signup_password);
        usernameET = findViewById(R.id.journal_signup_username);
        signup = findViewById(R.id.journal_signup_btn);

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Auth_Users");

        auth = FirebaseAuth.getInstance();

        /**
         * Listen for changes in the Authentication state and responds accordingly when the
         * state changes
         */
        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();

            //Check if user is logged in or not
            if (currentUser != null) {
                //CurrentUser already logged in
                //Do actions when user signed in
            } else {
                //User is signed out
                //Do actions when user signed out
            }
        };

        signup.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String username = usernameET.getText().toString().trim();
            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
                userSignUp(email, password, username);
            } else {
                Toast.makeText(this, "No Field Should Be Empty", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * new user sign up and store user info in Firebase Authentication System
     */
    private void userSignUp(String email, String password, String username) {
        auth.createUserWithEmailAndPassword(email, password)
                /**
                 * OnCompleteListener: to handle the result of user creation process
                 * check if user creation is successfully by task.isSuccessful()
                 */
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "User Signup Successfully", Toast.LENGTH_SHORT).show();
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        currentUser.updateProfile(profileUpdate);
                    }
                });

    }
}
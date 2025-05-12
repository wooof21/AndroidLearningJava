package com.androidlearning.firebase.journal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JournalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailET, passwordEt;
    private Button signIn, signUp;

    //Firebase Auth
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailET = findViewById(R.id.journal_activity_email);
        passwordEt = findViewById(R.id.journal_activity_password);
        signIn = findViewById(R.id.journal_activity_login);
        signUp = findViewById(R.id.journal_activity_create_account);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        //Firebase Auth - ensure singleton instance of FirebaseAuth across the whole application
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.journal_activity_login) {
            emailPwdUserLogin();
        } else if(id == R.id.journal_activity_create_account) {
            Intent intent = new Intent(this, JournalSignUpActivity.class);
            startActivity(intent);
        }
    }

    private void emailPwdUserLogin() {
        String email = emailET.getText().toString().trim();
        String pwd = passwordEt.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
            auth.signInWithEmailAndPassword(email, pwd)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser currentUser = authResult.getUser();
                        Intent intent = new Intent(this, JournalListActivity.class);
                        startActivity(intent);
                    });
        }
    }
}
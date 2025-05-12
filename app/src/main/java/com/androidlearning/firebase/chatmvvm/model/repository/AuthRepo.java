package com.androidlearning.firebase.chatmvvm.model.repository;

import android.content.Context;
import android.content.Intent;

import com.androidlearning.firebase.chatmvvm.view.ChatGroupsActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Repository: a bridge between ViewModel and data source
 */
public class AuthRepo {

    public void firebaseAnonymousLogin(Context context) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(task -> {
                    //Authentication is successful
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(context, ChatGroupsActivity.class);
                        /**
                         * FLAG_ACTIVITY_NEW_TASK: new activity will be started as a new task on the back stack
                         * rather than being added to the current task.
                         *
                         *  - use this flag when moving from one activity to another outside the activity
                         */
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

    }

    //Get Current User Id
    public String getUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    //signout
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}

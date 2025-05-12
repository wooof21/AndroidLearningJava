package com.androidlearning.firebase;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lombok.Data;

@Data
public class RealtimeDBService {

    private FirebaseDatabase realtimeDB;

    private FirebaseDatabase getInstance() {
        if(realtimeDB == null) {
            realtimeDB = FirebaseDatabase.getInstance();
        }
        return realtimeDB;
    }

    /**
     * Get a reference to a specific node(location) in the database
     * The reference allow you to read and write data at the DB locations
     *
     * Specify the node(path) to access within the database
     */
    public DatabaseReference getDBReference(String path) {
        return getInstance().getReference(path);
    }

    /**
     * Write msg to a specific database location
     */
    public void writeMsgToDB(String path, Object msg) {
        getDBReference(path).setValue(msg);
    }


    /**
     * Read any change in the value of specified database location
     *
     * change from Firebase DB console will also be reflected on UI
     */

    public void changeListener(String path, TextView tv) {
        getDBReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**
                 * get called when the data at specified database reference changes
                 *
                 * DataSnapshot: contains the updated data, and can be extracted and processed
                 * as needed
                 */
                if("message".equals(path)) {
                    String updatedMsg = snapshot.getValue(String.class);
                    tv.setText(updatedMsg);
                } else if ("user".equals(path)) {
                    User user = snapshot.getValue(User.class);
                    tv.setText("Email: " + user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 * called if there is an error when trying to read from database
                 */
            }
        });
    }
}

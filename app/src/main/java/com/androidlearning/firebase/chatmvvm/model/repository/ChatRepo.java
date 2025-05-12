package com.androidlearning.firebase.chatmvvm.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.androidlearning.firebase.chatmvvm.model.pojo.ChatGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRepo {

    private MutableLiveData<List<ChatGroup>> chatGroupsLiveData;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public ChatRepo() {
        chatGroupsLiveData = new MutableLiveData<>();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("chat_groups");
    }

    //Get Chat Groups from Firebase Realtime DB
    public MutableLiveData<List<ChatGroup>> getChatGroupsLiveData() {
        List<ChatGroup> chatGroups = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatGroups.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatGroup chatGroup = ChatGroup.builder().groupName(dataSnapshot.getKey()).build();
                    chatGroups.add(chatGroup);
                }
                chatGroupsLiveData.postValue(chatGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatGroupsLiveData;
    }

    //Creating a new group
    public void createChatGroup(String groupName) {
        dbRef.child(groupName).setValue(groupName);
    }
}

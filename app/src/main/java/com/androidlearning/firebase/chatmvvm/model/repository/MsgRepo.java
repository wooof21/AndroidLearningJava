package com.androidlearning.firebase.chatmvvm.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.androidlearning.firebase.chatmvvm.model.pojo.ChatMessage;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MsgRepo {

    private MutableLiveData<List<ChatMessage>> chatMsgLiveData;
    private DatabaseReference groupMsgRef;

    public MsgRepo() {
        chatMsgLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ChatMessage>> getChatMsgLiveData(String groupName) {
        //get the child node(groupName) under the DB root reference
        groupMsgRef = FirebaseDatabase.getInstance().getReference("chat_groups").child(groupName);

        List<ChatMessage> messages = new ArrayList<>();

        groupMsgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMessage msg = dataSnapshot.getValue(ChatMessage.class);
                    messages.add(msg);
                }

                chatMsgLiveData.postValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatMsgLiveData;
    }

    public void sendMsg(String msg, String chatGroup) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chat_groups").child(chatGroup);
        ChatMessage message = new ChatMessage(
                FirebaseAuth.getInstance().getUid(),
                msg,
                System.currentTimeMillis());

        //create a random ID
        String id = ref.push().getKey();
        ref.child(id).setValue(message);
    }
}

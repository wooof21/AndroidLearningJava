package com.androidlearning.firebase.chatmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.androidlearning.firebase.chatmvvm.model.pojo.ChatGroup;
import com.androidlearning.firebase.chatmvvm.model.pojo.ChatMessage;
import com.androidlearning.firebase.chatmvvm.model.repository.AuthRepo;
import com.androidlearning.firebase.chatmvvm.model.repository.ChatRepo;
import com.androidlearning.firebase.chatmvvm.model.repository.MsgRepo;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private AuthRepo authRepo;
    private ChatRepo chatRepo;
    private MsgRepo msgRepo;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
        chatRepo = new ChatRepo();
        msgRepo = new MsgRepo();
    }

    public void firebaseAnonymousLogin() {
        authRepo.firebaseAnonymousLogin(getApplication());
    }

    public String getUserId() {
        return authRepo.getUserId();
    }

    public void signOut() {
        authRepo.signOut();
    }

    public MutableLiveData<List<ChatGroup>> getChatGroupList() {
        return chatRepo.getChatGroupsLiveData();
    }

    public void createChatGroup(String groupName) {
        chatRepo.createChatGroup(groupName);
    }

    public MutableLiveData<List<ChatMessage>> getChatMessages(String groupName) {
        return msgRepo.getChatMsgLiveData(groupName);
    }

    public void sendMsg(String msg, String groupName) {
        msgRepo.sendMsg(msg, groupName);
    }
}

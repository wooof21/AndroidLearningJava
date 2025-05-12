package com.androidlearning.firebase.chatmvvm.model.pojo;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ChatMessage {

    private String senderId;
    private String message;
    private Long time;
    private Boolean isMine;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String message, Long time) {
        this.senderId = senderId;
        this.message = message;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Boolean isMine() {
        if (senderId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return true;
        }
        return false;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }


    public String convertTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(getTime());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }
}

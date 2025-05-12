package com.androidlearning.firebase.chatmvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.BR;
import com.androidlearning.R;
import com.androidlearning.databinding.ChatRowLayoutBinding;
import com.androidlearning.firebase.chatmvvm.model.pojo.ChatMessage;

import java.util.List;

public class GroupMsgAdapter extends RecyclerView.Adapter<GroupMsgAdapter.ViewHolder> {

    private List<ChatMessage> chatMessages;

    public GroupMsgAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatRowLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chat_row_layout,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * set binding variable of name (chatMessage)
         *
         * executePendingBindings: triggers the execution of pending bindings, ensure that
         * the data from the model (chatMessage), is bound to UI element specified in the layout
         *
         */
//        holder.binding.setChatMessage(chatMessages.get(position));
        holder.binding.setVariable(BR.chatMessage, chatMessages.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ChatRowLayoutBinding binding;

        public ViewHolder(ChatRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

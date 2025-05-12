package com.androidlearning.firebase.chatmvvm.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.ChatItemCardBinding;
import com.androidlearning.firebase.chatmvvm.model.pojo.ChatGroup;
import com.androidlearning.firebase.chatmvvm.view.GroupChatActivity;

import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ViewHolder> {

    private List<ChatGroup> chatGroups;

    public ChatGroupAdapter(List<ChatGroup> chatGroups) {
        this.chatGroups = chatGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chat_item_card,
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatGroup group = chatGroups.get(position);
        holder.binding.setChatGroup(group);
    }

    @Override
    public int getItemCount() {
        return chatGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ChatItemCardBinding binding;

        public ViewHolder(ChatItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();

                ChatGroup group = chatGroups.get(position);

                Intent intent = new Intent(v.getContext(), GroupChatActivity.class);
                intent.putExtra("GROUP_NAME", group.getGroupName());
                v.getContext().startActivity(intent);
            });
        }
    }
}

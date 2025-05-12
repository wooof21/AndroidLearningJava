package com.androidlearning.firebase.chatmvvm.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityGroupChatBinding;
import com.androidlearning.firebase.chatmvvm.model.pojo.ChatMessage;
import com.androidlearning.firebase.chatmvvm.view.adapter.GroupMsgAdapter;
import com.androidlearning.firebase.chatmvvm.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private ActivityGroupChatBinding binding;
    private ChatViewModel viewModel;
    private RecyclerView recyclerView;
    private GroupMsgAdapter adapter;
    private List<ChatMessage> chatMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_chat);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_chat);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        recyclerView = binding.groupChatRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        chatMessageList = new ArrayList<>();
        adapter = new GroupMsgAdapter(chatMessageList);
        recyclerView.setAdapter(adapter);


        //Getting the groupName from the clicked item in ChatGroupsActivity
        String groupName = getIntent().getStringExtra("GROUP_NAME");

        viewModel.getChatMessages(groupName).observe(this, chatMessages -> {
            chatMessageList.clear();
            chatMessageList.addAll(chatMessages);

            adapter.notifyDataSetChanged();

            //Scroll to the latest message added:
            int latestPosition = adapter.getItemCount() - 1;
            if(latestPosition > 0) {
                recyclerView.smoothScrollToPosition(latestPosition);
            }
        });

        binding.setViewModel(viewModel);

        binding.groupChatInputSend.setOnClickListener(v -> {
            String msg = binding.groupChatInputEt.getText().toString().trim();
            viewModel.sendMsg(msg, groupName);

            //clear the input ET
            binding.groupChatInputEt.getText().clear();
        });
    }
}
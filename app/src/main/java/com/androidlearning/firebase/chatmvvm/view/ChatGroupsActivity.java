package com.androidlearning.firebase.chatmvvm.view;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.androidlearning.databinding.ActivityChatGroupsBinding;
import com.androidlearning.databinding.ChatAddGroupDialogBinding;
import com.androidlearning.firebase.chatmvvm.model.pojo.ChatGroup;
import com.androidlearning.firebase.chatmvvm.view.adapter.ChatGroupAdapter;
import com.androidlearning.firebase.chatmvvm.viewmodel.ChatViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupsActivity extends AppCompatActivity {

    private List<ChatGroup> chatGroupList;
    private ActivityChatGroupsBinding binding;
    private RecyclerView recyclerView;
    private ChatGroupAdapter adapter;
    private ChatViewModel viewModel;

    private Dialog addGroupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_groups);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chatGroupList = new ArrayList<>();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_groups);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        recyclerView = binding.chatGroupRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatGroupAdapter(chatGroupList);
        recyclerView.setAdapter(adapter);

        //Set up observer to listen for changes in the LiveData object
        viewModel.getChatGroupList().observe(this, chatGroups -> {
            //the updated data is received as 'chatGroups' parameter in onChanged()
            chatGroupList.clear();
            chatGroupList.addAll(chatGroups);

            adapter.notifyDataSetChanged();

        });

        binding.chatGroupFab.setOnClickListener(v -> {
            openAddGroupDialog();
        });
    }

    public void openAddGroupDialog() {
        addGroupDialog = new Dialog(this);
        addGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(this)
                .inflate(R.layout.chat_add_group_dialog, null);
        addGroupDialog.setContentView(view);
        addGroupDialog.show();

        Button create = view.findViewById(R.id.add_group_dialog_create);
        EditText nameET = view.findViewById(R.id.add_group_dialog_name_et);

        create.setOnClickListener(v -> {
            String groupName = nameET.getText().toString().trim();
            viewModel.createChatGroup(groupName);
            addGroupDialog.dismiss();
        });
    }
}
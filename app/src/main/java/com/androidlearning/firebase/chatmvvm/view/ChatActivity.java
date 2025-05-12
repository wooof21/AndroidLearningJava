package com.androidlearning.firebase.chatmvvm.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityChatBinding;
import com.androidlearning.firebase.chatmvvm.viewmodel.ChatViewModel;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        ActivityChatBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        binding.setViewModel(viewModel);
    }
}
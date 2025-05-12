package com.androidlearning.viewmodel;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityCounterBinding;

public class CounterActivity extends AppCompatActivity {

    private ActivityCounterBinding binding;
    private CounterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_counter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_counter);

        /**
         * ViewModelProvider: manage the lifecycle of a ViewModel
         *
         * owner: Activity or Fragment -> this
         *
         * if ViewModel already exist(associate with current owner), it return and reuse
         */
        viewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        //linking ViewModel with DataBinding
        binding.setCounterviewmodel(viewModel);

        /**
         * using LiveData to observe the counter
         *
         * LiveData is lifecycle aware, means it will automatically start and stop
         * observing data based on the lifecycle state of the activity or fragment,
         * preventing potential memory leaks
         */
        viewModel.getCounter().observe(this, (counter) -> {
            //update UI for data changes
            binding.counterResult.setText("" + counter);
        });

//        binding.counterResult.setText("" + viewModel.getCounter());
    }
}
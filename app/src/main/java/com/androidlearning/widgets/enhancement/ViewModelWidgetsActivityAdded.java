package com.androidlearning.widgets.enhancement;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.androidlearning.R;

/**
 *     Lifecycle Awareness:
 *         The ViewModel and LiveData are lifecycle-aware, so UI updates only happen when the activity is in the correct state.
 *         No need for WeakReference or manually handling null checks for the activity.
 *
 *     Separation of Concerns:
 *         All state management (date and progress) is moved to the ViewModel, keeping the activity free of unnecessary logic.
 *         The ViewModel survives configuration changes, so the selected date and progress are preserved across screen rotations.
 *
 *     Cleaner Code:
 *         No need for static inner classes or manual listeners.
 *         The activity simply observes changes in LiveData and updates the UI reactively.
 *
 *
 *    Benefits:
 *          Avoid Memory Leaks: No WeakReference management required, and no risk of leaking the activity.
 *          Testability: The ViewModel is easier to unit test compared to the activity code.
 *          Future-Proof: Using modern Android architecture components ensures compatibility with evolving Android best practices.
 *
 */

public class ViewModelWidgetsActivityAdded extends AppCompatActivity {

    private WidgetsViewModel viewModel;
    private DatePicker datePicker;
    private ProgressBar progressBar;
    private Button progressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_widgets_added);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(WidgetsViewModel.class);

        // Setup DatePicker
        datePicker = findViewById(R.id.widgets_date_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                viewModel.setSelectedDate(year, monthOfYear, dayOfMonth);
            });
        }

        // Setup ProgressBar and Button
        progressBar = findViewById(R.id.widgets_progress_bar);
        progressBtn = findViewById(R.id.widgets_progress_add_btn);

        progressBtn.setOnClickListener(v -> viewModel.incrementProgress());

        // Observe LiveData for date updates
        viewModel.getSelectedDate().observe(this, date -> {
            Toast.makeText(this, "Date: " + date, Toast.LENGTH_SHORT).show();
        });

        // Observe LiveData for progress updates
        viewModel.getProgress().observe(this, progress -> {
            progressBar.setProgress(progress);
        });
    }
}

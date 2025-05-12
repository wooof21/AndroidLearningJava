package com.androidlearning.widgets;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

import java.lang.ref.WeakReference;
import java.util.Random;

public class WidgetsActivityAdded extends AppCompatActivity {


    DatePicker datePicker;
    ProgressBar progressBar;
    Button progressBtn;

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

        datePicker = findViewById(R.id.widgets_date_picker);
        //DatePicker.OnDateChangedListener is only effective when the DatePicker
        // is in spinner mode (not in calendar mode).
        // On devices running newer Android versions, DatePicker defaults to calendar mode.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //deprecated API, set DatePicker mode in XML file, android:datePickerMode="spinner"
            //datePicker.setSpinnersShown(true);
            //datePicker.setCalendarViewShown(false);
            datePicker.setOnDateChangedListener(new DatePickerListener(this));
        }

        progressBar = findViewById(R.id.widgets_progress_bar);
        progressBtn = findViewById(R.id.widgets_progress_add_btn);

        progressBtn.setOnClickListener(new ProgreeBtnClickListener(this));
    }

    /**
     * Problem: The use of WeakReference for the listeners ensures the WidgetsActivityAdded
     * instance can be garbage-collected when needed. However,
     * if the activity gets garbage-collected prematurely (e.g., during a long-running process),
     * the reference will be null, and the listener will stop working.
     * Improvement: Ensure that the listeners are only used when the activity is guaranteed to be alive.
     * This can be mitigated by managing the lifecycle properly.
     *
     * Problem: The listeners and the UI updates are not lifecycle-aware, which can lead to issues
     * if the activity is destroyed and recreated (e.g., during configuration changes like screen rotation).
     * Improvement:
     *
     *     Use a ViewModel to store the state of the progress bar and the selected date.
     *     This ensures the UI is updated correctly after configuration changes.
     */
    private static class DatePickerListener implements DatePicker.OnDateChangedListener {

        private final WeakReference<WidgetsActivityAdded> ref;

        private DatePickerListener(WidgetsActivityAdded activity) {
            this.ref = new WeakReference<>(activity);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            WidgetsActivityAdded activity = this.ref.get();
            if(activity != null) {
                //month start with 0
                int month = monthOfYear + 1;
                Toast.makeText(activity, "Date: " + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class ProgreeBtnClickListener implements View.OnClickListener {

        private final WeakReference<WidgetsActivityAdded> ref;

        private ProgreeBtnClickListener(WidgetsActivityAdded activity) {
            this.ref = new WeakReference<>(activity);
        }

        @Override
        public void onClick(View v) {
            WidgetsActivityAdded activity = this.ref.get();
            if(activity != null) {
                activity.progressBar.setProgress(new Random().nextInt(100));
            }
        }
    }
}
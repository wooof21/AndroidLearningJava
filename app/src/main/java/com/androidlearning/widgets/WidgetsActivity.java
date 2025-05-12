package com.androidlearning.widgets;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class WidgetsActivity extends AppCompatActivity {

    CheckBox checkbox;
    RadioGroup radioGroup;
    Spinner spinner;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_widgets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkbox = findViewById(R.id.widgets_checkbox);
        /**
         * checkbox.setOnCheckedChangeListener(new CheckBoxListener());
         *
         * Memory Leak Risk
         * If the CheckboxListener class is a non-static inner class, it implicitly holds a reference to
         * the outer WidgetsActivity instance. This could potentially cause a memory leak
         * if the listener's lifecycle outlives the activity
         * (e.g., if the activity is destroyed but the listener is still referenced somewhere).
         *
         * Solution:
         *
         * Make the CheckboxListener a static inner class and pass a weak reference to the WidgetsActivity:
         *
         */
        checkbox.setOnCheckedChangeListener(new CheckBoxListener(this));

        radioGroup = findViewById(R.id.widgets_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroupCheckedListener(this));

        spinner = findViewById(R.id.widget_spinner);
        //ArrayAdpter: used to populate the 'Spinner' with items from a string array resources
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                List.of("C", "Node.js", "Java", "Kotlin"));
        //apply adapter to spinner
        spinner.setAdapter(spinnerAdapter);

        timePicker = findViewById(R.id.widgets_time_picker);
        timePicker.setOnTimeChangedListener(new TimePickerListener(this));



    }

    private static class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        private final WeakReference<WidgetsActivity> ref;

        CheckBoxListener(WidgetsActivity activity) { this.ref = new WeakReference<>(activity); }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            WidgetsActivity activity = ref.get();
            if(activity != null) {
                if(isChecked) {
                    Toast.makeText(activity, "Checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Checkbox unchecked", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static class  RadioGroupCheckedListener implements RadioGroup.OnCheckedChangeListener {

        private final WeakReference<WidgetsActivity> ref;

        private RadioGroupCheckedListener(WidgetsActivity activity) {
            this.ref = new WeakReference<>(activity);
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            WidgetsActivity activity = ref.get();
            if(activity != null) {
                RadioButton radioButton = activity.findViewById(checkedId);
                Toast.makeText(activity, radioButton.getText().toString() + " Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class TimePickerListener implements TimePicker.OnTimeChangedListener {

        private final WeakReference<WidgetsActivity> ref;

        private TimePickerListener(WidgetsActivity activity) {
            this.ref = new WeakReference<>(activity);
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            WidgetsActivity activity = this.ref.get();
            if(activity != null) {
                String time = hourOfDay + ":" + minute;
                Toast.makeText(activity, "Time: " + time, Toast.LENGTH_LONG).show();
            }
        }
    }


}
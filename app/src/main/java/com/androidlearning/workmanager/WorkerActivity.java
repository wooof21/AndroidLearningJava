package com.androidlearning.workmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.androidlearning.R;

public class WorkerActivity extends AppCompatActivity {

    private Button run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_worker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        run = findViewById(R.id.worker_run_btn);

        /**
         * Passing and Receiving Data to/from Work Request
         *
         * Data: pass input data to a work request or a worker
         */

        Data data = new Data.Builder()
                .putInt("max_limit", 1000)
                .build();

        /**
         * Work Manager Constraints: conditions or requirements that can apply to the
         * Work Request to specify when and how the associated background task should be executed
         */
        Constraints constraints = new Constraints.Builder()
                //the task will only execute when the device is charging
                //WorkRequest will be first enqueued but wont be executed until
                //the device is in charging
                .setRequiresCharging(true)
                .build();

        /**
         * Making use of Worker
         *
         * OneTimeWorkRequest: represents a request to execute a one-time background task
         */
        WorkRequest wr = new OneTimeWorkRequest.Builder(WorkerTask.class)
                //Set constraints to Work Request
                .setConstraints(constraints)
                //pasing data to work request
                .setInputData(data)
                .build();

        //Enqueue the work request with WorkManager
        run.setOnClickListener(v -> {
            //Obtain an instance of WorkManager and enqueue a work request for execution
            WorkManager.getInstance(getApplicationContext())
                    .enqueue(wr);
        });

        /**
         * Monitor the status of Work Manager:
         *  - running, succeed, failed, canceled ...
         *
         *  Observe the changes in status of the Work Request, and notified when the status of
         *  request changes, and take appropriate actions based on the new status of Work Manager
         */

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(wr.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null) {
                        Toast.makeText(WorkerActivity.this, "Status: " + workInfo.getState().name(), Toast.LENGTH_LONG).show();

                        //Receive Data from Worker Task
                        if(workInfo.getState().isFinished()) {
                            String msgFromWorker = workInfo.getOutputData().getString("DONE_msg");
                            Toast.makeText(WorkerActivity.this, msgFromWorker, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
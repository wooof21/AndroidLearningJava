package com.androidlearning.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Define the background task to execute.
 */
public class WorkerTask extends Worker {

    public WorkerTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Run asynchronously on a background thread provided by WorkerManager
     *
     * return Result: informs the work manager service whether the work succeeded
     * and in case of failure, whether or not the work should be retried
     */
    @NonNull
    @Override
    public Result doWork() {

        //Retrieve Data from InputData
        Data data = getInputData();
        int countLimit = data.getInt("max_limit", 0);

        for(int i=0; i<countLimit; i++) {
            Log.i("TAG", "Count: " + i);
        }

        //Sending Data from Work Request to Activity
        Data sendMsg = new Data.Builder()
                .putString("DONE_msg", "Task Done Successfully")
                .build();

        return Result.success(sendMsg);
    }
}

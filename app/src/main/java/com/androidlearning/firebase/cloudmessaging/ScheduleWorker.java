package com.androidlearning.firebase.cloudmessaging;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

//Background job
public class ScheduleWorker extends Worker {

    private static final String TAG = "ScheduleWorker";

    public ScheduleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.v(TAG, "DO Long Running Task");
        return Result.success();
    }
}

package com.hfad.onlinemarket.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PollWorker extends Worker {
    public static final String TAG = "PollWorker";
    private static final String WORK_TAG_POLL = "NewProductPollWorker";


    public PollWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork");

        PollAndNotify.pollFromServerAndNotify(getApplicationContext());
        return Result.success();
    }


    public static void scheduleWork(Context context, boolean isOn, long repeatInterval) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(PollWorker.class, repeatInterval, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        if (isOn) {
            Log.d(TAG, "enqueue work");
            WorkManager
                    .getInstance(context)
                    .enqueueUniquePeriodicWork(
                            WORK_TAG_POLL,
                            ExistingPeriodicWorkPolicy.REPLACE,
                            workRequest);
        } else {
            Log.d(TAG, "cancel work");
            WorkManager
                    .getInstance(context)
                    .cancelUniqueWork(WORK_TAG_POLL);
        }
    }

    public static boolean isWorkScheduled(Context context) {
        try {
            WorkManager workManager = WorkManager.getInstance(context);
            List<WorkInfo> workInfos = workManager.getWorkInfosForUniqueWork(WORK_TAG_POLL).get();

            for (WorkInfo workInfo : workInfos) {
                if (workInfo.getState() == WorkInfo.State.ENQUEUED ||
                        workInfo.getState() == WorkInfo.State.RUNNING)
                    return true;
            }

            return false;
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }
}

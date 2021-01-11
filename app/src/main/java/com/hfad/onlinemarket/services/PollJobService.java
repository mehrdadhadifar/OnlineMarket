package com.hfad.onlinemarket.services;


import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hfad.onlinemarket.worker.PollAndNotify;

@SuppressLint("SpecifyJobSchedulerIdRange")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {

    public static final String TAG = "PollJobService";
    private static final int JOB_ID = 1;
    private PollTask mPollTask;

    public PollJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        mPollTask = new PollTask();
        mPollTask.execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mPollTask.cancel(true);
        return false;
    }

    public static void scheduleJob(Context context, boolean isOn,long delay) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo =
                new JobInfo.Builder(JOB_ID, new ComponentName(context, PollJobService.class))
                        .setMinimumLatency(delay)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(true)
                        .build();

        if (isOn) {
            Log.d(TAG, "job scheduled");
            jobScheduler.schedule(jobInfo);
        } else {
            Log.d(TAG, "job canceled");
            jobScheduler.cancel(JOB_ID);
        }
    }

    public static boolean isJobScheduled(Context context) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);

        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_ID)
                return true;
        }

        return false;
    }

    private class PollTask extends AsyncTask<JobParameters, Void, Void> {

        private JobParameters mJobParameters;

        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            mJobParameters = jobParameters[0];
            PollAndNotify.pollFromServerAndNotify(PollJobService.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            jobFinished(mJobParameters, false);
        }
    }
}
package com.sarker.jobschedulerexample.jobSchedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class JobServiceExample extends JobService {

    private static final String TAG = "JobServiceExample";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "Job Started");
        doBackgroundWork(params);

        return true; // return true if job completes within this method but return true if job 
        // will take more time than this method loop like dependent on network call or something
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<10;i++){
                    Log.i(TAG, "run: "+i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "job finished");
                jobFinished(params,false); //will set to true if we want to reschedule it
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "job cancelled before completion");
        jobCancelled = true;
        return true; // return true if u want to reschedule your task
    }
}

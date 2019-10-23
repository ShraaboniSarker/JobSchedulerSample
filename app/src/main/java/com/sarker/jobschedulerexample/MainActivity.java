package com.sarker.jobschedulerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sarker.jobschedulerexample.jobSchedule.JobServiceExample;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "JobServiceExample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View view) {

        ComponentName componentName = new ComponentName(this, JobServiceExample.class);
        JobInfo jobInfo = new JobInfo.Builder(000,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)//means want to keep serviice alive even after system reboot
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler schduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = schduler.schedule(jobInfo);
        if(resultCode == JobScheduler.RESULT_SUCCESS)
        {
            Log.i(TAG, "Successfully scheduled");
        }else{
            Log.i(TAG, "Job schedule cancel");
        }
    }

    public void cancelJob(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(000);
        Log.i(TAG, "Job cancelled");
    }
}

package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.BuildConfig;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver.ThemeModeReceiver;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ApplicationReceiverUtils;

public class ThemeModeJobService extends JobService {

    public static final int JOB_ID = 0;
    private static final long REFRESH_INTERVAL  = 5L * 1000L; // 5 seconds

    private ThemeModeReceiver mThemeModeReceiver;

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, ThemeModeJobService.class);

        // build the job
        JobInfo.Builder jobBuilder = new JobInfo.Builder(JOB_ID, serviceComponent)
                .setRequiresBatteryNotLow(false) // because we want to handle theme in low or not
                .setRequiresCharging(false) // don't care if the device in charging/not
                .setMinimumLatency(REFRESH_INTERVAL);

        String debugMsg = "";
        try {
            JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
            if(jobScheduler.getPendingJob(JOB_ID) == null) {
                jobScheduler.schedule(jobBuilder.build());
                debugMsg =  String.format("scheduleJob(context = [%s]) := %s", context, "schedule a job for ThemeModeJobService");
            } else {
                debugMsg =  String.format("scheduleJob(context = [%s]) := %s", context, "ThemeModeJobService has already scheduled");
            }
        } catch (NullPointerException e) {
            debugMsg = String.format("scheduleJob(context = [%s]) := %s", context, e.getMessage());
        } finally {
            if(BuildConfig.DEBUG) {
                Log.e(ThemeModeJobService.class.getName(), debugMsg);
            }
        }
    }

    /**
     * Start the job to register ThemeModeReceiver and keep it alive
     * @param jobParameters
     * @return true
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if(!this.isThemeModeReceiverExists()) {
            // register PowerSaveMode receiver
            this.setThemeModeReceiver(new ThemeModeReceiver());
            ApplicationReceiverUtils
                    .registerOnBatteryLowOrPowerSaveMode(getApplicationContext(), mThemeModeReceiver);
            String debugMsg = String.format("onStartJob(jobParameters = [%s]) := %s", jobParameters, "register ThemeModeReceiver");
            Log.d(ThemeModeJobService.class.getName(), debugMsg);
        }
        return true;
    }

    /**
     * Restart the job if failed, unregister the ThemeModeReceiver.
     * @param jobParameters
     * @return true
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(this.isThemeModeReceiverExists()) {
            ApplicationReceiverUtils.unregister(getApplicationContext(), mThemeModeReceiver);
            clearThemeModeReceiver();
            String debugMsg = String.format("onStartJob(jobParameters = [%s]) := %s", jobParameters, "unregister ThemeModeReceiver");
            Log.d(ThemeModeJobService.class.getName(), debugMsg);
        }
        return true;
    }

    private void setThemeModeReceiver(ThemeModeReceiver mThemeModeReceiver) {
        this.mThemeModeReceiver = mThemeModeReceiver;
    }

    private boolean isThemeModeReceiverExists() {
        return this.mThemeModeReceiver != null;
    }

    private void clearThemeModeReceiver() {
        setThemeModeReceiver(null);
    }
}

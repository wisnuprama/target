package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;

public class SeedDatabaseWorker extends Worker {

    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Context context = getApplicationContext();
            AppDatabase.seedUser(context);
            AppDatabase.seedProject(context);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}

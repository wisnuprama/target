package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ThemeModeJobService.scheduleJob(context);
        }
    }
}

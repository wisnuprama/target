package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.GoodMorningUtils;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // re run dark mode scheduler if enabled by user
            if(PreferenceRepository.isAutoDarkModeEnabled(context)) {
                ThemeModeJobService.scheduleJob(context);
            }
            // re-enable alarm after device boot is the good morning is activated
            if(PreferenceRepository.isGoodMorningActive(context)) {
                GoodMorningUtils.startGoodMorning(context);
            }
        }
    }
}

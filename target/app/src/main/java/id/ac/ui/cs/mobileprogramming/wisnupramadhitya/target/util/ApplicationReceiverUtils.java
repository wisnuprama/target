package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

import androidx.annotation.NonNull;

public class ApplicationReceiverUtils {
    private ApplicationReceiverUtils() {
    }

    /**
     * Register receiver for battery low and power save mode.
     * @param context
     * @param receiver
     */
    public static void registerOnBatteryLowOrPowerSaveMode(@NonNull Context context, @NonNull BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        context.getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    /**
     * Unregister receiver.
     * @param context
     * @param receiver
     */
    public static void unregister(@NonNull Context context, @NonNull BroadcastReceiver receiver) {
        context.getApplicationContext().unregisterReceiver(receiver);
    }
}

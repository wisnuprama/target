package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

import androidx.annotation.NonNull;

public class ApplicationReceiverUtils {

    public static void registerOnBatteryLowOrPowerSaveMode(@NonNull Context context, @NonNull BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        context.getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    public static void registerOnPackageChange(@NonNull Context context, @NonNull BroadcastReceiver receiver, String dataScheme) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme(dataScheme);
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        context.getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    public static void unregister(@NonNull Context context, @NonNull BroadcastReceiver receiver) {
        context.getApplicationContext().unregisterReceiver(receiver);
    }
}

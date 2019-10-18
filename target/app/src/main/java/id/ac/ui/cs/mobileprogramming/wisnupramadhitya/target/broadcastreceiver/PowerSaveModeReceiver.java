package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.BuildConfig;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ThemeUtils;

public class PowerSaveModeReceiver extends BroadcastReceiver {

    private static final String[] ACTIONS = {
        PowerManager.ACTION_POWER_SAVE_MODE_CHANGED,
        Intent.ACTION_BATTERY_LOW,
    };

    public static boolean isValidAction(Intent intent) {
        String intentAction = intent.getAction();
        for (String action : ACTIONS) {
            if(action.equals(intentAction)) return true;
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // if intent action is not pwoer save mode, then end.
        if(isValidAction(intent)) {
            if(BuildConfig.DEBUG) {
                String debugMsg = String.format("onReceive(context = [%s], intent = [%s])", context, intent);
                Log.d(this.getClass().getName(), debugMsg);
            }

            final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm != null && pm.isPowerSaveMode()) {
                ThemeUtils.applyTheme(ThemeUtils.ThemeMode.DARK);
            } else {
                // TODO: apply based on user settings
                ThemeUtils.applyTheme(ThemeUtils.ThemeMode.LIGHT);
            }
        }
    }
}

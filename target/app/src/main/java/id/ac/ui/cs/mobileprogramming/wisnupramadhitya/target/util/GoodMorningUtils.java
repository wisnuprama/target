package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.OkrActivity;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver.GoodMorningAlarmReceiver;

public class GoodMorningUtils {

    private GoodMorningUtils() {
    }

    private static AlarmManager getAlarmManager(@NonNull Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager == null) throw new RuntimeException("Alarm Manager is null");
        return alarmManager;
    }

    private static PendingIntent createGoodMorningAlarmPendingIntent(@NonNull Context context) {
        Intent intent = new Intent(context, GoodMorningAlarmReceiver.class);
        // create pending intent with flag update current
        // so we can recreate the intent and cancel it later if needed
        return PendingIntent.getBroadcast(context, 0,
                                          intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Start the good morning notification
     * @param context
     */
    public static void startGoodMorning(@NonNull Context context) {
        AlarmManager alarmManager = getAlarmManager(context);
        // create pending intent with flag update current
        // so we can recreate the intent and cancel it later
        PendingIntent alarmIntent = createGoodMorningAlarmPendingIntent(context);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8); // set 8AM

        // set non priority and repeating the alarm daily
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                          calendar.getTimeInMillis(),
                                          AlarmManager.INTERVAL_DAY,
                                          alarmIntent);
    }

    /**
     * Cancel the alarm with the same intent
     * @param context
     */
    public static void cancelGoodMorning(@NonNull Context context) {
        AlarmManager alarmManager = getAlarmManager(context);
        alarmManager.cancel(createGoodMorningAlarmPendingIntent(context));
    }

    // dispatch notification with same id so we can replace it
    // good morning notification is unique
    public static final int GOOD_MORNING_NOTIFICATION_ID = 1;

    /**
     * Create a good morning notification. You should dispatch it with {@link NotificationUtils}.
     * @param context
     * @return
     */
    public static Notification createGoodMorningNotification(@NonNull Context context) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, OkrActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, NotificationUtils.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(context.getString(R.string.good_morning_title))
                .setContentText(context.getString(R.string.notif_good_morning_content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // set intent to open okr activity when user tap
                .setAutoCancel(true); // remove notification after user tap

        return builder.build();
    }

    /**
     * To receive the alarm, we should enable the receiver. By default, it is disabled for battery reason.
     * Enable only when user enable the good morning feature.
     * @param context
     * @param isEnabled
     */
    public static void setReceiverEnabled(@NonNull Context context, boolean isEnabled) {
        ComponentName receiver = new ComponentName(context, GoodMorningAlarmReceiver.class);
        PackageManager pm = context.getPackageManager();
        final int NEW_STATE = isEnabled
                ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        pm.setComponentEnabledSetting(receiver,
                                      NEW_STATE,
                                      PackageManager.DONT_KILL_APP);
    }
}

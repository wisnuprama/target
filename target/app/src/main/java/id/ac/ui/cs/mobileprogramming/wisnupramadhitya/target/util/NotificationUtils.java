package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;

/**
 * {@link NotificationUtils} is a utility class for notification system in target app
 */
public class NotificationUtils {

    public static final String NOTIFICATION_CHANNEL_ID = "target_notification_1";

    private NotificationUtils() {}

    /**
     * Create notification channel for android higher than equal 8.
     */
    public static void createNotificationChannel(@NonNull Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.default_channel_name);
            String description = context.getString(R.string.default_channel_description);
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                                                                  name,
                                                                  NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void dispatchNotification(@NonNull Context context, @NonNull Integer id, @NonNull Notification notification) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, notification);
    }
}

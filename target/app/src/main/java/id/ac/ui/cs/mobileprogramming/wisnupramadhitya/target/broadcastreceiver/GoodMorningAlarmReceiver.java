package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.GoodMorningUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.NotificationUtils;

public class GoodMorningAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = GoodMorningUtils.createGoodMorningNotification(context);
        NotificationUtils.dispatchNotification(context,
                                               GoodMorningUtils.GOOD_MORNING_NOTIFICATION_ID,
                                               notification);
    }
}

package com.baidao.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.baidao.tools.BusProvider;
import com.baidao.tools.SharedPreferenceUtil;

/**
 * Created by rjhy on 14-12-10.
 */
abstract public class NotificationHandler {

    protected Context context;
    protected NotificationManager notificationManager;

    public NotificationHandler(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

//    static private int getDefaults(boolean sound) {
//        int setting = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
//        if (sound) {
//            setting |= Notification.DEFAULT_SOUND;
//        }
//        return setting;
//    }

    abstract public boolean canHandle(NotificationType dataType);

    protected Notification build(NotificationMessage message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        try {
            Drawable drawable = context.getPackageManager().getApplicationIcon(context.getPackageName());
            if(drawable instanceof BitmapDrawable){
                builder.setLargeIcon(((BitmapDrawable) drawable).getBitmap());
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        builder.setSmallIcon(context.getApplicationInfo().icon);
        builder.setContentTitle(message.title)
                .setContentText(message.text)
                .setTicker(message.ticker)
                .setContentIntent(getContentIntent(message))
                .setWhen(System.currentTimeMillis())
                .setDefaults(message.getDefaults())
                .setOnlyAlertOnce(true)
                .setAutoCancel(message.getAutoCancel());

        if (message.play_sound && !TextUtils.isEmpty(message.sound)) {
            builder.setSound(message.getSound());
        }

        return builder.build();
    }

    public void handle(NotificationMessage message) {
        onHandle(message);
    }

    abstract protected PendingIntent getContentIntent(NotificationMessage message);

    abstract public void onHandle(NotificationMessage message);
}

package com.baidao.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.baidao.tools.AppUtil;
import com.baidao.tools.BusProvider;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;

/**
 * Created by burizado on 15-1-29.
 */
public class ForegroundHandler extends NotificationHandler {
    public ForegroundHandler(Context context) {
        super(context);
    }

    @Override
    public boolean canHandle(NotificationType dataType) {
        boolean isForeground = AppUtil.isAppOnForeground(context);
        boolean isCallUI = AppUtil.isActivityForeground(context, context.getPackageName() + ".call.VoiceCallActivity");
        return Util.isScreenOn(context) && isForeground && !isCallUI && (dataType != null && dataType.isNeedHandle());
    }

    @Override
    protected PendingIntent getContentIntent(NotificationMessage message) {
        return null;
    }

    @Override
    public void onHandle(final NotificationMessage message) {
        if (message.type == NotificationType.EMP_SEND_NEWS) {
            if (!UserHelper.getInstance(context).isLogin()) {
                return;
            } else if (AppUtil.isActivityForeground(context, context.getPackageName() + ".chat.ui.ChatActivity")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new NotificationBusEvent(NotificationType.EMP_SEND_NEWS));
                    }
                });
                return ;
            }
        }

        if (message.type == NotificationType.FEEDBACK && AppUtil.isActivityForeground(context, context.getPackageName() + ".me.feedback.FeedbackActivity")) return;

        Intent intent = new Intent();
        intent.putExtra("message", message);
        intent.setClass(context, DialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

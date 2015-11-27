package com.baidao.notification;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.baidao.tools.AppUtil;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;

/**
 * Created by burizado on 15-1-14.
 */
public class CommonHandler extends NotificationHandler {
    public CommonHandler(Context context) {
        super(context);
    }

    @Override
    public boolean canHandle(NotificationType dataType) {
        boolean isForeground = AppUtil.isAppOnForeground(context);
        boolean isCallUI = AppUtil.isActivityForeground(context, context.getPackageName() + ".call.VoiceCallActivity");
        return !Util.isScreenOn(context) || ((!isForeground || isCallUI) && (dataType != null && dataType.isNeedHandle()));
    }

    @Override
    protected PendingIntent getContentIntent(NotificationMessage message) {

        Intent intent = new Intent();
        PendingIntent pendingIntent = null;

        if (message.type == NotificationType.FEEDBACK) {
            intent.setComponent(new ComponentName(context.getPackageName(), "com.baidao.ytxmobile.me.feedback.FeedbackActivity"));
        } else {
            intent.setComponent(new ComponentName(context.getPackageName(), context.getPackageName() + Definition.MAINACTIVITY_NAME));
            intent.putExtra(Definition.KEY_MESSAGE, message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public void onHandle(NotificationMessage message) {
        if (message.type == NotificationType.EMP_SEND_NEWS && !UserHelper.getInstance(this.context).isLogin())
            return;
        NotificationType type = message.type;
        Util.turnOnScreent(context);
        notificationManager.notify(type.getId(), build(message));
    }

}

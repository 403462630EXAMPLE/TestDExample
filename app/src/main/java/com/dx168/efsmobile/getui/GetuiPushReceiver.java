package com.dx168.efsmobile.getui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.baidao.notification.NotificationMessage;
import com.baidao.notification.NotificationProcessor;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import org.json.JSONException;
import org.json.JSONObject;

public class GetuiPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {

            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
                if (payload != null) {
                    String message = new String(payload);
                    Log.d("GetuiSdkDemo", "Got Payload:" + message);
                    try {
                        JSONObject jsonObject = new JSONObject(message);
//                        if (AppUtil.isActivityForeground(context, "com.baidao.ytxmobile.chat.ui.ChatActivity")) {
//                            BusProvider.getInstance().post(new NewNotificationEvent());
//                            return;
//                        }
                    } catch (Exception e) {
                        return;
                    }

                    try {
                        NotificationProcessor.getInstance().processNotification(NotificationMessage.fromGetuiMessage(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*String appid = bundle.getString("appid");
                String taskid = bundle.getString("taskid");
                String actionid = bundle.getString("actionid");
                String result = bundle.getString("result");
                long timestamp = bundle.getLong("timestamp");

                Log.d("GetuiSdkDemo", "appid = " + appid);
                Log.d("GetuiSdkDemo", "taskid = " + taskid);
                Log.d("GetuiSdkDemo", "actionid = " + actionid);
                Log.d("GetuiSdkDemo", "result = " + result);
                Log.d("GetuiSdkDemo", "timestamp = " + timestamp);*/
                break;
            default:
                break;
        }
    }
}

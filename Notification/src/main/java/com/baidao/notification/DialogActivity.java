package com.baidao.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.baidao.tracker.LogData;
import com.baidao.tracker.Tracker;

/**
 * Created by burizado on 15-1-29.
 */
public class DialogActivity extends Activity{
    private AlertDialog dialog;
    private AlertDialog chatDialog;
    private NotificationMessage message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        handleNotification();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleNotification();
    }

    private void handleNotification(){
        if (getIntent().hasExtra("message")) {
            message = getIntent().getParcelableExtra("message");
            showDialog(this);
        } else {
            //聊天提示
            showDialogForChat(this);
        }
    }

    public void showDialog(final Context context) {
        if(dialog == null){
            dialog = new AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setPositiveButton("立即查看", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            try {
                                String className;
                                if (message.type == NotificationType.FEEDBACK) {
                                    className = getPackageName() + ".me.feedback.FeedbackActivity";
                                } else {
                                    className = getPackageName() + Definition.MAINACTIVITY_NAME;
                                }
                                intent = new Intent(context, Class.forName(className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(intent == null) return;
                            intent.putExtra(Definition.KEY_MESSAGE, message);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            dialog.dismiss();
                            Tracker.getInstance(context).addLog(new LogData.Builder(context).event("push_open")
                                    .append("targetType", String.valueOf(message.type.getValue()))
                                    .append("targetId", String.valueOf(message.type == NotificationType.NEWS_MOVE ? message.contentId : message.detailId)));
                            finish();
                        }
                    })
                    .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Tracker.getInstance(context).addLog(new LogData.Builder(context).event("push_cancel")
                                    .append("targetType", String.valueOf(message.type.getValue()))
                                    .append("targetId", String.valueOf(message.type == NotificationType.NEWS_MOVE ? message.contentId : message.detailId)));

                            dialog.dismiss();
                            finish();
                        }
                    }).create();
        }
        dialog.setTitle(message.title);
        dialog.setMessage(message.text);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    public void showDialogForChat(final Context context) {
        if(chatDialog == null){
            chatDialog = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setPositiveButton("立即查看", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            try {
                                String className = getPackageName() + ".chat.ui.ChatActivity";
                                intent = new Intent(context, Class.forName(className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(intent == null) return;
                            context.startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create();
        }
        chatDialog.setTitle("您有一条投顾的聊天消息");
        chatDialog.show();
        chatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }
}

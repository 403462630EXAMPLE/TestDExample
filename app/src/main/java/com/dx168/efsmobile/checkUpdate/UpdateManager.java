package com.dx168.efsmobile.checkUpdate;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.baidao.data.UpdateAppResult;
import com.baidao.efsmobile.BuildConfig;
import com.dx168.efsmobile.utils.Util;
import com.baidao.tools.UserHelper;
import com.ytx.library.provider.ApiFactory;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class UpdateManager {
    private static final String TAG = UpdateManager.class.getName();

    public final static String CAN_UPDATE = "canUpdate";
    public final static String IGNORE_UPDATE = "ignoreUpdate";

    private Context context;
    private boolean checkUpdateByUser = false;

    public UpdateManager(Context context) {
        this.context = context;
//        checkUpdate();
    }

    public UpdateManager(Context context, boolean byUser) {
        this.context = context;
        this.checkUpdateByUser = byUser;
        checkUpdate();
    }

    public void checkUpdate() {

        if (isServiceRunning(context, DownloadService.class.getName())) {
            Toast.makeText(context, "正在下载中..", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiFactory.getJryApi().checkUpdate(BuildConfig.VERSION_NAME, Util.getChannel(context), context.getApplicationContext().getPackageName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateAppResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "网络连接超时,请检查当前网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(UpdateAppResult result) {

                        if (!result.forceUpdate) {
                            if (result.canUpdate) {
                                showNoticeDialog(result);
                            } else {
                                if (checkUpdateByUser) {
                                    Toast.makeText(context, "已是最新版本, 无需更新", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            startDownloadService(result.appUrl);
                        }
                    }
                });
    }


    private void showNoticeDialog(final UpdateAppResult result) {

        if (!checkUpdateByUser) {
            if (UserHelper.getInstance(context).getString(IGNORE_UPDATE, "").equals(result.versionId)) {
                return;
            }
        }

        AlertDialog dlg = new AlertDialog.Builder(context)
                .setTitle("有新版本可以更新")
                .setMessage(result.description)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startDownloadService(result.appUrl);
                        Toast.makeText(context, "正在下载中..", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserHelper.getInstance(context).putString(IGNORE_UPDATE, result.versionId);
                        dialog.dismiss();
                    }
                }).create();
        dlg.show();
    }

    private void startDownloadService(String appUrl) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.APP_URL, appUrl);
        context.startService(intent);
    }

    private boolean isServiceRunning(Context mContext, String className) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }

}
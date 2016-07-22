package cn.hdmoney.hdy.checkUpdate;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.UpdateApp;
import cn.hdmoney.hdy.dialog.AppDialog;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class UpdateManager {
    private static final String TAG = UpdateManager.class.getName();

    public final static String CAN_UPDATE = "canUpdate";
    public final static String IGNORE_UPDATE = "ignoreUpdate";

    private Context context;
    private boolean checkUpdateByUser = false;
    AppDialog appDialog;

    public UpdateManager(Context context) {
        this.context = context;
//        checkUpdate();
    }

    public UpdateManager(Context context, boolean byUser) {
        this.context = context;
        this.checkUpdateByUser = byUser;
//        checkUpdate();
    }

    public void checkUpdate() {

        if (isServiceRunning(context, DownloadService.class.getName())) {
            Toast.makeText(context, "正在下载中..", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiFactory.getHdyApi().checkUpdate("302399089028627", 1, "3").observeOn(AndroidSchedulers.mainThread())
            .subscribe(new HdyObserver<Result<UpdateApp>>() {
                @Override
                public void onCompleted() {}

                @Override
                public void onThrowable(Throwable e) {
                    Toast.makeText(context, "网络连接超时,请检查当前网络设置", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(Result<UpdateApp> result) {
                    if (result != null && result.isSuccess()) {
                        if (result.result.updateCode == 3) { //强制更新
                            startDownloadService(result.result.updateUrl);
                        } else if (result.result.updateCode == 2) { //有更新
                            showNoticeDialog(result.result);
                        } else {
                            if (checkUpdateByUser) {
                                Toast.makeText(context, "已是最新版本, 无需更新", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

//        ApiFactory.getHdyApi().getBidList(0, 1).observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//        ApiFactory.getHdyApi().getBidInfo(10009).observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//        ApiFactory.getHdyApi().getVerifyCode("1435816755986", 1, "13823242942", "1").observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Result<VerifyCode>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Result<VerifyCode> verifyCodeResult) {
//
//                    }
//                });
//        UploadParam param = new UploadParam.UploadParamBuilder().put("type", 1).put("uid", 10009).put("token", "f33239446565d76c0e204f09b778caf2").build();
//        ApiFactory.getHdyApi().uploadFile(param, MultipartUtils.getPart("file", "/storage/emulated/0/Android/data/cn.hdmoney.hdy/cache/image/image_selector/img_1467338883588.jpg"))
//                .subscribe(new Observer<Result<Result.UploadResult>>() {
//                    @Override
//                    public void onCompleted() {}
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError");
//                    }
//
//                    @Override
//                    public void onNext(Result<Result.UploadResult> uploadResultResult) {
//                        Log.i(TAG, "onNext");
//                    }
//                });
//        TradeSettingParam param = new TradeSettingParam();
//        param.type = 2;
//        param.uid = "1000089";
//        param.mobile = "13823242942";
//        param.verifyCode = "100890";
//        param.oldPayPasswor = Md5.encode("123456").toUpperCase();
//        param.payPassword = Md5.encode("111111").toUpperCase();
//        param.token = "f33239446565d76c0e204f09b778caf2";
//        ApiFactory.getHdyApi().modifyTradePassword(param).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Result>() {
//                    @Override
//                    public void onCompleted() {}
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError");
//                    }
//
//                    @Override
//                    public void onNext(Result result) {
//                        Log.i(TAG, "onNext");
//                    }
//                });
    }


    private void showNoticeDialog(final UpdateApp result) {

//        if (!checkUpdateByUser) {
//            if (UserHelper.getInstance(context).getString(IGNORE_UPDATE, "").equals(result.versionId)) {
//                return;
//            }
//        }
        if (appDialog == null) {
            appDialog = new AppDialog(context, AppDialog.FLAG_CHECK_UPDATE);
            appDialog.setDialogActionListener(new AppDialog.DialogActionListener() {
                @Override
                public void onLeftAction(int flag) {
                    appDialog.dismiss();
                }

                @Override
                public void onRightAction(int flag) {
                    startDownloadService(result.updateUrl);
                    Toast.makeText(context, "正在下载中..", Toast.LENGTH_SHORT).show();
                    appDialog.dismiss();
                }
            });
        }
        appDialog.show();
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
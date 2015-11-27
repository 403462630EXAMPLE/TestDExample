package com.dx168.efsmobile.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidao.data.Result;
import com.baidao.tools.BuildConfig;
import com.baidao.tools.TelephoneUtil;
import com.baidao.tools.UserHelper;
import com.ytx.library.provider.ApiFactory;

import rx.Observer;

/**
 * Created by hexi on 15/2/2.
 */
public class DeviceTokenManager {
    private static final String TAG= "DeviceTokenManager";

    public static void saveDeviceToken(Context context, String deviceToken) {

        if (TextUtils.isEmpty(deviceToken)) {
            return;
        }
        String deviceId = TelephoneUtil.getEncodedDeviceId(context);
        String imei = TelephoneUtil.getIMEI(context);
        String appId = BuildConfig.APPLICATION_ID;
        int serverId = com.baidao.tools.Util.getCompanyId(context);
        String appVersion = BuildConfig.VERSION_NAME;
        String token = UserHelper.getInstance(context).getToken();
        Log.d(TAG, "===start to save device token deviceId: " + deviceId);
        ApiFactory.getJryApi().saveDeviceToken(deviceToken, deviceId, imei, appId, serverId, appVersion).subscribe(new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "saveDeviceToken error ", e);
            }

            @Override
            public void onNext(Result result) {
                Log.d(TAG, "saveDeviceToken success? " + result.isSucces());
            }
        });
    }

    public static void clearUser(Context context) {
        String appId = BuildConfig.APPLICATION_ID;
        String deviceId = TelephoneUtil.getEncodedDeviceId(context);
        Log.d(TAG, "===start to clear username of device token deviceId: " + deviceId);
        ApiFactory.getJryApi().clearUsernameOfDeviceToken(appId, deviceId).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

            }
        });
    }

    public static void saveDeviceTokenForTradeAccount(Context context, String account, String deviceToken, String username) {

        if (TextUtils.isEmpty(account)) {
            return;
        }

        String deviceId = TelephoneUtil.getEncodedDeviceId(context);
        String appId = BuildConfig.APPLICATION_ID;
        int serverId = com.baidao.tools.Util.getCompanyId(context);
        String appVersion = BuildConfig.VERSION_NAME;
        Log.d(TAG, "===start to save device token for tradeAccount");
        ApiFactory.getJryApi().saveDeviceTokenForTradeAccount(account, deviceToken, username, deviceId, appId, serverId, appVersion).subscribe(new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "saveDeviceTokenForTradeAcconut error ", e);
            }

            @Override
            public void onNext(Result result) {
                Log.d(TAG, "saveDeviceTokenForTradeAcconut success? " + result.isSucces());
            }
        });
    }

    public static void clearTradeAccount(String account) {
        Log.d(TAG, "===start to clear tradeAccount");
        ApiFactory.getJryApi().clearTradeAccountOfDeviceToken(account).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

            }
        });
    }
}

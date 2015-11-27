package com.baidao.tools;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by chengxin on 3/2/15.
 */
public class TelephoneUtil {
    private static final String TAG = TelephoneUtil.class.getSimpleName();

    private static String getMacAddress(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wifimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = getDeviceId(context);
        }
        return macAddress;
    }

    private static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    public static String getIMEI(Context context) {
        //the IMEI for GSM and the MEID or ESN for CDMA phones
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getEncodedDeviceId(Context context) {
        String deviceId = getMacAddress(context) + "-" + getDeviceId(context);
        deviceId = EncryptUtil.sha1(deviceId);
        return deviceId;
    }

    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();

            json.put("mac", getMacAddress(context));
            json.put("device_id", getDeviceId(context));

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getScreen(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return width + "*" + height;
    }
}

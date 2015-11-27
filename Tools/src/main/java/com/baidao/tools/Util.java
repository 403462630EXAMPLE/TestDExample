package com.baidao.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.PowerManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidao.data.e.Server;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by BurizaDo on 3/3/15.
 */
public class Util {
    private static final String KEY_PHONE_PATTERN = "%s.phone";
    private static final String AGENT_ID = "agentId";

    public static void launchTradeApp(Context context){
        Intent launchIntent = null;
        int id = getCompanyId(context);
        if(id == Server.YG.serverId){
            launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.revenco.goldtrade.activity");
        }else if(id == Server.TT.serverId){
            launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.llbt.tmbs");
        }else if(id == Server.SSY.serverId) {
            launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.happyinsource.htny.shequ.ultimate");
        }

        if(launchIntent != null) {
            context.startActivity(launchIntent);
        }else{
            String url = "http://www.5800.com/appweb/traderdown/index.html";
            if(id == Server.TT.serverId){
                url += "?agent=tt";
            }else if(id == Server.YG.serverId){
                url += "?agent=yg";
            }else if(id == Server.SSY.serverId){
                url += "?agent=ssy";
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(browserIntent);
            } else {
                Toast.makeText(context, "请先安装浏览器", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int getCompanyId(Context context){
        int id;
        if(UserHelper.getInstance(context).isLogin()){
            id = UserHelper.getInstance(context).getUser().getServerId();
        }else {
            id = SharedPreferenceUtil.getSharedPreference(context).getInt(AGENT_ID, -1);
        }
        return id;
    }

    public static Server getServer(Context context) {
        return Server.from(getCompanyId(context));
    }

    public static void makeCompanyCall(Context context) {
        String phoneNumber = getPhoneNumber(context);
        if (TextUtils.isEmpty(phoneNumber)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static String getPhoneNumber(Context context) {
        String phoneNumber = getPhoneNumberOnline(context);
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = Server.from(Util.getCompanyId(context)).phoneNumber;
        }
        return phoneNumber;
    }

    private static String getPhoneNumberOnline(Context context) {
        Server server = Server.from(Util.getCompanyId(context));
        String phoneKey = String.format(KEY_PHONE_PATTERN, server.name);
        return MobclickAgent.getConfigParams(context, phoneKey);
    }

    public static void setAgentId(Context context, int agentId) {
        SharedPreferences.Editor editor = SharedPreferenceUtil.getSharedPreference(context).edit();
        editor.putInt(AGENT_ID, agentId);
        editor.commit();
    }

    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    public static void turnOnScreent(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
        wl.acquire(10000);
        PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
        wl_cpu.acquire(10000);
    }
}

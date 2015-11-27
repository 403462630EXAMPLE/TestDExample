package com.dx168.efsmobile.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.baidao.chart.db.model.ByDbData;
import com.baidao.chart.db.model.ByDbInfo;
import com.baidao.chart.db.model.KLineData;
import com.baidao.chart.db.model.KLineInfo;
import com.baidao.chart.db.model.QKLineDbData;
import com.baidao.chart.db.model.QKLineDbInfo;
import com.baidao.chart.db.model.QKTuDbData;
import com.baidao.chart.db.model.QKTuDbInfo;
import com.baidao.chart.db.typeSerializer.UtilDateSerializer;
import com.baidao.data.e.Server;
import com.baidao.efsmobile.BuildConfig;
import com.dx168.efsmobile.config.IndexConfigHelper;
import com.dx168.efsmobile.config.IndexPermissionHelper;
import com.dx168.efsmobile.me.feedback.FeedbackActivity;
import com.dx168.efsmobile.utils.EventIDS;
import com.baidao.notification.CommonHandler;
import com.baidao.notification.ForegroundHandler;
import com.baidao.notification.NotificationProcessor;
import com.baidao.quotation.CategoryConfig;
import com.baidao.tools.AppUtil;
import com.baidao.tools.TelephoneUtil;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;
import com.baidao.tracker.LogData;
import com.coolerfall.watcher.Watcher;
import com.google.gson.Gson;
import com.umeng.fb.push.FeedbackPush;
import com.ytx.library.provider.ApiFactory;
import com.ytx.library.provider.Domain;

/**
 * Created by Bruce on 12/5/14.
 */
public class DxApplication extends Application {
    private static final String TAG = "YtxApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "--YtxApplication--onCreate");
        startUninstallWatcher();

        FeedbackPush.getInstance(this).init(FeedbackActivity.class, false);

        Domain.setIsDebug(BuildConfig.DEBUG);
        if(Util.getCompanyId(this.getApplicationContext()) >= 0){
            Domain.setDOMAIN(Server.from(com.baidao.tools.Util.getCompanyId(this)));
        }
        initializeDB();
        ApiFactory.init(() -> {
            String token = UserHelper.getInstance(getApplicationContext()).getToken();
            if (token != null && token.length() > 1) {
                return token;
            }
            return null;
        });

        loadPreLoadConfig();

        NotificationProcessor.getInstance().clear();
        NotificationProcessor.getInstance().registerHandler(new CommonHandler(this));
        NotificationProcessor.getInstance().registerHandler(new ForegroundHandler(this));

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void startUninstallWatcher() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(Domain.get(Domain.DomainType.STATISTICS));
        urlBuilder.append("?os=Android");
        urlBuilder.append("&appVersion=" + AppUtil.getAppVersion(getApplicationContext()));
        urlBuilder.append("&deviceId=" + TelephoneUtil.getEncodedDeviceId(getApplicationContext()));

        LogData logData = new LogData.Builder(getApplicationContext()).event(EventIDS.APP_UNINSTALL);
        logData.withChannel(com.dx168.efsmobile.utils.Util.getChannel(this))
                .withScreen(TelephoneUtil.getScreen(getApplicationContext()))
                .withAppVersion(AppUtil.getAppVersion(getApplicationContext()))
                .withDeviceId(TelephoneUtil.getEncodedDeviceId(getApplicationContext()));

        Watcher.run(this, urlBuilder.toString(), new Gson().toJson(logData), false);
    }

    public void initializeDB() {
        Configuration configuration = new Configuration.Builder(this)
                .addModelClasses(
                        KLineInfo.class,
                        KLineData.class,
                        QKLineDbInfo.class,
                        QKLineDbData.class,
                        QKTuDbInfo.class,
                        QKTuDbData.class,
                        ByDbInfo.class,
                        ByDbData.class
                )
                .addTypeSerializers(
                        UtilDateSerializer.class
                )
                .create();
        ActiveAndroid.initialize(configuration);
    }

    private void loadPreLoadConfig() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CategoryConfig.loadConfig(DxApplication.this);
                } catch (Exception e) {
                    Log.d(TAG, "load config error ", e);
                }

                try {
                    IndexConfigHelper.loadIndexConfigOfLocal(getApplicationContext());
                } catch (Exception e) {
                    Log.d(TAG, "load config error ", e);
                }

                try {
                    IndexPermissionHelper.loadIndexPermissionOfLocal(getApplicationContext());
                } catch (Exception e) {
                    Log.d(TAG, "load config error ", e);
                }
            }
        }).start();
    }

}

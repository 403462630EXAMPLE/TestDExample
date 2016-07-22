package cn.hdmoney.hdy.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.liuguangqiang.framework.utils.AppUtils;
import com.liuguangqiang.framework.utils.PreferencesUtils;

import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.lockpattern.LockPatternUtils;
import cn.hdmoney.hdy.module.AppModule;
import cn.hdmoney.hdy.module.PresenterModule;
import cn.hdmoney.hdy.utils.AreaDbUtils;
//import cn.hdmoney.hdy.utils.ImageLoaderUtils;
import cn.hdmoney.hdy.utils.Foreground;
import dagger.ObjectGraph;
import haibison.android.lockpattern.LockPatternActivity;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MyApplication extends Application implements Foreground.Listener{
    private static MyApplication app;
    private ObjectGraph mObjectGraph;
    private Foreground.Binding binding;

    public static MyApplication from() {
        return app;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app=this;
//        ImageLoaderUtils.init(getApplicationContext());
        String processName = AppUtils.getProcessName(this, android.os.Process.myPid());
        //为避免当有多个线程时，onCreate()方法会被多次执行，如下处理
        if (processName != null) {
            boolean defaultProcess = processName.equals(Constants.REAL_PACKAGE_NAME);
            if (defaultProcess) {
                initAppForMainProcess();
            } else if (processName.contains(":webbrowser")) {
//                initAppForWebBrowseProcess();
            } else if (processName.contains(":wallet")) {
            }
        }
    }
    protected void initAppForMainProcess() {
//        ImageLoaderUtils.init(getApplicationContext());
//        LoginManager.init(getApplicationContext());
//        initOpenUDID();
//        initImeiCode();
//        createFolder();
//        initGalleryFinal();
        createObjectsGraph();
        AreaDbUtils.asynCopy(getApplicationContext());
        LockPatternUtils.init(getApplicationContext());

        if (binding != null) {
            binding.unbind();
        }
        binding = Foreground.init(this).addListener(this);
    }
    private void createObjectsGraph() {
        mObjectGraph = ObjectGraph.create(
                AppModule.class,
                PresenterModule.class
        );
        mObjectGraph.inject(this);
        
    }
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    @Override
    public void onBecameForeground() {
        long currentTime = System.currentTimeMillis();
        long lastTime = PreferencesUtils.getLong(this, getPackageName(), "becameBackgroundTime", 0);
        if (currentTime < lastTime || currentTime - lastTime > 5 * 60 * 1000) {
            if (!AppUtils.isActivityForeground(this, LockPatternActivity.class.getName())) {
                PreferencesUtils.putBoolean(this, getPackageName(), "isShouldShowLockPattern", true);
            }
        }
    }

    @Override
    public void onBecameBackground() {
        PreferencesUtils.putLong(this, getPackageName(), "becameBackgroundTime", System.currentTimeMillis());
    }
}

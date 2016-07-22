package cn.hdmoney.hdy.app;

import com.facebook.stetho.Stetho;

import cn.hdmoney.hdy.app.MyApplication;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class DebugApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}

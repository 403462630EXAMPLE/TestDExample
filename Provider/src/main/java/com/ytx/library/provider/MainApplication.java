package com.ytx.library.provider;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by chengxin on 12/18/14.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static ObjectGraph objectGraph = null;

    public static synchronized void init(Object... rootModules) {
        objectGraph = objectGraph == null ? ObjectGraph.create(rootModules) : objectGraph.plus(rootModules);
    }

    public static void inject(final Object target) {
        objectGraph.inject(target);
    }

    public static void add(Object... object) {
        objectGraph = ObjectGraph.create(object);
    }
}

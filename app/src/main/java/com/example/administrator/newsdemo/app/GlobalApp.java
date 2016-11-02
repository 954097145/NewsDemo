package com.example.administrator.newsdemo.app;

import android.app.Application;
import org.xutils.BuildConfig;
import org.xutils.x;
/**
 * Created by Administrator on 2016/10/29.
 */

public class GlobalApp extends Application {
    private static GlobalApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }

    public static GlobalApp getApp() {

        return app;
    }
}

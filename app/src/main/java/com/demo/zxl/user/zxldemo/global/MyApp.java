package com.demo.zxl.user.zxldemo.global;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by user on 2018/6/17.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


       //内存泄漏检测初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}

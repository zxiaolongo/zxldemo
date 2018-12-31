package com.demo.zxl.user.zxldemo.global;

import android.animation.ArgbEvaluator;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by user on 2018/6/17.
 */

public class MyApp extends Application {
    private static Context mCtx;
    @Override
    public void onCreate() {
        super.onCreate();
        //1.初始化上下文,让所有的页面都可以使用
        mCtx = this;

       //内存泄漏检测初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

    }
    public static Context getContext(){
        return mCtx;
    }
}

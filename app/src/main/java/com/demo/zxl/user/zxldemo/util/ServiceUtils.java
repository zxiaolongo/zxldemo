package com.demo.zxl.user.zxldemo.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by beibei on 2017/11/22.
 */

public class ServiceUtils {

    /**
     * 根据服务的类名获取当前的服务是否处于运行状态
     * @param context
     * @param className 服务的类名
     * @return
     */
    public static boolean isServiceRunning(Context context, String className){
        //ActivityManager 可以获取当前设备的运行状态
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //通过ActivityManager 可以获取到正在运行的服务信息
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(2000);
        if(runningServices!=null && runningServices.size()>0){
            //遍历所有正在运行的服务信息
            for(ActivityManager.RunningServiceInfo runningServiceInfo:runningServices){
                //获取所有正在运行的服务的组件的名字
                ComponentName service = runningServiceInfo.service;
                //通过ComponentName 获取到这个服务的类名
                String className1 = service.getClassName();
                if(className.equals(className1)){
                    //如果传入的类名 和当前遍历到这个类名一样 说明当前的服务正在运行
                    return true;
                }
            }
        }
        return false;
    }
}

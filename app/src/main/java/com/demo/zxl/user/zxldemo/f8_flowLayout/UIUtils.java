package com.demo.zxl.user.zxldemo.f8_flowLayout;

import android.content.Context;
import android.content.res.Resources;
import com.demo.zxl.user.zxldemo.global.MyApp;

/**
 * Created by user on 2017/12/19.
 */

public class UiUtils {
    public static Context getContext(){
        return MyApp.getContext();
    }

    /**
     * @return
     */
    public static Resources getResource(){
        return getContext().getResources();
    }

    /**
     * @param dip
     * @return
     */
    public static int dip2px(int dip){
        float density = getResource().getDisplayMetrics().density;
        return (int) (dip*density+0.5);
    }

    /**
     * @param px
     * @return
     */
    public static int px2dip(int px){
        float density = getResource().getDisplayMetrics().density;
        return (int)(px/density+0.5);
    }

}

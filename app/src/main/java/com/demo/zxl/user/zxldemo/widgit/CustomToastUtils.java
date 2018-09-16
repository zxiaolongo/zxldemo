package com.demo.zxl.user.zxldemo.widgit;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;


/**
 * Created by beibei on 2017/11/22.
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 */
//需要上边的权限
public class CustomToastUtils {
    //需要上边的权限
    private static View view;
    private static float startX;
    private static float startY;

    public static void showCustomToast(Context context, String address){
        hideCustomToast(context);
        //把自定义吐司 对应的xml布局文件 转换成View
        view = View.inflate(context, R.layout.layout_customtoast, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_address);
        //在自定义吐司的textview中显示归属地
        textView.setText(address);

        final WindowManager windowManger =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //布局参数  每一个ViewGroup中也有一个内部类LayoutParams 把一个View添加到一个ViewGroup或者是windowManger的时候
        //如果要使用LayoutParams 就看 要添加到的ViewGroup/WindowManager的类型
        //如果要加到线布局中 LinearLayout.LayoutParams
        //当前要把这个View添加到WindowManager中 所以 布局参数使用WindowManager.LayoutParams
        final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        //布局参数的宽度 指定View宽度
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        // 指定view的高度  相当于在布局文件中写了 android:layout_width
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口的背景是透明的
        mParams.format = PixelFormat.TRANSLUCENT;
        //设置当前View的类型  通过这个类型指定z轴优先级
//        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //z轴优先级数越大 优先级越高 这个View会盖在  mParams.type 数比较小的View的上面
        //TYPE_PRIORITY_PHONE 推荐使用这个type TYPE_TOAST 有的设备自定义toast不可以移动
        mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
//                    params.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
              //  | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        //添加到窗口管理器中
        windowManger.addView(view,mParams);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //getRawX getRawY 获取的坐标 0,0点是屏幕的左上角
                        //getX getY 获取的坐标 0,0 点 是当前view的左上角
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                       // mParams.y

                        float rawX = event.getRawX();
                        float rawY = event.getRawY();

                        mParams.x += (int) (rawX-startX);
                        mParams.y += (int) (rawY-startY);
                        //更新view的布局的位置
                        windowManger.updateViewLayout(view,mParams);
                        //更新起始的x坐标 和Y坐标
                         startX = rawX;
                        startY = rawY;
                        break;

                }

                return true;
            }
        });

    }

    public static void hideCustomToast(Context context){
        // view.getParent() 判断当前的View是否已经添加到了 WindowManager中
        if(view!=null && view.getParent()!=null){
            WindowManager windowManger =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            //移除自定义toast
            windowManger.removeViewImmediate(view);
            view =null;
        }
    }

    public static boolean isShow(){
        return view!=null && view.getParent()!=null ? true : false ;
    }
}

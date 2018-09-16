package com.demo.zxl.user.zxldemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.zxl.user.zxldemo.util.AddressDbUtils;
import com.demo.zxl.user.zxldemo.widgit.CustomToastUtils;

public class ListenCallService extends Service {

    private TelephonyManager telephonyManager;
    private MyPhoneStateListener phoneStateListener;
    private TextView tv_text;
    private MyReceiver receiver;


    public ListenCallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //1 向外拨打电话
        //TelephonyManager 电话状态管理器
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //windowManger = (WindowManager) getSystemService(WINDOW_SERVICE);
        //创建了一个电话状态的监听器
        phoneStateListener = new MyPhoneStateListener();
        //在电话状态管理中 调用listen 注册了创建出来的监听器
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

        //2 接收外部电话
        //创建接收外拨电话 广播的广播接收者对象
        receiver = new MyReceiver();
        //创建一个意图过滤器对象
        IntentFilter intentFilter = new IntentFilter();
        //指定过滤ACTION_NEW_OUTGOING_CALL 外拨电话的广播
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        //动态注册广播接收者
        registerReceiver(receiver,intentFilter);
    }

    private class MyPhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    CustomToastUtils.hideCustomToast(getApplicationContext());
//                    if(tv_text!=null && tv_text.getParent()!=null){
//                       // windowManger.removeViewImmediate(tv_text);
//                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if(!TextUtils.isEmpty(incomingNumber)){
                        String address = AddressDbUtils.getAddress(getApplicationContext(), incomingNumber);
                       Toast.makeText(ListenCallService.this, address, Toast.LENGTH_LONG).show();
                        CustomToastUtils.showCustomToast(getApplicationContext(),address);

                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //service销毁的时候 一定要注销电话状态的监听
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_NONE);
        //注销广播接收者
        unregisterReceiver(receiver);
    }

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取外拨的电话号码
            String outGoingnumber = getResultData();
            //利用电话号码查询地址
            String address = AddressDbUtils.getAddress(context, outGoingnumber);
            //显示自定义toast
            CustomToastUtils.showCustomToast(context,address);
        }
    }

}

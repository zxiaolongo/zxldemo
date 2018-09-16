package com.demo.zxl.user.zxldemo.f2_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.service.ListenCallService;
import com.demo.zxl.user.zxldemo.util.Constant;
import com.demo.zxl.user.zxldemo.util.SPUtils;
import com.demo.zxl.user.zxldemo.util.ServiceUtils;
import com.demo.zxl.user.zxldemo.widgit.LikeInputDialog;
import com.demo.zxl.user.zxldemo.widgit.SettingItemView;

/**
 * Created by user on 2018/6/17.
 */

public class SystemActivity extends BaseActivity {

    private boolean openService;
    private SettingItemView sivService;
    private SettingItemView sivDialog;
    private LikeInputDialog likeInputDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        sivService = findViewById(R.id.siv_service);
        sivDialog = findViewById(R.id.siv_dialog);

        //1 是否开启电话监听服务
        openService = SPUtils.getBoolean(getApplication(), Constant.ADDRESS_SERVICE, false);
        sivService.setToggle(openService);
        if (openService && !ServiceUtils.isServiceRunning(getApplicationContext(), ListenCallService.class.getName())) {
            //服务关闭  打开服务
            startService(new Intent(getApplicationContext(), ListenCallService.class));
        }
        sivService.setOnToggleClickListener(new SettingItemView.OnToggleClick() {
            @Override
            public void onToggle(boolean toggleState) {
                if (toggleState) {
                    //服务关闭  打开服务
                    startService(new Intent(getApplicationContext(), ListenCallService.class));
                } else {
                    stopService(new Intent(getApplicationContext(), ListenCallService.class));
                }
                SPUtils.putBoolean(
                        getApplication(), Constant.ADDRESS_SERVICE, toggleState);
            }


        });


        //2 开启模仿软键盘弹出的dialog
        sivDialog.setOnToggleClickListener(new SettingItemView.OnToggleClick()

        {
            @Override
            public void onToggle(boolean toggleState) {
                if (likeInputDialog == null) {
                    likeInputDialog = new LikeInputDialog(SystemActivity.this);
                }
                if (toggleState) {
                    likeInputDialog.show();
                } else {
                    likeInputDialog.dismiss();
                }

            }
        });

    }

    private void startListenCallService() {
        Intent intent = new Intent(getApplicationContext(), ListenCallService.class);
        startService(intent);
    }


    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}

package com.demo.zxl.user.zxldemo.f1_run.toast;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.widgit.CustomToastUtils;
import com.demo.zxl.user.zxldemo.widgit.SettingItemView;

/**
 * Created by user on 2018/6/17.
 */

public class RunActivity extends BaseActivity{


    private SettingItemView sivFirst;
    private SettingItemView sivMiddle;
    private EditText etNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        sivFirst = findViewById(R.id.siv_first);
        sivMiddle = findViewById(R.id.siv_middle);
        etNum = findViewById(R.id.et_number);

        // 1 移动Toast展示
        CustomToastUtils.showCustomToast(getApplicationContext(),"北京");

        //2 组合控件开关监听
        sivFirst.setOnToggleClickListener(new SettingItemView.OnToggleClick() {
            @Override
            public void onToggle(boolean toggleState) {
                if (toggleState){
                    Toast.makeText(getApplicationContext(),"打开上",Toast.LENGTH_SHORT).show();
                    CustomToastUtils.showCustomToast(getApplicationContext(),"北京");
                }else {
                    Toast.makeText(getApplicationContext(),"关闭上",Toast.LENGTH_SHORT).show();
                    CustomToastUtils.hideCustomToast(getApplicationContext());
                }

            }
        });
        sivMiddle.setOnToggleClickListener(new SettingItemView.OnToggleClick() {
            @Override
            public void onToggle(boolean toggleState) {
                if (toggleState){
                    Toast.makeText(getApplicationContext(),"打开中",Toast.LENGTH_SHORT).show();
                    CustomToastUtils.showCustomToast(getApplicationContext(),"北京");
                }else {
                    Toast.makeText(getApplicationContext(),"关闭中",Toast.LENGTH_SHORT).show();
                    CustomToastUtils.hideCustomToast(getApplicationContext());
                }
            }
        });


        //3 抖动动画
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etNum.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    etNum.startAnimation(animation);
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //开始查询
                }
            }
        });

    }



    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomToastUtils.hideCustomToast(getApplicationContext());
    }
}

package com.demo.zxl.user.zxldemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.zxl.user.zxldemo.f10_sra.RSAActivity;
import com.demo.zxl.user.zxldemo.f1_run.toast.RunActivity;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.f2_system.SystemActivity;
import com.demo.zxl.user.zxldemo.f3_video.VideoAvtivity;
import com.demo.zxl.user.zxldemo.f5_gridlayout.GridActivity;
import com.demo.zxl.user.zxldemo.f4_viewpager.ViewPagerActivity;
import com.demo.zxl.user.zxldemo.f6_viewdraghelper.QQUI_Activity;
import com.demo.zxl.user.zxldemo.f7_swipelayout.SwipeLayoutActivity;
import com.demo.zxl.user.zxldemo.f8_flowLayout.FlowLayoutActivity;
import com.demo.zxl.user.zxldemo.f9_randomlayout.RandomLayoutActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_run).setOnClickListener(this);
        findViewById(R.id.tv_system).setOnClickListener(this);
        findViewById(R.id.tv_video).setOnClickListener(this);
        findViewById(R.id.tv_viewpager).setOnClickListener(this);
        findViewById(R.id.tv_gride).setOnClickListener(this);
        findViewById(R.id.tv_qq_ui).setOnClickListener(this);
        findViewById(R.id.tv_qq_swipelayout).setOnClickListener(this);
        findViewById(R.id.tv_flowLayout).setOnClickListener(this);
        findViewById(R.id.tv_randomLayout).setOnClickListener(this);
        findViewById(R.id.tv_sra).setOnClickListener(this);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_run:
                Intent intent = new Intent(this, RunActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_system:
                Intent intent1 = new Intent(this, SystemActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_video:
                Intent intent2 = new Intent(this, VideoAvtivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_viewpager:
                Intent intent3 = new Intent(this, ViewPagerActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_gride:
                Intent intent4 = new Intent(this, GridActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_qq_ui:
                Intent intent6 = new Intent(this, QQUI_Activity.class);
                startActivity(intent6);
                break;
            case R.id.tv_qq_swipelayout:
                Intent intent7 = new Intent(this, SwipeLayoutActivity.class);
                startActivity(intent7);
                break;
            case R.id.tv_flowLayout:
                Intent intent8 = new Intent(this, FlowLayoutActivity.class);
                startActivity(intent8);
                break;
            case R.id.tv_randomLayout:
                Intent intent9 = new Intent(this, RandomLayoutActivity.class);
                startActivity(intent9);
                break;
            case R.id.tv_sra:
                Intent intent10 = new Intent(this, RSAActivity.class);
                startActivity(intent10);
                break;

        }
    }
}

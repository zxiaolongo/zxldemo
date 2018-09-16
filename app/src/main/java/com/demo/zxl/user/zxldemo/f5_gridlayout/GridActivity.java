package com.demo.zxl.user.zxldemo.f5_gridlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.widgit.MyGridLayout;

/**
 * Created by user on 2018/6/25.
 */

public class GridActivity extends BaseActivity {
    private String[] str = new String[]{"标题0","标题1","标题2","标题3","标题4","标题5","标题6","标题7","标题8"};
    private String[] str1 = new String[]{"体育0","新闻1","音乐2","咨询3","NBA4","CBA5","周琦6","小丁7","阿莲8"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridlayout);


        final MyGridLayout mglTop = (MyGridLayout) findViewById(R.id.mgl_top);
        final MyGridLayout mglBottom = (MyGridLayout) findViewById(R.id.mgl_bottom);

        //可以让内部的TexView长按和现有GridLayout可以拖拽
        mglTop.setLongClickAndDargEnable(true);
        //让内部的TexView无长按监听和现有GridLayout拖拽监听
        mglBottom.setLongClickAndDargEnable(false);

        mglTop.addItems(str);
        mglBottom.addItems(str1);

        mglTop.setOnCustomerClickListener(new MyGridLayout.OnCustomerClickListener() {
            @Override
            public void onCustomerClick(View view) {
                //顶部GridLayout中点击了TextView
                //顶部删除TextView,底部添加TextView
                Log.i("","此段代码在自定义控件中,已经帮助调用,顶部TextView被点击");
                mglTop.removeView(view);
                mglBottom.addItemView(((TextView)view).getText().toString());
            }
        });

        mglBottom.setOnCustomerClickListener(new MyGridLayout.OnCustomerClickListener() {
            @Override
            public void onCustomerClick(View view) {
                //底部删除TextView,顶部添加TextView
                Log.i("","此段代码在自定义控件中,已经帮助调用,底部TextView被点击");
                mglBottom.removeView(view);
                mglTop.addItemView(((TextView)view).getText().toString());
            }
        });

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}

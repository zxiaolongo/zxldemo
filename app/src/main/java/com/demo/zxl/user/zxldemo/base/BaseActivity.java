package com.demo.zxl.user.zxldemo.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by user on 2018/6/14.
 */

public abstract class BaseActivity<V,P extends BasePresenter<V>> extends Activity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter !=null){
            mPresenter.attach((V)this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detach();
        }

    }

    public abstract P createPresenter();
}

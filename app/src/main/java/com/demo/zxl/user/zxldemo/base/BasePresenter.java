package com.demo.zxl.user.zxldemo.base;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2018/6/14.
 */

public class BasePresenter<V> {
    private WeakReference<V> weakRefView;
    public void attach(V view){
        weakRefView = new WeakReference<V>(view);
    }

    public void detach() {
        if(isAttach()) {
            weakRefView.clear();
            weakRefView = null;
        }
    }
    public V obtainView(){
        return isAttach()?weakRefView.get():null;
    }

    protected boolean isAttach() {
        return weakRefView != null &&
                weakRefView.get() != null;
    }

}

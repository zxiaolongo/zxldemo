package com.demo.zxl.user.zxldemo.f12_dragger2;

import com.demo.zxl.user.zxldemo.MainActivity;

import dagger.Component;

/**
 * Created by user on 2019/5/6.
 */
//第一步 添加@Component
//第二步 添加module
@Component(modules = {MainModule.class})
public interface MainComponent {
    //第三步  写一个方法 绑定Activity /Fragment
    void inject(MainActivity activity);
}

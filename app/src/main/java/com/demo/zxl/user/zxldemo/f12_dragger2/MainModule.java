package com.demo.zxl.user.zxldemo.f12_dragger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2019/5/6.
 */
//第一步 添加@Module 注解
@Module
public class MainModule {
    //第二步 使用Provider 注解 实例化对象
    @Provides
    Student providerA() {
        return new Student();
    }
}

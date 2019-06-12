package com.demo.zxl.user.zxldemo.f11_annotion;

import android.util.SparseArray;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 2019/5/5.
 */

public class Person {

    @myTest
    public void eat() {
        System.out.println("吃");
    }

    @myTest
    public void sleep() {
        System.out.println("睡");
    }


    public void run() {
        System.out.println("跑");
    }


    //自己定义一个注解
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)  //在运行时 VM 将保留注释，因此可以反射性地读取。
    public @interface myTest{

    }
}

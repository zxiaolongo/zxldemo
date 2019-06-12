package com.demo.zxl.user.zxldemo.f11_annotion;

import java.lang.reflect.Method;

/**
 * Created by user on 2019/5/5.
 */

public class TestPerson {
    /**
     *  1.通过反射获取Person的Class对象
     *  2.获取person类的所有方法
     *  3.循环判断一下哪个方法上带@mytest注解
     *  4.方法上带@mytest注解的 让其执行
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //1.通过反射获取Person的Class对象
        Class clazz = Person.class;
        //2.获取person类的所有方法
        Method[] methods = clazz.getMethods(); //获取当前类和父类的方法 不包括私有的
        //3.遍历
        for (Method method : methods) {
            //4.判断方法上面是否带了注解  @myTest
            boolean flag =  method.isAnnotationPresent(Person.myTest.class);
            //5. 让方法执行
            if (flag) {
                //6.获取实例
                Object obj = clazz.newInstance();
                //7让方法执行
                method.invoke(obj);

            }

        }
    }
}

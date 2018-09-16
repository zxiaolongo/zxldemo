package com.demo.zxl.user.zxldemo.f4_viewpager;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;

/**
 * Created by HASEE.
 */
public class RotatePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        //切换页面过程中,调用方法  position值代表??
        Log.i("","========================position = "+position);

        //0-----变为-------负1     索引为0的界面     (0变成-1)*25
        // 索引为0的图片变化规则  0,-1,-2,-3,-4,................-25,角度越来越大


        //1-----变为-------0.      索引为1的界面     (1变成0)*25
        //索引为1的图片变化规则  25,24,23,22,21..................0,角度越来越小

//         第1个页面向第2个页面移动
//        从负1-------变为-------负2    索引为0的界面
//        从0-------变为-------负1	  索引为1的界面
//        从1-------变为-------0	     索引为2的界面

        //如果现在position的值小于-1,则此position是被索引0使用的,不需要考虑索引0指向页面的动画效果
        if(position<-1){
            //[-无穷大,-1)
            //索引为0界面的对象ImageView,设置旋转角度为0度
            page.setRotation(0);
        }else if(position>1){
            //[1,+无穷大]
            //索引为2的界面ImageView对象,设置旋转角度为0度
            page.setRotation(0);
        }else if (position>=0){
            //[0,1]  计算角度,然后让角度作用在page上
            //设置图片旋转的中心点在屏幕x轴的正中心,y轴至少大于等于屏幕的高度
            int x = page.getWidth()/2;
            int y = page.getHeight();

            page.setPivotX(x);
            page.setPivotY(y);
            page.setRotation(position*25);
        }else{
            //(0,-1]

            int x = page.getWidth()/2;
            int y = page.getHeight();

            page.setPivotX(x);
            page.setPivotY(y);
            page.setRotation(position*25);
        }
    }
}

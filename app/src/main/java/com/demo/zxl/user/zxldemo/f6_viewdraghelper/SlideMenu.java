package com.demo.zxl.user.zxldemo.f6_viewdraghelper;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by fullcircle on 2018/2/28.
 *继承与View和继承与现有控件都是下面的顺序，但是控件的大小是生成之后就固定的，不会再次改变。
 * 自定义View方法调用顺序
 * onMeasure()
 * →onSizeChanged()
 * →onLayout()
 * →onMeasure()
 * →onLayout()
 * →onDraw()
 */

public class SlideMenu extends FrameLayout {


    private ViewDragHelper dragHelper;
    private View main;
    private View menu;
    private int maxLeft;
    private FloatEvaluator floatEvaluator;
    private ArgbEvaluator argbEvaluator;

    public SlideMenu(Context context) {
        this(context,null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.i("*****","SlideMenu");
    }

    /**
     * 当xml文件解析完成之后就会调用这个方法 这个方法执行的时候 所有的子控件都已经加到了当前的ViewGroup中
     * 需要注意 super.onFinishInflate(); 不能去掉
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        main = getChildAt(1); //View
        menu = getChildAt(0); //View
        Log.i("*****","onFinishInflate");
    }
    // 当尺寸改变调用,onMeasure之后调用onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        maxLeft = (int) (w*0.6);
        Log.i("*****","onSizeChanged"+w);
    }

    private void init() {
//        main = getChildAt(1);
//        menu = getChildAt(0);
        argbEvaluator = new ArgbEvaluator();
        floatEvaluator = new FloatEvaluator();
        ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }
        });
        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            /**
             * 当前的ViewGroup的子View被选中时 通过tryCaptureView的返回值决定是否要通过
             * dragHelper 来处理子View的拖动效果 如果返回true 就是由这个ViewDragHelper处理滑动事件
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

//                if(child == main){
//                    return true;
//                }
                if (child == main){
                    Log.i("*****","tryCaptureView"+"--"+"main");
                }else {
                    Log.i("*****","tryCaptureView"+"--"+"menu");
                }

                return child == main || child == menu;
            }

            /**
             * 处理子控件水平方向滑动的效果
             * @param child  触摸到的子控件
             * @param left   子控件的最左边的位置  计算方法   child.getLeft()+dx
             * @param dx  x方向上当前移动的偏移量
             * @return 返回的 left值 就是希望child的左边移动到什么位置上
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                if(child == main){
                    if(left<0){
                        left = 0;
                    }else if(left>maxLeft){
                        left = maxLeft;
                    }
                }
                return left;
            }

            /**
             * 如果返回一个>0的数 那么就会拦截子View的滑动 也可以理解成强制滑动
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                if (child == main){
                    Log.i("*****","getViewHorizontalDragRange"+"--"+"main");
                }else {
                    Log.i("*****","getViewHorizontalDragRange"+"--"+"menu");
                }
                return 1;
            }


            /**
             * 当某一个子View的位置发生变化的时候 就会执行这个方法
             * @param changedView 发生位置变化的View对象
             * @param left        这个View对象的左边的坐标
             * @param top          这个View对象的上边的坐标
             * @param dx          这个View对象在x方向的偏移量
             * @param dy          这个View对象在y方向的偏移量
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
              //  System.out.println("left==="+left+"dx===="+dx);
                if(changedView == menu){
                    menu.layout(0,0,menu.getMeasuredWidth(),menu.getBottom());
                    int newLeft = main.getLeft()+dx;
                    if(newLeft < 0){
                        newLeft = 0;
                    }else if(newLeft>maxLeft){
                        newLeft = maxLeft;
                    }
                    main.layout(newLeft,0,newLeft+main.getMeasuredWidth(),main.getBottom());
                }

                //处理动画的效果
                float fraction = main.getLeft()*1f / maxLeft;
                System.out.println(fraction);
                executeAnim(fraction);
                if(listener!=null){
                    listener.onSlide(fraction);
                    if(main.getLeft() == 0){
                        listener.onMenuClosed();
                        Log.i("*****","onViewPositionChanged"+"--"+"close");
                    }else if(main.getLeft() == maxLeft){
                        listener.onMenuOpened();
                        Log.i("*****","onViewPositionChanged"+"--"+"open");
                    }
                }


            }

            /**
             * 当被拖动的View 释放的时候 会执行这个方法
             * @param releasedChild 被松开的子View
             * @param xvel  松手时 当前的releasedChild在x方向上的速度 像素/秒
             * @param yvel  松手时 当前的releasedChild在Y方向上的速度 像素/秒
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                System.out.println("xvel"+xvel);
                int left = main.getLeft();//获取到最左边的值
                if(left>maxLeft/2){ //如果松手的时候>菜单打开的最大值的一半
                    openMenu();//自动打开菜单
                    Log.i("*****","onViewReleased"+"--"+"open");
                }else{
                    closeMenu();//自动关闭菜单
                    Log.i("*****","onViewReleased"+"--"+"close");
                }
            }
        });
    }


    private void executeAnim(float fraction) {

        Float scale = floatEvaluator.evaluate(fraction, 1, 0.8f);
        System.out.println(scale);
        main.setScaleX(scale);
        main.setScaleY(scale);

        Float menuScale = floatEvaluator.evaluate(fraction, 0.3f, 1);
        menu.setScaleY(menuScale);
        menu.setScaleX(menuScale);

        Float transX = floatEvaluator.evaluate(fraction, -menu.getMeasuredWidth() / 2, 0);
        menu.setTranslationX(transX);

//        main.setRotationY(floatEvaluator.evaluate(fraction, 0, 90));  3D效果
//        menu.setRotationY(floatEvaluator.evaluate(fraction, -90, 0));
        Drawable background = getBackground();
        if(background!=null){
            int evaluate = (int) argbEvaluator.evaluate(fraction, Color.BLACK, Color.TRANSPARENT);
            background.setColorFilter(evaluate, PorterDuff.Mode.SRC_OVER);
            //https://blog.csdn.net/anhenzhufeng/article/details/45972155
            //PorterDuff.Mode.SRC_OVER 各种模式和显示效果
        }
    }
    // Scroller scroller = new Scroller(getContext());

    private void closeMenu() {
//        scroller.startScroll();//开始计算一些列的 可以交给scrollTo去产生一个滚动动画的坐标

//        invalidate();
        //参数1 执行平滑滚动动画的View对象  参数2 动画结束时View最左边的位置
        // 参数3 动画结束时View顶部的位置
        // 指定动画结束时view要在什么位置上
        dragHelper.smoothSlideViewTo(main,0,0);//通过这个方法可以让View实现一个平滑滚动的动画
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void openMenu() {
        dragHelper.smoothSlideViewTo(main,maxLeft,0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * smoothSlideViewTo这个方法 实际上就是封装了 Scroller 需要重写computeScroll
     * 来完成整个动画
     */
    @Override
    public void computeScroll() {
        //scroller.computeScrollOffset() 判断 坐标是否计算完了
//       if(scroller.computeScrollOffset()){
//           int currX = scroller.getCurrX();//获取到计算出来的坐标
//           scrollTo(currX,0);//滚动当前的页面
//           invalidate();//重绘
//       }
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        View menu = getChildAt(0);
//        View main = getChildAt(1);
//
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);//获取到触摸的坐标
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = dragHelper.shouldInterceptTouchEvent(ev);//通过dragHelper 处理事件的拦截
        return result;
    }

    interface SlideListener{
        void onSlide(float fraction);
        void onMenuOpened();
        void onMenuClosed();
    }

    private SlideListener listener;

    public void setListener(SlideListener listener) {
        this.listener = listener;
    }
}

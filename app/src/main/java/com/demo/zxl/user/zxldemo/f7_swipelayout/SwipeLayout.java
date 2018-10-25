package com.demo.zxl.user.zxldemo.f7_swipelayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by fullcircle on 2018/3/1.
 */

public class SwipeLayout extends FrameLayout {

    private View contentView;
    private View deleteView;
    private ViewDragHelper dragHelper;
    private float downY;
    private float downX;
    private long startTime;
    private long downTime;

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, new MyCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        contentView.layout(0,0,contentView.getMeasuredWidth(),contentView.getMeasuredHeight());
        deleteView.layout(contentView.getRight(),0,contentView.getRight()+deleteView.getMeasuredWidth(),deleteView.getMeasuredHeight());
    }

    private class MyCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child == contentView){
                if(left>0){
                    left = 0;
                }else if(left<-deleteView.getMeasuredWidth()){
                    left = -deleteView.getMeasuredWidth();
                }
            } else if(child == deleteView){
                if(left>contentView.getMeasuredWidth()){
                    left = contentView.getMeasuredHeight();
                }else if(left <(contentView.getMeasuredWidth()-deleteView.getMeasuredWidth())){
                    left = contentView.getMeasuredWidth()-deleteView.getMeasuredWidth();
                }
            }
            return left;
        }
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if(changedView == contentView){
                //deleteView.layout(contentView.getRight(),0,contentView.getRight()+deleteView.getMeasuredWidth(),deleteView.getMeasuredHeight());
                ViewCompat.offsetLeftAndRight(deleteView,dx);
            }else if(changedView == deleteView){
               // contentView.layout(deleteView.getLeft()-contentView.getMeasuredWidth(),0,deleteView.getLeft(),contentView.getMeasuredHeight());
                ViewCompat.offsetLeftAndRight(contentView,dx);
            }
            if(listener!=null){
                if(contentView.getLeft()==0){
                    listener.onClose(SwipeLayout.this);
                }else if(contentView.getLeft() == -deleteView.getMeasuredWidth()){
                    listener.onOpen(SwipeLayout.this);
                }
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = contentView.getLeft();
            if(left<-deleteView.getMeasuredWidth()/2){
                openLayout();
            }else if(left>=-deleteView.getMeasuredWidth()/2){
                closeLayout();
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }
    }

    public void closeLayout() {
        dragHelper.smoothSlideViewTo(contentView,0,0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void openLayout() {
        dragHelper.smoothSlideViewTo(contentView,-deleteView.getMeasuredWidth(),0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                downX = event.getX();
                //记住按下的时间
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(downX - event.getX());
                float dy = Math.abs(downY - event.getY());
                if(dx>dy){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                //计算按下的时间 和 抬起的时间 的时间差 如果这段时间足够短就认为是一个点击事件
                long time = System.currentTimeMillis() - downTime;
                //计算移动的距离
                double tempX = Math.pow(downX - event.getX(), 2);
                double tempY = Math.pow(downY - event.getY(), 2);
                double distance = Math.sqrt(tempX + tempY);

                ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
                if(time<ViewConfiguration.getLongPressTimeout()&& distance<viewConfiguration.getScaledTouchSlop()){
                   performClick();
                }
                break;
        }
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = dragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    public interface SwipeListener{
        void onOpen(SwipeLayout swipeLayout);
        void onClose(SwipeLayout swipeLayout);
    }

    private SwipeListener listener;

    public void setListener(SwipeListener listenr) {
        this.listener = listenr;
    }
}

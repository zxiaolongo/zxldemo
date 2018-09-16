package com.demo.zxl.user.zxldemo.widgit;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;

import java.util.ArrayList;

/**
 * Created by HASEE.
 */

public class MyGridLayout extends GridLayout {
    private Context ctx;
    private View currentView;
    private ArrayList<Rect> rectList;
    private OnCustomerClickListener listener;
    private boolean isEnable;

    public MyGridLayout(Context context) {
        this(context,null);
    }

    public MyGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        //view条目切换时候的动画效果
        setLayoutTransition(new LayoutTransition());
    }

    public void addItems(String[] str) {
        //将所有的TextView添加在GridLayout内部
        for (int i = 0; i < str.length; i++) {
            addItemView(str[i]);
        }
        //监听GridLayout内部控件的拖拽
        if (isEnable){
            //isEnable为true监听拖拽行为
            this.setOnDragListener(onDragListener);
        }else{
            //isEnable为false不监听拖拽行为
            this.setOnDragListener(null);
        }
    }

    /**
     * @param event 事件
     * @return  本次手指移动让手指落在那个索引位置的矩形区域内
     */
    private int getIndex(DragEvent event) {
        int locationX = (int) event.getX();
        int locationY = (int) event.getY();
        int index = -1;
        //判断locationX和locationY,落在那个矩形区域内
        for (int i = 0; i < rectList.size(); i++) {
            //获取每一个矩形对象
            Rect rect = rectList.get(i);
            if(rect.contains(locationX,locationY)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void getAllRect() {
        //矩形个数就等于textView个数,textView如何获取
        int childCount = getChildCount();
        rectList = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View viewChild = getChildAt(i);
            //创建矩形对象,矩形对象的位置就是TextView所在的位置
            Rect rect = new Rect(viewChild.getLeft(), viewChild.getTop(),
                    viewChild.getRight(), viewChild.getBottom());
            //将矩形对象添加在矩形集合内部
            rectList.add(rect);
        }
    }

    public void addItemView(String content) {
        //需要创建TextView并且将其添加在MyGridLayout内部
        TextView textView = new TextView(ctx);
        textView.setText(content);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.selector_textview_bg);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(10,10,10,10);

        //找到TextView父容器,然后指定LayoutParams
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.setMargins(5,5,5,5);
        addView(textView,layoutParams);

        if (isEnable){
            textView.setOnLongClickListener(onLongClickListener);
        }else{
            textView.setOnLongClickListener(null);
        }
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //你知道这个TextView属于上面的GridLayout还是下面的GridLayout
                //点击上方的GridLayout的时候,需要从上方TextView中移除一个,添加在下面的GridLayout中
                //点击下方的GridLayout的时候,需要从下方TextView中移除一个,添加在上面的GridLayout中

                //因为不知道此TextView属于GridLayoutTop还是GridLayoutBottom,所以无法处理后续的删除view和添加view的逻辑
                //所以在此处将此方法编写成一个未实现方法(接口,抽象类)
                if (listener!=null){
                    listener.onCustomerClick(v);
                }
            }
        });
        //在拖拽过程中手指落在那个矩形区域内,则此矩形位置就需要向后移动
    }

    public void setLongClickAndDargEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    //回调(一,定义接口  二,接口中根据未知业务逻辑的多少决定方法个数  三,在合适地方调用)
    public interface OnCustomerClickListener{
        public void onCustomerClick(View view);
    }
    public void setOnCustomerClickListener(OnCustomerClickListener listener){
        this.listener = listener;
    }

    private OnLongClickListener onLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            //提供长按事件触发后的倒映
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(null,new DragShadowBuilder(v),null,0);
            }else{
                v.startDrag(null,new DragShadowBuilder(v),null,0);
            }
            //让选中控件不可用,不可用状态下会显示红色边控
            v.setEnabled(false);
            //记录现在选中textView对象,即v
            currentView = v;
            return true;
        }
    };
    private OnDragListener onDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED://开始拖拽
                    Log.i("","ACTION_DRAG_STARTED  开始拖拽");
                    //获取屏幕中所有的矩形
                    getAllRect();
                    break;
                case DragEvent.ACTION_DRAG_ENTERED://进入拖拽
                    Log.i("","ACTION_DRAG_ENTERED  进入拖拽");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION://拖拽到了某个位置
                    Log.i("","ACTION_DRAG_LOCATION  拖拽到了某个位置");
                    //获取当前手指在拖拽过程中,落在那个矩形区域内,将落在矩形的索引位置获取出来
                    int index = getIndex(event);
                    //index则为找到索引位置,索引位置不能为-1
                    //如果现在触发了拖拽行为,则currentView一定不是空的
                    //拖拽的view不能回到初始位置
                    if (index!=-1 && currentView!=null && currentView != getChildAt(index)){
                        //将拖拽过程中的view从GridLayout中移除
                        MyGridLayout.this.removeView(currentView);
                        //再将之前移除的view添加在已知晓索引位置index
                        MyGridLayout.this.addView(currentView,index);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("","ACTION_DROP");
                    break;
                    /*case DragEvent.ACTION_DRAG_EXITED://退出拖拽
                        Log.i("","ACTION_DRAG_EXITED  退出拖拽");
                        break;*/
                case DragEvent.ACTION_DRAG_ENDED://结束拖拽
                    Log.i("","ACTION_DRAG_ENDED  结束拖拽");
                    if (currentView!=null){
                        currentView.setEnabled(true);
                    }
                    break;

            }
            return true;
        }
    };
}

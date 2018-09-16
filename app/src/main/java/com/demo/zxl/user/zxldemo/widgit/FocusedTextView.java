package com.demo.zxl.user.zxldemo.widgit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by beibei on 2017/11/20.
 */

public class FocusedTextView extends android.support.v7.widget.AppCompatTextView {
    public FocusedTextView(Context context) {
        //一个参数的构造 在代码中动态的创建一个View对象
        super(context);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        //多了一个参数AttributeSet 属性集合  系统在解析xml布局文件的时候
        super(context, attrs);
    }

    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        //比两个参数的构造多了一个  defStyleAttr 定义样式  如果xml文件中有 style 在解析的时候
        //就会从第三个参数传递进来
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        //通过这个方法的返回值来告诉系统当前这个textView是否有焦点
        //返回true就是有焦点
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        //第一个参数 写死true 这样当焦点变化的时候 不会影响到跑马灯
        super.onFocusChanged(true, direction, previouslyFocusedRect);
    }
}

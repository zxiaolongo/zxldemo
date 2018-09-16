package com.demo.zxl.user.zxldemo.widgit;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;

/**
 * Created by user on 2018/6/17.
 */

public class SettingItemView extends RelativeLayout {
    private TextView tv_title;
    private ImageView iv_icon;
    private boolean isToggleOn = true;
    private boolean isShowToggle;
    private int type;

    public SettingItemView(Context context) {
        this(context,null);//通过this调用当前的两个参数的构造
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);//通过this调用当前的三个参数的构造
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

//    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //第四个参数 默认样式 如果第三个参数defStyleAttr 没传入一个有效的样式 会尝试加载这个默认样式 如果默认样式也无效 没有样式
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private void init(AttributeSet attrs) {
        //加载xml布局文件
        //  View view = View.inflate(getContext(), R.layout.layout_settingitemview, null);
        //把xml文件转换出来的View对象放到了RelativeLayout中
        //   addView(view);

        //最后一个参数 如果传入一个非空的ViewGroup 那么 inflate 转换出来的View对象
        //就会作为最后这个参数对应的ViewGroup的子View

        View view = View.inflate(getContext(), R.layout.layout_settingitemview, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
                if (mOnToggleClick != null){
                    mOnToggleClick.onToggle(getToggleState());
                }
            }
        });
        //从attrs中获取到所有的自定义属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        // 在typedArray 分别获取每一个自定义属性的值

        //先获取textview 传入的文字内容
        String text = typedArray.getString(R.styleable.SettingItemView_siv_text);
        //设置到textView中
        tv_title.setText(text);

        //再获取是否要显示 开关按钮的属性值
        isShowToggle = typedArray.getBoolean(R.styleable.SettingItemView_siv_isShowToggle, true);
        //通过代码控制开关按钮的显示和隐藏  setVisibility
        // 可以传入三个值 View.VISIBLE 可见 View.INVISIBLE 不可见 View.GONE消失
        iv_icon.setVisibility(isShowToggle ?View.VISIBLE:View.GONE);

        //从typedArray中获取 开关按钮的开启还是关闭
        isToggleOn = typedArray.getBoolean(R.styleable.SettingItemView_siv_isToggleOn, true);
        iv_icon.setImageResource(isToggleOn ? R.drawable.on :R.drawable.off);

        type = typedArray.getInt(R.styleable.SettingItemView_backgroundType,0);
        switch (type){
            case 0:
                setBackgroundResource(R.drawable.selector_fristbg);
                break;
            case 1:
                setBackgroundResource(R.drawable.selector_middlebg);
                break;
            case 2:
                setBackgroundResource(R.drawable.selector_lastbg);
                break;
        }
        typedArray.recycle();

    }

    public void setTitleText(String text){
        tv_title.setText(text);
    }


    /**
     *切换开关的方法
     */
    public void toggle(){
        isToggleOn = !isToggleOn;
        iv_icon.setImageResource(isToggleOn? R.drawable.on:R.drawable.off);
    }

    /**
     * 传入开关的状态 并且更新开关的图标
     * @param toggleState
     */
    public void setToggle(boolean toggleState){
        this.isToggleOn = toggleState;
        iv_icon.setImageResource(isToggleOn? R.drawable.on:R.drawable.off);
    }

    /**
     * 获取当前开关的状态
     * @return
     */
    public boolean getToggleState(){
        return isToggleOn;
    }

    private OnToggleClick mOnToggleClick;
    public interface OnToggleClick{
        void onToggle(boolean toggleState);
    }
    public void setOnToggleClickListener(OnToggleClick onToggleClick){
        this.mOnToggleClick = onToggleClick;
    }
}

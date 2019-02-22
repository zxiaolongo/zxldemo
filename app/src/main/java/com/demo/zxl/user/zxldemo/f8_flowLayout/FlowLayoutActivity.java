package com.demo.zxl.user.zxldemo.f8_flowLayout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 2018/12/31.
 */

public class FlowLayoutActivity extends Activity {

    private ScrollView svFlow;
    private FlowLayout flowLayout;
    String[] list = new String[]{
        "课程分类","文史哲类","哲学","历史学","新闻传播","社会学",
         "文史哲类","哲学","涵盖文学","法学等","社会科学类","",
         "涵盖法学","管理学等学科类别","化学","医学","涵盖数学","建筑及土木",
         "政治学","及","心理学","电子信息","外语类","涵盖英语",
         "教育学","军事","环境工程","物理学","法语等学科类别",
         "理学类","海洋","生物学","涵盖经济学","经济管理类","涵盖美术",
         "工学类","涵盖机械","日语、德语","化工、材料","学","艺术类",
         "音乐","培养德智体全面发展人才，为进一步学习提供方法论的不可缺少的"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        //整体布局结构如下
        /*<ScrollView>
                <FlowLayout>
                    TextView
                    TextView
                     ....
                </FlowLayout>
        </ScrollView>   */
        svFlow = findViewById(R.id.sv_flow);
        flowLayout = findViewById(R.id.flow);

        for (int i = 0; i < list.length; i++) {
            TextView textView = new TextView(UiUtils.getContext());
            textView.setText(list[i]);
            textView.setTextColor(Color.WHITE);
            int paddingInner = UiUtils.dip2px(10);
            textView.setPadding(paddingInner,paddingInner,paddingInner,paddingInner);
            textView.setGravity(Gravity.CENTER);

            // 红red 00   绿green 55  蓝blue  77
            //0-254        0-254        0-254
            int red = 30+new Random().nextInt(190);//[30,219]
            int green = 30+new Random().nextInt(190);//[30,219]
            int blue = 30+new Random().nextInt(190);//[30,219]
            //将3个颜色混合成一个颜色最终作为图片的背景色
            int rgb = Color.rgb(red, green, blue);

            Drawable drawableNormal = MyDrawableUtil.getDrawable(rgb, UiUtils.dip2px(6));
            Drawable drawablePress = MyDrawableUtil.getDrawable(Color.LTGRAY, UiUtils.dip2px(6));
            //创建一个选择器对象,给textView作为背景使用
            StateListDrawable stateListDrawable = MyDrawableUtil.getStateListDrawable(
                    drawableNormal, drawablePress);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(stateListDrawable);
            }else{
                textView.setBackgroundDrawable(stateListDrawable);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayout.addView(textView);
        }

    }
}

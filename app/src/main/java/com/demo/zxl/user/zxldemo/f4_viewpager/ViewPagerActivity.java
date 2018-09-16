package com.demo.zxl.user.zxldemo.f4_viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.widgit.SettingItemView;

/**
 * Created by user on 2018/6/20.
 */

public class ViewPagerActivity extends BaseActivity implements View.OnClickListener{

    private int[] drawableIds;
    private ViewPager vp;
    private ImageView ivDotRed;
    private int width;
    private LinearLayout llDotContainer;
    private DrawerLayout drawerLayout;
    private DepthPageTransformer depthPageTransformer;
    private RotatePageTransformer rotatePageTransformer;
    private ZoomOutPageTransformer zoomOutPageTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        drawerLayout = findViewById(R.id.drawlayout);
        drawableIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        vp = findViewById(R.id.vp);
        ivDotRed = findViewById(R.id.iv_dot_red);
        llDotContainer = findViewById(R.id.ll_dot_container);

        findViewById(R.id.tv_rotatepager).setOnClickListener(this);
        findViewById(R.id.tv_depthpager).setOnClickListener(this);
        findViewById(R.id.tv_zoomoutpager).setOnClickListener(this);


        //1 初始化viewPager  添加指示点
        initViewPager();
        //添加点至界面中
        initDot();

//        int width = ivDotRed.getWidth();
//        Log.i("","======================================width = "+width);
//        onMeasure(测量)   onLayout(指定位置,如果现在控件已经开始指定位置了,则宽高是知晓的)    onDraw(画出来)

        //对系统的onLayout方法的调用进行一个监听
        ivDotRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //当onLayout被系统调用了以后会触发的方法
                width = ivDotRed.getWidth();
//                Log.i("","======================================width = "+ width);
            }
        });
    }
    private void initDot() {
        llDotContainer.removeAllViews();
        //点添加,点的个数就是图片的张数,数组的长度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < drawableIds.length; i++) {
            ImageView imageView = new ImageView(this);
            //前景  背景 == 控件宽高
            imageView.setBackgroundResource(R.drawable.shape_gray_dot);
            params.setMargins(0, 0, 10, 0);
//            imageView.setLayoutParams(params);
            llDotContainer.addView(imageView, params);
        }
    }

    private void initViewPager() {
        //viewpager提供数据适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        vp.setAdapter(myPagerAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滚动过程中调用方法
                //position  现在在第几个界面滚动
                //positionOffset  滚动过程中移动界面百分比
                //positionOffsetPixels  滚动了多少个像素
//                Log.i("","position = "+position);
//                Log.i("","positionOffset = "+positionOffset);
//                Log.i("","positionOffsetPixels = "+positionOffsetPixels);
                //viewpager界面移动的百分比和红色点移动的百分比一致,红色的点翻转一个界面的时候需要移动的总距离
//                红点宽度+间距(目前是10)
                int moveX = (int) ((width + 10) * (positionOffset + position));
                //让红点安装moveX进行移动,其实就是操控红点和左侧边缘间距即可
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = moveX;
                ivDotRed.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滚动状态发生改变调用方法
            }
        });
        //给viewpager设置动画效果
//        vp.setPageTransformer(false,new ZoomOutPageTransformer());
        //要反转绘制顺序 true     页面添加顺序安装0,1,2添加    绘制顺序和添加页面的顺序相反2,1,0
        //不要反转绘制顺序 false  页面添加顺序安装0,1,2添加    绘制顺序和添加页面的顺序一致的
//        vp.setPageTransformer(true,new DepthPageTransformer());


        //2 viewPager添加转换动画
        vp.setPageTransformer(true, new RotatePageTransformer());
    }





    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_rotatepager:
                drawerLayout.closeDrawers();
                break;
            case R.id.tv_depthpager:
                drawerLayout.closeDrawers();
                break;
            case R.id.tv_zoomoutpager:
                drawerLayout.closeDrawers();
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        //给viewpager添加界面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //向viewpager添加一个ImageView
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(drawableIds[position]);
            container.addView(imageView);
            //添加什么对象,此方法中返回什么对象
            return imageView;
        }

        //从viewpager中移除界面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return drawableIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}

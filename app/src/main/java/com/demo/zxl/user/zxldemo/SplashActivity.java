package com.demo.zxl.user.zxldemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;
import com.demo.zxl.user.zxldemo.service.ListenCallService;
import com.demo.zxl.user.zxldemo.util.Constant;
import com.demo.zxl.user.zxldemo.util.SPUtils;
import com.demo.zxl.user.zxldemo.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by user on 2018/6/17.
 */

public class SplashActivity extends BaseActivity {
    private ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1 执行动画
        ivSplash = findViewById(R.id.iv_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.2f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivSplash.clearAnimation();
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSplash.startAnimation(alphaAnimation);


//        //属性动画  参数1 要使用动画的View 参数2 属性动画要修改的属性  参数3.... 可变参数 动画执行的时候 具体的值
//        ObjectAnimator animator = ObjectAnimator.ofFloat(ivSplash,"RotationY",0,360);
//        animator.setDuration(2000);//设置动画执行的时长
////        animator.setRepeatCount(ObjectAnimator.INFINITE);//设置动画重复的次数 INFINITE无限次
//        animator.setRepeatCount(1);
//        //animator.setRepeatMode(ObjectAnimator.REVERSE);//设置动画车重复的模式  REVERSE 反着执行
//        animator.start();//开始动画
//        animator.addListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                animation.cancel();
//                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });



        //2 获取本地保存的是否显示归属地的配置信息
        if(SPUtils.getBoolean(this, Constant.ADDRESS_SERVICE,true)){
            //如果现实就打开显示归属地服务
            startListenCallService();
        }


        //3 将数据库加载到本地
        loadAddressDb();

    }
    private void startListenCallService() {
        Intent intent = new Intent(getApplicationContext(), ListenCallService.class);
        startService(intent);
    }

    /**
     * 加载数据库文件
     */
    private void loadAddressDb() {
        // "data/data/包名/files"
        File file = new File(getFilesDir(),"address.db");
        //判断数据库文件是否已经放到了"data/data/包名/files" 目录下 如果已经存在了直接返回
        if(file != null && file.length()>0){
            return;
        }
        //openFileOutput()
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            fos = new FileOutputStream(file);
            //getAssets 获取到AssetsManager 资产目录管理器
            inputStream = getAssets().open("address.db");
            int len = -1;
            byte[] buffer = new byte[1024];
            while((len = inputStream.read(buffer))!=-1){
                fos.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if(fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if(inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            StreamUtils.closeStream(fos,inputStream);
        }
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}

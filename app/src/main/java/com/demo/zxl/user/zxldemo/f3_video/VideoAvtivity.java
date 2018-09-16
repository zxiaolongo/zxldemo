package com.demo.zxl.user.zxldemo.f3_video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.base.BaseActivity;
import com.demo.zxl.user.zxldemo.base.BasePresenter;

/**
 * Created by user on 2018/6/20.
 */

public class VideoAvtivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.vv_video);

        //1 全屏--根布局为RelaytiveLayout(LinearLayout不能实现全屏)
        initVideoView();
        //播放视频
        initVideo();





    }

    private void initVideoView() {
      //1.如何获取屏幕宽高
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        //2.找到videoview父容器,父容器提供宽高规则让videoview使用
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                widthPixels,
                heightPixels);
        videoView.setLayoutParams(params);
    }

    private void initVideo() {
        //1.视频文件在存在工程中存储,指定加载文件路径
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kr36);
        videoView.setVideoURI(uri);
        //2.让videoview播放视频
        videoView.start();
        //3.视频一旦播放结束让页面跳转到后一个界面
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                //在视频播放结束后调用方法
//                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                startActivity(intent);
                //在视频播放结束后,重新播放一次
                videoView.start();
            }
        });
    }


    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}

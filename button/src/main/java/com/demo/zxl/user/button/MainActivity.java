package com.demo.zxl.user.button;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    ProgressBar progressBar = null;
    Button downLoadBtn = null;
    MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        downLoadBtn = (Button) findViewById(R.id.downLoadBtn);
        downLoadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i = 0;
                handler.sendEmptyMessage(new Message().what = 1);
                downLoadBtn.setEnabled(false);
            }
        });
    }

    public void freshButton() {
        i += 5;
        progressBar.setProgress(i);
        if (i != 100) {
            handler.sendEmptyMessageDelayed(new Message().what = 1, 500);
            downLoadBtn.setText(i + "%");
        } else if (i == 100) {
            downLoadBtn.setText("下载完成");
            handler.removeCallbacksAndMessages(null);
            downLoadBtn.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    static class MyHandler extends Handler {
        WeakReference<MainActivity> mWeakReference;

        public MyHandler(MainActivity activity) {
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity = mWeakReference.get();
            if (activity != null) {

                if (msg.what == 1) {
                    activity.freshButton();
                }
            }
        }
    }

}

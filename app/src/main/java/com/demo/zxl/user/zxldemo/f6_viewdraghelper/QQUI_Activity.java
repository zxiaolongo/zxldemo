package com.demo.zxl.user.zxldemo.f6_viewdraghelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.zxl.user.zxldemo.R;

public class QQUI_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqui);
        ListView mainList = (ListView) findViewById(R.id.main_listview);
        ListView menuList = (ListView) findViewById(R.id.menu_listview);

        mainList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.NAMES));
        menuList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.sCheeseStrings){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTextColor(Color.WHITE);
                return view;
            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.iv_head);
        SlideMenu slideMenu = (SlideMenu) findViewById(R.id.activity_main);
        slideMenu.setListener(new SlideMenu.SlideListener() {
            @Override
            public void onSlide(float fraction) {
                imageView.setRotation(fraction*360);
            }

            @Override
            public void onMenuOpened() {
                Toast.makeText(QQUI_Activity.this, "打开", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuClosed() {
                Toast.makeText(QQUI_Activity.this, "关闭", Toast.LENGTH_SHORT).show();

            }
        });

    }
}

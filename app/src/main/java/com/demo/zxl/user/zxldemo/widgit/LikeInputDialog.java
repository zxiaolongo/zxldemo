package com.demo.zxl.user.zxldemo.widgit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;
import com.demo.zxl.user.zxldemo.util.Constant;
import com.demo.zxl.user.zxldemo.util.SPUtils;


/**
 * Created by fullcircle on 2017/11/23.
 */

public class LikeInputDialog extends Dialog {
    private String[] titls = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
    private static final int[] mBgs = new int[] {
            R.drawable.bg_addr_normal_shape, R.drawable.bg_addr_orange_shape,
            R.drawable.bg_addr_blue_shape, R.drawable.bg_addr_gray_shape,
            R.drawable.bg_addr_green_shape };
    public LikeInputDialog(Context context) {
        //如果想修改对话框样式 可以自己定义样式通过两个参数的构造传递样式
        super(context,R.style.MyDialogStyle);
    }

    @Override
    public void show() {
        super.show();
       // WindowManager windowManager = getWindow().getWindowManager();
        //获取布局参数
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        //修改gravity 让窗口靠着父容器的底部
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        //把修改好的布局参数 设置到窗口中
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框的布局 用法跟activity没有区别
        setContentView(R.layout.layout_addrstyledialog);
        ListView lv_style = (ListView) findViewById(R.id.lv_style);
        final MyAdapter adapter = new MyAdapter();
        lv_style.setAdapter(adapter);
        lv_style.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选中一个条目之后 把这个条目要显示的背景对应的id保存起来
                SPUtils.putInt(getContext(), Constant.ADDRESS_BG_STYLE,mBgs[position]);
               // dismiss();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getContext(), R.layout.item_style, null);
            ImageView iv_image = (ImageView) view.findViewById(R.id.iv_style);
            TextView tv_style = (TextView) view.findViewById(R.id.tv_style);
            //用来显示 选中对号的imageview
            ImageView iv_selected = (ImageView) view.findViewById(R.id.iv_selected);

            //获取用户选中的背景id
            int id = SPUtils.getInt(getContext(), Constant.ADDRESS_BG_STYLE, R.drawable.bg_addr_normal_shape);
            //选中的背景id 和当前背景条目展示的背景id比较
            if(id == mBgs[position] ){
                //说明用户选中的就是这个条目
                iv_selected.setVisibility(View.VISIBLE);
            }else{
                iv_selected.setVisibility(View.INVISIBLE);
            }

            tv_style.setText(titls[position]);
            iv_image.setBackgroundResource(mBgs[position]);
            return view;
        }
    }
}

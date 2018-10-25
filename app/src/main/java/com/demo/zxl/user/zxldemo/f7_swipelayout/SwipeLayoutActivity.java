package com.demo.zxl.user.zxldemo.f7_swipelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.zxl.user.zxldemo.R;

public class SwipeLayoutActivity extends AppCompatActivity {
    private SwipeLayout currentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiplayout);
        ListView list  = (ListView) findViewById(R.id.list);
        MyAdatper adatper = new MyAdatper();
        list.setAdapter(adatper);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(currentLayout!=null){
                    currentLayout.closeLayout();
                    currentLayout = null;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    private class MyAdatper extends BaseAdapter{

        @Override
        public int getCount() {
            return Constant.NAMES.length;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView == null){
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            }else{
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.tv_name);
            textView.setText(Constant.NAMES[position]);
            SwipeLayout layout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
            layout.setListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onOpen(SwipeLayout swipeLayout) {
                    //判断之前是否有已经打开的 如果有 关闭
                    if(currentLayout != null && currentLayout != swipeLayout){
                        currentLayout.closeLayout();
                    }
                    //把当前打开的这个做一个记录
                    currentLayout = swipeLayout;
                }

                @Override
                public void onClose(SwipeLayout swipeLayout) {

                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SwipeLayoutActivity.this, "点击"+position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
}

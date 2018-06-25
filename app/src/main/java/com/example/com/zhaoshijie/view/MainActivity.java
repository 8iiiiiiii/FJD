package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.com.zhaoshijie.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //创建存放View的集合
    private List<View> vv = new ArrayList<>();
    private ViewPager pager;
    private Button but;
    private TextView time;
    //定义一个布尔值 通过判断 防止跳转两次
    boolean b = true;
    int i = 5;
    //倒计时跳转
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time.setText(msg.what + "S");
            if (b) {
                if (msg.what == 0) {
                    Intent it = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(it);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.mypager);
        initVv();
        pager.setAdapter(new MyPager());
    }

    private void initVv() {
        //将欢迎页的View添加到集合中
        vv.add(View.inflate(this, R.layout.halo_view01, null));
        vv.add(View.inflate(this, R.layout.halo_view02, null));
        View v3 = View.inflate(this, R.layout.halo_view03, null);
        vv.add(v3);
        time = v3.findViewById(R.id.time);
        //将倒计时时间发送给Handler
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (i > 0) {
                    i--;
                    try {
                        sleep(1000);
                        handler.sendEmptyMessage(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        but = v3.findViewById(R.id.but);
        //点击按钮跳转
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = false;
                Intent it = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(it);
            }
        });
    }

    //PagerAdapter适配器
    class MyPager extends PagerAdapter {
        @Override
        public int getCount() {
            return vv.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(vv.get(position));
            return vv.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

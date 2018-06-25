package com.example.com.zhaoshijie.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.EditDataAdapter;
import com.example.com.zhaoshijie.model.HotEditBean;
import com.google.gson.Gson;

import java.util.List;

public class EditDataActivity extends AppCompatActivity {

    private RecyclerView mHotRecy;
    private OkhttpManager manager;
    private EditDataAdapter editDataAdapter;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        initView();
        getEditdata();
    }

    //判断传过来的地址
    private void getEditdata() {
        //如果传过的搜索的地址不是空就加载数据
        if (getIntent().getStringExtra("hot_url") != null) {
            getdata(getIntent().getStringExtra("hot_url"));
        } else {//否则就加载点击的标签的数据
            getdata(getIntent().getStringExtra("hot_path"));
        }
    }

    //获取数据的方法，传一个地址的字符串参数f
    public void getdata(String f) {
        manager.asynStringByDATA(f, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                List<HotEditBean.DataBean> hot_data = new Gson().fromJson(result, HotEditBean.class).getData();
                editDataAdapter = new EditDataAdapter(EditDataActivity.this, hot_data);
                mHotRecy.setAdapter(editDataAdapter);
            }
        });
    }
    //获取控件Id
    private void initView() {
        manager = new OkhttpManager();
        mHotRecy = (RecyclerView) findViewById(R.id.hot_recy);
        mHotRecy.setLayoutManager(new LinearLayoutManager(EditDataActivity.this, LinearLayoutManager.VERTICAL, false));
    }

}

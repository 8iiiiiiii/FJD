package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.MyLabelBase;

import java.util.ArrayList;
import java.util.List;

public class HeadEditActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mHeaderErWeiMa;
    /**
     * 请输入要搜索的商品
     */
    private EditText mHeaderEditText;
    private ImageView mHeaderSearch;
    private RelativeLayout mHeadTitleEdit;
    private InterFaceUrl faceUrl;
    private RecyclerView mHeadLabelRecy;
    private OkhttpManager manager;
    private List<String> list = new ArrayList<>();
    private MyLabelBase myLabelBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_edit);
        initView();
        mHeaderSearch.setOnClickListener(this);
        setlabel();
    }

    private void setlabel() {
        for (int i = 0; i < 1; i++) {
            list.add("小米");
            list.add("笔记本");
            list.add("手机");
            list.add("拯救者");
            list.add("苹果");
            list.add("vivo");
        }
        myLabelBase = new MyLabelBase(this,list);
        mHeadLabelRecy.setAdapter(myLabelBase);
        myLabelBase.setOnLabelItemClickListener(new MyLabelBase.OnLabelItemClickListener() {
            @Override
            public void onLabelItemClick(int position) {
                Intent intent = new Intent(HeadEditActivity.this, EditDataActivity.class);
                String hot_path = faceUrl.hot_edit_Path + "?keywords=" + list.get(position) + "&page=1";
                intent.putExtra("hot_path", hot_path);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        manager = new OkhttpManager();
        faceUrl = new InterFaceUrl();
        mHeaderErWeiMa = (ImageView) findViewById(R.id.header_er_wei_ma);
        mHeaderEditText = (EditText) findViewById(R.id.header_edit_text);
        mHeaderSearch = (ImageView) findViewById(R.id.header_search);
        mHeadTitleEdit = (RelativeLayout) findViewById(R.id.head_title_edit);
        mHeadLabelRecy = (RecyclerView) findViewById(R.id.head_label_recy);
        mHeadLabelRecy.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
    }

    @Override
    public void onClick(View v) {
        String url = mHeaderEditText.getText().toString().trim();
        String hot_url = faceUrl.hot_edit_Path + "?keywords=" + url + "&page=1";
        Intent intent = new Intent(HeadEditActivity.this, EditDataActivity.class);
        intent.putExtra("hot_url", hot_url);
        startActivity(intent);
    }
}

package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.ProductAdapter;
import com.example.com.zhaoshijie.fragment.Fenl_Fragment02;
import com.example.com.zhaoshijie.fragment.Fragment02;
import com.example.com.zhaoshijie.model.ProductBean;
import com.google.gson.Gson;

import java.util.List;


/*
  商品详情的Activity
 */


public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mProductRecy;
    private OkhttpManager manager;
    private InterFaceUrl url;
    private int pscid;
    private ImageView mGoback;
    private ImageView mShare;
    private RelativeLayout mProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        pscid = intent.getIntExtra("pscid", 0);
        Log.i("PATH", "onCreate: " + url.product_list_path + "?pscid=" + pscid);
        initView();
        getProductdata();
    }

    //获取商品列表数据
    private void getProductdata() {
        manager.asynStringByDATA(url.product_list_path + "?pscid=" + pscid, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                List<ProductBean.DataBean> sp_list = new Gson().fromJson(result, ProductBean.class).getData();
                mProductRecy.setAdapter(new ProductAdapter(ProductActivity.this, sp_list));
            }
        });
    }

    private void initView() {
        manager = new OkhttpManager();
        url = new InterFaceUrl();
        mProductRecy = (RecyclerView) findViewById(R.id.product_recy);
        mProductRecy.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.VERTICAL, false));
        mGoback = (ImageView) findViewById(R.id.goback);
        mGoback.setOnClickListener(this);
        mShare = (ImageView) findViewById(R.id.share);
        mShare.setOnClickListener(this);
        mProd = (RelativeLayout) findViewById(R.id.prod);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.goback:
                getSupportFragmentManager().beginTransaction().replace(R.id.prod,new Fragment02()).commit();
                finish();
                break;
            case R.id.share:
                break;
        }
    }
}

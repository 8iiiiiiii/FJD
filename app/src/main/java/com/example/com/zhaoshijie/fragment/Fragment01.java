package com.example.com.zhaoshijie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.GlideImageLoader;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.ModuleAdapter;
import com.example.com.zhaoshijie.adapter.TuijianAdapter;
import com.example.com.zhaoshijie.model.BroadCastBean;
import com.example.com.zhaoshijie.model.ModuleBean;
import com.example.com.zhaoshijie.view.HomeGoodsActivity;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/8.
 */

public class Fragment01 extends Fragment {
    private Banner mBanner;
    private OkhttpManager manager;
    private InterFaceUrl face;
    private List<String> images = new ArrayList<>();
    private RecyclerView mXrv;
    private XRecyclerView mRcv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment01, container, false);
        initView(v);
        getBroadCast();
        getModule();
        getRecommend();
        return v;
    }

    //推荐商品
    private void getRecommend() {
        mRcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        manager.asynStringByDATA(face.broadPath, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                final List<BroadCastBean.TuijianBean.ListBean> tuijian_data = new Gson().fromJson(result, BroadCastBean.class).getTuijian().getList();
                TuijianAdapter tuijianAdapter = new TuijianAdapter(getActivity(), tuijian_data);
                mRcv.setAdapter(tuijianAdapter);
                tuijianAdapter.setTuijianItemClickListener(new TuijianAdapter.OnTuijianItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), HomeGoodsActivity.class);
                        intent.putExtra("pid",tuijian_data.get(position).getPid());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    //默认模块
    private void getModule() {
        mXrv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
        manager.asynStringByDATA(face.modulePath, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                List<ModuleBean.DataBean> mod_data = new Gson().fromJson(result, ModuleBean.class).getData();
                mXrv.setAdapter(new ModuleAdapter(getActivity(),mod_data));
            }
        });
    }

    //轮播图
    private void getBroadCast() {
        manager.asynStringByDATA(face.broadPath, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                BroadCastBean broadCastBean = gson.fromJson(result, BroadCastBean.class);
                List<BroadCastBean.DataBean> data = broadCastBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    images.add(data.get(i).getIcon());
                    //设置图片加载器
                    mBanner.setImageLoader(new GlideImageLoader());

                    //设置图片集合
                    mBanner.setImages(images);
                    //设置banner动画效果
                    mBanner.setBannerAnimation(Transformer.DepthPage);
                    //设置自动轮播，默认为true
                    mBanner.isAutoPlay(true);
                    //设置轮播时间
                    mBanner.setDelayTime(1500);
                    //banner设置方法全部调用完毕时最后调用
                    mBanner.start();
                }

            }
        });
    }


    //获取控件Id
    private void initView(View v) {
        mBanner = (Banner) v.findViewById(R.id.banner);
        manager = new OkhttpManager();
        face = new InterFaceUrl();
        mXrv = (RecyclerView) v.findViewById(R.id.xrv);
        mRcv = (XRecyclerView) v.findViewById(R.id.recy_tuijian);
    }
}

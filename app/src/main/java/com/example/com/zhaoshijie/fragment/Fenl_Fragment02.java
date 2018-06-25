package com.example.com.zhaoshijie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.ChildAdapter;
import com.example.com.zhaoshijie.adapter.FenlAdapter;
import com.example.com.zhaoshijie.model.ChildBean;
import com.example.com.zhaoshijie.model.ModuleBean;
import com.example.com.zhaoshijie.view.ProductActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class Fenl_Fragment02 extends Fragment {

    private InterFaceUrl url;
    private OkhttpManager manager;
    private String sub_url;
    private RecyclerView mChildRecy;
    private ChildAdapter.ChildrenAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fenl_fragment_02, container, false);
        initView(v);
        Bundle bundle = getArguments();
            if (bundle != null) {
                //得到侧分类传过来的Cid
                sub_url = bundle.getString("sub_url");
                getSubdata(sub_url);
            }
        return v;
    }

    //获取子分类数据
    private void getSubdata(String s) {
        manager.asynStringByDATA(url.subPath + "?cid=" + s, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                final List<ChildBean.DataBean> child_data = new Gson().fromJson(result, ChildBean.class).getData();
                ChildAdapter childAdapter = new ChildAdapter(getActivity(), child_data);
                mChildRecy.setAdapter(childAdapter);

                childAdapter.setOnItemChildClickListener(new ChildAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemClick(int position, List<ChildBean.DataBean.ListBean> children_List) {
                        Intent it = new Intent(getActivity(), ProductActivity.class);
                        it.putExtra("pscid",children_List.get(position).getPscid());
                        getActivity().startActivity(it);
                    }
                });
            }
        });
    }

    //获取控件Id
    private void initView(View v) {
        manager = new OkhttpManager();
        url = new InterFaceUrl();
        mChildRecy = (RecyclerView) v.findViewById(R.id.child_recy);
        mChildRecy.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
}

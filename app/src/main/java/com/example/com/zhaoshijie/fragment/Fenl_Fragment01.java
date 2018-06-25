package com.example.com.zhaoshijie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.adapter.FenlAdapter;
import com.example.com.zhaoshijie.model.ModuleBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class Fenl_Fragment01 extends Fragment {
    private RecyclerView mFenlRecy;
    private FenlAdapter fenlBase;
    private OkhttpManager manager;
    private InterFaceUrl url;
    private List<ModuleBean.DataBean> fenlList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fenl_fragment_01, container, false);
        initView(v);
        getFenlDate();
        return v;
    }

    //获取侧面分类列表数据
    private void getFenlDate() {
        manager.asynStringByDATA(url.modulePath, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                fenlList = new Gson().fromJson(result, ModuleBean.class).getData();
                fenlBase = new FenlAdapter(getActivity(), fenlList);
                mFenlRecy.setAdapter(fenlBase);
                //点击分类列表 传递不同的Cid 跳转不同页面
              fenlBase.setItemClickListener(new FenlAdapter.OnLeftItemClickListener() {
                  @Override
                  public void onItemClick(int position, List<ModuleBean.DataBean> list) {
                      Fenl_Fragment02 fenl_fragment02 = new Fenl_Fragment02();
                      Bundle bundle = new Bundle();
                      bundle.putString("sub_url", String.valueOf(fenlList.get(position).getCid()));
                      fenl_fragment02.setArguments(bundle);
                      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fraam, fenl_fragment02).commit();
                  }
              });
            }
        });
    }

    private void initView(View v) {
        manager = new OkhttpManager();
        url = new InterFaceUrl();
        mFenlRecy = (RecyclerView) v.findViewById(R.id.fenl_recy);
        //设置布局管理器
        mFenlRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //添加分割线
        mFenlRecy.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}

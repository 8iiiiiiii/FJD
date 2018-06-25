package com.example.com.zhaoshijie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.zhaoshijie.R;

/**
 * Created by 老赵的拯救者 on 2018/6/8.
 */

public class Fragment02 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment02, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.fraam, new Fenl_Fragment02()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment01, new Fenl_Fragment01()).commit();
        return v;
    }
}

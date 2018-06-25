package com.example.com.zhaoshijie.Utils;

import android.app.Application;

import com.mob.MobSDK;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by 老赵的拯救者 on 2018/6/14.
 */

public class MyShareApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}

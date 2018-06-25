package com.example.com.zhaoshijie.Utils;

import android.os.Handler;
import android.telecom.Call;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 老赵的拯救者 on 2018/6/8.
 *
 *
 *
 * OkHttp封装类
 *
 *
 */

public class OkhttpManager {


    //2、定义成员变量
    private static Handler handler;
    private final OkHttpClient client;
    private static OkhttpManager manager;

    //1、使用构造方法完成初始化
    public OkhttpManager() {

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) //设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)   //设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)  //设置写入超时时间
                .build();

        handler = new Handler();
    }

    //3、使用单例模式，暴露方法给外部提供对象
    public static OkhttpManager getInstance() {
        if (manager == null) {
            manager = new OkhttpManager();
        }
        return manager;
    }

    // 4、定义接口
    public interface Fun1 {
        void onResponse(String result);
    }

    interface Fun2 {
        void onResponse(byte[] result);
    }

    interface Fun3 {
        void onResponse(JSONObject jsonObject);
    }

    // 5、处理请求网络成功的方法，返回的结果是JSON串
    public static void onSuccessJsonString(final byte[] result, final Fun2 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(result);
                }
            }
        });
    }

    public static void onSuccessString(final String result, final Fun1 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(result);
                }
            }
        });
    }

    // 6、暴露给外界用来调用的方法

    /*
    * 指定的url 返回的是Json字符串
    */
    public void asynJsonStringByURL(String url, final Fun2 callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                onSuccessJsonString(response.body().bytes(), callback);
            }

        });
    }

    //暴露给外界请求数据的方法
    public void asynStringByDATA(String url, final Fun1 callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                onSuccessString(response.body().string(), callback);
            }

        });
    }

    //提交表单
    public void asynJsonStringByData(String url, Map<String, String> map, final Fun1 callback) {
        FormBody.Builder builder = new FormBody.Builder();
        //如果map不为null,就把集合的键值放到表单里
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                Log.d("zzzz", "asynJsonStringByData: "+entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                onSuccessString(response.body().string(),callback);
            }

        });
    }
}

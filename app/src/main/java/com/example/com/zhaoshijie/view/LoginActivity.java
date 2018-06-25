package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.EvbMessage;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.fragment.Fragment01;
import com.example.com.zhaoshijie.fragment.Fragment03;
import com.example.com.zhaoshijie.fragment.Fragment04;
import com.example.com.zhaoshijie.model.LoginBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 登 陆
     */
    private TextView mTvLogin;
    /**
     * 账号
     */
    private EditText mTvUsername;
    private TextInputLayout mTxipUsername;
    /**
     * 密码
     */
    private EditText mTxPassworld;
    private TextInputLayout mTxipPassworld;
    /**
     * 登 陆
     */
    private Button mBtLogin;
    /**
     * 新用户
     */
    private TextView mTvRegisteLogin;
    /**
     * 密码修改
     */
    private TextView mTvRegisteRefactor;
    private InterFaceUrl url;
    private OkhttpManager manager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
    }

    //获取登录数据
    private void getLogin() {
        final String name = mTvUsername.getText().toString().trim();
        final String pwd = mTxPassworld.getText().toString().trim();
        manager.asynStringByDATA(
                url.login_user_path + "?mobile=" + name + "&password=" + pwd, new OkhttpManager.Fun1() {
                    @Override
                    public void onResponse(String result) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                        String username = loginBean.getData().getUsername();
                        String msg = loginBean.getMsg();
                        String code = loginBean.getCode();
                        Toast.makeText(LoginActivity.this, "" + code, Toast.LENGTH_SHORT).show();
                        if (msg.equals("登录成功")) {
                            //存一个true的值
                            editor.putBoolean("isLog", true).commit();
                            editor.putInt("uid", loginBean.getData().getUid()).commit();
                            editor.putString("username", username).commit();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_login://点击登录
                getLogin();
                break;
            case R.id.tv_registe_login://点击注册
                LoginActivity.this.finish();
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
                break;

        }
    }

    //获取控件Id
    private void initView() {
        manager = new OkhttpManager();
        url = new InterFaceUrl();
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTvUsername = (EditText) findViewById(R.id.tv_username);
        mTxipUsername = (TextInputLayout) findViewById(R.id.txip_username);
        mTxPassworld = (EditText) findViewById(R.id.tx_passworld);
        mTxipPassworld = (TextInputLayout) findViewById(R.id.txip_passworld);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);
        mTvRegisteLogin = (TextView) findViewById(R.id.tv_registe_login);
        mTvRegisteLogin.setOnClickListener(this);
        mTvRegisteRefactor.setOnClickListener(this);
    }
}

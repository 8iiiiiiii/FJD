package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.model.RegBean;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * X
     */
    private TextView mTxtReturn;
    /**
     * 请输入用户名
     */
    private EditText mRegName;
    /**
     * 请输入密码
     */
    private EditText mRegPwd;
    /**
     * 请填写邮箱
     */
    private EditText mRegEmail;
    /**
     * 注册
     */
    private Button mRegBtn;
    /**
     * 登录
     */
    private Button mLoginBtn;
    private OkhttpManager manager;
    private InterFaceUrl url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
    }

    private void initView() {
        url = new InterFaceUrl();
        manager = new OkhttpManager();
        mTxtReturn = (TextView) findViewById(R.id.txtReturn);
        mRegName = (EditText) findViewById(R.id.reg_name);
        mRegPwd = (EditText) findViewById(R.id.reg_pwd);
        mRegEmail = (EditText) findViewById(R.id.reg_email);
        mRegBtn = (Button) findViewById(R.id.reg_btn);
        mRegBtn.setOnClickListener(this);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reg_btn:
                //获取注册的数据
                final String name = mRegName.getText().toString().trim();
                final String pwd = mRegPwd.getText().toString().trim();
                final String email = mRegEmail.getText().toString().trim();
                manager.asynStringByDATA(
                        url.reg_user_path + "?mobile=" + name + "&password=" + pwd, new OkhttpManager.Fun1() {
                            @Override
                            public void onResponse(String result) {
                                String s = new Gson().fromJson(result, RegBean.class).getMsg();
                                String c = new Gson().fromJson(result, RegBean.class).getCode();
                                if ("1".equals(c)) {
                                    Toast.makeText(RegActivity.this, s, Toast.LENGTH_SHORT).show();
                                } else if (email.equals("") && !isEmail(email)) {
                                    Toast.makeText(RegActivity.this, "邮箱格式不正确或不能为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    RegActivity.this.finish();
                                    startActivity(new Intent(RegActivity.this, LoginActivity.class));
                                }
                            }
                        });
                break;
            case R.id.login_btn:
                RegActivity.this.finish();
                startActivity(new Intent(RegActivity.this,LoginActivity.class));
                break;
        }
    }

    //邮箱格式
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}

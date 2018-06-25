package com.example.com.zhaoshijie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.fragment.Fragment01;
import com.example.com.zhaoshijie.fragment.Fragment02;
import com.example.com.zhaoshijie.fragment.Fragment03;
import com.example.com.zhaoshijie.fragment.Fragment04;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import static android.app.PendingIntent.getActivity;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FrameLayout mFrame;
    /**
     * 首页
     */
    private RadioButton mBut01;
    /**
     * 分类
     */
    private RadioButton mBut02;
    /**
     * 购物车
     */
    private RadioButton mBut03;
    /**
     * 我的
     */
    private RadioButton mBut04;
    private RadioGroup mGroup;
    private RelativeLayout mHeadTitleEdit;
    private ImageView mHeaderErWeiMa;
    /**
     * 请输入要搜索的商品
     */
    private EditText mHeaderEditText;
    private ImageView mHeaderSearch;
    private HeadEditText_Zh head_zh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {
        head_zh = (HeadEditText_Zh) findViewById(R.id.head_zh);
        mFrame = (FrameLayout) findViewById(R.id.frame);
        mBut01 = (RadioButton) findViewById(R.id.but01);
        mBut02 = (RadioButton) findViewById(R.id.but02);
        mBut03 = (RadioButton) findViewById(R.id.but03);
        mBut04 = (RadioButton) findViewById(R.id.but04);
        mGroup = (RadioGroup) findViewById(R.id.group);
        //设置默认显示的Fragment
        setFragment(new Fragment01());
        //点击按钮切换不同的Fragment
        mGroup.setOnCheckedChangeListener(this);
        //默认显示头部输入框
        head_zh.setVisibility(View.VISIBLE);
      //  mHeaderEditText.setFocusable(false);
       head_zh.setOnEditClickListener(new HeadEditText_Zh.OnEditClickListener() {
            @Override
            public void onEditClick() {
            startActivity(new Intent(HomeActivity.this,HeadEditActivity.class));
            }

            @Override
            public void onErWeiMaClick() {
                Toast.makeText(HomeActivity.this, "点击了二维码", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 444);
            }

            @Override
            public void onSearchClick() {
                Toast.makeText(HomeActivity.this, "点击了Seach", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.but01:
                setFragment(new Fragment01());
                head_zh.setVisibility(View.VISIBLE);
                break;
            case R.id.but02:
                setFragment(new Fragment02());
                head_zh.setVisibility(View.VISIBLE);
                break;
            case R.id.but03:
                setFragment(new Fragment03());
                head_zh.setVisibility(View.GONE);
                break;
            case R.id.but04:
                setFragment(new Fragment04());
                head_zh.setVisibility(View.GONE);
                break;
        }
    }

    //设置Fragment的方法
    private void setFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 444) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(HomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

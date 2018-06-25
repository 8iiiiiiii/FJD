package com.example.com.zhaoshijie.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.fragment.Fragment04;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    /**
     * 0
     */
    private TextView mWaitPayTv;
    private LinearLayout mWaitPayLl;
    /**
     * 0
     */
    private TextView mWaitReceiveTv;
    private LinearLayout mWaitReceiveLl;
    private LinearLayout mMyjdOrder;
    private ImageView mImageView;
    /**
     * 退 出 登 录
     */
    private Button mLogoutBtn;
    private RelativeLayout mMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();

    }

    private void initView() {
        mUserIconIv = (ImageView) findViewById(R.id.user_icon_iv);
        mUserIconIv.setOnClickListener(this);
        mUserNameTv = (TextView) findViewById(R.id.user_name_tv);
        mUserNameTv.setOnClickListener(this);
        mUserLevelTv = (TextView) findViewById(R.id.user_level_tv);
        mUserLevelTv.setOnClickListener(this);
        mWaitPayTv = (TextView) findViewById(R.id.wait_pay_tv);
        mWaitPayTv.setOnClickListener(this);
        mWaitPayLl = (LinearLayout) findViewById(R.id.wait_pay_ll);
        mWaitPayLl.setOnClickListener(this);
        mWaitReceiveTv = (TextView) findViewById(R.id.wait_receive_tv);
        mWaitReceiveTv.setOnClickListener(this);
        mWaitReceiveLl = (LinearLayout) findViewById(R.id.wait_receive_ll);
        mMyjdOrder = (LinearLayout) findViewById(R.id.myjd_order);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setOnClickListener(this);
        mLogoutBtn = (Button) findViewById(R.id.logout_btn);
        mLogoutBtn.setOnClickListener(this);
        mMy = (RelativeLayout) findViewById(R.id.my);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_icon_iv:
                break;
            case R.id.user_name_tv:
                break;
            case R.id.user_level_tv:
                break;
            case R.id.wait_pay_tv:
                break;
            case R.id.wait_pay_ll:
                break;
            case R.id.wait_receive_tv:
                break;
            case R.id.imageView:
                break;
            case R.id.logout_btn:
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.clear().commit();
                MyActivity.this.finish();
                getSupportFragmentManager().beginTransaction().replace(R.id.my,new Fragment04()).commit();
                break;

        }
    }
}

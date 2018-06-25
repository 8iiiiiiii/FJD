package com.example.com.zhaoshijie.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.EvbMessage;
import com.example.com.zhaoshijie.view.LoginActivity;
import com.example.com.zhaoshijie.view.MainActivity;
import com.example.com.zhaoshijie.view.MyActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import static com.tencent.open.web.security.a.b;

/**
 * Created by 老赵的拯救者 on 2018/6/8.
 */

public class Fragment04 extends Fragment implements View.OnClickListener {
    private View view;
    ;
    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    /**
     * 登录  /  注册
     */
    private TextView mGoLogin;
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
    private RelativeLayout mMy;
    private Button mBackLogin;
    private SharedPreferences sharedPreferences;
    private String[] mCustomItems = new String[]{"本地相册", "相机拍照"};
    private String path = Environment.getExternalStorageDirectory() + "/zsj.png";
    private EventBus eventBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment04, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.goLogin://点击登录 / 注册 、跳转登录页面
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.logout_btn://点击退出登录
                sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.wait_pay_tv:
                break;
            case R.id.wait_receive_tv:
                break;
            case R.id.user_icon_iv://点击头像
                setUserIcon();
                break;
        }
    }

    //设置用户头像
    private void setUserIcon() {

        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择：");
        builder.setItems(mCustomItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //相册
                    setPhoto();
                } else if (which == 1) {
                    //照相机
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                    startActivityForResult(it, 100);
                }
            }
        });
        builder.create().show();
    }

    //设置相册
    private void setPhoto() {
        //调取系统的相册  Intent.ACTION_PICK相册
        Intent it = new Intent(Intent.ACTION_PICK);
        //设置格式
        it.setType("image/*");
        startActivityForResult(it, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean b = sharedPreferences.getBoolean("isLog", false);
        if(b){
            String username = sharedPreferences.getString("username", null);
            mGoLogin.setText(username);
            mBackLogin.setVisibility(View.VISIBLE);
        }else{
            mGoLogin.setText("登录  /  注册");
        }
    }

    //获取控件Id
    private void initView(View v) {
        mUserIconIv = (ImageView) v.findViewById(R.id.user_icon_iv);
        mUserIconIv.setOnClickListener(this);
        mUserNameTv = (TextView) v.findViewById(R.id.user_name_tv);
        mUserLevelTv = (TextView) v.findViewById(R.id.user_level_tv);
        mGoLogin = (TextView) v.findViewById(R.id.goLogin);
        mGoLogin.setOnClickListener(this);
        mWaitPayTv = (TextView) v.findViewById(R.id.wait_pay_tv);
        mWaitPayTv.setOnClickListener(this);
        mWaitPayLl = (LinearLayout) v.findViewById(R.id.wait_pay_ll);
        mWaitReceiveTv = (TextView) v.findViewById(R.id.wait_receive_tv);
        mWaitReceiveTv.setOnClickListener(this);
        mWaitReceiveLl = (LinearLayout) v.findViewById(R.id.wait_receive_ll);
        mMyjdOrder = (LinearLayout) v.findViewById(R.id.myjd_order);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        mMy = (RelativeLayout) v.findViewById(R.id.my);
        mBackLogin = (Button) v.findViewById(R.id.logout_btn);
        mBackLogin.setOnClickListener(this);
        mBackLogin.setVisibility(View.GONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //相机剪裁
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            Intent it = new Intent("com.android.camera.action.CROP");
            it.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            it.putExtra("CROP", true);
            it.putExtra("aspaceX", 1);
            it.putExtra("aspanceY", 1);
            it.putExtra("outputX", 300);
            it.putExtra("outputY", 300);
            it.putExtra("return-data", true);
            startActivityForResult(it, 200);
        }
        if (requestCode == 200 && resultCode == getActivity().RESULT_OK) {
            Bitmap map = data.getParcelableExtra("data");
            mUserIconIv.setImageBitmap(map);
        }

        //得到相册里的图片进行裁剪
        if (requestCode == 1000 && resultCode == getActivity().RESULT_OK) {
            //得到相册图片
            Uri uri = data.getData();
            //裁剪
            Intent it = new Intent("com.android.camera.action.CROP");
            //设置图片 以及格式
            it.setDataAndType(uri, "image/*");
            //是否支持裁剪
            it.putExtra("crop", true);
            //设置比例
            it.putExtra("aspectX", 1);
            it.putExtra("aspectY", 1);
            //设置输出的大小
            it.putExtra("outputX", 250);
            it.putExtra("outputY", 250);
            //是否支持人脸识别
//    			it.putExtra("onFaceDetection", true);
            //返回
            it.putExtra("return-data", true);
            startActivityForResult(it, 2000);
        }

        //2.点击裁剪完成
        if (requestCode == 2000 && resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            mUserIconIv.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

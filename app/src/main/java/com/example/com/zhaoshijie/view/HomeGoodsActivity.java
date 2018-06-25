package com.example.com.zhaoshijie.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.GlideImageLoader;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.fragment.Fragment01;
import com.example.com.zhaoshijie.model.AddBean;
import com.example.com.zhaoshijie.model.HomeGoodsBean;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class HomeGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private Banner mBanner;
    private List<String> images = new ArrayList<>();
    /**
     * TextView
     */
    private TextView mHomegoodsTitle;
    /**
     * TextView
     */
    private TextView mHomegoodsPrice;
    /**
     * TextView
     */
    private TextView mHomegoodsNowPrice;
    private InterFaceUrl url;
    private OkhttpManager manager;
    /**
     * TextView
     */
    private TextView mHomeGoodsMs;
    private ImageView mGoback;
    private ImageView mShare;
    private RelativeLayout mHeadBack;
    private ImageView mHomegoodsHalo;
    private ImageView mHomegoodsDianpu;
    private ImageView mHomegoodsShop;
    /**
     * 立即购买
     */
    private Button mGoshop;
    /**
     * 加入购物车
     */
    private Button mAddshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_goods);
        initView();
        getBroadCast();
    }

    //轮播图
    private void getBroadCast() {
        int pid = getIntent().getIntExtra("pid", 0);
        manager.asynStringByDATA(url.homegoods_path + "?pid=" + pid, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                HomeGoodsBean homeGoodsBean = gson.fromJson(result, HomeGoodsBean.class);
                final HomeGoodsBean.DataBean data = homeGoodsBean.getData();
                mHomegoodsTitle.setText(data.getTitle());
                mHomeGoodsMs.setText(data.getSubhead());
                mHomegoodsNowPrice.setText("618促销：￥" + (float) data.getBargainPrice());
                mHomegoodsPrice.setText("原价：￥" + (float) data.getPrice());
                String[] image = data.getImages().split("\\|");
                for (int i = 0; i < image.length; i++) {
                    images.add(image[i]);
                }
                for (int i = 0; i < images.size(); i++) {
                    //设置图片加载器
                    mBanner.setImageLoader(new GlideImageLoader());
                    //设置数字指示器
                    mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                    //设置指示器居右
                    mBanner.setIndicatorGravity(BannerConfig.RIGHT);
                    //设置图片集合
                    mBanner.setImages(images);
                    //设置banner动画效果
                    mBanner.setBannerAnimation(Transformer.DepthPage);
                    //设置自动轮播，默认为true
                    mBanner.isAutoPlay(true);
                    //设置轮播时间
                    mBanner.setDelayTime(1500);
                    //banner设置方法全部调用完毕时最后调用
                    mBanner.start();
                }

                //点击返回
                mGoback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeGoodsActivity.this.finish();
                        getSupportFragmentManager().beginTransaction().replace(R.id.homegoods,new Fragment01()).commit();
                    }
                });

                //点击分享
                mShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnekeyShare oks = new OnekeyShare();
                        //关闭sso授权
                        oks.disableSSOWhenAuthorize();
                        // title标题，微信、QQ和QQ空间等平台使用
                        oks.setTitle("分享");
                        // titleUrl QQ和QQ空间跳转链接
                        oks.setTitleUrl("https://item.jd.com/7632577.html");
                        // text是分享文本，所有平台都需要这个字段
                        oks.setText(data.getTitle());
                        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                        // url在微信、微博，Facebook等平台中使用
                        oks.setUrl("https://item.jd.com/7632577.html");
                        // comment是我对这条分享的评论，仅在人人网使用
                        oks.setComment("好，很好，非常好");
                        // 启动分享GUI
                        oks.show(HomeGoodsActivity.this);
                    }
                });
            }
        });
    }

    //获取控件Id
    private void initView() {
        manager = new OkhttpManager();
        url = new InterFaceUrl();
        mBanner = (Banner) findViewById(R.id.banner);
        mHomegoodsTitle = (TextView) findViewById(R.id.homegoods_title);
        mHomegoodsPrice = (TextView) findViewById(R.id.homegoods_price);
        mHomegoodsNowPrice = (TextView) findViewById(R.id.homegoods_now_price);
        //设置加粗
        mHomegoodsTitle.getPaint().setFakeBoldText(true);
        //设置删除线
        mHomegoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mHomeGoodsMs = (TextView) findViewById(R.id.home_goods_ms);
        mGoback = (ImageView) findViewById(R.id.goback);
        mShare = (ImageView) findViewById(R.id.share);
        mHeadBack = (RelativeLayout) findViewById(R.id.head_back);
        mHomegoodsHalo = (ImageView) findViewById(R.id.homegoods_halo);
        mHomegoodsDianpu = (ImageView) findViewById(R.id.homegoods_dianpu);
        mHomegoodsShop = (ImageView) findViewById(R.id.homegoods_shop);
        mGoshop = (Button) findViewById(R.id.goshop);
        mGoshop.setOnClickListener(this);
        mAddshop = (Button) findViewById(R.id.addshop);
        mAddshop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.goshop:
                break;
            case R.id.addshop:
                getAddShop();
                break;
        }
    }

    //添加到购物车
    private void getAddShop() {
        int uid = getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
        int pid = getIntent().getIntExtra("pid", 0);
        manager.asynStringByDATA(url.add_shopping + "?uid=" + uid + "&pid=" + pid, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                String s = new Gson().fromJson(result, AddBean.class).getCode();
                String m = new Gson().fromJson(result, AddBean.class).getMsg();
                if("0".equals(s)){
                    Toast.makeText(HomeGoodsActivity.this, m, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeGoodsActivity.this, m, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

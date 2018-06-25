package com.example.com.zhaoshijie.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.zhaoshijie.R;


public class UI_Add extends LinearLayout {

    private int MaxValue = 10;
    private int minValue = 1;
    private ImageView mImgSub;
    private TextView mTextNum;
    private ImageView mImgAdd;

    //设置库存
    public int getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(int maxValue) {
        MaxValue = maxValue;
    }

    public UI_Add(Context context) {
        this(context, null);
    }

    public UI_Add(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UI_Add(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.addjian, this, true);
        initView(view);
        mImgSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mTextNum.getText().toString();
                Integer num = Integer.valueOf(s);
                if (num > minValue) {
                    num--;
                }
                if (mGetDataTextView != null) {
                    mGetDataTextView.getTextViewDataToMun(num);
                    mTextNum.setText(num + "");
                }
            }
        });


        mImgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mTextNum.getText().toString();
                Integer num = Integer.valueOf(s);
                if (num < MaxValue) {
                    num++;
                }
                if (mGetDataTextView != null) {
                    mGetDataTextView.getTextViewDataToMun(num);
                    mTextNum.setText(num + "");
                }
            }
        });

    }
    //设置存放num的方法
    public void setShopNum(int num) {
        mTextNum.setText(num + "");
    }


    private void initView(View view) {
        mImgSub = view.findViewById(R.id.img_sub);
        mTextNum = view.findViewById(R.id.text_num);
        mImgAdd = view.findViewById(R.id.img_add);
    }

    //接口传值
    public interface GetDataTextView {
        void getTextViewDataToMun(Integer num);
    }

    private GetDataTextView mGetDataTextView;

    public void setGetDataTextView(GetDataTextView getDataTextView) {
        this.mGetDataTextView = getDataTextView;
    }
}

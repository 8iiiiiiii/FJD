package com.example.com.zhaoshijie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.com.zhaoshijie.R;

/**
 * Created by 老赵的拯救者 on 2018/6/14.
 *
 *
 * 自定义组合控件类 引用热门搜索布局
 *
 *
 */

public class HeadEditText_Zh extends RelativeLayout implements View.OnClickListener {

    private ImageView er_wei_ma;
    private EditText head_edit_text;
    private ImageView head_serach;

    public HeadEditText_Zh(Context context) {
        this(context, null);
    }

    public HeadEditText_Zh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadEditText_Zh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.head_edittext, this);
        er_wei_ma = (ImageView) findViewById(R.id.header_er_wei_ma);
        head_edit_text = (EditText) findViewById(R.id.header_edit_text);
        head_serach = (ImageView) findViewById(R.id.header_search);
        initView();
    }

    private void initView() {
        er_wei_ma.setOnClickListener(this);
        head_edit_text.setOnClickListener(this);
        head_serach.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_er_wei_ma:
                if (mClickListener != null) {
                    mClickListener.onErWeiMaClick();
                }
                break;
            case R.id.header_edit_text:
                if (mClickListener != null) {
                    mClickListener.onEditClick();
                }
                break;
            case R.id.header_search:
                if (mClickListener != null) {
                    mClickListener.onSearchClick();
                }
                break;
        }
    }

    //定义接口
    public interface OnEditClickListener {
        void onEditClick();
        void onErWeiMaClick();
        void onSearchClick();
    }

    //声明接口对象
    private OnEditClickListener mClickListener;

    //判断
    public void setOnEditClickListener(OnEditClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }


}

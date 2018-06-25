package com.example.com.zhaoshijie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.model.BroadCastBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class TuijianAdapter extends RecyclerView.Adapter<TuijianAdapter.ViewHodler> implements View.OnClickListener {

    private Context context;
    private List<BroadCastBean.TuijianBean.ListBean> list;

    public TuijianAdapter(Context context, List<BroadCastBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.tuijian_item_base, null);
        v.setOnClickListener(this);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.itemView.setTag(position);
        holder.mTuijianTitle.setText(list.get(position).getTitle());
        holder.mTuijianPrice.setText("￥" + list.get(position).getPrice());
        Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(holder.mTuijianImage);
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        ImageView mTuijianImage;
        TextView mTuijianPrice;
        TextView mTuijianTitle;

        public ViewHodler(View view) {
            super(view);
            this.mTuijianImage = (ImageView) view.findViewById(R.id.tuijian_image);
            this.mTuijianPrice = (TextView) view.findViewById(R.id.tuijian_price);
            this.mTuijianTitle = (TextView) view.findViewById(R.id.tuijian_title);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //定义接口
    public interface OnTuijianItemClickListener {
        void onItemClick(int position);
    }

    //声明接口对象
    private OnTuijianItemClickListener mItemClickListener;

    //判断
    public void setTuijianItemClickListener(OnTuijianItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}

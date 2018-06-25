package com.example.com.zhaoshijie.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.view.HeadEditActivity;

import java.util.List;

public class MyLabelBase extends RecyclerView.Adapter<MyLabelBase.ViewHodler> implements View.OnClickListener {
    private Context context;
    private List<String> list;

    public MyLabelBase(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.label_item_base, null);
        v.setOnClickListener(this);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.itemView.setTag(position);
        holder.mLabelTv.setText(list.get(position));
    }

    @Override
    public void onClick(View v) {
        if (mOnLabelItemClickListener != null) {
            mOnLabelItemClickListener.onLabelItemClick((Integer) v.getTag());
        }
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView mLabelTv;

        public ViewHodler(View view) {
            super(view);
            mLabelTv = (TextView) view.findViewById(R.id.label_tv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnLabelItemClickListener {
        void onLabelItemClick(int position);
    }

    private OnLabelItemClickListener mOnLabelItemClickListener;

    public void setOnLabelItemClickListener(OnLabelItemClickListener onLabelItemClickListener) {
        mOnLabelItemClickListener = onLabelItemClickListener;
    }

}
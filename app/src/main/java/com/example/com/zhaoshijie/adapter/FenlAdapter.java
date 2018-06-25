package com.example.com.zhaoshijie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.model.ModuleBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class FenlAdapter extends RecyclerView.Adapter<FenlAdapter.ViewHodler> implements View.OnClickListener {

    private Context con;
    private List<ModuleBean.DataBean> list;

    public FenlAdapter(Context con, List<ModuleBean.DataBean> list) {
        this.con = con;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(con, R.layout.fenl_item_base, null);
        v.setOnClickListener(this);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        holder.itemView.setTag(position);
        holder.mFenlItemText.setText(list.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag(), list);
        }
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView mFenlItemText;

        public ViewHodler(View view) {
            super(view);
            mFenlItemText = (TextView) view.findViewById(R.id.fenl_item_text);
            //设置加粗
            mFenlItemText.getPaint().setFakeBoldText(true);
        }
    }

    //定义接口
    public interface OnLeftItemClickListener {
        void onItemClick(int position, List<ModuleBean.DataBean> list);
    }

    //声明接口对象
    private OnLeftItemClickListener mItemClickListener;

    //判断
    public void setItemClickListener(OnLeftItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}

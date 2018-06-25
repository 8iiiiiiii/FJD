package com.example.com.zhaoshijie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.Utils.UI_Add;
import com.example.com.zhaoshijie.fragment.Fragment03;
import com.example.com.zhaoshijie.fragment.Fragment04;
import com.example.com.zhaoshijie.model.DropBean;
import com.example.com.zhaoshijie.model.QueryBean;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHodler> {

    private Context con;
    private List<QueryBean.DataBean> list;

    public QueryAdapter(Context con, List<QueryBean.DataBean> list) {
        this.con = con;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(con, R.layout.query_item_base, null);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        holder.itemView.setTag(position);
        //获取商家名
        holder.mTvSeller.setText(list.get(position).getSellerName());
        //把List集合传到ChildQueryAdapter中
        ChildQueryAdapter childQueryAdapter = new ChildQueryAdapter(list.get(position).getList());
        holder.mQueryRecy.setAdapter(childQueryAdapter);
        holder.mQueryRecy.setLayoutManager(new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHodler extends RecyclerView.ViewHolder {

        TextView mTvSeller;
        TextView mTvEdit;
        RecyclerView mQueryRecy;

        public ViewHodler(View view) {
            super(view);
            mTvSeller = (TextView) view.findViewById(R.id.tv_seller);
            mTvEdit = (TextView) view.findViewById(R.id.tv_edit);
            mQueryRecy = (RecyclerView) view.findViewById(R.id.query_recy);
        }
    }


    /**
     * 内部适配器
     */

    class ChildQueryAdapter extends RecyclerView.Adapter<ChildQueryAdapter.ViewHodler> implements View.OnLongClickListener {

        List<QueryBean.DataBean.ListBean> slist;
        int price;
        boolean ischeck;
        private OkhttpManager manager;

        public ChildQueryAdapter(List<QueryBean.DataBean.ListBean> slist) {
            this.slist = slist;
        }

        @NonNull
        @Override
        public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = View.inflate(con, R.layout.childquery_item_base, null);
            v.setOnLongClickListener(this);
            ViewHodler hodler = new ViewHodler(v);
            return hodler;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
            holder.itemView.setTag(position);
            Glide.with(con).load(slist.get(position).getImages().split("\\|")[0]).into(holder.mIdIvLogo);
            holder.mTvItemsChild.setText(slist.get(position).getTitle());
            holder.mTvItemsChildDesc.setText(slist.get(position).getSubhead());
            holder.mIdTvDiscountPrice.setText("￥:" + slist.get(position).getPrice());
            holder.ui_add.setShopNum(slist.get(position).getNum());
            //得到集合中的状态值
            int ischeck = slist.get(position).getSelected();
            //通过判断给复选框设置状态
            if (ischeck == 0) {
                holder.mCheckbox.setChecked(false);
            } else {
                holder.mCheckbox.setChecked(true);
            }
            //加减按钮的点击事件 返回的是数量
            holder.ui_add.setGetDataTextView(new UI_Add.GetDataTextView() {
                @Override
                public void getTextViewDataToMun(final Integer num) {
                    int uid = con.getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
                    int pid = slist.get(position).getPid();
                    int selected = slist.get(position).getSelected();
                    int sellerid = slist.get(position).getSellerid();
                    if (mOnClickUpdateListener != null) {
                        mOnClickUpdateListener.onItemupdateClick(uid, sellerid, pid, selected, num);
                    }
                }
            });

            //复选框的点击事件
            holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean select) {
                    if (select) {
                        int uid = con.getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
                        int pid = slist.get(position).getPid();
                        int sellerid = slist.get(position).getSellerid();
                        int num = slist.get(position).getNum();
                        if (mOnClickUpdateListener != null) {
                            mOnClickUpdateListener.onItemupdateClick(uid, sellerid, pid, 1, num);
                        }
                    } else {
                        int uid = con.getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
                        int pid = slist.get(position).getPid();
                        int sellerid = slist.get(position).getSellerid();
                        int num = slist.get(position).getNum();
                        if (mOnClickUpdateListener != null) {
                            mOnClickUpdateListener.onItemupdateClick(uid, sellerid, pid, 0, num);
                        }
                    }
                }
            });
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick((Integer) v.getTag(), slist);
            }
            return false;
        }

        public class ViewHodler extends RecyclerView.ViewHolder {
            ImageView mIdIvLogo;
            TextView mTvItemsChild;
            TextView mTvItemsChildDesc;
            TextView mIdTvDiscountPrice;
            ImageView drop;
            UI_Add ui_add;
            CheckBox mCheckbox;


            public ViewHodler(View view) {
                super(view);
                mIdIvLogo = (ImageView) view.findViewById(R.id.id_iv_logo);
                mTvItemsChild = (TextView) view.findViewById(R.id.tv_items_child);
                mTvItemsChildDesc = (TextView) view.findViewById(R.id.tv_items_child_desc);
                mIdTvDiscountPrice = (TextView) view.findViewById(R.id.id_tv_discount_price);
                ui_add = (UI_Add) view.findViewById(R.id.add_sub);
                mCheckbox = (CheckBox) view.findViewById(R.id.id_cb_select_child);
                drop = (ImageView) view.findViewById(R.id.drop);

            }
        }

        @Override
        public int getItemCount() {
            return slist.size();
        }

    }

    //定义长按删除的接口
    public interface OnItemLongClickListener {
        void onItemClick(int position, List<QueryBean.DataBean.ListBean> slist);
    }

    private OnItemLongClickListener mItemClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    //更新购物车的接口
    public interface OnClickUpdateListener {
        void onItemupdateClick(int uid, int sellerid, int pid, int selected, int num);
    }

    private OnClickUpdateListener mOnClickUpdateListener;

    public void setOnUpdateClickListener(OnClickUpdateListener OnClickUpdateListener) {
        mOnClickUpdateListener = OnClickUpdateListener;
    }

}

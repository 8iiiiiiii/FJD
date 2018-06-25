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
import com.example.com.zhaoshijie.model.HotEditBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class EditDataAdapter extends RecyclerView.Adapter<EditDataAdapter.ViewHodler> {

    private Context context;
    private List<HotEditBean.DataBean> list;

    public EditDataAdapter(Context context, List<HotEditBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.product_item_base, null);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.mProductTitle.setText(list.get(position).getTitle());
        holder.mProductPrice.setText("￥" + list.get(position).getBargainPrice());
        holder.mProductNowPrice.setText("￥" + list.get(position).getPrice());
        Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(holder.mProductImage);
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        ImageView mProductImage;
        TextView mProductTitle;
        TextView mProductPrice;
        TextView mProductNowPrice;

        public ViewHodler(View view) {
            super(view);
            mProductImage = (ImageView) view.findViewById(R.id.product_image);
            mProductTitle = (TextView) view.findViewById(R.id.product_title);
            mProductPrice = (TextView) view.findViewById(R.id.product_price);
            mProductNowPrice = (TextView) view.findViewById(R.id.product_now_price);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

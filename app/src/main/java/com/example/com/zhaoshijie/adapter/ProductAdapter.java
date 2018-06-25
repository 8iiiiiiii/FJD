package com.example.com.zhaoshijie.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.model.ProductBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 * <p>
 * 商品详情的适配器
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHodler> {

    private Context con;
    private List<ProductBean.DataBean> list;

    public ProductAdapter(Context con, List<ProductBean.DataBean> list) {
        this.con = con;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(con, R.layout.product_item_base, null);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
        holder.itemView.setTag(position);
        Glide.with(con).load(list.get(position).getImages().split("\\|")[0]).into(holder.mProductImage);
        holder.mProductTitle.setText(list.get(position).getTitle());
        holder.mProductPrice.setText("￥"+(float)list.get(position).getPrice()+"0");
        holder.mProductNowPrice.setText("￥"+(float)list.get(position).getBargainPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            //设置加粗
            mProductNowPrice.getPaint().setFakeBoldText(true);
            //设置删除线
            mProductPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }


}

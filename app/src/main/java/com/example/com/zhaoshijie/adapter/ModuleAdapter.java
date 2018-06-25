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
import com.example.com.zhaoshijie.model.ModuleBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHodler> {

    private Context context;
    private List<ModuleBean.DataBean> list;

    public ModuleAdapter(Context context, List<ModuleBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHodler hodler = new ViewHodler(View.inflate(context, R.layout.module_item_base, null));
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.mModuleText.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.mModuleImage);
    }


    public class ViewHodler extends RecyclerView.ViewHolder {

        ImageView mModuleImage;
        TextView mModuleText;

        public ViewHodler(View itemView) {
            super(itemView);
            mModuleImage = (ImageView) itemView.findViewById(R.id.module_image);
            mModuleText = (TextView) itemView.findViewById(R.id.module_text);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

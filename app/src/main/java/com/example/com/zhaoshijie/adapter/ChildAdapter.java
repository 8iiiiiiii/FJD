package com.example.com.zhaoshijie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.model.ChildBean;

import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHodler>{

    private Context con;
    private List<ChildBean.DataBean> list;

    public ChildAdapter(Context con, List<ChildBean.DataBean> list) {
        this.con = con;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(con, R.layout.child_item_base, null);
        ViewHodler hodler = new ViewHodler(v);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {

        //得到一级子类的Name
        String name = list.get(position).getName();
        holder.mChildTitle.setText(name);
        //加载二级子类的数据 把二级子类的集合传过去
        ChildrenAdapter adapter = new ChildrenAdapter(list.get(position).getList());
        holder.mChildRecy.setAdapter(adapter);
        holder.mChildRecy.setLayoutManager(new GridLayoutManager(con, 2));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView mChildTitle;
        RecyclerView mChildRecy;

        public ViewHodler(View view) {
            super(view);
            mChildTitle = (TextView) view.findViewById(R.id.child_title);
            //设置加粗
            mChildTitle.getPaint().setFakeBoldText(true);
            mChildRecy = (RecyclerView) view.findViewById(R.id.child_recy);
        }
    }


  /*
  *******
  内部适配器
  *******
   */

   public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ViewHodler>{
        List<ChildBean.DataBean.ListBean> children_list;

        public ChildrenAdapter(List<ChildBean.DataBean.ListBean> children_list) {
            this.children_list = children_list;
        }

        @NonNull
        @Override
        public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = View.inflate(con, R.layout.children_item_base, null);
            ViewHodler hodler = new ViewHodler(v);
            return hodler;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHodler holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(onItemChildClickListener != null) {
                      onItemChildClickListener.onItemClick(position,children_list);
                  }
                }
            });
            holder.mChildrenName.setText(children_list.get(position).getName());
            Glide.with(con).load(children_list.get(position).getIcon()).into(holder.mChildrenImage);
        }

        @Override
        public int getItemCount() {
            return children_list.size();
        }

        public class ViewHodler extends RecyclerView.ViewHolder {
            ImageView mChildrenImage;
            TextView mChildrenName;

            public ViewHodler(View view) {
                super(view);
                mChildrenImage = (ImageView) view.findViewById(R.id.children_image);
                mChildrenName = (TextView) view.findViewById(R.id.children_name);
            }
        }

    }

    //定义接口
    private OnItemChildClickListener onItemChildClickListener;

    public interface OnItemChildClickListener {
        void onItemClick(int position,List<ChildBean.DataBean.ListBean> children_List);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

}

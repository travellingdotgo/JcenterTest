package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;



public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {
    private Context mContext;
    private List<HashMap<String, String>> mList;

    public CardviewAdapter(Context context, List<HashMap<String, String>> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        ImageLoader.getInstance().displayImage(mList.get(i).get("A"), viewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return mList == null ? 0 : mList.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.pic);
        }
    }
}
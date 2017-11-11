package com.bewant2be.doit.jcentertest.waterflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bewant2be.doit.jcentertest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓勇 on 2015/7/4 0004.
 */
public class WaterflowRecyclerAdapter extends RecyclerView.Adapter<WaterflowViewHolder> {
    private List<String> lists;
    private Context context;
    private List<Integer> heights;
    private OnItemClickListener mListener;
    public WaterflowRecyclerAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        getRandomHeight(this.lists);
    }
    private void getRandomHeight(List<String> lists){//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            heights.add((int)(200+ Math.random()*400));
        }
    }
    public interface OnItemClickListener{
        void ItemClickListener(View view, int postion);
        void ItemLongClickListener(View view, int postion);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    @Override
    public WaterflowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.waterflow_item,parent,false);
        WaterflowViewHolder viewHolder = new WaterflowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WaterflowViewHolder holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = heights.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局

        holder.mTv.setText(lists.get(position));//为控件绑定数据
        if(mListener!=null){//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                mListener.ItemClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemLongClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

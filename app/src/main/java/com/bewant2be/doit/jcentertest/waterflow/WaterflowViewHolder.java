package com.bewant2be.doit.jcentertest.waterflow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bewant2be.doit.jcentertest.R;

/**
 * Created by user on 6/13/17.
 */
public class WaterflowViewHolder extends RecyclerView.ViewHolder{
    TextView mTv;

    public WaterflowViewHolder(View itemView) {
        super(itemView);
        mTv = (TextView) itemView.findViewById(R.id.textView);
    }
}
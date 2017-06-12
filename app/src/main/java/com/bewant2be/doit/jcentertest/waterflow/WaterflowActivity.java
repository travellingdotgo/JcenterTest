package com.bewant2be.doit.jcentertest.waterflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bewant2be.doit.jcentertest.R;

import java.util.ArrayList;
import java.util.List;

public class WaterflowActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> lists;
    private WaterflowRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterflow);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration();//设置分割线
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
        adapter = new WaterflowRecyclerAdapter(this,lists);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new WaterflowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int postion) {
                Toast.makeText(WaterflowActivity.this, "点击了：" + postion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
                //长按删除
                lists.remove(postion);
                adapter.notifyItemRemoved(postion);
            }
        });
    }

    private void initData() {
        lists = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            lists.add("" + i);
        }
    }
}

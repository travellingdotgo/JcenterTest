package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.bewant2be.doit.utilslib.view.FlingListView;
import com.bewant2be.doit.utilslib.view.FlingListView.OnDeleteListener;

import java.util.ArrayList;
import java.util.List;

public class FlingListViewActivity extends AppCompatActivity {

    private FlingListView flingListView;
    private FlingListviewAdapter adapter;
    private List<String> contentList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fling_list_view);

        initContentList();
        initViews();
    }

    private void initContentList() {
        contentList.add("Content Item 1");
        contentList.add("Content Item 2");
        contentList.add("Content Item 3");
        contentList.add("Content Item 4");
        contentList.add("Content Item 5");
        contentList.add("Content Item 6");
        contentList.add("Content Item 7");
        contentList.add("Content Item 8");
        contentList.add("Content Item 9");
        contentList.add("Content Item 10");
    }

    private void initViews() {
        flingListView = (FlingListView) findViewById(R.id.flingListView);
        flingListView.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new FlingListviewAdapter(this, 0, contentList);
        flingListView.setAdapter(adapter);
    }

}

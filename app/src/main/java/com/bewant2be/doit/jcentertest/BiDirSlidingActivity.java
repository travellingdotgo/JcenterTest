package com.bewant2be.doit.jcentertest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bewant2be.doit.jcentertest.view.BidirSlidingLayout;
import com.bewant2be.doit.utilslib.ToastUtil;

public class BiDirSlidingActivity extends AppCompatActivity {

    private BidirSlidingLayout bidirSldingLayout;

    private ListView contentList;
    private ArrayAdapter<String> contentListAdapter;

    public final static int ITEMS_ARRAY_SIZE = 1000;
    private String[] contentItems = new String[ITEMS_ARRAY_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidirsliding);

        for (int i=0;i<ITEMS_ARRAY_SIZE; i++){
            contentItems[i] = "Content Item " + i;
        }

        bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.bidir_sliding_layout);
        contentList = (ListView) findViewById(R.id.contentList);

        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contentItems);
        contentList.setAdapter(contentListAdapter);
        bidirSldingLayout.setScrollEvent(contentList);

        findViewById(R.id.left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toastComptible(getApplicationContext(), "left_menu");
            }
        });
    }


}

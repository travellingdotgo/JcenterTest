package com.bewant2be.doit.jcentertest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class GridviewActivity extends Activity {

    private String[] localCartoonText = {"名侦探柯南", "死亡笔记", "火影忍者", "海贼王"};
    private String[] imgURL ={
            "http://img1.imgtn.bdimg.com/it/u=3350993068,3652800343&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2545030489,2226096219&fm=21&gp=0.jpg",
            //"http://img3.imgtn.bdimg.com/it/u=3171772449,1023293196&fm=21&gp=0.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://img0.imgtn.bdimg.com/it/u=820734872,552500686&fm=21&gp=0.jpg"};
    private GridView mGridView = null;
    private GridViewAdapter mGridViewAdapter = null;
    private ArrayList<GridItem> mGridData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        mGridView = (GridView) findViewById(R.id.gridview_cartoon);
        mGridData = new ArrayList<GridItem>();
        for (int i=0; i<imgURL.length; i++) {
            GridItem item = new GridItem();
            item.setTitle(localCartoonText[i]);
            item.setImage(imgURL[i]);
            mGridData.add(item);
        }
        mGridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, mGridData);
        mGridView.setAdapter(mGridViewAdapter);
    }
}

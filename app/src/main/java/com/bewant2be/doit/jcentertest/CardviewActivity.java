package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CardviewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardviewAdapter myAdapter;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        initImageLoader(getApplicationContext());
        getPicUrls();

        // 获取RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new CardviewAdapter(this, mList);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
    }

    private void getPicUrls() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("A", "http://img3.hao123.com/data/1_8583424a3f55c06ebeafce438a637c0d_0");
        list.add(map1);

        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("A", "http://img0.hao123.com/data/1_981ee6e65d3f13ec691804ab82f2a0ab_510");
        list.add(map2);

        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("A", "http://img3.hao123.com/data/desktop/4926b79748d1c9d4db328e1a8b7a7794_1280_800");
        list.add(map3);

        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("A", "http://img5.hao123.com/data/1_7793be4df6fd23d63ca321b205ba083b_510");
        list.add(map4);

        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("A", "http://img3.hao123.com/data/1_c46275cc6b24a480ecec31b3b5ec3c39_510");
        list.add(map5);
        mList = list;
    }

    private void initImageLoader(Context context){
        /*
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                //.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                //.diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                //.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        */
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
}
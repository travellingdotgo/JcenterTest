package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageviewActivity extends AppCompatActivity {

    ImageView imageView;
    String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    String gif = "http://p1.pstatp.com/large/166200019850062839d3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void loadImage(View view) {
        Glide.with(this)
                .load(gif)
                .placeholder(R.drawable.backblue) // optional
                .error(R.drawable.delete)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /*
        // 加载本地图片
        File file = new File(getExternalCacheDir() + "/image.jpg");
        Glide.with(this).load(file).into(imageView);

        // 加载应用资源
        int resource = R.drawable.image;
        Glide.with(this).load(resource).into(imageView);

        // 加载二进制流
        byte[] image = getImageBytes();
        Glide.with(this).load(image).into(imageView);

        // 加载Uri对象
        Uri imageUri = getImageUri();
        Glide.with(this).load(imageUri).into(imageView);
    */
}

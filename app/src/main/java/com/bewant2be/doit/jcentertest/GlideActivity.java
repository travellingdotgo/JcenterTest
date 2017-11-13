package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bewant2be.doit.jcentertest.glideissue.GlideCircleTransform;
import com.bewant2be.doit.jcentertest.glideissue.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideActivity extends AppCompatActivity {

    ImageView imageView;
    private final static String mUrl = "http://portal.zjlianhua.com/oto/file/getQRCode?url=http%3A%2F%2Ft.cn%2FR9ys6qz%3F_fcheck%3DLHJX68320285";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        imageView = (ImageView)findViewById(R.id.imageView);

        Context context = imageView.getContext();
        Glide.with(this)
                .load(mUrl)
                //.transform(new GlideRoundTransform(context)) // 圆角矩形, 验证可用
                //.transform(new GlideCircleTransform(context)) // 圆形, 验证可用
                .placeholder(R.drawable.backblue) // optional
                .error(R.drawable.delete)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


}

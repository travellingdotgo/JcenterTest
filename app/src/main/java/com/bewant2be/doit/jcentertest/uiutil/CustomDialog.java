package com.bewant2be.doit.jcentertest.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bewant2be.doit.jcentertest.R;

public class CustomDialog {

    public static Dialog createLoadingDialog(Context context, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loadview, null);

        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);

        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView tipText = (TextView) view.findViewById(R.id.tvTip);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_mg_loading);
        img.startAnimation(animation);
        tipText.setText(msg);

        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(true);  //设置返回键无效
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }
}
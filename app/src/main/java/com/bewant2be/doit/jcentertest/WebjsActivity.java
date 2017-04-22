package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.view.TitleView;

public class WebjsActivity extends AppCompatActivity {

    private TitleView mTitleView;
    private WebView mWebView;
    private TextView logTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webjs);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/wx.html");
        mWebView.addJavascriptInterface(this, "wx");
        logTextView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("javascript:actionFromNative()");
                mWebView.loadUrl("javascript:actionFromNativeWithParam(" + "'come from Native'" + ")");
            }
        });

        mTitleView = (TitleView)findViewById(R.id.titleView);
        mTitleView.setLeftButtonText("back");
    }

    @android.webkit.JavascriptInterface
    public void actionFromJs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebjsActivity.this, "js调用了Native函数", Toast.LENGTH_SHORT).show();
                String text = logTextView.getText() + "\njs调用了Native函数";
                logTextView.setText(text);
            }
        });
    }

    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebjsActivity.this, "js调用了Native函数传递参数：" + str, Toast.LENGTH_SHORT).show();
                String text = logTextView.getText() + "\njs调用了Native函数传递参数：" + str;
                logTextView.setText(text);
            }
        });

    }
}

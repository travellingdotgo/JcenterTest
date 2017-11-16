package com.bewant2be.doit.jcentertest;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

import com.bewant2be.doit.utilslib.ToastUtil;

public class TouchDelegateActivity extends AppCompatActivity {

    public final static String TAG = "TouchDelegateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_delegate);

        init();
    }

    private void init(){
        final View parent = findViewById(R.id.activity_touch_delegate);
        final View child = findViewById(R.id.btn_child);

        largerViewBounds(child, -200,-200,200,200);/* left,top,right,bottom */

        child.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String s = String.format("action=%d, x:%f,y=%f", motionEvent.getAction(), motionEvent.getX(), motionEvent.getY());
                ToastUtil.toastComptible(getApplicationContext(), s);
                Log.i(TAG, s);

                if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                    return true;
                }else{
                    return false;
                }
            }
        });
    }


    /**
     * 用于扩大View的点击范围
     *
     * @param delegateView 需要扩大点击范围的View
     * @param l          left
     * @param t          top
     * @param r          right
     * @param b          bottom
     */
    public static void largerViewBounds(final View delegateView, final int l, final int t, final int r, final int b) {
        final View parent = (View) delegateView.getParent();
        if (parent != null) {
            parent.post(new Runnable() {
                @Override
                public void run() {
                    Rect mRect = new Rect();
                    delegateView.getHitRect(mRect);
                    mRect.left += l;
                    mRect.top += t;
                    mRect.right += r;
                    mRect.bottom += b;
                    TouchDelegate mTouchDelegate = new TouchDelegate(mRect, delegateView);
                    parent.setTouchDelegate(mTouchDelegate);
                }
            });
        }
    }
}

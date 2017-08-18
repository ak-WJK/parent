package com.kzb.parents.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/********************
 * 作者：malus
 * 日期：16/12/25
 * 时间：上午1:08
 * 注释：
 ********************/
public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(400, 400);
    }
}

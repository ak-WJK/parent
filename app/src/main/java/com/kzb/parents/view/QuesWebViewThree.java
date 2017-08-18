package com.kzb.parents.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.kzb.baselibrary.log.Log;
import com.kzb.parents.util.DensityUtil;

import java.lang.reflect.Field;


/********************
 * 作者：malus
 * 日期：16/12/6
 * 时间：下午8:41
 * 注释：使用html的textView
 ********************/


public class QuesWebViewThree extends WebView {
    double mHeight;
    private float mRate = 0.65f;
    public QuesWebViewThree(Context context) {
        super(context);
        setCommonWebView(this);
    }

    public QuesWebViewThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCommonWebView(this);
//        setBackgroundColor(Color.parseColor("#0000ff"));
    }

    public QuesWebViewThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCommonWebView(this);
    }


    public void loadData(String data) {
        data = ansData(data);

        if (!data.contains("http://t.kaozhibao.com/Public/ewebeditor")) {
            data = data.replace("/Public/ewebeditor", "http://t.kaozhibao.com/Public/ewebeditor");
        }
        loadData(data,"text/html; charset=UTF-8", null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(DensityUtil.dip2px(getContext(), (float) (mHeight+15)), MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec,expandSpec);
    }

    private String ansData(String ans) {
        int index = 0;
        mHeight = 0;
        while ((index = ans.indexOf("style=", index)) > 0) {
            index++;
            int startIndex = ans.indexOf("\"", index);
            int stopIndex = ans.indexOf("\"", startIndex + 1);
            String style = ans.substring(startIndex + 1, stopIndex);
            if (style.contains("width") && style.contains("height")&&startIndex>0&&stopIndex>startIndex) {
                String width = style.split("width")[1];
                width = width.split(";")[0];
                width = width.replaceAll(":", "");
                width = width.replaceAll(" ", "");
                width = width.replaceAll("px", "");


                String height = style.split("height")[1];
                height = height.split(";")[0];
                height = height.replaceAll(":", "");
                height = height.replaceAll(" ", "");
                height = height.replaceAll("px", "");


                try {
                    double w = Double.parseDouble(width);
                    double h = Double.parseDouble(height);

                    Log.e("kaoshi","get:w="+w+";h="+h);

                    w *= mRate;
                    h *= mRate;

                    Log.e("kaoshi","after:w="+w+";h="+h);

                    ans = ans.replaceAll("width:"+width,"width:"+(int)w);
                    ans = ans.replaceAll("height:"+height,"height:"+(int)h);

                    ans = ans.replaceAll("width: "+width,"width:"+(int)w);
                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);

                    ans = ans.replaceAll("width :"+width,"width:"+(int)w);
                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);

                    mHeight += h;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        index = 0;
        while ((index = ans.indexOf("<br>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }
        index = 0;
        while ((index = ans.indexOf("<br/>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }
        index = 0;
        while ((index = ans.indexOf("<p>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }

        Log.e("kaoshi","after:ans="+ans);

//        Log.e("kaoshi","mheight"+mHeight);
        return ans;
    }

    public static void setCommonWebView(WebView webView) {

//        if (webView != null) {
//            WebSettings webSettings = webView.getSettings();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                // 用于判断是否为Android 3.0系统,
//                // 然后隐藏缩放控件
//                webSettings.setDisplayZoomControls(false);
//            } else {
//                setZoomControlGone(webView);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                webSettings.setAllowFileAccessFromFileURLs(true);
//                webSettings.setAllowUniversalAccessFromFileURLs(true);
//            }
//            webSettings.setAllowContentAccess(true);
//            webSettings.setAppCacheEnabled(true);
//            webView.setVerticalScrollBarEnabled(false);
//            webSettings.setDefaultTextEncodingName("utf-8");
//            webSettings.setJavaScriptEnabled(true);
//            webSettings
//                    .setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setUseWideViewPort(true);
//            webSettings.setBuiltInZoomControls(false);
//            webSettings.setSupportZoom(false);
//            webSettings.setPluginState(WebSettings.PluginState.ON);
//            webSettings.setDomStorageEnabled(true);
//            webSettings.setDatabaseEnabled(true);
//            webSettings.setAllowFileAccess(true);
//            webSettings.setDefaultFontSize(16);
////			webSettings.setCacheMode(
////					WebSettings.LOAD_CACHE_ELSE_NETWORK);
//            webSettings
//                    .setJavaScriptCanOpenWindowsAutomatically(true);
//            webSettings.setLoadsImagesAutomatically(true);
//
//            // webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//            int SDK_INT = Build.VERSION.SDK_INT;
//            if (SDK_INT > 16) {
//                webSettings
//                        .setMediaPlaybackRequiresUserGesture(false);
//            }
//
//        }
    }

    // Android 3.0(11) 以下使用以下方法:
    // 利用java的反射机制
    private static void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
                    view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}


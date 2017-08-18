package com.kzb.parents.common;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;


/**
 * Created by wanghaofei on 17/6/8.
 */

public class ArticeDetailActivity extends BaseActivity {


    private TextView titleLeft, titleCenter;
    private WebView webView;

    private String id;

    private String url;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        id = getIntent().getStringExtra("id");

//        Toast.makeText(ArticeDetailActivity.this,"id="+id,Toast.LENGTH_SHORT).show();
        initView();
        initData();
    }


    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        webView = getView(R.id.artice_detail_view);


        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setDomStorageEnabled(true);





//        webView.getSettings().setJavaScriptEnabled(true);//设置支持js


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(ArticeDetailActivity.this);
            }
        });


    }

    @Override
    protected void initData() {

        url = AddressConfig.ARTICLE_URL + id + ".html";

        LogUtils.e("TAG", "URL==" + url);



        webView.setWebChromeClient(new WebChromeClient());


        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error

            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                titleCenter.setText(view.getTitle());
                return true;
            }
        });

        webView.loadUrl(url);
    }


}


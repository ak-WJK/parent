package com.kzb.parents.xunlian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.ZhenDuiXunLianActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.zhishidiandemo.WeiZhangWoZhiShiDianActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/5/26.
 */

public class XunLianKaoShiActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private TextView zhangjie, quanke;

    private TextView TVchuti;
    private RelativeLayout zhiShiDianLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunlian_kaoshi);
        httpConfig = new HttpConfig();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        zhangjie = getView(R.id.xunlian_zhangjie);
        quanke = getView(R.id.xunlian_quanke);
        TVchuti = getView(R.id.xunlian_chuti);
        zhiShiDianLayout = getView(R.id.rl_zhishidian);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        zhiShiDianLayout.setOnClickListener(this);
        titleCenter.setText("针对训练");

        zhangjie.setOnClickListener(this);
        quanke.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(XunLianKaoShiActivity.this);

                break;
            case R.id.xunlian_zhangjie:
                Map<String, String> val = new HashMap<String, String>();
                val.put("type", "zhangjie");
                IntentUtil.startActivity(XunLianKaoShiActivity.this, ZhenDuiXunLianActivity.class, val);
                break;
            case R.id.xunlian_quanke:
                Map<String, String> valM = new HashMap<String, String>();
                valM.put("type", "quanke");
                IntentUtil.startActivity(XunLianKaoShiActivity.this, ZhenDuiXunLianActivity.class, valM);
                break;
            case R.id.xunlian_chuti:

                IntentUtil.startActivity(XunLianKaoShiActivity.this, ZhenDuiXunLianActivity.class);
                break;

            case R.id.rl_zhishidian:

                IntentUtil.startActivity(XunLianKaoShiActivity.this, WeiZhangWoZhiShiDianActivity.class);
                break;


        }
    }
}

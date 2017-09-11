package com.kzb.parents.zhishidiandemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

public class WeiZhangWoZhiShiDianActivity extends BaseActivity implements View.OnClickListener {


    private TextView stView, unView;
    private TextView titleLeft, titleCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_zhang_wo_zhi_shi_dian);
        initView();
        initData();

    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        stView = getView(R.id.tv_yizhangwo);
        unView = getView(R.id.tv_un_zhishidian);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("强化提高");
        stView.setOnClickListener(this);
        unView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(this);
                break;
            case R.id.tv_yizhangwo:
                Map<String, String> mapVal1 = new HashMap<String, String>();
                mapVal1.put("type", "2");
                IntentUtil.startActivity(this, StrengthListActivity2.class, mapVal1);
                break;
            case R.id.tv_un_zhishidian:
                Map<String, String> mapVal2 = new HashMap<String, String>();
                mapVal2.put("type", "1");
                IntentUtil.startActivity(this, StrengthListActivity2.class, mapVal2);
                break;
        }
    }
}

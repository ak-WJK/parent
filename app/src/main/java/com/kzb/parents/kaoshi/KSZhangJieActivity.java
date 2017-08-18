package com.kzb.parents.kaoshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/6/1.
 */

public class KSZhangJieActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private TextView comView, noComView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshi_zhangjie);
        httpConfig = new HttpConfig();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        comView = getView(R.id.kaoshi_zhangjie_com);
        noComView = getView(R.id.kaoshi_zhangjie_no_com);



        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("章节考试");
        comView.setOnClickListener(this);
        noComView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.kaoshi_zhangjie_com:
               Map<String,String> mapV = new HashMap<>();
               mapV.put("position","1");
                IntentUtil.startActivity(KSZhangJieActivity.this, ZhangJieListActivity.class,mapV);
                break;
            case R.id.common_head_left:
                IntentUtil.finish(KSZhangJieActivity.this);

                break;
            case R.id.kaoshi_zhangjie_no_com:
                Map<String,String> mapV1 = new HashMap<>();
                mapV1.put("position","0");
                IntentUtil.startActivity(KSZhangJieActivity.this, ZhangJieListActivity.class,mapV1);
                break;
        }
    }
}

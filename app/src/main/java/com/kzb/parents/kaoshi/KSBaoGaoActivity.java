package com.kzb.parents.kaoshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/6/1.
 */

public class KSBaoGaoActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private TextView zhangjie, quanke;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshi_baogg);
        httpConfig = new HttpConfig();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        zhangjie = getView(R.id.kaoshi_baogao_zhangjie);
        quanke = getView(R.id.kaoshi_baogao_quanke);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("考试报告");
        zhangjie.setOnClickListener(this);
        quanke.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kaoshi_baogao_zhangjie:
                IntentUtil.startActivity(KSBaoGaoActivity.this, BGZJListActivity.class);
                break;
            case R.id.common_head_left:
                IntentUtil.finish(KSBaoGaoActivity.this);

                break;
            case R.id.kaoshi_baogao_quanke:

                IntentUtil.startActivity(KSBaoGaoActivity.this, BGQKListActivity.class);
                break;
        }
    }
}

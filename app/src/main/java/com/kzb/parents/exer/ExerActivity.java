package com.kzb.parents.exer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

public class ExerActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private TextView exercomView, exernoComView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer);
        httpConfig = new HttpConfig();
        initView();
        initData();

    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        exercomView = getView(R.id.exer_no_com);
        exernoComView = getView(R.id.exer_com);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("作业");
        exercomView.setOnClickListener(this);
        exernoComView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exer_no_com:
                Map<String, String> mapV = new HashMap<>();
                mapV.put("position", "0");
                IntentUtil.startActivity(ExerActivity.this, ExerListActivity.class, mapV);
                break;
            case R.id.common_head_left:
                IntentUtil.finish(ExerActivity.this);

                break;
            case R.id.exer_com:
                Map<String, String> map1 = new HashMap<>();
                map1.put("position", "1");
                IntentUtil.startActivity(ExerActivity.this, ExerListActivity.class, map1);
                break;
        }
    }


}

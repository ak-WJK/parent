package com.kzb.parents.strengthen;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.course.StrengthListActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/4/1.
 *
 * 已(未)强化页面
 */

public class StrengthenActivity extends BaseActivity implements View.OnClickListener{

    private TextView stView,unView;
    private TextView titleLeft, titleCenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_str);
        LogUtils.e("TAG", "已强化未强化页面");
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        stView = getView(R.id.stg_view);
        unView = getView(R.id.stg_un_view);

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
                IntentUtil.finish(StrengthenActivity.this);
                break;
            case R.id.stg_view:
                Map<String, String> mapVal1 = new HashMap<String, String>();
                mapVal1.put("type", "2");
                IntentUtil.startActivity(StrengthenActivity.this, StrengthListActivity.class, mapVal1);
                break;
            case R.id.stg_un_view:
                Map<String, String> mapVal2 = new HashMap<String, String>();
                mapVal2.put("type", "1");
                IntentUtil.startActivity(StrengthenActivity.this, StrengthListActivity.class, mapVal2);
                break;
        }
    }
}

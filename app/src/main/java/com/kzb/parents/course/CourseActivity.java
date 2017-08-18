package com.kzb.parents.course;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/3/30.
 * (已)未学习页面
 */

public class CourseActivity extends BaseActivity implements View.OnClickListener{

    private TextView learnView,unLearnView;
    private TextView titleLeft, titleCenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur);

        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        learnView = getView(R.id.cur_learn_view);
        unLearnView = getView(R.id.cur_un_learn_view);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("课程学习");
        learnView.setOnClickListener(this);
        unLearnView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(CourseActivity.this);

                break;
            case R.id.cur_learn_view:
                Map<String, String> mapVal1 = new HashMap<String, String>();
                mapVal1.put("type", "2");
                IntentUtil.startActivity(CourseActivity.this, CourseListActivity.class, mapVal1);
                break;
            case R.id.cur_un_learn_view:
                Map<String, String> mapVal2 = new HashMap<String, String>();
                mapVal2.put("type", "1");
                IntentUtil.startActivity(CourseActivity.this, CourseListActivity.class, mapVal2);
                break;
        }
    }
}

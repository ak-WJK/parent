package com.kzb.parents.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.strengthen.StrengthenActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/3/30.
 */

public class CourseMainActivity extends BaseActivity implements View.OnClickListener{

    private TextView xuexiCourse,xuexiJindu;
    private TextView titleLeft, titleCenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_main);

        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        xuexiCourse = getView(R.id.xuexi_kecheng);
        xuexiJindu = getView(R.id.xuexi_tigao);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("学习");
        xuexiCourse.setOnClickListener(this);
        xuexiJindu.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(CourseMainActivity.this);

                break;
            case R.id.xuexi_kecheng:
                IntentUtil.startActivity(CourseMainActivity.this, CourseActivity.class);
                break;
            case R.id.xuexi_tigao:
                IntentUtil.startActivity(CourseMainActivity.this, StrengthenActivity.class);
                break;
        }
    }
}

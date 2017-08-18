package com.kzb.parents.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/4/1.
 */

public class ExamActivity extends BaseActivity implements View.OnClickListener{

    private TextView titleLeft, titleCenter;
    private TextView zjieView,qKeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        zjieView = getView(R.id.exam_zhangjie);
        qKeView = getView(R.id.exam_quanke);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("考试报告");
        zjieView.setOnClickListener(this);
        qKeView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(ExamActivity.this);
                break;
            case R.id.exam_quanke:
                IntentUtil.startActivity(ExamActivity.this, ExamQuanKeActivity.class);
                break;
            case R.id.exam_zhangjie:
                IntentUtil.startActivity(ExamActivity.this, ExamZhangJieActivity.class);

                break;
        }
    }
}

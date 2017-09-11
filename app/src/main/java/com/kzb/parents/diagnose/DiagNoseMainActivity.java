package com.kzb.parents.diagnose;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.exam.ExamActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.report.DiagnoseActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/5/24.
 *诊断页面
 */

public class DiagNoseMainActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private TextView zjieView, qKeView, zTiView,zBaogao;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_main);
        httpConfig = new HttpConfig();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        zjieView = getView(R.id.dg_zhangjie);
        qKeView = getView(R.id.dg_quanke);
        zTiView = getView(R.id.dg_zhenti);
        zBaogao = getView(R.id.dg_baogao);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("诊断");
        zjieView.setOnClickListener(this);
        qKeView.setOnClickListener(this);
        zTiView.setOnClickListener(this);
        zBaogao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dg_baogao:
                IntentUtil.startActivity(DiagNoseMainActivity.this, DiagnoseActivity.class);
                break;
            case R.id.common_head_left:
                IntentUtil.finish(DiagNoseMainActivity.this);

                break;
            case R.id.dg_zhangjie:

                IntentUtil.startActivity(DiagNoseMainActivity.this, DNZhangJieActivity.class);
                break;
            case R.id.dg_quanke:

               Intent intent = new Intent(DiagNoseMainActivity.this, ExamActivity.class);
                intent.putExtra("type", "quanke");
                intent.putExtra("from", "quanke");
                IntentUtil.startActivity(DiagNoseMainActivity.this, intent);
                break;
            case R.id.dg_zhenti:
                IntentUtil.startActivity(DiagNoseMainActivity.this, DNZhenTiActivity.class);

                break;
        }
    }
}

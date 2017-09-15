//package com.kzb.parents.kaoshi;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.TextView;
//
//import com.kzb.parents.R;
//import com.kzb.parents.base.BaseActivity;
//import com.kzb.parents.http.HttpConfig;
//import com.kzb.parents.util.IntentUtil;
//
///**
// * Created by wanghaofei on 17/5/26.
// */

//public class KaoShiActivity extends BaseActivity implements View.OnClickListener {

//    private TextView titleLeft, titleCenter;
//    private TextView qKeView, zBaogao;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_kaoshi);
//        httpConfig = new HttpConfig();
//        initView();
//        initData();
//    }
//
//    @Override
//    protected void initView() {
//
//        titleLeft = getView(R.id.common_head_left);
//        titleCenter = getView(R.id.common_head_center);
//
//        qKeView = getView(R.id.kaoshi_quanke);
//
//        titleLeft.setText("返回");
//        titleLeft.setVisibility(View.VISIBLE);
//        titleLeft.setOnClickListener(this);
//
//        titleCenter.setText("考试");
//
//        qKeView.setOnClickListener(this);
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.common_head_left:
//                IntentUtil.finish(KaoShiActivity.this);
//
//                break;
////
//            case R.id.kaoshi_quanke:
//
//                IntentUtil.startActivity(KaoShiActivity.this, KSQuanKeActivity.class);
//                break;
//
//        }
//    }
//}
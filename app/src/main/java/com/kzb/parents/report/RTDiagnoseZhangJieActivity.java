package com.kzb.parents.report;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kzb.baselibrary.log.Log;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.report.adapter.DiagnoseZhangJieAdapter;
import com.kzb.parents.report.model.ReportZjListPro;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/4/9.
 */

public class RTDiagnoseZhangJieActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleLeft, titleCenter;
    private ExpandableListView expandableListView;
    DiagnoseZhangJieAdapter diagnoseZhangJieAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_zhangjie);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(RTDiagnoseZhangJieActivity.this);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        expandableListView = getView(R.id.list_view_expand);
        expandableListView.setGroupIndicator(null);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("章节诊断报告");
    }

    @Override
    protected void initData() {
        getZjRecord(AddressConfig.GET_CHART_RECORD);
    }

    public void getZjRecord(String url) {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", SpSetting.loadLoginInfo().getUid());
            json.put("subject_id", SpSetting.loadLoginInfo().getSubject_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<ReportZjListPro>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
            }

            @Override
            public void onResponse(ReportZjListPro response, int id) {

                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }

                if (response.errorCode == 0) {
                    if (response.getContent() != null) {
                        diagnoseZhangJieAdapter = new DiagnoseZhangJieAdapter(RTDiagnoseZhangJieActivity.this, response.getContent());
                        expandableListView.setAdapter(diagnoseZhangJieAdapter);
                        Log.e("kzb", response.getContent().toString());
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(RTDiagnoseZhangJieActivity.this);
                break;
        }
    }
}

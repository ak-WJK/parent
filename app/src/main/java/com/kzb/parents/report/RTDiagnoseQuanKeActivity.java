package com.kzb.parents.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.report.adapter.DiagnoseQuanKeAdapter;
import com.kzb.parents.report.model.ReportListItem;
import com.kzb.parents.report.model.ReportListPro;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/4/9.
 * 报告-->自我诊断报告页面.
 */

public class RTDiagnoseQuanKeActivity extends BaseActivity implements View.OnClickListener{

    private TextView titleLeft, titleCenter;
    private ListView listView;
    List<ReportListItem> mData = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_quanke);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(RTDiagnoseQuanKeActivity.this);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.list_view);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("全科诊断报告");
    }

    @Override
    protected void initData() {
        getRecord(AddressConfig.GET_QUANKE_RECORD);
    }


    public void getRecord(String url) {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", SpSetting.loadLoginInfo().getUid());
            json.put("subject_id",SpSetting.loadLoginInfo().getSubject_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<ReportListPro>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
            }

            @Override
            public void onResponse(ReportListPro response, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
                if (response.errorCode == 0) {
                    if (response.getContent() != null) {
                        DiagnoseQuanKeAdapter reportListAdapter = new DiagnoseQuanKeAdapter(RTDiagnoseQuanKeActivity.this);
                        reportListAdapter.addItems(response.getContent());
                        listView.setAdapter(reportListAdapter);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(RTDiagnoseQuanKeActivity.this);
                break;
        }
    }
}

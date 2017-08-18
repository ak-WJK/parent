package com.kzb.parents.kaoshi;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.adapter.BGZJAdapter;
import com.kzb.parents.kaoshi.model.ReportZjListPro;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class BGZJListActivity extends BaseActivity {

    private TextView titleLeft, titleCenter;
    ExpandableListView expandableListView;
    BGZJAdapter bgzjAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshi_bg_zhangjie_list);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(BGZJListActivity.this);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        expandableListView = getView(R.id.bg_list_view_expand);
        expandableListView.setGroupIndicator(null);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(BGZJListActivity.this);
            }
        });

        titleCenter.setText("章节考试报告");

    }

    @Override
    protected void initData() {
        getZJExam(AddressConfig.ZJ_REPORT_LIST);
    }


    /**
     * 获取完成情况
     */
    public void getZJExam(String url) {
        //显示加载视图
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
                        bgzjAdapter = new BGZJAdapter(BGZJListActivity.this, response.getContent());
                        expandableListView.setAdapter(bgzjAdapter);
                    }
                }
            }
        });
    }
}

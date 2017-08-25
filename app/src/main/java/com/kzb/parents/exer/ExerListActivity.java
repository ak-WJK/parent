package com.kzb.parents.exer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
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
import com.kzb.parents.kaoshi.adapter.ZhangJieFinAdapter;
import com.kzb.parents.kaoshi.model.KSZhangJieResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import okhttp3.Call;

public class ExerListActivity extends BaseActivity {

    private TextView titleLeft, titleCenter;
    private ListView listView;
    ZhangJieFinAdapter zhangJieFinAdapter;
    String mPosition = "0";
    private boolean paush = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_list);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(ExerListActivity.this);
        mPosition = getIntent().getStringExtra("position");
        initView();
        initData();

    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.exer_list_view);

        zhangJieFinAdapter = new ZhangJieFinAdapter(ExerListActivity.this, "quanke");

        listView.setAdapter(zhangJieFinAdapter);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(ExerListActivity.this);
            }
        });

        titleCenter.setText("作业");

    }

    //通过mPosition确定是否完成考试
    @Override
    protected void initData() {
        if (mPosition.equals("0")) {
            zhangJieFinAdapter.setType(0);
            //未完成
            getZJExam(AddressConfig.EXAM_QK_UN_FINISH);
        } else {
            zhangJieFinAdapter.setType(1);
            //已完成
            getZJExam(AddressConfig.EXAM_QK_FINISH);
        }
    }


    /**
     * 获取完成情况
     */
    public void getZJExam(String url) {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", SpSetting.loadLoginInfo().getUid());
            json.put("subject_id", SpSetting.loadLoginInfo().getSubject_id());
            json.put("ispaper", "6");
            if (mPosition.equals("0")) {
                json.put("grade_id", SpSetting.loadLoginInfo().getGrade_id());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<KSZhangJieResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }

            }

            @Override
            public void onResponse(KSZhangJieResponse response, int id) {

                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
                if (response.errorCode == 0) {
                    if (response.getContent() != null) {

                        List<KSZhangJieResponse.ZhangJieModel> zhangJieModels = response.getContent();
                        //实现数据的倒序排列
                        Collections.reverse(zhangJieModels);
                        LogUtils.e("TAG", "zhangjieModles == " + zhangJieModels.toString());
                        zhangJieFinAdapter.setItems(response.getContent());
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mHomeKeyListener, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        paush = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (paush) {
            if (mPosition.equals("0")) {
                finish();
            }
        }

        unregisterReceiver(mHomeKeyListener);

    }

    BroadcastReceiver mHomeKeyListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String HOME_REASON = "reason";
            String HOME_KEY = "homekey";
            String HOME_LONG = "recentapps";

            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(HOME_REASON);
                if (TextUtils.equals(reason, HOME_KEY)) {
                    paush = false;
                } else {

                }

            }


        }
    };


}

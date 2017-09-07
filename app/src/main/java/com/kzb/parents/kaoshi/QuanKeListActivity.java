package com.kzb.parents.kaoshi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/1.
 */

public class QuanKeListActivity extends BaseActivity {

    private TextView titleLeft, titleCenter;
    private ListView listView;
    ZhangJieFinAdapter zhangJieFinAdapter;
    String mPosition = "0";

    private boolean paush = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshi_zhangjie_list);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(QuanKeListActivity.this);
        mPosition = getIntent().getStringExtra("position");
        initView();
        initData();


    }


    /**
     * 监听是否点击了home键将客户端推到后台
     */
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason"; //固定字段
        String SYSTEM_HOME_KEY = "homekey";//"reason"得到value为"homekey"
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

                String reason = intent.getStringExtra(SYSTEM_REASON);

                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台

                    paush = false;
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };


    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.zj_exam_list_view);

        zhangJieFinAdapter = new ZhangJieFinAdapter(QuanKeListActivity.this, "quanke");
        listView.setAdapter(zhangJieFinAdapter);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(QuanKeListActivity.this);
            }
        });

        titleCenter.setText("全科考试");

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
            json.put("ispaper", "2");
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
//                        if (mPosition.equals("1")) {
//                            Collections.reverse(zhangJieModels);
//                        }
                        zhangJieFinAdapter.setItems(response.getContent());
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        paush = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        LogUtils.e("TAG", "paush == " + paush);

        if (paush) {
            if (mPosition.equals("0")) {
                finish();
            }
        }
        unregisterReceiver(mHomeKeyEventReceiver);
    }


}

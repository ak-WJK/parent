package com.kzb.parents.kaoshi;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.kzb.parents.kaoshi.adapter.ZhenDuiXunLianAdapter;
import com.kzb.parents.kaoshi.model.ZhenDuiXunLianResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class ZhenDuiXunLianActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ListView xunLianView;

    private String type;

    ZhenDuiXunLianAdapter zhenDuiXunLianAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhenduixunlian);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(ZhenDuiXunLianActivity.this);
        type = getIntent().getStringExtra("type");
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        xunLianView = getView(R.id.zhendui_xunlian_listview);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        zhenDuiXunLianAdapter = new ZhenDuiXunLianAdapter(ZhenDuiXunLianActivity.this);
        xunLianView.setAdapter(zhenDuiXunLianAdapter);
        zhenDuiXunLianAdapter.setmType(type);
        if (type.equals("zhangjie")) {
            titleCenter.setText("章节训练");

        } else {
            titleCenter.setText("全科训练");
        }


    }

    @Override
    protected void initData() {
        getQuesList();
    }


    /**
     * 获取试题
     */
    public void getQuesList() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", SpSetting.loadLoginInfo().getUid());
            json.put("type", type);//type值为:zhangjie,quanke
            json.put("kemu_id", SpSetting.loadLoginInfo().getSubject_id());
            json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            json.put("schsystem_id",SpSetting.loadLoginInfo().getSchsystemid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.EXAM_ZJ_ZHENDUI, json), new GenericsCallback<ZhenDuiXunLianResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
            }

            @Override
            public void onResponse(ZhenDuiXunLianResponse response, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
                if (response.errorCode == 0) {
                    if (response.getContent() != null) {
                        zhenDuiXunLianAdapter.setItems(response.getContent());
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(ZhenDuiXunLianActivity.this);
                break;
        }
    }
}
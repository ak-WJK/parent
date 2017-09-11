package com.kzb.parents.diagnose;

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
import com.kzb.parents.diagnose.adapter.DNZJAdapter;
import com.kzb.parents.diagnose.model.Diagnose;
import com.kzb.parents.diagnose.model.DiagnoseItem;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/5/29.
 * 章节诊断list
 */

public class DNZhangJieActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ListView listView;

    List<DiagnoseItem> mDatas ;
    DNZJAdapter dnzjAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_zhangjie_kaoshi);
        dialogView = new DialogView(DNZhangJieActivity.this);
        httpConfig = new HttpConfig();
        mDatas = new ArrayList<>();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.diagnose_zhangjie_kaoshi_listview);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("章节诊断");

        dnzjAdapter = new DNZJAdapter(DNZhangJieActivity.this);
        listView.setAdapter(dnzjAdapter);

    }

    @Override
    protected void initData() {
        getCharterDiagnoseList();
    }



    /**
     * 获取列表
     */
    public void getCharterDiagnoseList() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", SpSetting.loadLoginInfo().getSubject_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.DIAGNOSE_CHAPTER_LIST, json), new GenericsCallback<Diagnose>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(Diagnose response, int id) {
                dialogView.handleDialog(false);
                if (response.getContent() != null) {
                    dnzjAdapter.setItems(response.getContent());
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(DNZhangJieActivity.this);
                break;
        }
    }
}
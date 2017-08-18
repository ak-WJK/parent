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
import com.kzb.parents.diagnose.adapter.DNZTAdapter;
import com.kzb.parents.diagnose.model.ZhentiDiagnose;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.report.RTDiagnoseZhangJieActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class DNZhenTiActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ListView listView;

    private String type;

    DNZTAdapter dnztAdapter;
    //当前省份ID
    private String currentYearId, currentProId,currentCityId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_zhangjie_kaoshi);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(DNZhenTiActivity.this);
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
        titleCenter.setText("真题诊断");

        dnztAdapter = new DNZTAdapter(DNZhenTiActivity.this);
        listView.setAdapter(dnztAdapter);
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
            json.put("uid", SpSetting.loadLoginInfo().getUid());
            json.put("year_id", currentYearId);
            json.put("area_id", currentProId);
            json.put("city_id", currentCityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.DIAGNOSE_QUANKE_LIST, json), new GenericsCallback<ZhentiDiagnose>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(ZhentiDiagnose response, int id) {
                dialogView.handleDialog(false);

                if (response.getContent() != null) {
                    dnztAdapter.setItems(response.getContent());
                }

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(DNZhenTiActivity.this);
                break;
        }
    }
}
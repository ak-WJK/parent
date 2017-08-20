package com.kzb.parents.diagnose;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.kzb.baselibrary.log.Log;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.BGQKListActivity;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.report.RTDiagnoseQuanKeActivity;
import com.kzb.parents.report.RTDiagnoseZhangJieActivity;
import com.kzb.parents.report.model.NumberResponse;
import com.kzb.parents.util.IntentUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/7/3.
 * 报告-->自我诊断报告页面
 */

public class DiagnoseTwoActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    //自我诊断报告
    private TextView zjieView, qKeView;
//    private TextView zjieView, qKeView, zTiView;
    //学校考试报告
    private TextView  qKeViewTwo;
//    private TextView zjieViewTwo, qKeViewTwo;

//    private TextView zjieNum, qKeNum, zTiNum;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_two);
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
//        zTiView = getView(R.id.dg_zhenti);

//        zjieViewTwo = getView(R.id.dg_sys_zhangjie);
        qKeViewTwo = getView(R.id.dg_sys_quanke);


//        zjieNum = getView(R.id.dg_zhangjie_num);
//        qKeNum = getView(R.id.dg_quanke_num);
//        zTiNum = getView(R.id.dg_zhenti_num);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("诊断报告");
        zjieView.setOnClickListener(this);
        qKeView.setOnClickListener(this);
//        zTiView.setOnClickListener(this);
//        zjieViewTwo.setOnClickListener(this);
        qKeViewTwo.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        getData();
    }


    private void getData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.BAOGAO_TOTAL_NUM);

        Map<String, String> map = new HashMap<>();

        if (null == SpSetting.loadLoginInfo() || null == SpSetting.loadLoginInfo().getUid()) {
            IntentUtil.startActivity(DiagnoseTwoActivity.this, LoginTypeActivity.class);
            return;
        }

        JSONObject object = new JSONObject();
        try {
            Log.e("wang", "uid=" + SpSetting.loadLoginInfo().getUid());
            object.put("uid", SpSetting.loadLoginInfo().getUid());
            object.put("id", SpSetting.loadLoginInfo().getSubject_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<NumberResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(NumberResponse response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.getContent() != null) {
                    setNumVals(response.getContent());

                } else {
                }

            }
        });
    }


    private void setNumVals(NumberResponse.NumberModel numVal) {
//        zjieNum.setText("(" + numVal.getZhangjie() + ")");
//        qKeNum.setText("(" + numVal.getQunake() + ")");
//        zTiNum.setText("(" + numVal.getZhenti() + ")");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dg_sys_quanke:

                IntentUtil.startActivity(DiagnoseTwoActivity.this, BGQKListActivity.class);
                break;

//            case R.id.dg_sys_zhangjie:
//                IntentUtil.startActivity(DiagnoseTwoActivity.this, BGZJListActivity.class);
//
//                break;
            case R.id.common_head_left:
                IntentUtil.finish(DiagnoseTwoActivity.this);

                break;
            case R.id.dg_zhangjie:

                IntentUtil.startActivity(DiagnoseTwoActivity.this, RTDiagnoseZhangJieActivity.class);
                break;
            case R.id.dg_quanke:

                IntentUtil.startActivity(DiagnoseTwoActivity.this, RTDiagnoseQuanKeActivity.class);
                break;
//            case R.id.dg_zhenti:
//                IntentUtil.startActivity(DiagnoseTwoActivity.this, RTDiagnoseZhenTiActivity.class);
//
//                break;
        }
    }
}

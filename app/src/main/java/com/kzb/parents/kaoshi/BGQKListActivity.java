package com.kzb.parents.kaoshi;

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
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.diagnose.WTDiagNoseDetailActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.adapter.BGQKAdapter;
import com.kzb.parents.kaoshi.model.KSReportQKResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/2.
 * 报告List页面
 */

public class BGQKListActivity extends BaseActivity {

    private TextView titleLeft, titleCenter;
    private ListView listView;
    BGQKAdapter bgqkAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("TAG", "全科报告List");
        setContentView(R.layout.activity_kaoshi_zhangjie_list);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(BGQKListActivity.this);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.zj_exam_list_view);

        bgqkAdapter = new BGQKAdapter(BGQKListActivity.this);
        listView.setAdapter(bgqkAdapter);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(BGQKListActivity.this);
            }
        });

        titleCenter.setText("全科考试报告");
    }

    @Override
    protected void initData() {
        getZJExam(AddressConfig.QK_REPORT_LIST);

        bgqkAdapter.setOnBtnClickListener(new BGQKAdapter.onBtnClickListener() {
            @Override
            public void onBtnClick(View view, String typeName, KSReportQKResponse.ReportQKModel model) {

                String ispaper = model.getIspaper();
                int ispaper1 = Integer.parseInt(ispaper);

                LogUtils.e("TAG", "ispaper  == " + ispaper);

                if (ispaper1 != 5) {

//                    Toast.makeText(BGQKListActivity.this, ispaper, Toast.LENGTH_SHORT).show();

                    Map<String, String> mapVal = new HashMap<String, String>();
                    mapVal.put("test_id", model.getTest_id());
                    mapVal.put("score", model.getScore());
                    mapVal.put("from", "kaoshi");
                    IntentUtil.startActivity(BGQKListActivity.this, DiagNoseDetailActivity.class, mapVal);
                } else {

//                    Toast.makeText(BGQKListActivity.this, ispaper, Toast.LENGTH_SHORT).show();

                    Map<String, String> mapVal = new HashMap<String, String>();
                    mapVal.put("test_id", model.getTest_id());
                    mapVal.put("score", model.getScore());
                    mapVal.put("from", "kaoshi");
                    IntentUtil.startActivity(BGQKListActivity.this, WTDiagNoseDetailActivity.class, mapVal);


                }
            }
        });


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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<KSReportQKResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }

            }

            @Override
            public void onResponse(KSReportQKResponse response, int id) {

                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
                if (response.errorCode == 0) {
                    if (response.getContent() != null) {

                        List<KSReportQKResponse.ReportQKModel> content = response.getContent();

                        //实现倒序排列
//                        Collections.reverse(response.getContent());
                        bgqkAdapter.setItems(response.getContent());

                    }
                }
            }
        });

    }


}

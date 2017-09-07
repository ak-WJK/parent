package com.kzb.parents.course;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.diagnose.model.ExplainPro;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.wrong.adapter.QuesAdapter;
import com.kzb.parents.wrong.model.ExplainContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by wanghaofei on 17/1/20.
 */

public class ReportFinishActivity extends BaseActivity {


    private String testId = "";
    private String name = "";
    private String type;

    private TextView titleLeft, titleCenter;

    private ListView mQuesListView;
    private List<ExplainContent> mQuesData = new ArrayList<>();
    private QuesAdapter mQuesAdapter;

    View convertView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_wancheng);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        testId = getIntent().getExtras().getString("test_id");
        name = getIntent().getExtras().getString("name");
        type = getIntent().getExtras().getString("typev");

        Log.e("tttt", testId + ";type=" + type + ";name=" + name);

        initView();
        initData();
        getQuesList();
    }


    @Override
    protected void initView() {
        mQuesListView = (ListView) getView(R.id.know_timu_list);


        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(ReportFinishActivity.this);
            }
        });

        titleCenter.setText(name);


        mQuesAdapter = new QuesAdapter(ReportFinishActivity.this, mQuesData, R.layout.item_reprot_ques);
        mQuesAdapter.setOnJiexi(new QuesAdapter.OnJiexi() {
            @Override
            public void onJiexi() {
                mQuesAdapter.notifyDataSetChanged();

            }
        });


        if (Integer.parseInt(type) == 1) {
            mQuesAdapter.setTypeVal(0);//错误，显示红色
        } else {
            mQuesAdapter.setTypeVal(1);//表示正确，显示绿色
        }
        mQuesListView.setAdapter(mQuesAdapter);
    }

    @Override
    protected void initData() {


//        mQuesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
////                mQuesAdapter.notifyDataSetInvalidated();
//
////                mQuesAdapter.notifyAll();
//                mQuesAdapter.notifyDataSetChanged();
//
//            }
//        });


    }


    /**
     * 获取完成情况
     */
    public void getQuesList() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("test_id", testId);
            json.put("type", type);
            json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            json.put("schsystem_id", SpSetting.loadLoginInfo().getSchsystemid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.GET_TEST_QUES_LIST, json), new GenericsCallback<ExplainPro>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(ExplainPro response, int id) {
                dialogView.handleDialog(false);
                if (response.errorCode == 0) {
                    if (response.getContent() != null) {
                        mQuesData.addAll(response.getContent());
                    }
                }
                mQuesAdapter.notifyDataSetChanged();
            }
        });
    }

}

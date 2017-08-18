package com.kzb.parents.wrong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.wrong.adapter.QuesAdapter;
import com.kzb.parents.wrong.model.ExamQuestion;
import com.kzb.parents.wrong.model.ExplainContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class WrongDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ListView listView;
    private String jieId;
    private List<ExplainContent> mQuesData = new ArrayList<>();
    private QuesAdapter mQuesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_detail);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        jieId = getIntent().getStringExtra("id");
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.wrong_detail_listview);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("错题本");
    }

    @Override
    protected void initData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.WRONG_DETAIL_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("subject_id", jieId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<ExamQuestion>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(ExamQuestion response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    setVal(response);
                } else {
                    MineToast.show(WrongDetailActivity.this, response.msg);
                }
            }
        });
    }


    private void setVal(ExamQuestion mResponse) {

        mQuesData.addAll(mResponse.getExplainContent());
        mQuesAdapter = new QuesAdapter(this, mQuesData, R.layout.item_reprot_ques);
        mQuesAdapter.setOnJiexi(new QuesAdapter.OnJiexi() {
            @Override
            public void onJiexi() {
                mQuesAdapter.notifyDataSetChanged();

            }
        });
        listView.setAdapter(mQuesAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(WrongDetailActivity.this);
                break;

        }
    }
}

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
import com.kzb.parents.diagnose.model.ExplainPro;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.wrong.adapter.QuesAdapter;
import com.kzb.parents.wrong.model.ExplainContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/5/13.
 *
 */

public class JiXieActivity extends BaseActivity {

    private String testId="";
    private String name="";
    private ListView mQuesListView;
    private List<ExplainContent> mQuesData = new ArrayList<>();
    private QuesAdapter mQuesAdapter;

    View convertView;

    private TextView titleLeft, titleCenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiexie);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        testId = getIntent().getExtras().getString("test_id");
        name = getIntent().getExtras().getString("name");

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
        titleCenter.setText(name);

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mQuesAdapter = new QuesAdapter(JiXieActivity.this, mQuesData, R.layout.item_reprot_ques);
        mQuesAdapter.setOnJiexi(new QuesAdapter.OnJiexi() {
            @Override
            public void onJiexi() {
                mQuesAdapter.notifyDataSetChanged();

            }
        });
//        if(Integer.parseInt(type) == 1){
//            mQuesAdapter.setTypeVal(0);//错误，显示红色
//        }else {
//            mQuesAdapter.setTypeVal(1);//表示正确，显示绿色
//        }
        mQuesListView.setAdapter(mQuesAdapter);
    }

    @Override
    protected void initData() {

    }







    /**
     * 获取完成情况
     */
    public void getQuesList() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("id", testId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.GET_TEST_QUES_LIST_TWO, json), new GenericsCallback<ExplainPro>(new JsonGenericsSerializator()) {
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

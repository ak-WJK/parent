package com.kzb.parents.msg;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.msg.adapter.MsgAdapter;
import com.kzb.parents.msg.model.MsgListResponse;
import com.kzb.parents.msg.model.MsgListResponse.MsgListModel;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/7/17.
 * 消息页面
 */

public class MsgListActivity extends BaseActivity {

    private ListView listView;
    private int pageNo = 1;
    private int pageSize = 100;
    private MsgAdapter letterAdapter;
    private TextView titleLeft, titleCenter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        pageNo = 1;
        initView();
        initData();
    }


    @Override
    protected void initView() {

        listView = getView(R.id.msg_list_listview);
        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_left);

        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");
        titleCenter.setText("消息");

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MsgListModel msgListModel = (MsgListModel)parent.getItemAtPosition(position);

                Map<String, String> mapVal = new HashMap<>();
                mapVal.put("id", msgListModel.getId());

                IntentUtil.startActivity(MsgListActivity.this, MsgDetailActivity.class, mapVal);
            }
        });

    }

    @Override
    protected void initData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.MSG_LIST_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("page", pageNo);
            object.put("row", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<MsgListResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
//                if (xRefreshView != null) {
//                    xRefreshView.stopRefresh();
//                    xRefreshView.stopLoadMore();
//                }
            }

            @Override
            public void onResponse(MsgListResponse response, int id) {
                dialogView.handleDialog(false);
//
//                if (xRefreshView != null) {
//                    xRefreshView.stopRefresh();
//                    xRefreshView.stopLoadMore();
//                }


                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                    setVal(response.getContent());

                } else {
                    MineToast.show(MsgListActivity.this, response.msg);
                }
            }
        });

    }

    private void setVal(List<MsgListModel> content) {

        if (letterAdapter == null) {
            letterAdapter = new MsgAdapter(this);
            letterAdapter.setItems(content);
            listView.setAdapter(letterAdapter);
        } else {
            if (pageNo == 1) {
                letterAdapter.setItems(content);
            } else {
                letterAdapter.addItems(content);
            }

        }
    }

}

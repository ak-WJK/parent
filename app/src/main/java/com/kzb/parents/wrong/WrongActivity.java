package com.kzb.parents.wrong;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
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
import com.kzb.parents.wrong.adapter.WrongAdapter;
import com.kzb.parents.wrong.model.WrongResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/3/17.
 */

public class WrongActivity extends BaseActivity implements View.OnClickListener{


    private TextView titleLeft, titleCenter;
    private ExpandableListView expandableListView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
        initData();

    }

    @Override
    protected void initView() {
        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        expandableListView = getView(R.id.wrong_expand);
        expandableListView.setGroupIndicator(null);
        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("错题本");

    }

    @Override
    protected void initData() {
        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.WRONG_LIST_URL);

        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("subject_id",SpSetting.loadLoginInfo().getSubject_id());
            object.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            object.put("schsystem_id",SpSetting.loadLoginInfo().getSchsystemid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<WrongResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(WrongResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
//                    setVal(response.getContent());

//                    Log.e("tttt",response.getContent().toString());

                    setVal(response.getContent());

                } else {
                    MineToast.show(WrongActivity.this, response.msg);
                }
            }
        });
    }


    private void setVal(final List<WrongResponse.WZhangModel> wZhangModelList){
        WrongAdapter wrongAdapter = new WrongAdapter(WrongActivity.this,wZhangModelList);
        expandableListView.setAdapter(wrongAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                for (int i=0;i<wZhangModelList.size();i++){
                    if(groupPosition != i){
                        expandableListView.collapseGroup(i);
                    }
                }

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(WrongActivity.this);
                break;

        }
    }
}

package com.kzb.parents.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.msg.model.LetterDetailResponse;
import com.kzb.parents.util.DateUtils;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/7/17.
 */

public class MsgDetailActivity extends BaseActivity {

    private int pageNo = 1;
    private int pageSize = 10;
    private String letId;

    private TextView titleView, timeView, contentView;
    private TextView titleLeft;
    private TextView titleCenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        letId = getIntent().getStringExtra("id");
        pageNo = 1;

        initView();
        initData();

    }

    @Override
    protected void initView() {

        titleView = getView(R.id.le_detail_title);
        timeView = getView(R.id.le_detail_time);
        contentView = getView(R.id.le_detail_content);
        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        titleLeft.setVisibility(View.VISIBLE);
        titleCenter.setText("消息");
        titleLeft.setText("返回");

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.LETTER_DETAIL_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", letId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<LetterDetailResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(LetterDetailResponse response, int id) {
                dialogView.handleDialog(false);


                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                    setVal(response.getContent());
                } else {
                    MineToast.show(MsgDetailActivity.this, response.msg);
                }
            }
        });

    }


    private void setVal(LetterDetailResponse.LeDetailModel leDetailModel){

        titleView.setText(leDetailModel.getTitle());
        if(null != leDetailModel.getAddtime()){
            timeView.setText(DateUtils.formatDataTime(Long.parseLong(leDetailModel.getAddtime())*1000) );
        }

        contentView.setText(Html.fromHtml(leDetailModel.getContent()));
    }

}

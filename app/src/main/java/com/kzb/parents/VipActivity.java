package com.kzb.parents;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.main.adapter.VipAdapter;
import com.kzb.parents.main.model.PayResponse;
import com.kzb.parents.main.model.VipResponse;
import com.kzb.parents.paywx.PayActivity;
import com.kzb.parents.payzfb.PayDemoActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.kzb.parents.config.SpSetting.loadLoginInfo;

/**
 * Created by wanghaofei on 17/6/21.
 */

public class VipActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;

    private RelativeLayout zfbLayout, wxLayout;


    private RadioGroup radioGroup;

    public HttpConfig httpConfig;
    public DialogView dialogView;

    private String goodsId;

    private ListView listView;

    private VipAdapter vipAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        EventBus.getDefault().register(this);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        initView();
        initData();
//        getStatus();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        zfbLayout = getView(R.id.vip_zfb_layout);
        wxLayout = getView(R.id.vip_wx_layout);

        radioGroup = getView(R.id.vip_group_layout);
        listView = getView(R.id.vip_listview);

        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");
        titleCenter.setText("会员");

        titleLeft.setOnClickListener(this);
        zfbLayout.setOnClickListener(this);
        wxLayout.setOnClickListener(this);

        vipAdapter = new VipAdapter(VipActivity.this);
        listView.setAdapter(vipAdapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                goodsId = radioButton.getTag().toString();
            }
        });
    }

    @Override
    protected void initData() {

        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.ORDER_INFO_LIST_URL);

//        Map<String, String> map = new HashMap<>();
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("id", SpSetting.loadLoginInfo().getUid());
//            object.put("subject_id",SpSetting.loadLoginInfo().getSubject_id());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("info", object.toString());
//
//        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<VipResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(VipResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    setDatas(response.getContent());

                } else {
                    MineToast.show(VipActivity.this, response.msg);
                }
            }
        });

    }


    private void setDatas(List<VipResponse.MoneyModel> vals) {

        if (null == vals || vals.size() == 0) {
            return;
        }


        vipAdapter.setItems(vals);

//        for (int i = 0; i < vals.size(); i++) {
//
//            VipResponse.MoneyModel moneyModel = vals.get(i);
//
//            RadioButton tempButton = new RadioButton(this);
//
//            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(16, 10, 20, 10);//设置边距
//
////            tempButton.setBackgroundResource(R.drawable.xxx);   // 设置RadioButton的背景图片
////            tempButton.setButtonDrawable(R.drawable.xxx);           // 设置按钮的样式
//            tempButton.setPadding(80, 0, 0, 0);                 // 设置文字距离按钮四周的距离
////            tempButton.setText(moneyModel.getName() + moneyModel.getPrice()+"\n"+moneyModel.getContent());
//
//            tempButton.setText(Html.fromHtml("<font color='#ff4800'>" + moneyModel.getName() + moneyModel.getPrice() + "</font>" + "\n" + moneyModel.getContent()));
//
//
//            tempButton.setTag(moneyModel.getId());
//            radioGroup.addView(tempButton, lp);
//        }

    }


    //获取支付后的订单状态
    protected void getStatus() {

        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.ORDER_STATUS_URL);

        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("uid", loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<PayResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(PayResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                 LoginResponse.LoginModel loginModel =  SpSetting.loadLoginInfo();
                    loginModel.setGood_id(response.getContent().getStatus());
                    SpSetting.saveLoginInfo(loginModel);

                } else {
                    MineToast.show(VipActivity.this, response.msg);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(VipActivity.this);
                break;
            case R.id.vip_zfb_layout:

                if (TextUtils.isEmpty(goodsId)) {
                    Toast.makeText(VipActivity.this, "请选择金额,再支付...", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> mapVal = new HashMap<>();
                mapVal.put("id", goodsId);

                IntentUtil.startActivity(VipActivity.this, PayDemoActivity.class, mapVal);
                break;
            case R.id.vip_wx_layout:

                if (TextUtils.isEmpty(goodsId)) {
                    Toast.makeText(VipActivity.this, "请选择金额,再支付...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> mapVals = new HashMap<>();
                mapVals.put("id", goodsId);

                IntentUtil.startActivity(VipActivity.this, PayActivity.class, mapVals);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMesg(VipResponse type) {
        getStatus();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

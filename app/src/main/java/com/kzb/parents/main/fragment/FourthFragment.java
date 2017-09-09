package com.kzb.parents.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.set.SetCourseActivity;
import com.kzb.parents.settwo.SchoolShenHeActivity;
import com.kzb.parents.settwo.SetDetailActivity;
import com.kzb.parents.settwo.UpdateActivity;
import com.kzb.parents.settwo.model.UpdateResponse;
import com.kzb.parents.util.DeviceUtil;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.kzb.parents.R.id.first_banben_gengxin;

/**
 * Created by wanghaofei on 17/2/15.
 */

public class FourthFragment extends BaseFragment implements View.OnClickListener {

    private HttpConfig httpConfig;
    private DialogView dialogView;

    private TextView headTitle , userName;
    private RelativeLayout setLayout, courseLayout, lgLayout, msgLayout ;
    private RelativeLayout schoolRenzheng,bianhaoLyaout;
    private RelativeLayout banbenGengxin;
    private UpdateResponse.UpdateModel updateModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, parent, false);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getActivity());
        initView(view);
        checkVersion();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView(View view) {
        setLayout = (RelativeLayout) view.findViewById(R.id.fourth_set_view);
        courseLayout = (RelativeLayout) view.findViewById(R.id.fourth_course_layout);
        lgLayout = (RelativeLayout) view.findViewById(R.id.first_fourth_lgout);
        msgLayout = (RelativeLayout) view.findViewById(R.id.fourth_msg_layout);
        schoolRenzheng = (RelativeLayout) view.findViewById(R.id.first_school_renzheng);
        bianhaoLyaout = (RelativeLayout) view.findViewById(R.id.first_shebei_bianhao);
        banbenGengxin = (RelativeLayout) view.findViewById(first_banben_gengxin);


        userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText(SpSetting.loadLoginInfo().getName());

//        vipLayout = (RelativeLayout) view.findViewById(R.id.first_vip_lgout);
        setLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        lgLayout.setOnClickListener(this);
        msgLayout.setOnClickListener(this);
        schoolRenzheng.setOnClickListener(this);
        bianhaoLyaout.setOnClickListener(this);
        banbenGengxin.setOnClickListener(this);
//        vipLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fourth_msg_layout:
                Toast.makeText(getActivity(), "此功能暂未开通...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.first_fourth_lgout:
                IntentUtil.startActivity(getActivity(), LoginTypeActivity.class);
//                SpSetting.clearLoginInfo();

                break;
            case R.id.fourth_course_layout:
                IntentUtil.startActivity(getActivity(), SetCourseActivity.class);
                break;
            case R.id.fourth_set_view:
                IntentUtil.startActivity(getActivity(), SetDetailActivity.class);
                break;
            case R.id.first_school_renzheng:
                IntentUtil.startActivity(getActivity(), SchoolShenHeActivity.class);
                break;
            case R.id.first_shebei_bianhao:
                EventBus.getDefault().post("finishOne");
                break;
            case R.id.first_banben_gengxin:


                Map<String, String> mapVal = new HashMap<>();
                mapVal.put("versioncode", updateModel.getVersioncode());
                mapVal.put("versionname", updateModel.getVersionname());
                mapVal.put("content", updateModel.getContent());
                mapVal.put("url", updateModel.getUrl());
                mapVal.put("required", updateModel.getRequired());
                mapVal.put("timer", updateModel.getTimer());
                mapVal.put("filesize", updateModel.getFilesize());
                IntentUtil.startActivity(getActivity(), UpdateActivity.class, mapVal);
                break;
        }
    }



    public void checkVersion() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.CHECK_VERSION, json), new GenericsCallback<UpdateResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(UpdateResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    updateModel = response.getContent();


                    LogUtils.e("TAG", "updateModel  = " + updateModel.toString());


                    if (!TextUtils.isEmpty(updateModel.getVersioncode())) {

                        int backCode = Integer.parseInt(updateModel.getVersioncode());
                        int curCode = DeviceUtil.getCurVersionCode(getActivity());
                        if (backCode > curCode) {
//                            redSignView.setVisibility(View.VISIBLE);
//                            updateLayout.setEnabled(true);
                        } else {
//                            updateLayout.setEnabled(false);
                            Toast.makeText(getActivity(), "当前已是最新版本!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "获取版本失败,点击版本更新,重新获取!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}

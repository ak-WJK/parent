package com.kzb.parents.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.BGQKListActivity;
import com.kzb.parents.report.RTDiagnoseQuanKeActivity;
import com.kzb.parents.report.RTDiagnoseZhangJieActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

/**
 * Created by wanghaofei on 17/2/15.
 */

public class SecFragment extends BaseFragment implements View.OnClickListener {

    private HttpConfig httpConfig;
    private DialogView dialogView;

    private TextView headTitle;
    private TextView  ksView, zjieView, qKeView;


    //会员等级
    int level = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, parent, false);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getActivity());


        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            level = Integer.parseInt(SpSetting.loadLoginInfo().getGood_id());
        } catch (Exception e) {
            level = 1;
        }
    }


    private void initView(View view) {
//        dgView = (TextView) view.findViewById(R.id.sec_dig_view);
        ksView = (TextView) view.findViewById(R.id.sec_ks_view);
        zjieView = (TextView) view.findViewById(R.id.dg_zhangjie);
        qKeView = (TextView) view.findViewById(R.id.dg_quanke);
//        dgView.setOnClickListener(this);
        ksView.setOnClickListener(this);
        zjieView.setOnClickListener(this);
        qKeView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.sec_dig_view:
//                IntentUtil.startActivity(getActivity(), DiagnoseActivity.class);
//                break;
            case R.id.sec_ks_view:
                IntentUtil.startActivity(getActivity(), BGQKListActivity.class);
                break;
            case R.id.dg_zhangjie:

                IntentUtil.startActivity(getActivity(), RTDiagnoseZhangJieActivity.class);
                break;
            case R.id.dg_quanke:

                IntentUtil.startActivity(getActivity(), RTDiagnoseQuanKeActivity.class);
                break;
        }
    }
}

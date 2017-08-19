package com.kzb.parents.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.set.SetCourseActivity;
import com.kzb.parents.settwo.SetDetailActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

/**
 * Created by wanghaofei on 17/2/15.
 */

public class FourthFragment extends BaseFragment implements View.OnClickListener {

    private HttpConfig httpConfig;
    private DialogView dialogView;

    private TextView headTitle;
    private RelativeLayout setLayout,courseLayout,lgLayout,msgLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, parent, false);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getActivity());
        initView(view);

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
        setLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        lgLayout.setOnClickListener(this);
        msgLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fourth_msg_layout:
                Toast.makeText(getActivity(),"此功能暂未开通...",Toast.LENGTH_SHORT).show();
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
        }
    }
}

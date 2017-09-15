package com.kzb.parents.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.kaoshi.KSQuanKeActivity;
import com.kzb.parents.strengthen.StrengthenActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/2/15.
 */

public class ThirdFragment extends BaseFragment implements View.OnClickListener{

    private TextView headTitle;


    private TextView thirdQH;
    private TextView thirdXX;

    //会员等级
    int level = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, parent, false);




        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        try {
//            level = Integer.parseInt(SpSetting.loadLoginInfo().getGood_id());
//        } catch (Exception e) {
//            level = 1;
//        }
    }

    private void initView(View view){

        headTitle = (TextView) getActivity().findViewById(R.id.common_head_center);
        thirdQH = (TextView)view.findViewById(R.id.thir_qianghua_view);
        thirdXX = (TextView)view.findViewById(R.id.thir_xuexi_view);
//        headTitle.setText("知识点2");

        thirdQH.setOnClickListener(this);
        thirdXX.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.thir_qianghua_view:

                if(level == 3){
                    IntentUtil.startActivity(getActivity(), StrengthenActivity.class);
                }else {
                    Toast.makeText(getActivity(), R.string.notice_val, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.thir_xuexi_view:
                IntentUtil.startActivity(getActivity(), KSQuanKeActivity.class);
                break;
        }
    }
}

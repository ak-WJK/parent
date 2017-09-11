package com.kzb.parents;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;

public class VipImageDetail extends BaseActivity implements View.OnClickListener {

    private TextView left, center;
    private ImageView ivIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_image_detail);


        initView();
        initData();
    }

    @Override
    protected void initView() {
        left = getView(R.id.common_head_left);
        center = getView(R.id.common_head_center);

        left.setVisibility(View.VISIBLE);
        left.setText("返回");
        center.setText("会员详情");
        left.setOnClickListener(this);


        ivIcon = getView(R.id.iv_vip_icon);

    }

    @Override
    protected void initData() {
        ivIcon.setImageResource(R.drawable.vip);


    }

    @Override
    public void onClick(View v) {
        IntentUtil.finish(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        IntentUtil.finish(this);
    }
}

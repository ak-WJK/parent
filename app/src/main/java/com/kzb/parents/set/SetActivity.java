package com.kzb.parents.set;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/2/28.
 */

public class SetActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    @Override
    protected void initView() {
        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        titleLeft.setOnClickListener(this);
        titleCenter.setText("设置");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_head_left:
                IntentUtil.finish(SetActivity.this);
                break;

        }
    }
}

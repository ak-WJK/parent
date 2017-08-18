package com.kzb.parents.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.container.MineContainer;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

/**
 * Created by wanghaofei on 17/6/18.
 */

public class LoginTypeActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneView, numView;

    private TextView titleCenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
    }

    @Override
    protected void initView() {

        phoneView = getView(R.id.login_type_phone);
        numView = getView(R.id.login_type_num);

        titleCenter = getView(R.id.common_head_center);

        titleCenter.setText("我的账号");
        //家长登陆
        phoneView.setOnClickListener(this);
        //学生登陆
        numView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_type_phone:
                //家长登陆
                IntentUtil.startActivity(LoginTypeActivity.this, LoginActivity.class);
                break;
            case R.id.login_type_num:
                //学生登陆
                IntentUtil.startActivity(LoginTypeActivity.this, LoginTwoActivity.class);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出应用(销毁所有Activity)
            MineContainer.getInstance().exit();
            return true;
        } else {
            return false;
        }
    }
}

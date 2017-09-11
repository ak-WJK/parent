package com.kzb.parents;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.main.MainActivity;
import com.kzb.parents.util.IntentUtil;

public class WelcomeActivity extends BaseActivity {

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:

                    IntentUtil.startActivity(WelcomeActivity.this, MainActivity.class);
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        if(SpSetting.loadLoginInfo() == null){
            IntentUtil.startActivity(WelcomeActivity.this, LoginTypeActivity.class);
            IntentUtil.finish(WelcomeActivity.this);
            return;
        }
        handler.sendEmptyMessageDelayed(1, 3500);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

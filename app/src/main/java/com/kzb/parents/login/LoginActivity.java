package com.kzb.parents.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.model.CheckResponse;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.main.MainActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.CountDownTimerUtils;
import com.kzb.parents.view.DialogView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 16/11/29.
 */

public class LoginActivity extends BaseActivity {
    private HttpConfig httpConfig;
    private DialogView dialogView;

    //    private TextView timeView, comitView;
    private EditText nameView, pwdView;
    private LinearLayout loginView;

    private TextView centerView, leftView;
    private TextView codeView;

    private String codeVal;//


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
    }

    private void setTime() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR);
        int mMinuts = mCalendar.get(Calendar.MINUTE);
    }

    @Override
    protected void initView() {
        nameView = getView(R.id.login_uname_view);
        pwdView = getView(R.id.login_pwd_view);
        loginView = getView(R.id.login_view);
        centerView = getView(R.id.common_head_center);
        leftView = getView(R.id.common_head_left);
        codeView = getView(R.id.login_check_code);

        leftView.setVisibility(View.VISIBLE);
        leftView.setText("返回");
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(LoginActivity.this);
            }
        });
        centerView.setText("考之宝");


        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameVal = nameView.getText().toString().trim();
                String pwdVal = pwdView.getText().toString().trim();
                if (nameVal.length() == 0) {
                    MineToast.show(LoginActivity.this, "请输入手机号...");
                    return;
                }
                if (pwdVal.length() == 0) {
                    MineToast.show(LoginActivity.this, "请输入验证码...");
                    return;
                }

                if (nameVal.length() == 11) {
                    if (codeVal != null && codeVal.equals(pwdVal)) {

                        doLogin(nameVal);
                    } else {
                        MineToast.show(LoginActivity.this, "验证码错误,请重新输入");
                    }
                } else {
                    MineToast.show(LoginActivity.this, "手机号不合法");
                }
            }
        });


        codeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameVal = nameView.getText().toString().trim();
                if (nameVal.length() == 0) {
                    MineToast.show(LoginActivity.this, "请输入手机号...");
                    return;
                } else {
                    CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(codeView, 60000, 1000);
                    countDownTimerUtils.start();

                    commitData(nameVal);
                }

            }
        });

    }

    @Override
    protected void initData() {

    }


    private void doLogin(String phoneNum) {
        dialogView.show();
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.LOGIN_PAR_URL);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("username", phoneNum);
            object.put("type", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<LoginResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.dismiss();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(LoginResponse response, int id) {
                dialogView.dismiss();
                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    //登录成功
                    SpSetting.saveLoginInfo(response.getContent());
                    IntentUtil.finish(LoginActivity.this);
                    IntentUtil.startActivity(LoginActivity.this, MainActivity.class);

                } else {
                    Toast.makeText(LoginActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //获取验证码
    protected void commitData(String nameVal) {
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.CODE_URL);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("phone", nameVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<CheckResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(CheckResponse response, int id) {

                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                    codeVal = response.getContent();
                    //登录成功
//                    SpSetting.saveLoginInfo(response.getContent());
//                    IntentUtil.startActivity(LoginActivity.this, MainActivity.class);

                }
            }
        });
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            return true;
//        }else {
//            return false;
//        }
//
//    }
}

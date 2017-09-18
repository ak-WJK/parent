package com.kzb.parents.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.application.Application;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.main.MainActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/12.
 * 学生登陆页面
 */

public class LoginTwoActivity extends BaseActivity {
    private HttpConfig httpConfig;
    private DialogView dialogView;

    private TextView comitView;
    private EditText nameView, pwdView;

    private TextView titleLeft, titleCenter;
    private CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_two);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
        initData();
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
        comitView = getView(R.id.login_commit_view);
        nameView = getView(R.id.login_name_view);
        pwdView = getView(R.id.login_pwd_view);

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        checkBox = getView(R.id.cb_xsmm);


        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");
        titleCenter.setVisibility(View.VISIBLE);
        titleCenter.setText("学号登录");





        setTime();

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(LoginTwoActivity.this);
            }
        });

        comitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameVal = nameView.getText().toString().trim();
                String pwdVal = pwdView.getText().toString().trim();
                if (nameVal.length() == 0) {
                    MineToast.show(LoginTwoActivity.this, "请输入用户名...");
                    return;
                }
                if (pwdVal.length() == 0) {
                    MineToast.show(LoginTwoActivity.this, "请输入密码...");
                    return;
                }
                commitData(nameVal, pwdVal);
            }
        });
    }

    @Override
    protected void initData() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pwdView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                pwdView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                
            }
        });




    }

    //去服务器请求登陆
    protected void commitData(String nameVal, String pwdVal) {
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.LOGIN_URL);

        //设置显示加载页面
        dialogView.handleDialog(true);

        Map<String, String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("username", nameVal);
            object.put("password", pwdVal);
            object.put("imeicode", Application.deviceId);
            object.put("type", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<LoginResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                LogUtils.e("TAG", "登陆错误== " + e.getMessage());

            }

            @Override
            public void onResponse(LoginResponse response, int id) {
                //不显示加载页面
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    //登录成功
                    SpSetting.saveLoginInfo(response.getContent());

                    if (response.getContent().getIs_active().equals("0")) {
                        //没有完善信息，先到完善信息页面
                        IntentUtil.startActivity(LoginTwoActivity.this, ImproveActivity.class);
                    } else if (response.getContent().getIs_active().equals("2")) {
                        //外来用户，完善信息
                        IntentUtil.startActivity(LoginTwoActivity.this, ImproveActivity.class);
                    } else if (response.getContent().getIs_active().equals("1")) {


                        if (TextUtils.isEmpty(response.getContent().getSubject())) {
                            //跳转到课程选择页面
                            IntentUtil.startActivity(LoginTwoActivity.this, CourseSelectActivity.class);
                        } else {
                            IntentUtil.startActivity(LoginTwoActivity.this, MainActivity.class);
                        }
//                        IntentUtil.startActivity(LoginActivity.this, ImproveActivity.class);
                    } else {
                        if (SpSetting.loadLoginInfo().getSubject_id() == null) {
                            IntentUtil.startActivity(LoginTwoActivity.this, CourseSelectActivity.class);
                        } else {
                            //跳转到主页
                            IntentUtil.startActivity(LoginTwoActivity.this, MainActivity.class);
                        }
                    }
                } else {
                    MineToast.show(LoginTwoActivity.this, response.msg);
                }
            }
        });
    }
}

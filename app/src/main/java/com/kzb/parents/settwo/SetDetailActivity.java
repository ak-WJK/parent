package com.kzb.parents.settwo;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kzb.parents.set.SetCourseActivity;
import com.kzb.parents.settwo.model.StatusResponse;
import com.kzb.parents.settwo.model.UpdateResponse;
import com.kzb.parents.settwo.model.YearResponse;
import com.kzb.parents.settwo.model.YearResponse.GradeModel;
import com.kzb.parents.settwo.model.YearResponse.YearModel;
import com.kzb.parents.util.DeviceUtil;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.wheelview.DateUtils;
import com.kzb.parents.view.wheelview.JudgeDate;
import com.kzb.parents.view.wheelview.ScreenInfo;
import com.kzb.parents.view.wheelview.WheelMain;
import com.kzb.parents.view.widget.AbstractSpinerAdapter;
import com.kzb.parents.view.widget.SpinerPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class SetDetailActivity extends BaseActivity implements View.OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener {

    private TextView leftView, rightView;
    private RelativeLayout gradeLayout, classLayout,updateLayout,outLayout,renZhenLayout;
    private TextView womanView, manView, birthView;

    private TextView gradeView, classView, schoolView, classNum;

    private TextView renzhenState;

    private EditText nameView;

    private TextView mVersionName;

    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    private List<YearResponse.YearModel> nameList = new ArrayList<YearResponse.YearModel>();

    private List<YearResponse.YearModel> yearList = new ArrayList<>();
    private List<YearResponse.YearModel> classList = new ArrayList<>();

    private int sexSign = 0;//0表示都未选中,1表示男选中,2表示女选中

    private YearModel yearModel;
    //    private GradeModel classModel;
    private int selectSign = 1;//1 grade,2 class
    private String gradeIdVal,classIdVal;


    private List<YearModel> listYears;//年级
    private List<GradeModel> listGrades;//班级


    private TextView upView, downView;//上学期，下学期
    private int vuType;//1表示上学期，2表示下学期


    //版本更新
    private TextView redSignView,updateView;
    private UpdateResponse.UpdateModel updateModel;//版本更新对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_two);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        initView();
//        initData();
    }

    @Override
    protected void initView() {

        leftView = getView(R.id.set_back_view);
        rightView = getView(R.id.set_save_view);

        gradeLayout = getView(R.id.set_grade_layout);
        classLayout = getView(R.id.set_class_layout);
        updateLayout = getView(R.id.set_update_layout);

        updateLayout.setEnabled(false);

        gradeView = getView(R.id.set_grade_view);
        classView = getView(R.id.set_class_view);
        schoolView = getView(R.id.set_school_name);
        classNum = getView(R.id.set_class_num);


        nameView = getView(R.id.set_name_view);
        womanView = getView(R.id.set_sex_woman);
        manView = getView(R.id.set_sex_man);
        birthView = getView(R.id.set_birth_view);

        upView = getView(R.id.improve_up_view);
        downView = getView(R.id.improve_down_view);
        redSignView = getView(R.id.set_upate_sign_view);
        updateView = getView(R.id.set_update_name_view);

        mVersionName = getView(R.id.set_update_num);
        outLayout = getView(R.id.set_out_layout);
        mVersionName.setText("V"+ DeviceUtil.getCurVersionName(this));


        outLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(SetDetailActivity.this,"id="+ Application.deviceId,Toast.LENGTH_LONG).show();

                return true;
            }
        });

        renZhenLayout = getView(R.id.set_school_renzhen);
        renzhenState = getView(R.id.set_school_renzhen_state);


        leftView.setOnClickListener(this);
        rightView.setOnClickListener(this);
        gradeLayout.setOnClickListener(this);
        classLayout.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
        birthView.setOnClickListener(this);
        manView.setOnClickListener(this);
        womanView.setOnClickListener(this);
        classView.setOnClickListener(this);
        gradeView.setOnClickListener(this);
        updateView.setOnClickListener(this);

        upView.setOnClickListener(this);
        downView.setOnClickListener(this);
        outLayout.setOnClickListener(this);
        renZhenLayout.setOnClickListener(this);

        checkVersion();

        LoginResponse.LoginModel loginModel = SpSetting.loadLoginInfo();
        if (null != loginModel) {
            nameView.setText(loginModel.getName());
            if(!TextUtils.isEmpty(loginModel.getName())){
                nameView.setSelection(loginModel.getName().length());
            }

            schoolView.setText(loginModel.getSchoolname());
            gradeView.setText(loginModel.getYear());
            classView.setText(loginModel.getGrade());
            classNum.setText(loginModel.getCode());

            if(loginModel.getBirthday() != null){
                birthView.setText(loginModel.getBirthday().replace("00:00:00",""));
            }



            if(loginModel.getBirthday() != null){
                String[] birArr = loginModel.getBirthday().split(" ");
                if(birArr.length == 2){
                    birthView.setText(birArr[0]);
                }
            }

//            try {
//                if(loginModel.getStatus().equals("1")){
//                    renzhenState.setText("审核中");
//                }else if(loginModel.getStatus().equals("2")){
//                    renzhenState.setText("已认证");
//                }else if(loginModel.getStatus().equals("3")){
//                    renzhenState.setText("拒绝认证");
//                }else {
//                    renzhenState.setText("未认证");
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }




            if(loginModel.getSex()!=null){
                sexSign = Integer.parseInt(loginModel.getSex());

                if(sexSign == 1){
                    Drawable mandrawable2 = getResources().getDrawable(R.mipmap.gray_select_press);
                    Drawable womdrawable2 = getResources().getDrawable(R.mipmap.gray_select_normal);
                    mandrawable2.setBounds(0, 0, mandrawable2.getMinimumWidth(), mandrawable2.getMinimumHeight());
                    womdrawable2.setBounds(0, 0, womdrawable2.getMinimumWidth(), womdrawable2.getMinimumHeight());
                    manView.setCompoundDrawables(mandrawable2, null, null, null);
                    womanView.setCompoundDrawables(womdrawable2, null, null, null);
                }else if(sexSign == 2){
                    Drawable mandrawable2 = getResources().getDrawable(R.mipmap.gray_select_normal);
                    Drawable womdrawable2 = getResources().getDrawable(R.mipmap.gray_select_press);
                    mandrawable2.setBounds(0, 0, mandrawable2.getMinimumWidth(), mandrawable2.getMinimumHeight());
                    womdrawable2.setBounds(0, 0, womdrawable2.getMinimumWidth(), womdrawable2.getMinimumHeight());
                    manView.setCompoundDrawables(mandrawable2, null, null, null);
                    womanView.setCompoundDrawables(womdrawable2, null, null, null);
                }else {
                    //默认为1
                    sexSign = 1;
                    Drawable mandrawable2 = getResources().getDrawable(R.mipmap.gray_select_press);
                    Drawable womdrawable2 = getResources().getDrawable(R.mipmap.gray_select_normal);
                    mandrawable2.setBounds(0, 0, mandrawable2.getMinimumWidth(), mandrawable2.getMinimumHeight());
                    womdrawable2.setBounds(0, 0, womdrawable2.getMinimumWidth(), womdrawable2.getMinimumHeight());
                    manView.setCompoundDrawables(mandrawable2, null, null, null);
                    womanView.setCompoundDrawables(womdrawable2, null, null, null);
                }

            }

            gradeIdVal = loginModel.getYear_id();
            classIdVal = loginModel.getGrade_id();

            if(loginModel.getVolume_id() != null && loginModel.getVolume_id().trim().length() != 0){
                if(loginModel.getVolume_id().equals("1")){
                    vuType = 1;

                    Drawable drawable1= getResources().getDrawable(R.mipmap.blue_uncheck_img);
                    /// 这一步必须要做,否则不会显示.
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    downView.setCompoundDrawables(drawable1,null,null,null);

                    Drawable drawable2= getResources().getDrawable(R.mipmap.blue_check_img);
                    /// 这一步必须要做,否则不会显示.
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    upView.setCompoundDrawables(drawable2,null,null,null);
                }else {

                    vuType = 2;
                    Drawable drawable= getResources().getDrawable(R.mipmap.blue_check_img);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    downView.setCompoundDrawables(drawable,null,null,null);

                    Drawable drawableTwo= getResources().getDrawable(R.mipmap.blue_uncheck_img);
                    /// 这一步必须要做,否则不会显示.
                    drawableTwo.setBounds(0, 0, drawableTwo.getMinimumWidth(), drawableTwo.getMinimumHeight());
                    upView.setCompoundDrawables(drawableTwo,null,null,null);
                }
            }

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        getStatusData();
    }

    //获取审核状态值
    protected void getStatusData() {

        //获取年级信息
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.SHENHE_STATUS_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("uid",SpSetting.loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<StatusResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(StatusResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    setStaView(response.getContent());
                }  else {
                    MineToast.show(SetDetailActivity.this, response.msg);
                }

            }
        });
    }



    private void setStaView(StatusResponse.StatusModel response){

        if(renzhenState != null){
            if( TextUtils.isEmpty(response.getStatus())){
                renzhenState.setText("未认证");
                renZhenLayout.setEnabled(true);
            }else {
                if(response.getStatus().equals("1")){
                    renzhenState.setText("审核中");
                    renZhenLayout.setEnabled(false);
                }else if(response.getStatus().equals("2")){
                    renzhenState.setText("已认证");
                    renZhenLayout.setEnabled(false);
                }else if(response.getStatus().equals("3")){
                    renzhenState.setText("拒绝认证");
                    renZhenLayout.setEnabled(true);
                }else {
                    renzhenState.setText("未认证");
                    renZhenLayout.setEnabled(true);
                }
            }

        }

    }



    @Override
    protected void initData() {

        //获取年级信息
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.IMPROVE_YEAR_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("school_id", SpSetting.loadLoginInfo().getSchool_id());
            object.put("uid",SpSetting.loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<YearResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(YearResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    setCommonData(response.getContent());
                    listYears = response.getContent();
                    //调试先写死
                } else if (response.errorCode == 101) {
                    //完善信息页面
                } else {
                    MineToast.show(SetDetailActivity.this, response.msg);
                }

            }
        });
    }




//    private void getClassVal(String yearId) {
//
//        XBaseRequest baseRequest = new XBaseRequest();
//        baseRequest.setUrl(AddressConfig.IMPROVE_CLASS_URL);
//        dialogView.handleDialog(true);
//        Map<String, String> map = new HashMap<>();
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("school_id", SpSetting.loadLoginInfo().getSchool_id());
//            object.put("year_id", yearId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("info", object.toString());
//        baseRequest.setRequestParams(map);
//        httpConfig.doPostRequest(baseRequest, new GenericsCallback<YearResponse>(new JsonGenericsSerializator()) {
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);
//
//            }
//
//            @Override
//            public void onResponse(YearResponse response, int id) {
//                dialogView.handleDialog(false);
//
//                if (response != null && response.errorCode == 0 && response.getContent() != null) {
//                    //登录成功
//                    setClassCommonData(response.getContent());
//                } else {
//                    MineToast.show(SetActivity.this, response.msg);
//                }
//
//            }
//        });
//    }


    private void setCommonData(List<YearResponse.YearModel> listVals) {
        nameList.clear();
        nameList.addAll(listVals);
        mAdapter = new AbstractSpinerAdapter(this);
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);
        showSpinWindow();
    }


    //设置班级
    private void setClassCommonData(List<GradeModel> listVals) {

        List<YearModel> listYM = new ArrayList<>();
        for (int i=0;i<listVals.size();i++){
            GradeModel gradeModel = listVals.get(i);
            //转为yearmodel
            YearModel yearModel = new YearModel();
            yearModel.setId(gradeModel.getId());
            yearModel.setName(gradeModel.getName());
            listYM.add(yearModel);
        }

        nameList.clear();
        nameList.addAll(listYM);
        mAdapter = new AbstractSpinerAdapter(this);
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);
        showSpinWindow();
    }


    public void checkVersion(){
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

                    if(!TextUtils.isEmpty(updateModel.getVersioncode())){

                        int backCode = Integer.parseInt(updateModel.getVersioncode());
                        int curCode = DeviceUtil.getCurVersionCode(SetDetailActivity.this);
                        if (backCode > curCode){
                            redSignView.setVisibility(View.VISIBLE);
                            updateLayout.setEnabled(true);
                        }else {
                            updateLayout.setEnabled(false);
                            Toast.makeText(SetDetailActivity.this, "当前已是最新版本!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SetDetailActivity.this, "获取版本失败,点击版本更新,重新获取!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_out_layout:
//                SpSetting.loginOut();
                EventBus.getDefault().post("finishOne");
//                IntentUtil.finish(SetActivity.this);
                break;
            case R.id.set_update_name_view:
                //点击获取版本信息
                checkVersion();
                break;
            case R.id.improve_down_view:

                vuType = 2;
                Drawable drawable= getResources().getDrawable(R.mipmap.blue_check_img);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                downView.setCompoundDrawables(drawable,null,null,null);

                Drawable drawableTwo= getResources().getDrawable(R.mipmap.blue_uncheck_img);
                /// 这一步必须要做,否则不会显示.
                drawableTwo.setBounds(0, 0, drawableTwo.getMinimumWidth(), drawableTwo.getMinimumHeight());
                upView.setCompoundDrawables(drawableTwo,null,null,null);

                break;
            case R.id.improve_up_view:

                vuType = 1;

                Drawable drawable1= getResources().getDrawable(R.mipmap.blue_uncheck_img);
                /// 这一步必须要做,否则不会显示.
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                downView.setCompoundDrawables(drawable1,null,null,null);

                Drawable drawable2= getResources().getDrawable(R.mipmap.blue_check_img);
                /// 这一步必须要做,否则不会显示.
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                upView.setCompoundDrawables(drawable2,null,null,null);

                break;
            case R.id.set_update_layout:

                Map<String,String> mapVal = new HashMap<>();
                mapVal.put("versioncode",updateModel.getVersioncode());
                mapVal.put("versionname",updateModel.getVersionname());
                mapVal.put("content",updateModel.getContent());
                mapVal.put("url",updateModel.getUrl());
                mapVal.put("required",updateModel.getRequired());
                mapVal.put("timer",updateModel.getTimer());
                mapVal.put("filesize",updateModel.getFilesize());
                IntentUtil.startActivity(SetDetailActivity.this,UpdateActivity.class,mapVal);
                break;
            case R.id.set_grade_view:
                selectSign = 1;

                if(null == listYears){
                    initData();
                }else {
                    setCommonData(listYears);
                }


                break;
            case R.id.set_class_view:
                selectSign = 2;
                if (listGrades == null) {
                    MineToast.show(SetDetailActivity.this, "请先选择年级...");
                    return;
                }else {
                    setClassCommonData(listGrades);
                }
//                getClassVal(yearModel.getId());
                break;
            case R.id.set_sex_woman:
                sexSign = 2;
                Drawable mandrawable2 = getResources().getDrawable(R.mipmap.gray_select_normal);
                Drawable womdrawable2 = getResources().getDrawable(R.mipmap.gray_select_press);
                mandrawable2.setBounds(0, 0, mandrawable2.getMinimumWidth(), mandrawable2.getMinimumHeight());
                womdrawable2.setBounds(0, 0, womdrawable2.getMinimumWidth(), womdrawable2.getMinimumHeight());
                manView.setCompoundDrawables(mandrawable2, null, null, null);
                womanView.setCompoundDrawables(womdrawable2, null, null, null);
                break;
            case R.id.set_sex_man:
                sexSign = 1;
                Drawable mandrawable = getResources().getDrawable(R.mipmap.gray_select_press);
                Drawable womdrawable = getResources().getDrawable(R.mipmap.gray_select_normal);
                mandrawable.setBounds(0, 0, mandrawable.getMinimumWidth(), mandrawable.getMinimumHeight());
                womdrawable.setBounds(0, 0, womdrawable.getMinimumWidth(), womdrawable.getMinimumHeight());
                manView.setCompoundDrawables(mandrawable, null, null, null);
                womanView.setCompoundDrawables(womdrawable, null, null, null);
                break;
            case R.id.set_birth_view:
                showBottoPopupWindow(v);
                break;
            case R.id.set_back_view:
                IntentUtil.finish(SetDetailActivity.this);
                break;
            case R.id.set_save_view:

                String nameVal = nameView.getText().toString().trim();
                if (nameVal.length() == 0) {
                    MineToast.show(SetDetailActivity.this, "请输入姓名...");
                    return;
                }

                if (sexSign == 0) {
                    MineToast.show(SetDetailActivity.this, "请选择性别...");
                    return;
                }

                String birthVal = birthView.getText().toString().trim();
                if (birthVal.length() == 0) {
                    MineToast.show(SetDetailActivity.this, "请选择生日...");
                    return;
                }


                String gradeVal = gradeView.getText().toString().trim();
                if (gradeVal.length() == 0) {
                    MineToast.show(SetDetailActivity.this, "请选择年级...");
                    return;
                }

                String classVal = classView.getText().toString().trim();
                if (classVal.length() == 0) {
                    MineToast.show(SetDetailActivity.this, "请选择班级...");
                    return;
                }

                if(vuType == 0){
                    MineToast.show(SetDetailActivity.this, "请选择学期...");
                    return;
                }

                String numVal = classNum.getText().toString().trim();

                birthVal = birthVal+" 00:00:00";
                saveVal(nameVal,numVal,String.valueOf(sexSign),birthVal,gradeIdVal,classIdVal);

                break;
            case R.id.set_school_renzhen:
                IntentUtil.startActivity(SetDetailActivity.this,SchoolShenHeActivity.class);
                break;


        }
    }



    private void saveVal(String nameVal,String numVal,String sexVal,String birthVal,String gradeId,String classId) {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.COMIT_IMPROVE_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("type", SpSetting.loadLoginInfo().getType());
            object.put("name", nameVal);
            object.put("code", numVal);
            object.put("volume_id",vuType);
            object.put("sex", sexVal);
            object.put("birthday", birthVal);
            object.put("year_id", gradeId);
            object.put("grade_id", classId);
            object.put("oauth_token", SpSetting.loadLoginInfo().getOauth_token());
            object.put("oauth_token_secret", SpSetting.loadLoginInfo().getOauth_token_secret());

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);

//        Log.e("tttt",map.toString());

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<LoginResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(LoginResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    //登录成功
//                    setClassCommonData(response.getContent());
                    SpSetting.saveLoginInfo(response.getContent());
                    IntentUtil.finish(SetDetailActivity.this);
                    EventBus.getDefault().post("classname");

                    IntentUtil.startActivity(SetDetailActivity.this,SetCourseActivity.class);

                } else {
                    MineToast.show(SetDetailActivity.this, response.msg);
                }

            }
        });
    }



    private WheelMain wheelMainDate;
    private String beginTime;
    private TextView tv_house_time;
    private java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void showBottoPopupWindow(View textViewV) {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window, null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int) (width * 0.8),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        wheelMainDate.screenheight = screenInfoDate.getHeight();
        String time = DateUtils.currentMonth().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelMainDate.initDateTimePicker(year, month, day, hours, minute);
        final String currentTime = wheelMainDate.getTime().toString();
//        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.showAtLocation(textViewV, Gravity.CENTER, 0, 0);

        mPopupWindow.setWidth(birthView.getWidth());
        mPopupWindow.showAsDropDown(birthView);

        mPopupWindow.setOnDismissListener(new SetDetailActivity.poponDismissListener());
        backgroundAlpha(0.6f);
//        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
//        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
//        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
//        tv_pop_title.setText("选择起始时间");
//        tv_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mPopupWindow.dismiss();
//                backgroundAlpha(1f);
//            }
//        });
//        tv_ensure.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                beginTime = wheelMainDate.getTime().toString();
//                try {
//                    Date begin = dateFormat.parse(currentTime);
//                    Date end = dateFormat.parse(beginTime);
//                    tv_house_time.setText(DateUtils.currentTimeDeatil(begin));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                mPopupWindow.dismiss();
//                backgroundAlpha(1f);
//            }
//        });
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {

//            Toast.makeText(SetActivity.this,"...dismis...",Toast.LENGTH_SHORT).show();
            backgroundAlpha(1f);
            birthView.setText(wheelMainDate.getTime().toString());

        }

    }


    private void showSpinWindow() {
        if (selectSign == 1) {
            mSpinerPopWindow.setWidth(gradeView.getWidth());
            mSpinerPopWindow.showAsDropDown(gradeView);
        } else {
            mSpinerPopWindow.setWidth(classView.getWidth());
            mSpinerPopWindow.showAsDropDown(classView);
        }
    }

    private void setHero(int pos) {

        if (selectSign == 1) {

            if (pos >= 0 && pos <= nameList.size()) {
                YearResponse.YearModel value = nameList.get(pos);
                yearModel = value;
                gradeIdVal = value.getId();
                gradeView.setText(value.getName().toString());
                listGrades = value.getGrade();
                //给班级默认选择第一个
                if(listGrades != null && listGrades.size() != 0){
//                    classModel = listGrades.get(0);
                    classIdVal = listGrades.get(0).getId();
                    classView.setText(listGrades.get(0).getName());
                }


            }
        } else {
            if (pos >= 0 && pos <= nameList.size()) {
                YearResponse.YearModel value = nameList.get(pos);

//                classModel = value;
                classIdVal = value.getId();
                classView.setText(value.getName().toString());
            }
        }
    }


    @Override
    public void onItemClick(int pos) {
        setHero(pos);
    }

}

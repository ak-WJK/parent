package com.kzb.parents.login;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.settwo.model.YearResponse;
import com.kzb.parents.settwo.model.YearResponse.YearModel;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.wheelview.DateUtils;
import com.kzb.parents.view.wheelview.JudgeDate;
import com.kzb.parents.view.wheelview.ScreenInfo;
import com.kzb.parents.view.wheelview.WheelMain;
import com.kzb.parents.view.widget.AbstractSpinerAdapter;
import com.kzb.parents.view.widget.SpinerPopWindow;

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

public class ImproveActivity extends BaseActivity implements View.OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener {

    private HttpConfig httpConfig;
    private DialogView dialogView;


    private TextView manView, womanView, birthView, gradeView, classView;


    private EditText nameView, numView;
    private TextView resetView, commitView;


    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    private List<YearModel> nameList = new ArrayList<YearModel>();

    private List<YearModel> yearList = new ArrayList<>();
    private List<YearModel> classList = new ArrayList<>();

    private int sexSign = 0;//0表示都未选中,1表示男选中,2表示女选中

    private YearModel yearModel, classModel;
    private int selectSign = 1;//1 grade,2 class
    private String gradeIdVal, classIdVal;

    private List<YearModel> listYears;//年级
    private List<YearResponse.GradeModel> listGrades;//班级

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);


        initView();
//        initData();


    }


    @Override
    protected void initView() {

        nameView = getView(R.id.improve_name_view);
        numView = getView(R.id.improve_num_view);
        manView = getView(R.id.improve_sex_man);
        womanView = getView(R.id.improve_sex_woman);
        birthView = getView(R.id.improve_birth_view);
        gradeView = getView(R.id.improve_grade_view);
        classView = getView(R.id.improve_class_view);

        resetView = getView(R.id.improve_reset_view);
        commitView = getView(R.id.improve_commit_view);


        gradeView.setOnClickListener(this);
        classView.setOnClickListener(this);
        birthView.setOnClickListener(this);
        commitView.setOnClickListener(this);
        manView.setOnClickListener(this);
        womanView.setOnClickListener(this);

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

                } else if (response.errorCode == 101) {
                    //完善信息页面

                } else {
                    MineToast.show(ImproveActivity.this, response.msg);
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
//                    setClassCommonData(response.getContent());
//                    listYears = response.getContent();
//                } else {
//                    MineToast.show(ImproveActivity.this, response.msg);
//                }
//
//            }
//        });
//    }


    private void setClassCommonData(List<YearResponse.GradeModel> listVals) {

        List<YearModel> listYM = new ArrayList<>();
        for (int i=0;i<listVals.size();i++){
            YearResponse.GradeModel gradeModel = listVals.get(i);
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


    private void setCommonData(List<YearModel> listVals) {
        nameList.clear();
        nameList.addAll(listVals);
        mAdapter = new AbstractSpinerAdapter(this);
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);
        showSpinWindow();
    }


    private void commitImprove(String nameVal, String numVal, String sexVal, String birthVal, String gradeId, String classId) {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.COMIT_IMPROVE_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("name", nameVal);
            object.put("code", numVal);
            object.put("sex", sexVal);
            object.put("birthday", birthVal);
            object.put("year", gradeId);
            object.put("year_id", gradeId);
            object.put("grade_id", classId);
            object.put("oauth_token", SpSetting.loadLoginInfo().getOauth_token());
            object.put("oauth_token_secret", SpSetting.loadLoginInfo().getOauth_token_secret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
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
                    SpSetting.saveLoginInfo(response.getContent());
                    //登录成功
                    IntentUtil.startActivity(ImproveActivity.this, CourseSelectActivity.class);

                } else if (response.errorCode == 101) {
                    //完善信息页面

                } else {
                    MineToast.show(ImproveActivity.this, response.msg);
                }

            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.improve_sex_man:
                sexSign = 1;
                Drawable mandrawable = getResources().getDrawable(R.mipmap.select_press);
                Drawable womdrawable = getResources().getDrawable(R.mipmap.select_normal);
                mandrawable.setBounds(0, 0, mandrawable.getMinimumWidth(), mandrawable.getMinimumHeight());
                womdrawable.setBounds(0, 0, womdrawable.getMinimumWidth(), womdrawable.getMinimumHeight());
                manView.setCompoundDrawables(mandrawable, null, null, null);
                womanView.setCompoundDrawables(womdrawable, null, null, null);
                break;
            case R.id.improve_sex_woman:
                sexSign = 2;
                Drawable mandrawable2 = getResources().getDrawable(R.mipmap.select_normal);
                Drawable womdrawable2 = getResources().getDrawable(R.mipmap.select_press);
                mandrawable2.setBounds(0, 0, mandrawable2.getMinimumWidth(), mandrawable2.getMinimumHeight());
                womdrawable2.setBounds(0, 0, womdrawable2.getMinimumWidth(), womdrawable2.getMinimumHeight());
                manView.setCompoundDrawables(mandrawable2, null, null, null);
                womanView.setCompoundDrawables(womdrawable2, null, null, null);
                break;
            case R.id.improve_grade_view:
                selectSign = 1;

                if(null == listYears){
                    initData();
                }else {
                    setCommonData(listYears);
                }

                break;
            case R.id.improve_class_view:
                selectSign = 2;
                if (listGrades == null) {
                    MineToast.show(ImproveActivity.this, "请先选择年级...");
                    return;
                }else {
                    setClassCommonData(listGrades);
                }

                break;
            case R.id.improve_birth_view:
                showBottoPopupWindow(v);
                break;
            case R.id.improve_commit_view:

                String nameVal = nameView.getText().toString().trim();
                if (nameVal.length() == 0) {
                    MineToast.show(ImproveActivity.this, "请输入姓名...");
                    return;
                }

                String numVal = numView.getText().toString().trim();

                if (numVal.length() == 0) {
                    MineToast.show(ImproveActivity.this, "请输入学号...");
                    return;
                }

                if (sexSign == 0) {
                    MineToast.show(ImproveActivity.this, "请选择性别...");
                    return;
                }

                String birthVal = birthView.getText().toString().trim();
                if (birthVal.length() == 0) {
                    MineToast.show(ImproveActivity.this, "请选择生日...");
                    return;
                }


                String gradeVal = gradeView.getText().toString().trim();
                if (gradeVal.length() == 0) {
                    MineToast.show(ImproveActivity.this, "请选择年级...");
                    return;
                }

                String classVal = classView.getText().toString().trim();
                if (classVal.length() == 0) {
                    MineToast.show(ImproveActivity.this, "请选择班级...");
                    return;
                }

                commitImprove(nameVal, numVal, String.valueOf(sexSign), birthVal, gradeIdVal, classIdVal);


                break;
            case R.id.improve_reset_view:

                nameView.setText("");
                numView.setText("");
                birthView.setText("");
                gradeView.setText("");
                classView.setText("");

                yearModel = null;
                classModel = null;
                gradeIdVal = null;
                classIdVal = null;

                break;
        }
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

        mPopupWindow.setOnDismissListener(new poponDismissListener());
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

//            Toast.makeText(ImproveActivity.this,"...dismis...",Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemClick(int pos) {
        setHero(pos);
    }

}

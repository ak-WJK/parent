package com.kzb.parents.settwo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseResponse;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.settwo.model.EventModel;
import com.kzb.parents.settwo.model.YearResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.widget.AbstractSpinerAdapter;
import com.kzb.parents.view.widget.SpinerPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class SchoolShenHeActivity extends BaseActivity implements View.OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener{


    //

    private RelativeLayout schoolLayout, gradeLayout, classLayout;
    private TextView schoolView, gradeView, classView;

    EventModel eventModels;
    private YearResponse.YearModel yearModel;
    private int selectSign = 1;//1 grade,2 class
    private String gradeIdVal,classIdVal;
    private List<YearResponse.YearModel> listYears;//年级
    private List<YearResponse.GradeModel> listGrades;//班级

    private List<YearResponse.YearModel> nameList = new ArrayList<YearResponse.YearModel>();


    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    private int types = 0;

    //提交审核
    private TextView subShenheView;
    private TextView backView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenhe);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
        initData();
        EventBus.getDefault().register(this);

//        dialogView.handleDialog(true);


//        getPro();

    }


    @Override
    protected void initView() {

        schoolLayout = getView(R.id.shenhe_school_layout);
        gradeLayout = getView(R.id.shenhe_grade_layout);
        classLayout = getView(R.id.shenhe_class_layout);
        schoolView = getView(R.id.shenhe_school_view);
        gradeView = getView(R.id.shenhe_grade_view);
        classView = getView(R.id.shenhe_class_view);

        subShenheView = getView(R.id.shenhe_class_sub);

        backView = getView(R.id.shenhe_back_view);

        schoolLayout.setOnClickListener(this);
        gradeLayout.setOnClickListener(this);
        classLayout.setOnClickListener(this);
        subShenheView.setOnClickListener(this);
        backView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }




    //获取市区数据
    private void getYearsData(){

        if(eventModels == null){
            Toast.makeText(SchoolShenHeActivity.this, "请先选择学校...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(eventModels.getSchool_id())){
            Toast.makeText(SchoolShenHeActivity.this, "请先选择学校...", Toast.LENGTH_SHORT).show();

            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("school_id",eventModels.getSchool_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.RENZHENG_YEARS_URL, json), new GenericsCallback<YearResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolShenHeActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(YearResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {
                    if (response.getContent() != null) {
//
                        if(types == 1){
                            setCommonData(response.getContent());
                        }

                        listYears = response.getContent();
                    }
                } else {
                    Toast.makeText(SchoolShenHeActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



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
    private void setClassCommonData(List<YearResponse.GradeModel> listVals) {

        List<YearResponse.YearModel> listYM = new ArrayList<>();
        for (int i=0;i<listVals.size();i++){
            YearResponse.GradeModel gradeModel = listVals.get(i);
            //转为yearmodel
            YearResponse.YearModel yearModel = new YearResponse.YearModel();
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


    private void showSpinWindow() {
        if (selectSign == 1) {
            mSpinerPopWindow.setWidth(gradeView.getWidth());
            mSpinerPopWindow.showAsDropDown(gradeView);
        } else {
            mSpinerPopWindow.setWidth(classView.getWidth());
            mSpinerPopWindow.showAsDropDown(classView);
        }
    }

    //提交认证数据
    private void saveData(){

        if(eventModels == null){
            Toast.makeText(SchoolShenHeActivity.this, "请先选择学校...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(gradeIdVal == null){
            Toast.makeText(SchoolShenHeActivity.this, "请先选择年级...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(classIdVal == null){
            Toast.makeText(SchoolShenHeActivity.this, "请先选择班级...", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("area_id",eventModels.getArea_id());
            json.put("city_id",eventModels.getCity_id());
            json.put("distinct_id",eventModels.getDistinct_id());
            json.put("school_id",eventModels.getSchool_id());
            json.put("year_id",gradeIdVal);
            json.put("grade_id",classIdVal);
            json.put("uid", SpSetting.loadLoginInfo().getUid());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.RENZHENG_URL, json), new GenericsCallback<XBaseResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolShenHeActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(XBaseResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {

                    if(response.errorCode == 0){
                        Toast.makeText(SchoolShenHeActivity.this, "提交成功...", Toast.LENGTH_SHORT).show();
                        SchoolShenHeActivity.this.finish();
                    }
                } else {
                    Toast.makeText(SchoolShenHeActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //接收数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptData(EventModel eventModel){

        eventModels = eventModel;

        schoolView.setText(eventModel.getSchool_name());
        getYearsData();
        types = 0;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shenhe_school_layout:
                IntentUtil.startActivity(SchoolShenHeActivity.this,SchoolRenZhengActivity.class);
                break;
            case R.id.shenhe_grade_layout:
                selectSign = 1;
                if(null == listYears){
                    getYearsData();
                }else {
                    types = 1;
                    setCommonData(listYears);
                }
                break;
            case R.id.shenhe_class_layout:
                selectSign = 2;
                if (listGrades == null) {
                    MineToast.show(SchoolShenHeActivity.this, "请先选择年级...");
                    return;
                }else {
                    setClassCommonData(listGrades);
                }
                break;
            case R.id.shenhe_class_sub:
                saveData();
                break;
            case R.id.shenhe_back_view:
                SchoolShenHeActivity.this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(int pos) {

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
}

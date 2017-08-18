package com.kzb.parents.set;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.baselibrary.log.Log;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.base.XBaseResponse;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.set.adapter.CourseAdapter;
import com.kzb.parents.set.model.CourseResponse;
import com.kzb.parents.util.IntentUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/2/28.
 * 课程选择页面
 */

public class SetCourseActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;

    private ListView listView;
    private List<CourseResponse.CourseModel> listVVV;
    CourseResponse.CourseModel tempModel;
    private TextView compView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        httpConfig = new HttpConfig();
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        listView = getView(R.id.set_course_listview);
        compView = getView(R.id.set_course_cmt_view);

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        compView.setOnClickListener(this);
        titleCenter.setText("选择课程");
    }


    @Override
    protected void initData() {
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.COURSE_URL);
//        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        if (null == SpSetting.loadLoginInfo() || null == SpSetting.loadLoginInfo().getUid()) {
            IntentUtil.startActivity(SetCourseActivity.this, LoginTypeActivity.class);
            return;
        }

        JSONObject object = new JSONObject();
        try {
            Log.e("wang", "uid=" + SpSetting.loadLoginInfo().getUid());
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("type", SpSetting.loadLoginInfo().getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<CourseResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(CourseResponse response, int id) {
//                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    //成功
                    setVal(response.getContent());
                } else if (response.errorCode == 101) {
                    //完善信息页面

                } else {
                    MineToast.show(SetCourseActivity.this, response.msg);
                }

            }
        });
    }


    private void setVal(List<CourseResponse.CourseModel> listVals) {

        final CourseAdapter courseAdapter = new CourseAdapter(SetCourseActivity.this);
        courseAdapter.setItems(listVals);
        listVVV = listVals;

        //先显示默认的，如果存在的话
        if (null == listVVV) {
            return;
        }

        if (SpSetting.loadLoginInfo() != null && SpSetting.loadLoginInfo().getSubject_id() != null) {
            for (int i = 0; i < listVVV.size(); i++) {

                CourseResponse.CourseModel model = listVVV.get(i);
                if (model.getId().equals(SpSetting.loadLoginInfo().getSubject_id())) {
                    model.setType(1);
                    break;
                }
            }
        }

        listView.setAdapter(courseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                CourseResponse.CourseModel courseModel = (CourseResponse.CourseModel) parent.getItemAtPosition(position);

                if (courseModel.getIsopen().trim().equals("1")) {
                    LoginResponse.LoginModel loginModel = SpSetting.loadLoginInfo();
                    loginModel.setSubject(courseModel.getName());
                    loginModel.setSubject_id(courseModel.getId());
                    SpSetting.saveLoginInfo(loginModel);
                    tempModel = courseModel;
//                    saveCourse(courseModel);
                } else {
                    MineToast.show(SetCourseActivity.this, "该课程暂未开通，请耐心等待！");
                    return;
                }


                for (int i = 0; i < listVVV.size(); i++) {
                    CourseResponse.CourseModel temp = listVVV.get(i);
                    if (courseModel.getId().equals(temp.getId())) {
                        temp.setType(1);
                    } else {
                        temp.setType(0);
                    }
                }
                courseAdapter.notifyDataSetChanged();
            }
        });

    }


    private void saveCourse() {

        if (tempModel == null) {
            MineToast.show(SetCourseActivity.this, "请选择课程！");
            return;
        }

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.SAVE_COURSE_URL);
//        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("subject_id", tempModel.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<XBaseResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(XBaseResponse response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0) {
                    //成功
//                    IntentUtil.startActivity(SetCourseActivity.this,MainPageActivity.class);

                    EventBus.getDefault().post("course");
                    IntentUtil.finish(SetCourseActivity.this);

                } else {
                    MineToast.show(SetCourseActivity.this, response.msg);
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(SetCourseActivity.this);
                break;
            case R.id.set_course_cmt_view:
                saveCourse();
                break;
        }
    }
}

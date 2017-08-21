package com.kzb.parents.course;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.base.XBaseResponse;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.model.CourseDetailResponse;
import com.kzb.parents.course.model.CourseDetailResponse.CourseDetailModel;
import com.kzb.parents.exam.ExamActivity;
import com.kzb.parents.exam.bean.NeedKnowPro;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.QuesWebViewSeven;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/4/15.
 *
 *报告-->自我诊断报告-->查看-->诊断报告详情页(知识点掌握)-->(点击知识点Item进入)课程学习页面
 */

public class CourseDetailActivity extends BaseActivity {

    private TextView titleLeft, titleContent;

    private String kid, mPoint;

    private LinearLayout txtLayout;
    private QuesWebViewSeven contentView;
    private TextView nameView, timesView;
    private ImageView xingOne, xingTwo, xingThree;
    private TextView learnOver;
    private TextView strongView;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        LogUtils.e("TAG", "此页面为课程学习页面");


        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        kid = getIntent().getStringExtra("kid");
        mPoint = getIntent().getStringExtra("point");

//
//        pageNo = 1;
//
//        type = getIntent().getStringExtra("type");

        initView();
        initData();
    }

    @Override
    protected void initView() {
        titleContent = getView(R.id.common_head_center);
        titleLeft = getView(R.id.common_head_left);
        contentView = getView(R.id.course_detail_content);

        nameView = getView(R.id.course_detail_name);
        timesView = getView(R.id.course_detail_times);
        xingOne = getView(R.id.course_detail_xing_one);
        xingTwo = getView(R.id.course_detail_xing_two);
        xingThree = getView(R.id.course_detail_xing_three);
        learnOver = getView(R.id.course_detail_learn_over);
        strongView = getView(R.id.course_detail_strong_view);

        learnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnOver();
            }
        });

        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");
        titleContent.setText("课程学习");

        strongView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证当前知识点，是否在强化列表
                LogUtils.e("TAG", "kid== " + kid + "\n强化训练");

                if (kid != null) {
                    checkKidValidate();
                }
            }
        });

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(CourseDetailActivity.this);
            }
        });


    }



    //验证是否需要强化提高
    private void checkKidValidate() {
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.CHECK_STRENGTH_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("kid", kid);
            object.put("uid", SpSetting.loadLoginInfo().getUid());
            object.put("version_id", SpSetting.loadLoginInfo().getVersion_id());

            LogUtils.e("TAG", "version_id---------=====","添加数据version_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<XBaseResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(XBaseResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 2) {
                    showComitDialog();
                } else {

                    Map<String, String> val = new HashMap<String, String>();
                    val.put("point", mPoint);
                    val.put("kid", kid);
                    IntentUtil.startActivity(CourseDetailActivity.this, ExamActivity.class, val);

//                   MineToast.show(CourseDetailActivity.this, response.msg);
                }
            }
        });
    }



    public void showComitDialog() {


        View viewDialog = (View) LayoutInflater.from(this).inflate(
                R.layout.dialog_one_exam, null);
        final Dialog dialog = new Dialog(CourseDetailActivity.this,
                R.style.myDialogTheme);
        dialog.setCanceledOnTouchOutside(false);
        TextView yesView = (TextView) viewDialog.findViewById(R.id.dialog_sure_btn);
        TextView noView = (TextView) viewDialog.findViewById(R.id.dialog_cancel_btn);


        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStrengthPoint(dialog);

//                dialog.dismiss();
//                Map<String, String> val = new HashMap<String, String>();
//                val.put("point", mPoint);
//                val.put("kid", kid);
//                IntentUtil.startActivity(CourseDetailActivity.this, ExamActivity.class, val);

            }
        });

        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setContentView(viewDialog);
        dialog.show();

    }



    //加入到强化提高知识点
    private void addStrengthPoint(final Dialog dialog) {
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.ADD_CL_SL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("kid", kid);
            object.put("uid", SpSetting.loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<XBaseResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(XBaseResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0) {
                    dialog.dismiss();
                    Map<String, String> val = new HashMap<String, String>();
                    val.put("point", mPoint);
                    val.put("kid", kid);
                    IntentUtil.startActivity(CourseDetailActivity.this, ExamActivity.class, val);
                    CourseDetailActivity.this.finish();
                }


//                else {
//
//                    Map<String, String> val = new HashMap<String, String>();
//                    val.put("point", mPoint);
//                    val.put("kid", kid);
//                    IntentUtil.startActivity(CourseDetailActivity.this, ExamActivity.class, val);


//                    MineToast.show(CourseDetailActivity.this, response.msg);
//                }
            }
        });
    }


    public void learnOver() {
//        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        if (!TextUtils.isEmpty(kid)) {
            try {
                json.put("id", kid);
                json.put("uid", SpSetting.loadLoginInfo().getUid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //考试，须知
        String url = AddressConfig.LEARN_OVER;
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<NeedKnowPro>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);
                IntentUtil.finish(CourseDetailActivity.this);
            }

            @Override
            public void onResponse(NeedKnowPro response, int id) {
//                dialogView.handleDialog(false);
                IntentUtil.finish(CourseDetailActivity.this);
            }
        });
    }



    @Override
    protected void initData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.COURSE_DETAIL_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("kid", kid);
            object.put("uid", SpSetting.loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<CourseDetailResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(CourseDetailResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                    setVal(response.getContent());

                } else {
                    MineToast.show(CourseDetailActivity.this, response.msg);
                }
            }
        });

    }


    private void setVal(CourseDetailModel courseDetailModel) {
//        contentView.setNeedFilter(false);
//        contentView.setNetText(courseDetailModel.getExplain());
//        contentView.loadData(courseDetailModel.getExplain(),"text/html; charset=UTF-8", null);
        contentView.loadData(courseDetailModel.getExplain());



        nameView.setText("知识点: " + courseDetailModel.getKpoint());
        timesView.setText("近年考试出现 " + courseDetailModel.getTimes() + " 次");

        if (courseDetailModel.getImportance() != null) {

            if (courseDetailModel.getImportance().trim().equals("1")) {
                xingOne.setVisibility(View.VISIBLE);
                xingTwo.setVisibility(View.GONE);
                xingThree.setVisibility(View.GONE);
            } else if (courseDetailModel.getImportance().trim().equals("2")) {
                xingOne.setVisibility(View.VISIBLE);
                xingTwo.setVisibility(View.VISIBLE);
                xingThree.setVisibility(View.GONE);
            } else if (courseDetailModel.getImportance().trim().equals("3")) {
                xingOne.setVisibility(View.VISIBLE);
                xingTwo.setVisibility(View.VISIBLE);
                xingThree.setVisibility(View.VISIBLE);
            } else {
                xingOne.setVisibility(View.GONE);
                xingTwo.setVisibility(View.GONE);
                xingThree.setVisibility(View.GONE);
            }
        }
    }

}

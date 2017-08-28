package com.kzb.parents.wrong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.CourseDetailActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.QuesWebViewSeven;
import com.kzb.parents.view.QuesWebViewSix;
import com.kzb.parents.wrong.model.Answer;
import com.kzb.parents.wrong.model.ExamQuestion;
import com.kzb.parents.wrong.model.ExplainContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.kzb.parents.application.Application.mContext;

/**
 * Created by wanghaofei on 17/7/6.
 * 错题本点击查看答案解析页面
 */

public class WrongDetailThreeActivity extends BaseActivity implements View.OnClickListener {
    //上一题
    private TextView lastView;
    //下一题
    private TextView nextView;
    //当前题目号
    private TextView curNumView;
    //问题
    private QuesWebViewSeven webViewSeven;
    //答案
    private LinearLayout qanswerLayout;
    //我的选项
    private LinearLayout myanswerLayout;
    //参考答案
    private LinearLayout answerLayout;
    //知识点
    private TextView kgView;
    //解析内容
    private QuesWebViewSeven webViewSix;

    private String jieId;

    private TextView titleLeft, titleCenter;

    //
    private ImageView rightImg;

    //试题数据
    protected List<ExplainContent> mDatas = new ArrayList<>();

    //当前试题编号
    private int curNum = 0;

    //无解析
    private TextView noJieXiView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_three_detail);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        jieId = getIntent().getStringExtra("id");
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("错题本");


        lastView = getView(R.id.item_last_view);
        //下一题
        nextView = getView(R.id.item_next_view);
        //当前题目号
        curNumView = getView(R.id.item_cur_num);
        //问题
        webViewSeven = getView(R.id.item_ques_content);
        //答案
        qanswerLayout = getView(R.id.item_ques_ans_layout);
        //我的选项
        myanswerLayout = getView(R.id.item_ques_myanswer);
        //参考答案
        answerLayout = getView(R.id.item_ques_answer);
        //知识点
        kgView = getView(R.id.item_ques_knowadge_view);
        //解析内容
        webViewSix = getView(R.id.item_ques_jiexi);

        noJieXiView = getView(R.id.item_ques_no_jiexi);

        rightImg = getView(R.id.item_ques_ans_right);

        lastView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.WRONG_DETAIL_URL);
        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getUid());
            object.put("subject_id", jieId);
            object.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<ExamQuestion>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(ExamQuestion response, int id) {
                dialogView.handleDialog(false);
//Log.e("xue","ccc="+response.toString());
                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    mDatas.addAll(response.getExplainContent());
                    curNum = 0;
                    showContent();
                } else {
                    MineToast.show(WrongDetailThreeActivity.this, response.msg);
                }
            }
        });
    }


    //显示一道题
    private void showContent() {

        if (mDatas.size() == 0) {
            return;
        }

        if (mDatas.size() == 1) {
            lastView.setBackgroundResource(R.mipmap.common_btn_gray);
            nextView.setBackgroundResource(R.mipmap.common_btn_gray);
        } else {
            if (curNum == 0) {
                lastView.setBackgroundResource(R.mipmap.common_btn_gray);
            } else {
                lastView.setBackgroundResource(R.mipmap.common_button_green);
            }

            if (curNum == (mDatas.size() - 1)) {
                nextView.setBackgroundResource(R.mipmap.common_btn_gray);
            } else {
                nextView.setBackgroundResource(R.mipmap.common_button_green);
            }
        }


        final ExplainContent explainContent = mDatas.get(curNum);
        curNumView.setText((curNum + 1) + "/" + mDatas.size());
        webViewSeven.loadData(explainContent.getQuestion());
        //答案
        qanswerLayout.removeAllViews();

//        Log.e("xue",explainContent.getIstrue());

        if (!TextUtils.isEmpty(explainContent.getIstrue())) {
            if ("1".equals(explainContent.getIstrue())) {
                rightImg.setImageResource(R.mipmap.ques_ans_right);

                kgView.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_green));

            } else {
                rightImg.setImageResource(R.mipmap.ques_ans_wrong);
                kgView.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_red));
            }
        }


//        rightImg.setImageResource(R.mipmap.ques_ans_right);

        if (explainContent.getAnswers() != null) {

            for (int i = 0; i < explainContent.getAnswers().size(); i++) {
                final Answer answer = explainContent.getAnswers().get(i);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = DensityUtil.dip2px(mContext, 5);
                lp.topMargin = DensityUtil.dip2px(mContext, 8);
                LinearLayout answerL = new LinearLayout(mContext);
                answerL.setGravity(Gravity.CENTER_VERTICAL);
                answerL.setHorizontalGravity(LinearLayout.HORIZONTAL);
                answerL.setLayoutParams(lp);

                lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                lp.leftMargin = DensityUtil.dip2px(mContext, 5);
                lp.rightMargin = DensityUtil.dip2px(mContext, 10);
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.answer_btn_gray);
                tv.setLayoutParams(lp);
                tv.setTextColor(mContext.getResources().getColor(R.color.white));
                tv.setTextSize(12);
                tv.setGravity(Gravity.CENTER);
                char c = 'A';
                c = (char) (c + i);
                tv.setText(new StringBuffer().append(c).toString());
                answerL.addView(tv);


                QuesWebViewSix quesWebViewSix = new QuesWebViewSix(mContext);

                WebSettings webSettings = quesWebViewSix.getSettings();//获取webview设置属性
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
                webSettings.setJavaScriptEnabled(true);//支持js
//            webSettings.setBuiltInZoomControls(true); // 显示放大缩小
//            webSettings.setSupportZoom(true); // 可以缩放
                webSettings.setTextSize(WebSettings.TextSize.SMALLER);

                quesWebViewSix.setVerticalScrollBarEnabled(false);
                quesWebViewSix.setHorizontalScrollBarEnabled(false);
                quesWebViewSix.setScrollContainer(false);
                quesWebViewSix.requestFocus();
                quesWebViewSix.loadData(answer.getAnswer());

                answerL.addView(quesWebViewSix);

                qanswerLayout.addView(answerL);
            }
        }


        //我的选项
        myanswerLayout.removeAllViews();

        if (explainContent.getMyanswer() != null) {
            String[] myAns = explainContent.getMyanswer().split(",");
            if (myAns != null && myAns.length > 0) {
                myanswerLayout.removeAllViews();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                for (String my : myAns) {

                    if (null != explainContent.getAnswers()) {

                        for (int i = 0; i < explainContent.getAnswers().size(); i++) {
                            Answer answer = explainContent.getAnswers().get(i);
                            if (my.equals(answer.getAnswer_id())) {
                                TextView textView = new TextView(mContext);
                                textView.setBackgroundResource(R.drawable.answer_btn_red);
                                textView.setTextColor(Color.WHITE);
                                textView.setGravity(Gravity.CENTER);
                                textView.setTextSize(12);
                                lp.rightMargin = DensityUtil.dip2px(mContext, 12);
                                textView.setLayoutParams(lp);
                                switch (i) {
                                    case 0:
                                        textView.setText("A");
                                        break;
                                    case 1:
                                        textView.setText("B");
                                        break;
                                    case 2:
                                        textView.setText("C");
                                        break;
                                    case 3:
                                        textView.setText("D");
                                        break;
                                    case 4:
                                        textView.setText("E");
                                        break;
                                }
                                myanswerLayout.addView(textView);
                            }
                        }
                    }
                }
            }
        }

        //正确选项
        answerLayout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));

        if (null != explainContent.getAnswers()) {

            for (int i = 0; i < explainContent.getAnswers().size(); i++) {
                Answer answer = explainContent.getAnswers().get(i);
                if (answer.istrue.equals("1")) {
                    TextView textView = new TextView(mContext);
                    textView.setBackgroundResource(R.drawable.answer_btn_green);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(12);
                    lp.rightMargin = DensityUtil.dip2px(mContext, 12);
                    textView.setLayoutParams(lp);
                    switch (i) {
                        case 0:
                            textView.setText("A");
                            break;
                        case 1:
                            textView.setText("B");
                            break;
                        case 2:
                            textView.setText("C");
                            break;
                        case 3:
                            textView.setText("D");
                            break;
                        case 4:
                            textView.setText("E");
                            break;
                    }
                    answerLayout.addView(textView);
                }
            }
        }

        //知识点
        kgView.setText(explainContent.getKnowledges().get(0).getKpoint());


        kgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (explainContent != null && explainContent.getKnowledges() != null && explainContent.getKnowledges().size() > 0) {
                    Intent intent = new Intent(WrongDetailThreeActivity.this, CourseDetailActivity.class);
                    intent.putExtra("kid", explainContent.getKnowledges().get(0).getKid());
                    IntentUtil.startActivity(WrongDetailThreeActivity.this, intent);
                }
            }
        });

        //解析内容
        if (!TextUtils.isEmpty(explainContent.getExplain()) && !explainContent.getExplain().contains("暂无解析")) {
            webViewSix.setVisibility(View.VISIBLE);
            noJieXiView.setVisibility(View.GONE);
            webViewSix.loadData(explainContent.getExplain());
        } else {
            noJieXiView.setVisibility(View.VISIBLE);
            webViewSix.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(WrongDetailThreeActivity.this);
                break;
            case R.id.item_last_view:
                if (curNum == 0) {
                    lastView.setBackgroundResource(R.mipmap.common_btn_gray);

//                    if(curNum != mDatas.size()){
//                        nextView.setBackgroundResource(R.mipmap.common_btn_gray);
//                    }

                    return;
                }
                curNum--;
                showContent();
                break;
            case R.id.item_next_view:
                if (curNum == (mDatas.size() - 1)) {
                    nextView.setBackgroundResource(R.mipmap.common_btn_gray);
                    return;
                }
                curNum++;
                showContent();
                break;

        }
    }
}

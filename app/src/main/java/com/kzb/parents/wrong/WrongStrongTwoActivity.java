package com.kzb.parents.wrong;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.course.CourseDetailActivity;
import com.kzb.parents.exam.ExamActivity;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.CustomReTextView;
import com.kzb.parents.view.QuesWebViewSeven;
import com.kzb.parents.view.QuesWebViewSix;
import com.kzb.parents.view.topbar.Topbar;
import com.kzb.parents.wrong.model.Answer;
import com.kzb.parents.wrong.model.ExamQuestion;
import com.kzb.parents.wrong.model.ExplainContent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kzb.parents.application.Application.mContext;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class WrongStrongTwoActivity extends BaseActivity implements View.OnClickListener {


    TextView mRight, mTotal, mWrong;
    CustomReTextView mLine;
    TextView mPoint;
    private String mDone;
    private String mAll;

    private String allper;//全部百分比
    private String thisper;//和上次对比，百分比

    private String point;
    private String kid;


    private View mHeader;
    private View mFooter;


    private Topbar mTopabr;
    ExamQuestion mResponse;

    private TextView percentView;


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
    //试题数据
    protected List<ExplainContent> mDatas = new ArrayList<>();

    //当前试题编号
    private int curNum = 0;


    private int tempCurNum;

    //无解析
    private TextView noJieXiView;

    private String baifenbi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.e("TAG", "错题强化页面 ");

        setContentView(R.layout.activity_wrong_three_detail);

        mHeader = LayoutInflater.from(this).inflate(R.layout.activity_strong_report_header, null);
        mFooter = LayoutInflater.from(this).inflate(R.layout.activity_strong_report_footer, null);


        String response = getIntent().getStringExtra("question");
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            mResponse = gson.fromJson(response, ExamQuestion.class);
        }
        mAll = getIntent().getStringExtra("all");
        mDone = getIntent().getStringExtra("done");

        allper = getIntent().getStringExtra("allper");
        thisper = getIntent().getStringExtra("thisper");
        //这里的知识点涉及到，和界面显示配合一起
        point = getIntent().getStringExtra("point");
        kid = getIntent().getStringExtra("kid");

//        httpConfig = new HttpConfig();
//        dialogView = new DialogView(this);

//        jieId = getIntent().getStringExtra("id");
        initView();
        initData();
    }


    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        percentView = (TextView) mHeader.findViewById(R.id.strong_percent_view);
        percentView.setText("累计强化进度:" + allper + "%");

//        if (thisper != null) {
//            int thisper1 = Integer.parseInt(thisper);
//        }
//
//        if (baifenbi != null) {
//            int baifenbi1 = Integer.parseInt(baifenbi);
//        }


//        if (thisper != null && baifenbi != thisper) {
//
//
        MineToast.show(WrongStrongTwoActivity.this, "对比上次强化提高了" + thisper + "%");
//
//            baifenbi = thisper;
//        }


        mRight = (TextView) mHeader.findViewById(R.id.strong_right);
        mWrong = (TextView) mHeader.findViewById(R.id.strong_wrong);
        mLine = (CustomReTextView) mHeader.findViewById(R.id.strong_line);
        mTotal = (TextView) mHeader.findViewById(R.id.strong_total);
        mPoint = (TextView) mHeader.findViewById(R.id.strong_point);

        String point = getIntent().getStringExtra("point");
        if (!TextUtils.isEmpty(point)) {
            mPoint.setText(point);
        }
        mFooter.findViewById(R.id.strong_report_replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> val = new HashMap<String, String>();
                val.put("point", mPoint.getText().toString());
                val.put("kid", getIntent().getStringExtra("kid"));
                IntentUtil.startActivity(WrongStrongTwoActivity.this, ExamActivity.class, val);
            }
        });
        mFooter.findViewById(R.id.strong_report_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(WrongStrongTwoActivity.this);
                EventBus.getDefault().post("refresh");
            }
        });

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("强化提高");


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


        lastView.setOnClickListener(this);
        nextView.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        if (mResponse == null || mResponse.getContent() == null || mResponse.getContent().size() <= 0) {
            return;
        }
//        int right = getRightCount();
//        int wrong = getWrongCount();
//        int total = right+wrong;
//        float r = (float)right;
//        float t = (float)total;
//        mRight.setText("答对" + right + "题");
//        mWrong.setText("答错" + wrong + "题");
//        mTotal.setText("共" + total + "题");
//        mLine.setSign(3, r / t);

        mDatas.addAll(mResponse.getExplainContent());
        showContent();
    }


    //显示一道题
    private void showContent() {

//        if (mDatas.size() == 0) {
//            return;
//        }
//
//        if (mDatas.size() == 1) {
//            lastView.setBackgroundResource(R.mipmap.common_btn_gray);
//            nextView.setBackgroundResource(R.mipmap.common_btn_gray);
//        } else {
//            if (curNum == 0) {
//                lastView.setBackgroundResource(R.mipmap.common_btn_gray);
//            } else {
//                lastView.setBackgroundResource(R.mipmap.common_button_green);
//            }
//
//            if (curNum == (mDatas.size() - 1)) {
//                nextView.setBackgroundResource(R.mipmap.common_btn_gray);
//            } else {
//                nextView.setBackgroundResource(R.mipmap.common_button_green);
//            }
//        }


        if (mDatas.size() == 0) {

            return;
        }


        if (mDatas.size() == 1) {
            lastView.setBackgroundResource(R.drawable.btn_white);
            nextView.setBackgroundResource(R.drawable.btn_white);

            nextView.setTextColor(Color.parseColor("#1AA97B"));
            lastView.setTextColor(Color.parseColor("#1AA97B"));

        } else {
            lastView.setBackgroundResource(R.drawable.btn_white);
            nextView.setBackgroundResource(R.drawable.btn_blue);
            nextView.setTextColor(Color.parseColor("#ffffff"));
            lastView.setTextColor(Color.parseColor("#1AA97B"));
        }


        if (tempCurNum < curNum) {
            lastView.setBackgroundResource(R.drawable.btn_white);
            nextView.setBackgroundResource(R.drawable.btn_blue);

            nextView.setTextColor(Color.parseColor("#ffffff"));
            lastView.setTextColor(Color.parseColor("#1AA97B"));
        } else if (tempCurNum >= curNum) {
            lastView.setBackgroundResource(R.drawable.btn_blue);
            nextView.setBackgroundResource(R.drawable.btn_white);

            nextView.setTextColor(Color.parseColor("#1AA97B"));
            lastView.setTextColor(Color.parseColor("#ffffff"));
        } else if (curNum == mDatas.size() - 1) {
            nextView.setTextColor(Color.parseColor("#1AA97B"));
            lastView.setTextColor(Color.parseColor("#1AA97B"));

            lastView.setBackgroundResource(R.drawable.btn_white);
            nextView.setBackgroundResource(R.drawable.btn_white);

        }

        if (curNum == 0 || mDatas.size() == 0) {
            lastView.setBackgroundResource(R.drawable.btn_white);
            nextView.setBackgroundResource(R.drawable.btn_white);

            nextView.setTextColor(Color.parseColor("#1AA97B"));
            lastView.setTextColor(Color.parseColor("#1AA97B"));

        }


        if (mDatas.size() > 1) {

            if (curNum == 0) {
                lastView.setBackgroundResource(R.drawable.btn_white);
                nextView.setBackgroundResource(R.drawable.btn_blue);

                nextView.setTextColor(Color.parseColor("#ffffff"));
                lastView.setTextColor(Color.parseColor("#1AA97B"));
            }


            if (mDatas.size() - 1 == curNum) {
                lastView.setBackgroundResource(R.drawable.btn_blue);
                nextView.setBackgroundResource(R.drawable.btn_white);

                nextView.setTextColor(Color.parseColor("#1AA97B"));
                lastView.setTextColor(Color.parseColor("#ffffff"));
            }


        }




        final ExplainContent explainContent = mDatas.get(curNum);
        curNumView.setText((curNum + 1) + "/" + mDatas.size());
        webViewSeven.loadData(explainContent.getQuestion());
        //答案
        qanswerLayout.removeAllViews();

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
        kgView.setText("《" + explainContent.getKnowledges().get(0).getKpoint() + "》");
        kgView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        kgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (explainContent != null && explainContent.getKnowledges() != null && explainContent.getKnowledges().size() > 0) {
                    Intent intent = new Intent(WrongStrongTwoActivity.this, CourseDetailActivity.class);
                    intent.putExtra("kid", explainContent.getKnowledges().get(0).getKid());
                    IntentUtil.startActivity(WrongStrongTwoActivity.this, intent);
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
                IntentUtil.finish(WrongStrongTwoActivity.this);
                break;
            case R.id.item_last_view:
                if (curNum == 0) {
                    lastView.setBackgroundResource(R.drawable.btn_white);

//                    if(curNum != mDatas.size()){
//                        nextView.setBackgroundResource(R.mipmap.common_btn_gray);
//                    }

                    return;
                }
                curNum--;
                showContent();
                tempCurNum = curNum;
                break;
            case R.id.item_next_view:
                if (curNum == (mDatas.size() - 1)) {
                    nextView.setBackgroundResource(R.drawable.btn_white);
                    return;
                }
                curNum++;
                showContent();
                tempCurNum = curNum;
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        IntentUtil.finish(this);
    }
}

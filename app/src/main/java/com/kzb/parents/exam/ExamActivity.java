package com.kzb.parents.exam;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.exam.bean.Answer;
import com.kzb.parents.exam.bean.ExamQuestion;
import com.kzb.parents.exam.bean.NeedKnow;
import com.kzb.parents.exam.bean.NeedKnowPro;
import com.kzb.parents.exam.bean.Question;
import com.kzb.parents.exam.bean.SubmitExam;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.util.TimeHelper;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.ProgressDialogView;
import com.kzb.parents.view.dialog.AtackDialog;
import com.kzb.parents.view.dialog.CommonTwoBtnDialog;
import com.kzb.parents.view.dialog.ExamDialog;
import com.kzb.parents.view.exam.ExamQuestionView;
import com.kzb.parents.wrong.WrongStrongTwoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/2.
 * 考试页面
 */

public class ExamActivity extends BaseActivity {
    String mId;
    String mType;//（章节诊断=‘zhangjie’全科诊断=‘quanke’真题诊断=‘zhenti 教师考试=‘jiaoshi’）
    String mKid;//如果kid有值，则表示强化学习
    String mPoint;//知识点强化，强化的内容
    String mTestId;//如果有testid,表示考试
    ExamQuestionView questionView;
    String mPkScore;//如果有值，表示pk场
    String from;

    private String zhangjieType;//1考试2针对训练


    ProgressDialogView progressBar;

    ExamDialog mExamDialog;
    AtackDialog mAtackDialog;
    private int mTotalTime = 0;
    private int mCount;

    private TextView timeCountView;
    private TextView curNumView;
    private TextView totalNumView;

    NeedKnow needKnow;

    CommonTwoBtnDialog mTwoBtnDialog;

    CommonTwoBtnDialog backDialog;

    ExamQuestion mResponse;

    TextView titleLeft, titleCenter, titleRight;

    private static final int HANDLER_TIME_CLICK = 101;
    private static final int HANDLER_TIME_OVER = 102;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_TIME_CLICK:
                    mCount--;
                    questionView.setTime(TimeHelper.getTime(mCount), TimeHelper.getTime(mTotalTime - mCount));
                    if (mCount > 0) {
                        if (mHandler != null) {
                            mHandler.sendEmptyMessageDelayed(HANDLER_TIME_CLICK, 1000);
                        }
                    } else {
                        if (mHandler != null) {
                            mHandler.sendEmptyMessage(HANDLER_TIME_OVER);
                        }
                    }
                    break;

                case HANDLER_TIME_OVER:
                    Toast.makeText(ExamActivity.this, "时间到，正在提交试卷", Toast.LENGTH_SHORT).show();
                    submit();
                    break;

            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_exam);

        LogUtils.e("TAG", "考试页面");

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        mTwoBtnDialog = new CommonTwoBtnDialog(ExamActivity.this);
        progressBar = new ProgressDialogView(ExamActivity.this);
        mExamDialog = new ExamDialog(ExamActivity.this);
        mAtackDialog = new AtackDialog(ExamActivity.this);


        dialogView = new DialogView(this);
        backDialog = new CommonTwoBtnDialog(this);
        backDialog.setContent("是否退出考试？");
        backDialog.setBtn("否", "是");
        backDialog.setOnDialogClickListener(new CommonTwoBtnDialog.OnDialogClickListener() {
            @Override
            public void onNagetiveClick() {

            }

            @Override
            public void onPositiveClick() {
                IntentUtil.finish(ExamActivity.this);
            }
        });

        mId = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
        mKid = getIntent().getStringExtra("kid");
        mPoint = getIntent().getStringExtra("point");
        mTestId = getIntent().getStringExtra("test_id");
        from = getIntent().getStringExtra("from");

        zhangjieType = getIntent().getStringExtra("zhangjieType");

        if (from == null) {
            from = "";
        }

        mPkScore = getIntent().getStringExtra("mPkScore");
        initView();
        initData();

        if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {
            titleCenter.setText("强化提高");
            mExamDialog.setTitle("强化须知");
            mExamDialog.setBtn("进入强化");

            getQuestion(AddressConfig.STRENGTH_LIST_URL);
        } else if (!TextUtils.isEmpty(mTestId)) {
            getNeedknow();
            if (from.equals("quanke")) {
                titleCenter.setText("全科考试");
                mExamDialog.setTitle("考试须知");
                mExamDialog.setBtn("进入考试");
            } else if (from.equals("zhangjie")) {
                titleCenter.setText("章节考试");
                mExamDialog.setTitle("考试须知");
                mExamDialog.setBtn("进入考试");
            }
//            getQuestion(AddressConfig.KAOSHI_EXAM_START);
        } else {

            getNeedknow();
            if (from.equals("quanke")) {
                titleCenter.setText("全科诊断");
//                getQuestion(AddressConfig.QUANKE_QUESTION_LIST);
                mExamDialog.setMode(ExamDialog.MODE_SB);
                mExamDialog.showRadio();

                mExamDialog.setTitle("诊断须知");
                mExamDialog.setBtn("进入诊断");
            } else if (from.equals("zhenti")) {
                titleCenter.setText("真题诊断");
//                getQuestion(AddressConfig.ZHENTI_QUESTION_LIST);

                mExamDialog.setTitle("诊断须知");
                mExamDialog.setBtn("进入诊断");
            } else if (from.equals("zhangjie")) {
                titleCenter.setText("章节诊断");
//                getQuestion(AddressConfig.CHAPTER_QUESTION_LIST);
                mExamDialog.showRadio();
                mExamDialog.setTitle("诊断须知");
                mExamDialog.setBtn("进入诊断");
            }
        }
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        timeCountView = getView(R.id.exam_title_one);
        questionView = getView(R.id.exam_question);
        curNumView = getView(R.id.exam_cur_num_view);
        totalNumView = getView(R.id.exam_cur_total_view);
        titleRight = getView(R.id.common_head_right);

        titleRight.setTextSize(16);

        titleRight.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setText("答题卡");

        questionView.setActivityV(ExamActivity.this);
        questionView.setCurNumView(curNumView);
        questionView.setTotalNumView(totalNumView);
        questionView.setTimeView(timeCountView);
        questionView.setRightView(titleRight);

        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDialog.showProgressDialog();
            }
        });

    }

    @Override
    protected void initData() {

    }

    /**
     * 交卷
     */
    public void submit() {
        //拼接我的答案
        final ExamQuestion question = questionView.getExamQuestion();

        LogUtils.e("TAG", "交卷");

        dialogView.handleDialog(true);
        final JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (question != null) {
            if (question.getContent() != null) {
                List<Question> questions = question.getContent();
                for (Question ques : questions) {
                    String answer = "";
                    if (ques.getAnswers() != null) {
                        for (Answer ans : ques.getAnswers()) {
                            if (ans.isCheck()) {
                                answer += ans.getAnswer_id() + ",";
                            }
                        }

                        if (TextUtils.isEmpty(answer)) {

                            answer = ques.getAnswers().get(ques.getAnswers().size() - 1).getAnswer_id();


                        } else {
                            answer = answer.substring(0, answer.length() - 1);
                        }
                    }

                    JSONObject object = new JSONObject();
                    try {
                        object.put("qid", ques.getQuestion_id());
                        object.put("aid", answer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    array.put(object);
                }
            }
        }


        if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {

            if (question != null && question.getContent() != null) {
                for (Question ques : question.getContent()) {
                    if (!ques.isDone()) {
                        if (ques.getAnswers() != null && ques.getAnswers().size() > 0) {
                            ques.setMyanswer(ques.getAnswers().get(ques.getAnswers().size() - 1).getAnswer_id());
                        }
                    }
                }
            }

//            dialogView.handleDialog(false);
//            Intent intent = new Intent(ExamActivity.this, StrongReportActivity.class);
//            intent.putExtra("question",new Gson().toJson(question));
//            intent.putExtra("point",mPoint);
//            intent.putExtra("kid",mKid);
//            IntentUtil.startActivityWithFinish(ExamActivity.this, intent);
//            return;
        }

        final List<Question> rightList = questionView.getRightQuestion();
        StringBuilder right = new StringBuilder();
        for (Question rq : rightList) {
            right.append("," + rq.getQuestion_id());
        }
        if (right.length() > 0) {
            right.deleteCharAt(0);
        }
        List<Question> wrongList = questionView.getWrongQuestion();
        StringBuilder wrong = new StringBuilder();
        for (Question wq : wrongList) {
            wrong.append("," + wq.getQuestion_id());
        }
        if (wrong.length() > 0) {
            wrong.deleteCharAt(0);
        }

        final String url;
        if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {
            //强化
            url = AddressConfig.EXAM_S_SUBMIT;

            try {
                json.put("uid", SpSetting.loadLoginInfo().getUid());
                json.put("kid", mKid);
                json.put("qids", right.toString());
                json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            url = AddressConfig.EXAM_SUBMIT;

            try {
                json.put("uid", SpSetting.loadLoginInfo().getUid());
                json.put("subject_id", SpSetting.loadLoginInfo().getSubject_id());

                json.put("type", mType);
                json.put("all_ques", array);


                if (!TextUtils.isEmpty(mTestId)) {
                    //考试
                    json.put("tt_id", mTestId);
                } else {
                    if ("zhenti".equals(from)) {
                        json.put("tt_id", mId);
                    } else if ("zhangjie".equals(from)) {
                        json.put("sid", mId);
                    } else {
                        json.put("tt_id", "0");
                    }
                }
                json.put("right_qids", right.toString());
                json.put("wrong_qids", wrong.toString());
                json.put("usedtime", mTotalTime - mCount);
                if (rightList.size() == 0) {
                    json.put("score", "0");
                } else {
                    if (needKnow != null && needKnow.getInfo() != null && needKnow.getInfo().getScore() > 0.5) {
                        json.put("score", rightList.size() * needKnow.getInfo().getScore());
                    } else {
                        DecimalFormat df = new DecimalFormat("##.#");
                        json.put("score", df.format(100.0 * rightList.size() / questionView.getQuesCount()));
                    }

                }
                json.put("oauth_token", SpSetting.loadLoginInfo().getOauth_token());
                json.put("oauth_token_secret", SpSetting.loadLoginInfo().getOauth_token_secret());
                json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<SubmitExam>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(ExamActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                LogUtils.e("TAG", "onError == " + e.getMessage(), " id == " + id);


            }

            @Override
            public void onResponse(SubmitExam response, int id) {
                dialogView.handleDialog(false);
                if (response.getContent() != null) {
                    if (response.errorCode == 0) {
                        if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {
                            if (question != null && question.getContent() != null) {
                                for (Question ques :
                                        question.getContent()) {
                                    if (!ques.isDone()) {
                                        if (ques.getAnswers() != null && ques.getAnswers().size() > 0) {
                                            ques.setMyanswer(ques.getAnswers().get(ques.getAnswers().size() - 1).getAnswer_id());
                                        }
                                    }
                                }
                            }

                            dialogView.handleDialog(false);
//                            Intent intent = new Intent(ExamActivity.this, WrongStrongActivity.class);
                            Intent intent = new Intent(ExamActivity.this, WrongStrongTwoActivity.class);
//                            Intent intent = new Intent(ExamActivity.this, StrongReportTwoActivity.class);
                            intent.putExtra("question", new Gson().toJson(question));
                            intent.putExtra("point", mPoint);
                            intent.putExtra("kid", mKid);
                            intent.putExtra("all", response.getContent().getAll());
                            intent.putExtra("done", response.getContent().getDone());
                            intent.putExtra("allper", response.getContent().getAllper());
                            intent.putExtra("thisper", response.getContent().getThisper());

                            IntentUtil.startActivityWithFinish(ExamActivity.this, intent);
                            return;
                        }
//                        final Intent intent = new Intent(ExamActivity.this, ReportTwoActivity.class);
                        final Intent intent = new Intent(ExamActivity.this, DiagNoseDetailActivity.class);

                        if (TextUtils.isEmpty(mTestId)) {
                            intent.putExtra("from", "diagnose");
                        }

                        intent.putExtra("test_id", response.getContent().getTest_id());
                        if (!TextUtils.isEmpty(mPkScore)) {
                            String time;
                            if (((mTotalTime - mCount) / 60) >= 10) {
                                time = (mTotalTime - mCount) / 60 + "";
                            } else {
                                time = "0" + (mTotalTime - mCount) / 60;
                            }
                            if ((mTotalTime - mCount) % 60 >= 10) {
                                time = time + ":" + (((mTotalTime - mCount) % 60)) + "分钟";
                            } else if ((mTotalTime - mCount) % 60 == 0) {
                                time = time + "分钟";
                            } else {
                                time = time + ":0" + (((mTotalTime - mCount) % 60)) + "分钟";
                            }
                            double score;
                            if (needKnow != null && needKnow.getInfo() != null && needKnow.getInfo().getScore() > 0.5) {
                                score = rightList.size() * needKnow.getInfo().getScore();
                            } else {

                                score = 100.0 * rightList.size() / questionView.getQuesCount();
                            }
                            DecimalFormat df = new DecimalFormat("##.#");
                            if (mPkScore.compareTo(score + "") > 0) {
                                mAtackDialog.setMsg("很遗憾！攻擂失败！", df.format(score) + "分", time, false);
                            } else {
                                mAtackDialog.setMsg("恭喜你！攻擂成功！", df.format(score) + "分", time, true);
                            }

                            mAtackDialog.show();
                            mAtackDialog.setCuOnBackPressed(new AtackDialog.ProgressDialogBack() {
                                @Override
                                public void onReport() {
                                    IntentUtil.startActivityWithFinish(ExamActivity.this, intent);
                                }

                                @Override
                                public void onReturn() {
                                    ExamActivity.this.finish();
                                }

                                @Override
                                public void onReplay() {
                                    Intent intent = new Intent(ExamActivity.this, ExamActivity.class);
                                    intent.putExtra("type", "quanke");
                                    intent.putExtra("from", "quanke");
                                    intent.putExtra("mPkScore", mPkScore);
                                    IntentUtil.startActivityWithFinish(ExamActivity.this, intent);
                                }
                            });
                        } else {
                            IntentUtil.startActivityWithFinish(ExamActivity.this, intent);
                        }

                    } else {
                        Toast.makeText(ExamActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                        LogUtils.e("TAG", "onError == " + "response.errorCode == 0");
                    }
                } else {
                    Toast.makeText(ExamActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    LogUtils.e("TAG", "onError == " + "response.getCount == null");
                }
            }


        });

    }

    /**
     * 获取题目
     */
    public void getQuestion(String url) {
        if (progressBar != null) {

            if (mResponse == null || mResponse.getContent() == null || mResponse.getContent().size() <= 0 || needKnow == null || needKnow.getInfo() == null || needKnow.getInfo().getCount() <= 0) {
                progressBar.setProgress(0);
                dialogView.handleDialog(true);
            } else {
                progressBar.handleDialog(true);
                if (needKnow != null && needKnow.getInfo() != null) {
                    progressBar.setProgress(mResponse.getContent().size() * 100 / needKnow.getInfo().getCount());
                } else {
                    dialogView.handleDialog(true);
                }
            }
        } else {
            dialogView.handleDialog(true);
        }
        JSONObject json = new JSONObject();
        try {
            if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {
                //强化训练
                json.put("kid", mKid);
                json.put("uid", SpSetting.loadLoginInfo().getUid());
                json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            } else if (!TextUtils.isEmpty(mTestId)) {
                //章节考试
                json.put("test_id", mTestId);
                json.put("type", zhangjieType);
                json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            } else {

                if (from.equals("quanke")) {
                    json.put("type", mExamDialog.getCheck() + "");
                    json.put("uid", SpSetting.loadLoginInfo().getUid());
                    json.put("id", SpSetting.loadLoginInfo().getSubject_id());
                    json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
                } else if (from.equals("zhenti")) {
                    json.put("id", mId);
                    json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
                } else if (from.equals("zhangjie")) {
                    json.put("uid", SpSetting.loadLoginInfo().getUid());
                    json.put("num", mExamDialog.getCheck() + "");
                    json.put("id", mId);
                    json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<ExamQuestion>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (progressBar != null) {
                    progressBar.handleDialog(false);
                }
                if (dialogView != null) {
                    dialogView.handleDialog(false);
                }
                Toast.makeText(ExamActivity.this, "获取题目失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ExamQuestion response, int id) {
                if (response.getContent() != null) {
                    if (true) {
                        //如果是诊断,则一次性返回
                        if (dialogView != null) {
                            dialogView.handleDialog(false);
                        }
                        mResponse = response;
                        questionView.setExamQuestion(mResponse);

                        if (mTotalTime > 0) {
                            mCount = mTotalTime;
                            if (mHandler != null) {
                                mHandler.sendEmptyMessageDelayed(HANDLER_TIME_CLICK, 1000);
                            }

                        } else {
                            questionView.hideTime();
                        }

                        questionView.setSubmitClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (questionView.isDoAll()) {
                                    submit();
                                } else {
                                    mTwoBtnDialog.setContent("题目未全部完成，是否提交试卷？");
                                    mTwoBtnDialog.setBtn("取消", "交卷");
                                    mTwoBtnDialog.showProgressDialog();
                                    mTwoBtnDialog.setOnDialogClickListener(new CommonTwoBtnDialog.OnDialogClickListener() {
                                        @Override
                                        public void onNagetiveClick() {

                                        }

                                        @Override
                                        public void onPositiveClick() {
                                            submit();
                                        }
                                    });
                                }
                            }
                        });
                        return;
                    }


                    if (mResponse == null) {
                        mResponse = response;
                        getDiapatchQuestion();
                    } else {
                        if (needKnow == null || needKnow.getInfo() == null || mResponse.getContent().size() >= needKnow.getInfo().getCount()) {
                            if (progressBar != null) {
                                progressBar.handleDialog(false);
                            }

                            if (needKnow != null && needKnow.getInfo() != null) {
                                mResponse.setContent(mResponse.getContent().subList(0, needKnow.getInfo().getCount()));
                                mCount = mTotalTime;
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessageDelayed(HANDLER_TIME_CLICK, 1000);
                                }
                            } else {
                                //不计算时间

                            }
                            questionView.setExamQuestion(mResponse);
                            questionView.setSubmitClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (questionView.isDoAll()) {
                                        submit();
                                    } else {
                                        mTwoBtnDialog.setContent("题目未全部完成，是否提交试卷？");
                                        mTwoBtnDialog.setBtn("取消", "交卷");
                                        mTwoBtnDialog.showProgressDialog();
                                        mTwoBtnDialog.setOnDialogClickListener(new CommonTwoBtnDialog.OnDialogClickListener() {
                                            @Override
                                            public void onNagetiveClick() {

                                            }

                                            @Override
                                            public void onPositiveClick() {
                                                submit();
                                            }
                                        });
                                    }
                                }
                            });

                        } else {
                            mResponse.getContent().addAll(response.getContent());
                            getDiapatchQuestion();
                        }
                    }
                } else {
                    if (progressBar != null) {
                        progressBar.handleDialog(false);
                    }
                    if (dialogView != null) {
                        dialogView.handleDialog(false);
                    }
                    Toast.makeText(ExamActivity.this, "暂时没有对应试题...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 获取须知
     */
    public void getNeedknow() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        if (!TextUtils.isEmpty(mTestId)) {
            try {
                json.put("test_id", mTestId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String url;
        if (!TextUtils.isEmpty(mTestId)) {
            //考试，须知
            url = AddressConfig.NEED_KENO_EXAM;
            try {
                json.put("type", zhangjieType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //诊断，强化，须知
            if ("zhenti".equals(mType)) {
                url = AddressConfig.NEED_KENO_ZHENTI;
                try {
                    json.put("id", mId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                url = AddressConfig.NEED_KENO_DIAGNOSE;
            }
        }

        httpConfig.doPostRequest(HttpConfig.getBaseRequest(url, json), new GenericsCallback<NeedKnowPro>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(ExamActivity.this, "获取须知失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(NeedKnowPro response, int id) {
                dialogView.handleDialog(false);
                if (response != null && response.getContent() != null) {
                    if (response.errorCode == 0) {
                        //成功
                        needKnow = response.getContent();

                        mExamDialog.setMessage(needKnow.getXuzhi()[0], needKnow.getXuzhi()[1], needKnow.getXuzhi()[2]);
                        mExamDialog.setCuOnBackPressed(new ExamDialog.ProgressDialogBack() {
                            @Override
                            public void onBackPressed() {

                                getDiapatchQuestion();
                            }
                        });
                        mExamDialog.showProgressDialog();

                        mTotalTime = needKnow.getInfo().getTime() * 60;

                    } else {
                        Toast.makeText(ExamActivity.this, "获取须知失败", Toast.LENGTH_SHORT).show();
                        IntentUtil.finish(ExamActivity.this);
                    }
                } else {
                    Toast.makeText(ExamActivity.this, "获取须知失败", Toast.LENGTH_SHORT).show();
                    IntentUtil.finish(ExamActivity.this);
                }
            }
        });
    }


    public void getDiapatchQuestion() {
        if (!TextUtils.isEmpty(mKid) || !TextUtils.isEmpty(mPoint)) {
            getQuestion(AddressConfig.STRENGTH_LIST_URL);
        } else if (!TextUtils.isEmpty(mTestId)) {
            getQuestion(AddressConfig.KAOSHI_EXAM_START);
        } else {
            if (from.equals("quanke")) {
                getQuestion(AddressConfig.CHAPTER_QUESTION_QK);
            } else if (from.equals("zhenti")) {
                getQuestion(AddressConfig.ZHENTI_QUESTION_LIST);
            } else if (from.equals("zhangjie")) {
                getQuestion(AddressConfig.CHAPTER_QUESTION_ZJ);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backDialog.showProgressDialog();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class Item {
        private String qid;
        private String aid;

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }
    }
}

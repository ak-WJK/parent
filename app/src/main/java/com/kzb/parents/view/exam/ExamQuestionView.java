package com.kzb.parents.view.exam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.log.Log;
import com.kzb.parents.R;
import com.kzb.parents.exam.adapter.ItemAdapter;
import com.kzb.parents.exam.bean.Answer;
import com.kzb.parents.exam.bean.ExamQuestion;
import com.kzb.parents.exam.bean.Question;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.view.MyGridView;
import com.kzb.parents.view.QuesWebViewFour;
import com.kzb.parents.view.QuesWebViewSix;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/12/1
 * 时间：下午11:24
 * 注释：一个题目列表
 * <p>
 * 自定义按钮(上一个 下一个 交卷)列表
 ********************/
public class ExamQuestionView extends LinearLayout {
    public int currentQuestion;

    public ExamQuestion mExamQuestion;
    public boolean isSingle = true;//是否单选

    private TextView currentText;
    //    private QuesTextView questionText;
    private QuesWebViewFour questionText;
    private LinearLayout answerLayout;
    private ItemAdapter mAdapter;
    private List<TextView> mAnswerClicks = new ArrayList<>();
    //    private TextView currStaText, currUseText;
    private Button submitExam;
    //    LinearLayout useTimeLayout;
    private int timuDo = 0;
    private int timuUnDo = 0;

    private TextView timeView;
    private TextView curNumView;
    private TextView totalNumView;
    private TextView titleRight;

    private Activity activity;
    private View cardView;
    private TextView cardBackView;
    private TextView cardSubmitView;
    private LinearLayout cardContentLayout;
    ViewGroup cardViewGroup;

    public void setActivityV(Activity activity) {
        this.activity = activity;

        cardViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        cardView = (View) LayoutInflater.from(activity).inflate(R.layout.card_layout_view, null);
        cardViewGroup.addView(cardView);
        cardView.setVisibility(View.GONE);
        initCardView();
    }

    public void setRightView(final TextView titleRight) {
        this.titleRight = titleRight;

        titleRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getVisibility() == View.GONE) {
                    cardView.setVisibility(VISIBLE);
                } else {
                    Log.e("kzb", "已经打开窗口了......");
                }
            }
        });

    }

    public void setCurNumView(TextView curNumView) {
        this.curNumView = curNumView;
    }

    public void setTotalNumView(TextView totalNumView) {
        this.totalNumView = totalNumView;
    }

    public void setTimeView(TextView timeView) {
        this.timeView = timeView;
    }

    public ExamQuestionView(Context context) {
        super(context);
    }

    public ExamQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void initCardView() {
        cardBackView = (TextView) cardView.findViewById(R.id.card_head_left);
        cardSubmitView = (TextView) cardView.findViewById(R.id.card_commit_view);
        cardContentLayout = (LinearLayout) cardView.findViewById(R.id.card_content_layout);

        cardBackView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.GONE);
            }
        });
    }


    public void putLayout() {
        this.setOrientation(VERTICAL);
        //一开始，第一题
        final Question mQuestion = mExamQuestion.getContent().get(0);
        if (mQuestion != null && mQuestion.getType_id() != null) {
            if (mQuestion.getType_id().equals("1")) {
                isSingle = false;
            } else {
                isSingle = true;
            }
        }

        //题目区
        LinearLayout mainQuestion = new LinearLayout(getContext());
        LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        p.weight = 3;
        mainQuestion.setOrientation(VERTICAL);
        mainQuestion.setLayoutParams(p);

        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setVerticalScrollBarEnabled(false);
        p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p.weight = 1;
        scrollView.setLayoutParams(p);

        LinearLayout scrollInLayout = new LinearLayout(getContext());
        p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollInLayout.setLayoutParams(p);
        scrollInLayout.setOrientation(VERTICAL);
        scrollView.addView(scrollInLayout);
        mainQuestion.addView(scrollView);


        curNumView.setText((currentQuestion + 1) + "");
        curNumView.setTextColor(getResources().getColor(R.color.blue));
        totalNumView.setText("/" + mExamQuestion.getContent().size());
        totalNumView.setTextColor(getResources().getColor(R.color.blue));

        p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayout questionLayout = new LinearLayout(getContext());
        questionLayout.setLayoutParams(p);
        questionLayout.setOrientation(HORIZONTAL);


        p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.weight = 1;
        questionText = new QuesWebViewFour(getContext());
//        questionText.setLayoutParams(p);
//        questionText.setTextColor(Color.BLACK);
//        questionText.setTextSize(20);
//        questionText.setNetText(mQuestion.getQuestion());
//        questionText.setLineSpacing(10,1.2f);

        questionText.loadData(mQuestion.getQuestion());
        questionLayout.addView(questionText);

        scrollInLayout.addView(questionLayout);

        answerLayout = new LinearLayout(getContext());
        p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        answerLayout.setOrientation(VERTICAL);
        answerLayout.setLayoutParams(p);

        setAnswerLayout(mQuestion);
        scrollInLayout.addView(answerLayout);

        //上一题，下一题
        p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        p.topMargin = DensityUtil.dip2px(getContext(), 24);
        LinearLayout skipLayout = new LinearLayout(getContext());
        skipLayout.setGravity(Gravity.CENTER_VERTICAL);
        skipLayout.setLayoutParams(p);
        skipLayout.setOrientation(HORIZONTAL);

        final Button lastBtn = new Button(getContext());
        final Button nextBtn = new Button(getContext());
        submitExam = new Button(getContext());
        p = new LayoutParams(DensityUtil.dip2px(getContext(), 80), DensityUtil.dip2px(getContext(), 35));
        p.leftMargin = DensityUtil.dip2px(getContext(), 10);
        lastBtn.setLayoutParams(p);
        lastBtn.setPadding(DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6), DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6));
        lastBtn.setText("上一题");
        lastBtn.setTextSize(12);
        lastBtn.setTextColor(Color.WHITE);
        lastBtn.setBackgroundResource(R.mipmap.kaoshi_green);
        lastBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion > 0) {
                    currentQuestion--;
                    setQuestion();
                }
                if (currentQuestion > 0) {
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }
                if (currentQuestion < mExamQuestion.getContent().size() - 1) {
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }

            }
        });

        p = new LayoutParams(DensityUtil.dip2px(getContext(), 80), DensityUtil.dip2px(getContext(), 35));
        p.leftMargin = DensityUtil.dip2px(getContext(), 10);
        nextBtn.setLayoutParams(p);
        nextBtn.setPadding(DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6), DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6));
        nextBtn.setText("下一题");
        nextBtn.setTextSize(12);
        nextBtn.setTextColor(Color.WHITE);
        if (mExamQuestion != null && mExamQuestion.getContent() != null && mExamQuestion.getContent().size() > 1) {

            nextBtn.setBackgroundResource(R.mipmap.kaoshi_green);
        } else {
            nextBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
        }

        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion < mExamQuestion.getContent().size() - 1) {
                    currentQuestion++;
                    setQuestion();
                }
                if (currentQuestion > 0) {
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }
                if (currentQuestion < mExamQuestion.getContent().size() - 1) {
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }
            }
        });


        //交卷按鈕
        p = new LayoutParams(DensityUtil.dip2px(getContext(), 80), DensityUtil.dip2px(getContext(), 35));
        p.leftMargin = DensityUtil.dip2px(getContext(), 10);
        submitExam.setLayoutParams(p);
        submitExam.setPadding(DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6), DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6));
        submitExam.setText("交卷");
        submitExam.setTextSize(15);
        submitExam.setTextColor(Color.WHITE);
        submitExam.setBackgroundResource(R.mipmap.jiaojuan);
        submitExam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        View skipS = new View(getContext());
        p = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        p.weight = 1;
        skipS.setLayoutParams(p);

        View skipE = new View(getContext());
        p = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        p.weight = 1;
        skipE.setLayoutParams(p);

        skipLayout.addView(skipS);
        skipLayout.addView(lastBtn);
        skipLayout.addView(nextBtn);
        skipLayout.addView(submitExam);
        skipLayout.addView(skipE);

        mainQuestion.addView(skipLayout);

        //选题区
        LinearLayout checkLayout = new LinearLayout(getContext());
        p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p.weight = 1;
        checkLayout.setLayoutParams(p);
        checkLayout.setOrientation(VERTICAL);

        //gridview
        MyGridView gridView = new MyGridView(getContext());
        List<String> btnList = new ArrayList<>();
//        for (int i = 0; i < mExamQuestion.getContent().size(); i++) {
        for (int i = 0; i < 100; i++) {
            btnList.add((1 + i) + "");
        }
        p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p.weight = 1;
        p.topMargin = DensityUtil.dip2px(getContext(), 20);
        p.leftMargin = DensityUtil.dip2px(getContext(), 10);
        p.rightMargin = DensityUtil.dip2px(getContext(), 10);
        gridView.setLayoutParams(p);
        gridView.setNumColumns(7);

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(getContext(), mExamQuestion.getContent(), R.layout.item_exam_question_btn_two);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        gridView.setAdapter(mAdapter);
        gridView.setHorizontalSpacing(DensityUtil.dip2px(getContext(), 12));
        gridView.setVerticalSpacing(DensityUtil.dip2px(getContext(), 12));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < mExamQuestion.getContent().size()) {
                    currentQuestion = position;
                    setQuestion();
                }
                if (currentQuestion > 0) {
//                    lastBtn.setBackgroundResource(R.drawable.answer_skip_btn_bg_click);
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    lastBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }
                if (currentQuestion < mExamQuestion.getContent().size() - 1) {
//                    nextBtn.setBackgroundResource(R.drawable.answer_skip_btn_bg_click);
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_green);
                } else {
                    nextBtn.setBackgroundResource(R.mipmap.kaoshi_gray);
                }
                cardView.setVisibility(GONE);
            }
        });

//        submitExam = new Button(getContext());
//        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        p.topMargin = DensityUtil.dip2px(getContext(), 10);
//        p.gravity = Gravity.CENTER_HORIZONTAL;
//        submitExam.setLayoutParams(p);
//        submitExam.setPadding(DensityUtil.dip2px(getContext(), 42), DensityUtil.dip2px(getContext(), 6), DensityUtil.dip2px(getContext(), 42), DensityUtil.dip2px(getContext(), 6));
//        submitExam.setBackgroundResource(R.mipmap.kaoshi_red);
//        submitExam.setGravity(Gravity.CENTER);
//        submitExam.setText("交卷");
//        submitExam.setTextSize(20);
//        submitExam.setTextColor(Color.WHITE);

        checkLayout.addView(gridView);
//        checkLayout.addView(submitExam);

        addView(mainQuestion);
//        addView(checkLayout);

        cardContentLayout.addView(checkLayout);

        postInvalidate();
    }

    private void setAnswerLayout(final Question mQuestion) {
        LayoutParams p;//添加答案
        answerLayout.removeAllViews();
        mAnswerClicks.clear();
        if (null == mQuestion || null == mQuestion.getAnswers()) {
            return;
        }
        for (int i = 0; i < mQuestion.getAnswers().size(); i++) {
            final Answer answer = mQuestion.getAnswers().get(i);

            p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            p.leftMargin = DensityUtil.dip2px(getContext(), 10);
            p.topMargin = DensityUtil.dip2px(getContext(), 10);
            LinearLayout answerItemLayout = new LinearLayout(getContext());
            answerItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            answerItemLayout.setLayoutParams(p);

            //按钮
            p = new LayoutParams(DensityUtil.dip2px(getContext(), 25), DensityUtil.dip2px(getContext(), 25));
//            p.rightMargin = DensityUtil.dip2px(getContext(), 5);
            final TextView answerClick = new TextView(getContext());
            answerClick.setLayoutParams(p);
            answerClick.setTextSize(14);
            answerClick.setTextColor(Color.WHITE);
            answerClick.setText(String.valueOf((char) (i + 65)));
            if (answer.isCheck()) {
                answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
            } else {
                answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
            }
            answerClick.setGravity(Gravity.CENTER);
            answerItemLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSingle) {
                        if (!answer.isCheck()) {
                            List<Answer> answers = mQuestion.getAnswers();
                            for (int i = 0; i < answers.size(); i++) {
                                Answer ans = answers.get(i);
                                if (ans != answer) {
                                    ans.setCheck(false);
                                    mAnswerClicks.get(i).setTag("0");
                                    mAnswerClicks.get(i).setBackgroundResource(R.drawable.answer_btn_gray);
                                }
                            }
                            answerClick.setTag("1");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                            answer.setCheck(true);
                            mQuestion.setMyanswer(answer.getAnswer_id() + ",");
                        }
                    } else {
                        if (answer.isCheck()) {
                            answerClick.setTag("0");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
                            answer.setCheck(false);
                            if (!TextUtils.isEmpty(mQuestion.getMyanswer())) {
                                mQuestion.getMyanswer().replace(answer.getAnswer_id() + ",", "");
                            }
                        } else {
                            answerClick.setTag("1");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                            answer.setCheck(true);
                            mQuestion.setMyanswer(mQuestion.getMyanswer() + answer.getAnswer_id() + ",");

                        }
                    }

                    if (mQuestion.getAnswers() != null) {
                        //是否有答题
                        mQuestion.setDone(false);
                        for (Answer answer : mQuestion.getAnswers()) {
                            if (answer.isCheck()) {
                                mQuestion.setDone(true);
                            }
                        }
                    }
                    timuDo = 0;
                    timuUnDo = 0;
                    for (Question q :
                            mExamQuestion.getContent()) {
                        if (q.isDone()) {
                            timuDo++;
                        } else {
                            timuUnDo++;
                        }
                    }
                    setTime(null, null);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });

            //答案
            p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            QuesTextView answerMsg = new QuesTextView(getContext());
//            answerMsg.setNeedFilter(true);
//            answerMsg.setLayoutParams(p);
//            answerMsg.setTextSize(15);
//            answerMsg.setTextColor(Color.parseColor("#222222"));
//            answerMsg.setNetText(answer.getAnswer());
//            answerMsg.setExpandLevel(10);
//            answerMsg.setNeedExpandHeight(true);
//            quesWebViewThree.loadData(answer.getAnswer());


            QuesWebViewSix quesWebViewSix = new QuesWebViewSix(getContext());

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

            quesWebViewSix.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (isSingle) {
                            if (!answer.isCheck()) {
                                List<Answer> answers = mQuestion.getAnswers();
                                for (int i = 0; i < answers.size(); i++) {
                                    Answer ans = answers.get(i);
                                    if (ans != answer) {
                                        ans.setCheck(false);
                                        mAnswerClicks.get(i).setTag("0");
                                        mAnswerClicks.get(i).setBackgroundResource(R.drawable.answer_btn_gray);
                                    }
                                }
                                answerClick.setTag("1");
                                answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                                answer.setCheck(true);
                                mQuestion.setMyanswer(answer.getAnswer_id() + ",");
                            }
                        } else {
                            if (answer.isCheck()) {
                                answerClick.setTag("0");
                                answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
                                answer.setCheck(false);
                                if (!TextUtils.isEmpty(mQuestion.getMyanswer())) {
                                    mQuestion.getMyanswer().replace(answer.getAnswer_id() + ",", "");
                                }
                            } else {
                                answerClick.setTag("1");
                                answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                                answer.setCheck(true);
                                mQuestion.setMyanswer(mQuestion.getMyanswer() + answer.getAnswer_id() + ",");
                            }
                        }

                        if (mQuestion.getAnswers() != null) {
                            //是否有答题
                            mQuestion.setDone(false);
                            for (Answer answer : mQuestion.getAnswers()) {
                                if (answer.isCheck()) {
                                    mQuestion.setDone(true);
                                }
                            }
                        }
                        timuDo = 0;
                        timuUnDo = 0;
                        for (Question q :
                                mExamQuestion.getContent()) {
                            if (q.isDone()) {
                                timuDo++;
                            } else {
                                timuUnDo++;
                            }
                        }
                        setTime(null, null);
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

//                        Toast.makeText(activity,"..my.333.clicll....",Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(activity,"..my.333.clicll....",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            quesWebViewSix.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "..my..clicll....", Toast.LENGTH_SHORT).show();


                    if (isSingle) {
                        if (!answer.isCheck()) {
                            List<Answer> answers = mQuestion.getAnswers();
                            for (int i = 0; i < answers.size(); i++) {
                                Answer ans = answers.get(i);
                                if (ans != answer) {
                                    ans.setCheck(false);
                                    mAnswerClicks.get(i).setTag("0");
                                    mAnswerClicks.get(i).setBackgroundResource(R.drawable.answer_btn_gray);
                                }
                            }
                            answerClick.setTag("1");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                            answer.setCheck(true);
                            mQuestion.setMyanswer(answer.getAnswer_id() + ",");
                        }
                    } else {
                        if (answer.isCheck()) {
                            answerClick.setTag("0");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
                            answer.setCheck(false);
                            if (!TextUtils.isEmpty(mQuestion.getMyanswer())) {
                                mQuestion.getMyanswer().replace(answer.getAnswer_id() + ",", "");
                            }
                        } else {
                            answerClick.setTag("1");
                            answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
                            answer.setCheck(true);
                            mQuestion.setMyanswer(mQuestion.getMyanswer() + answer.getAnswer_id() + ",");
                        }
                    }

                    if (mQuestion.getAnswers() != null) {
                        //是否有答题
                        mQuestion.setDone(false);
                        for (Answer answer : mQuestion.getAnswers()) {
                            if (answer.isCheck()) {
                                mQuestion.setDone(true);
                            }
                        }
                    }
                    timuDo = 0;
                    timuUnDo = 0;
                    for (Question q :
                            mExamQuestion.getContent()) {
                        if (q.isDone()) {
                            timuDo++;
                        } else {
                            timuUnDo++;
                        }
                    }
                    setTime(null, null);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
            mAnswerClicks.add(answerClick);
            answerItemLayout.addView(answerClick);
//            answerItemLayout.addView(answerMsg);
            answerItemLayout.addView(quesWebViewSix);

            answerLayout.addView(answerItemLayout);
        }
    }

    /**
     * 切换题目
     */
    public void setQuestion() {
        Question question = mExamQuestion.getContent().get(currentQuestion);

        Log.e("tttt", "question=" + question.toString());

        isSingle = true;
        if (question != null && question.getType_id() != null) {
            if (question.getType_id().equals("1")) {
                isSingle = false;
            } else {
                isSingle = true;
            }
        }

        curNumView.setText((1 + currentQuestion) + "");
//        questionText.setNetText(question.getQuestion());
        questionText.loadData(question.getQuestion());

        setAnswerLayout(question);
    }


    public ExamQuestion getExamQuestion() {
        return mExamQuestion;
    }

    /**
     * 获取正确答案
     *
     * @return
     */
    public List<Question> getRightQuestion() {
        List<Question> ret = new ArrayList<>();
        if (mExamQuestion != null && mExamQuestion.getContent() != null) {
            for (int i = 0; i < mExamQuestion.getContent().size(); i++) {
                Question question = mExamQuestion.getContent().get(i);
                boolean isTrue = true;
                if (question != null && question.getAnswers() != null) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        Answer answer = question.getAnswers().get(j);
                        if (answer.isCheck()) {
                            if (answer.istrue == null || answer.istrue.equals("0")) {
                                isTrue = false;
                            }
                        } else {
                            if (answer.istrue == null || answer.istrue.equals("1")) {
                                isTrue = false;
                            }
                        }
                    }
                }
                if (isTrue) {
                    ret.add(question);
                }
            }
        }

        return ret;
    }

    //获取错误答案
    public List<Question> getWrongQuestion() {
        List<Question> ret = new ArrayList<>();
        if (mExamQuestion != null && mExamQuestion.getContent() != null) {
            for (int i = 0; i < mExamQuestion.getContent().size(); i++) {
                Question question = mExamQuestion.getContent().get(i);
                boolean isTrue = true;
                if (question != null && question.getAnswers() != null) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        Answer answer = question.getAnswers().get(j);
                        if (answer.isCheck()) {
                            if (answer.istrue == null || answer.istrue.equals("0")) {
                                isTrue = false;
                            }
                        } else {
                            if (answer.istrue == null || answer.istrue.equals("1")) {
                                isTrue = false;
                            }
                        }
                    }
                }
                if (!isTrue) {
                    ret.add(question);
                }
            }
        }

        return ret;
    }

    public int getQuesCount() {
        if (mExamQuestion == null || mExamQuestion.getContent() == null) {
            return 0;
        } else {
            return mExamQuestion.getContent().size();
        }
    }

    /**
     * 是否完成
     */
    public boolean isDoAll() {
        boolean isDoAll = true;
        for (Question question : mExamQuestion.getContent()) {
            if (!question.isDone()) {
                isDoAll = false;
            }
        }
        return isDoAll;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        removeAllViews();
        this.mExamQuestion = examQuestion;
        if (mExamQuestion != null && mExamQuestion.getContent() != null && mExamQuestion.getContent().size() > 0) {
            timuUnDo = examQuestion.getContent().size();
            putLayout();
            setQuestion();
        } else {
            timuUnDo = 0;
        }
    }


    public void setSubmitClick(OnClickListener listener) {
        if (submitExam != null) {
            submitExam.setOnClickListener(listener);
//            return;
        }

        if (cardSubmitView != null) {
            cardSubmitView.setOnClickListener(listener);
//            return;
        }
    }

    public void setTime(String time, String useTime) {
        if (!TextUtils.isEmpty(time) && !TextUtils.isEmpty(useTime)) {
            if (timeView != null) {
//                currStaText.setText(Html.fromHtml("已答: <font color='#1ec0f3'>" + timuDo + "</font> 道，未答: <font color='#1ec0f3'>" + timuUnDo + "</font> 道<br>剩余: <font color='#1ec0f3'>" + time + "</font> 分钟"));
//                timeView.setText(Html.fromHtml("已答: <font color='#1ec0f3'>" + timuDo + "</font> 道，未答: <font color='#1ec0f3'>" + timuUnDo + "</font> 道<br>用时: <font color='#1ec0f3'>" +useTime + "</font> 分钟"));
                timeView.setText(Html.fromHtml("已答完: <font color='#1ec0f3'>" + timuDo + "</font> 道题，还有 <font color='#1ec0f3'>" + timuUnDo + "</font> 道未答完，考试剩余: <font color='#1ec0f3'>" + time + "</font> 分钟"));
            }
//            if (timeView != null) {
//
//                timeView.setText(Html.fromHtml("剩余: <font color='#1ec0f3'>" + time + "</font> 分钟"));
////                currUseText.setText(Html.fromHtml("用时: <font color='#1ec0f3'>" +useTime + "</font> 分钟"));
//
//            }
        } else {
            if (timeView != null) {
                timeView.setText(Html.fromHtml("已答完: <font color='#1ec0f3'>" + timuDo + "</font> 道题，还有 <font color='#1ec0f3'>" + timuUnDo + "</font> 道未答完"));
            }
        }
    }

    /**
     * 强化提高，不显示时间
     */
    public void hideTime() {
        if (timeView != null) {
            timeView.setVisibility(View.GONE);
        }
        setTime("", "");
    }


    public void setEmpty() {
        questionText = null;
        questionText.clearHistory();
        questionText.clearCache(true);
        questionText.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
        questionText.freeMemory();
        questionText.pauseTimers();
        questionText = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
    }

}
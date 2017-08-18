//package com.kzb.parents.view.exam;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.kaozhibao.R;
//import com.kaozhibao.diagnose.bean.Answer;
//import com.kaozhibao.diagnose.bean.ExamQuestion;
//import com.kaozhibao.diagnose.bean.Question;
//import com.kaozhibao.util.DensityUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///********************
// * 作者：malus
// * 日期：16/12/1
// * 时间：下午11:24
// * 注释：单个题目
// ********************/
//public class QuestionView extends LinearLayout {
//    public Question mQuestion;
//    public ExamQuestion mExamQuestion;
//
//    public QuestionView(Context context) {
//        super(context);
//    }
//
//    public QuestionView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mQuestion = new Question();
//        mQuestion.setQuestion("解二元一次方程组<br>\r\nAsdasd<br>\r\nAsd<br>");
//        mQuestion.setQuestion_id("24");
//        List<Answer> answers = new ArrayList<>();
//        Answer answer = new Answer();
//        answer.setAnswer("&lt;p&gt;A&lt;/p&gt;");
//        answer.setIstrue("0");
//        answers.add(answer);
//
//        answer = new Answer();
//        answer.setAnswer("&lt;p&gt;B&lt;/p&gt;");
//        answer.setIstrue("1");
//        answers.add(answer);
//
//        answer = new Answer();
//        answer.setAnswer("&lt;p&gt;C&lt;/p&gt;");
//        answer.setIstrue("1");
//        answers.add(answer);
//
//        answer = new Answer();
//        answer.setAnswer("&lt;p&gt;D&lt;/p&gt;");
//        answer.setIstrue("0");
//        answers.add(answer);
//
//        answer = new Answer();
//        answer.setAnswer("这题我不会");
//        answer.setIstrue("0");
//        answers.add(answer);
//
//        mQuestion.setAnswers(answers);
//        putLayout();
//    }
//
//    public void putLayout(){
//        this.setOrientation(VERTICAL);
//
//        LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        LinearLayout questionLayout = new LinearLayout(getContext());
//        questionLayout.setLayoutParams(p);
//        questionLayout.setOrientation(HORIZONTAL);
//
//        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        TextView currQuestion = new TextView(getContext());
//        currQuestion.setLayoutParams(p);
//        currQuestion.setTextColor(Color.BLACK);
//        currQuestion.setTextSize(38);
//        currQuestion.setText("1");
//
//        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        p.rightMargin = DensityUtil.dip2px(getContext(),16);
//        TextView totalQuestion = new TextView(getContext());
//        totalQuestion.setLayoutParams(p);
//        totalQuestion.setTextColor(Color.BLACK);
//        totalQuestion.setTextSize(30);
//        totalQuestion.setText("/100");
//
//        p = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
//        p.weight = 1;
//        TextView question = new TextView(getContext());
//        question.setLayoutParams(p);
//        question.setTextColor(Color.BLACK);
//        question.setTextSize(30);
//        question.setText(mQuestion.getQuestion());
//
//        questionLayout.addView(currQuestion);
//        questionLayout.addView(totalQuestion);
//        questionLayout.addView(question);
//
//        this.addView(questionLayout);
//
//        //添加答案
//        for (int i = 0; i < mQuestion.getAnswers().size(); i++) {
//            Answer answer = mQuestion.getAnswers().get(i);
//
//            p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            p.leftMargin = DensityUtil.dip2px(getContext(),20);
//            p.topMargin = DensityUtil.dip2px(getContext(),20);
//            LinearLayout answerItemLayout = new LinearLayout(getContext());
//            answerItemLayout.setGravity(Gravity.CENTER_VERTICAL);
//            answerItemLayout.setLayoutParams(p);
//
//            //按钮
//            p = new LayoutParams(DensityUtil.dip2px(getContext(), 50), DensityUtil.dip2px(getContext(), 50));
//            p.rightMargin = DensityUtil.dip2px(getContext(),30);
//            final TextView answerClick = new TextView(getContext());
//            answerClick.setLayoutParams(p);
//            answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
//            answerClick.setTextSize(30);
//            answerClick.setTextColor(Color.WHITE);
//            answerClick.setText(String.valueOf((char)(i+65)));
//            answerClick.setTag("0");
//            answerClick.setGravity(Gravity.CENTER);
//            answerClick.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String click =  answerClick.getTag().toString();
//                    if (click.equals("0")) {
//                        answerClick.setTag("1");
//                        answerClick.setBackgroundResource(R.drawable.answer_btn_blue);
//                    } else {
//                        answerClick.setTag("0");
//                        answerClick.setBackgroundResource(R.drawable.answer_btn_gray);
//                    }
//
//                }
//            });
//
//            //答案
//            p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            TextView answerMsg = new TextView(getContext());
//            answerMsg.setLayoutParams(p);
//            answerMsg.setTextSize(25);
//            answerMsg.setTextColor(Color.parseColor("#222222"));
//            answerMsg.setText(answer.getAnswer());
//
//
//            answerItemLayout.addView(answerClick);
//            answerItemLayout.addView(answerMsg);
//
//            this.addView(answerItemLayout);
//        }
//
//
//    }
//
//    public Question getQuestion() {
//        return mQuestion;
//    }
//
//    public void setQuestion(Question question) {
//        this.mQuestion = question;
//    }
//
//}

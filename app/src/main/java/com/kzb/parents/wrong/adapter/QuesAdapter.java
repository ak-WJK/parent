package com.kzb.parents.wrong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.view.QuesTextView;
import com.kzb.parents.view.QuesWebViewFive;
import com.kzb.parents.wrong.model.Answer;
import com.kzb.parents.wrong.model.ExplainContent;

import java.util.List;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class QuesAdapter  extends CommonAdapter<ExplainContent> {

    private int typeVal;//诊断报告，跳转过来的需要0为默认值，红色，1为绿色

    public QuesAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    private String myanswr;
    private StringBuffer trueanswer = new StringBuffer();

    public void setTypeVal(int typeVal) {
        this.typeVal = typeVal;
    }

    @Override
    protected void convert(final ViewHolder holder, final ExplainContent o) {
        holder.setText(R.id.item_ques_current, (1 + holder.getPosition()) + "/"+getCount());
        ((QuesWebViewFive) holder.getView(R.id.item_ques_ques)).loadData(o.getQuestion());

        LinearLayout mAnswer = holder.getView(R.id.item_ques_ans_layout);
        mAnswer.removeAllViews();

        if (o.getAnswers() != null) {

            for (int i = 0; i < o.getAnswers().size(); i++) {
                final Answer answer = o.getAnswers().get(i);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = DensityUtil.dip2px(mContext,5);
                lp.topMargin = DensityUtil.dip2px(mContext,8);
                LinearLayout answerL = new LinearLayout(mContext);
                answerL.setGravity(Gravity.CENTER_VERTICAL);
                answerL.setHorizontalGravity(LinearLayout.HORIZONTAL);
                answerL.setLayoutParams(lp);

                lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,25),DensityUtil.dip2px(mContext,25));
                lp.leftMargin = DensityUtil.dip2px(mContext,5);
                lp.rightMargin = DensityUtil.dip2px(mContext,10);
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.answer_btn_gray);
                tv.setLayoutParams(lp);
                tv.setTextColor(mContext.getResources().getColor(R.color.white));
                tv.setTextSize(14);
                tv.setGravity(Gravity.CENTER);
                char c = 'A';
                c = (char) (c + i);
                tv.setText(new StringBuffer().append(c).toString());
                answerL.addView(tv);


                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.topMargin = DensityUtil.dip2px(mContext,4);
                QuesTextView an = new QuesTextView(mContext);
                an.setLayoutParams(lp);
                an.setTextColor(Color.parseColor("#222222"));
                an.setGravity(Gravity.CENTER_VERTICAL);
                an.setTextSize(14);
                an.setNeedExpandHeight(true).setExpandLevel(2);
                an.setNeedFilter(true).setNetText(answer.getAnswer());
                answerL.addView(an);

                mAnswer.addView(answerL);
            }
        }

        if (o.getKids() != null || o.getKnowledges() != null) {

            LinearLayout verLayout = holder.getView(R.id.item_ques_assws);
            verLayout.removeAllViews();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (o.getKnowledges() != null) {
                for (int i = 0; i < o.getKnowledges().size(); i++) {
                    final int fI = i;

                    TextView knowlegeView  = (TextView)holder.getView(R.id.item_ques_knowadge_view);
                    knowlegeView.setText(o.getKnowledges().get(fI).getKpoint());

                    if (!TextUtils.isEmpty(o.getKnowledges().get(fI).getKid())) {

                        TextView jiexi  = (TextView)holder.getView(R.id.item_ques_ans_jiexi_view);

                        jiexi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (o.isJiexi()) {
                                    o.setJiexi(false);
                                    holder.gone(R.id.item_ques_jiexi_layout);
                                } else {

                                    if (o.getMyanswer() != null) {
                                        String[] myAns = o.getMyanswer().split(",");
                                        if (myAns != null && myAns.length > 0) {
                                            LinearLayout linearLayout = holder.getView(R.id.item_ques_myanswer);
                                            linearLayout.removeAllViews();
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                                            for (String my : myAns) {
                                                for (int i = 0; i < o.getAnswers().size(); i++) {
                                                    Answer answer = o.getAnswers().get(i);
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
                                                        linearLayout.addView(textView);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    //正确选项
                                    LinearLayout linearLayout = holder.getView(R.id.item_ques_answer);
                                    linearLayout.removeAllViews();
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                                    for (int i = 0; i < o.getAnswers().size(); i++) {
                                        Answer answer = o.getAnswers().get(i);
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
                                            linearLayout.addView(textView);
                                        }
                                    }

//                                    ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedFilter(true).setNeedExpandHeight(true).setExpandLevel(0);
                                    if (!TextUtils.isEmpty(o.getExplain()) && !o.getExplain().contains("暂无解析")) {
                                        holder.setVisibility(R.id.item_ques_jiexi,true);
                                        holder.setVisibility(R.id.item_ques_no_jiexi,false);
                                        ((QuesWebViewFive) holder.getView(R.id.item_ques_jiexi)).loadData(o.getExplain());
//                                        Log.e("xue","o.getExplain()="+o.getExplain());
//                                        ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedFilter(true).setNeedExpandHeight(true).setExpandLevel(0).setNetText(o.getExplain());
                                    } else {
//                                        ((QuesWebViewTwo) holder.getView(R.id.item_ques_jiexi)).loadData("没有解析。");
                                        holder.setVisibility(R.id.item_ques_no_jiexi,true);
                                        holder.setVisibility(R.id.item_ques_jiexi,false);
//                                        ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedFilter(true).setNeedExpandHeight(true).setExpandLevel(0).setNetText("没有解析。");
                                    }

                                    holder.setVisibility(R.id.item_ques_jiexi_layout, true);
                                    if (onJiexi != null) {
                                        onJiexi.onJiexi();
                                    }
                                    o.setJiexi(true);
                                }

                            }
                        });


                    }

                }
            } else {
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setGravity(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(lp);

                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 10);
                lp.weight = 1;
                View zView = new View(mContext);
                zView.setLayoutParams(lp);

                linearLayout.addView(zView);


                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView jiexi  = (TextView)holder.getView(R.id.item_ques_ans_jiexi_view);

                jiexi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (o.isJiexi()) {
                            o.setJiexi(false);
                            holder.gone(R.id.item_ques_jiexi_layout);
                        } else {

                            if (o.getMyanswer() != null) {
                                String[] myAns = o.getMyanswer().split(",");
                                if (myAns != null && myAns.length > 0) {
                                    LinearLayout linearLayout = holder.getView(R.id.item_ques_myanswer);
                                    linearLayout.removeAllViews();
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                                    for (String my : myAns) {
                                        for (int i = 0; i < o.getAnswers().size(); i++) {
                                            Answer answer = o.getAnswers().get(i);
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
                                                linearLayout.addView(textView);
                                            }
                                        }
                                    }
                                }
                            }

                            //正确选项
                            LinearLayout linearLayout = holder.getView(R.id.item_ques_answer);
                            linearLayout.removeAllViews();
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 25), DensityUtil.dip2px(mContext, 25));
                            for (int i = 0; i < o.getAnswers().size(); i++) {
                                Answer answer = o.getAnswers().get(i);
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
                                    linearLayout.addView(textView);
                                }
                            }
//                            ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedExpandHeight(true).setExpandLevel(0).setNeedFilter(true);
                            if (!TextUtils.isEmpty(o.getExplain()) && !o.getExplain().contains("暂无解析")) {
                                ((QuesWebViewFive) holder.getView(R.id.item_ques_jiexi)).loadData(o.getExplain());
                                holder.setVisibility(R.id.item_ques_no_jiexi,false);
                                holder.setVisibility(R.id.item_ques_jiexi,true);
//                                ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedFilter(true).setNeedExpandHeight(true).setExpandLevel(0).setNetText(o.getExplain());
                            } else {
//                                ((QuesWebViewTwo) holder.getView(R.id.item_ques_jiexi)).loadData("没有解析。");
                                holder.setVisibility(R.id.item_ques_no_jiexi,true);
                                holder.setVisibility(R.id.item_ques_jiexi,false);

//                                ((QuesTextView) holder.getView(R.id.item_ques_jiexi)).setNeedFilter(true).setNeedExpandHeight(true).setExpandLevel(0).setNetText("没有解析。");
                            }

                            holder.setVisibility(R.id.item_ques_jiexi_layout, true);
                            if (onJiexi != null) {
                                onJiexi.onJiexi();
                            }
                            o.setJiexi(true);
                        }

                    }
                });
                linearLayout.addView(jiexi);

                verLayout.addView(linearLayout);
            }

//            holder.setVisibility(R.id.item_ques_assws_points, true);
            holder.setVisibility(R.id.item_ques_assws_points_line, true);
        } else {
//            holder.gone(R.id.item_ques_assws_points);
            holder.gone(R.id.item_ques_assws_points_line);
        }

        if (o.isJiexi()) {
            holder.setVisibility(R.id.item_ques_jiexi_layout, true);
        } else {
            holder.gone(R.id.item_ques_jiexi_layout);
        }
    }

    OnJiexi onJiexi;

    public OnJiexi getOnJiexi() {
        return onJiexi;
    }

    public void setOnJiexi(OnJiexi onJiexi) {
        this.onJiexi = onJiexi;
    }

    public interface OnJiexi {
        void onJiexi();
    }

}

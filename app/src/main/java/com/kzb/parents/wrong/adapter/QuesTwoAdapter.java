package com.kzb.parents.wrong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.view.QuesWebViewSeven;
import com.kzb.parents.view.QuesWebViewSix;
import com.kzb.parents.wrong.model.Answer;
import com.kzb.parents.wrong.model.ExplainContent;

import java.util.List;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class QuesTwoAdapter extends BaseAdapter {

    protected Context mContext;
    protected List<ExplainContent> mDatas;
    protected LayoutInflater mInflater;
    private LayoutInflater layout = null;

    public QuesTwoAdapter(Context context, List datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final KViewHoler holder;
        //获取当前展示类型

        final ExplainContent o = mDatas.get(position);

        if (convertView == null) {
            convertView = layout.inflate(R.layout.item_reprot_ques, null);
            holder = new KViewHoler(convertView);
            convertView.setTag(holder);

        } else {
            holder = (KViewHoler) convertView.getTag();
        }


        holder.currentNum.setText((1 + position) + "/" + getCount());

        holder.questionView.loadData(o.getQuestion());


//        LinearLayout mAnswer = holder.getView(R.id.item_ques_ans_layout);
        holder.mAnswer.removeAllViews();

        if (o.getAnswers() != null) {

            for (int i = 0; i < o.getAnswers().size(); i++) {
                final Answer answer = o.getAnswers().get(i);

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


//                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp.topMargin = DensityUtil.dip2px(mContext, 4);
//                QuesTextView an = new QuesTextView(mContext);
//                an.setLayoutParams(lp);
//                an.setTextColor(Color.parseColor("#222222"));
//                an.setGravity(Gravity.CENTER_VERTICAL);
//                an.setTextSize(12);
//                an.setNeedExpandHeight(true).setExpandLevel(2);
//                an.setNeedFilter(true).setNetText(answer.getAnswer());


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

                holder.mAnswer.addView(answerL);
            }
        }

        if (o.getKids() != null || o.getKnowledges() != null) {

            holder.verLayout.removeAllViews();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (o.getKnowledges() != null) {
                holder.knowlegeView.setText(o.getKnowledges().get(0).getKpoint());
                if(!TextUtils.isEmpty(o.getKnowledges().get(0).getKid())){
                    holder.jiexi.setOnClickListener(new QuOnclick(holder, 1, o));
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

                holder.jiexi.setOnClickListener(new QuOnclick(holder, 1, o));

//                linearLayout.addView(holder.jiexi);

                holder.verLayout.addView(linearLayout);
            }

//            holder.setVisibility(R.id.item_ques_assws_points_line, true);
        } else {
//            holder.gone(R.id.item_ques_assws_points);
//            holder.gone(R.id.item_ques_assws_points_line);
        }

        if (o.isJiexi()) {
//            holder.setVisibility(R.id.item_ques_jiexi_layout, true);
            holder.jieXiLayout.setVisibility(View.VISIBLE);
        } else {
//            holder.gone(R.id.item_ques_jiexi_layout);
            holder.jieXiLayout.setVisibility(View.GONE);
        }

        return convertView;
    }


    class QuOnclick implements View.OnClickListener {

        KViewHoler holder;
        ExplainContent o;
        int type = 0;

        public QuOnclick(KViewHoler holer, int type, ExplainContent explainContent) {
            this.holder = holer;
            this.type = type;
            this.o = explainContent;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_ques_ans_jiexi_view:

                    if (o.isJiexi()) {
                        o.setJiexi(false);
                        holder.jieXiLayout.setVisibility(View.GONE);
                    } else {

                        if (o.getMyanswer() != null) {
                            String[] myAns = o.getMyanswer().split(",");
                            if (myAns != null && myAns.length > 0) {
                                holder.myRightAnswer.removeAllViews();
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
                                            holder.myRightAnswer.addView(textView);
                                        }
                                    }
                                }
                            }
                        }

                        //正确选项
                        holder.rightAnswer.removeAllViews();
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
                                holder.rightAnswer.addView(textView);
                            }
                        }

                        if (!TextUtils.isEmpty(o.getExplain()) && !o.getExplain().contains("暂无解析")) {
                            holder.jiexieContent.setVisibility(View.VISIBLE);
                            holder.noJiexiView.setVisibility(View.GONE);
                            holder.jiexieContent.loadData(o.getExplain());
                        } else {
                            holder.noJiexiView.setVisibility(View.VISIBLE);
                            holder.jiexieContent.setVisibility(View.GONE);
                        }

                        holder.jieXiLayout.setVisibility(View.VISIBLE);
                        if (onJiexi != null) {
                            onJiexi.onJiexi();
                        }
                        o.setJiexi(true);
                    }


                    break;
            }
        }
    }


    static class KViewHoler {

        TextView currentNum;
        QuesWebViewSeven questionView;
        LinearLayout mAnswer;
        LinearLayout verLayout;
        TextView knowlegeView;
        TextView jiexi;
        LinearLayout jieXiLayout;
        LinearLayout myRightAnswer;
        LinearLayout rightAnswer;
        QuesWebViewSix jiexieContent;
        TextView noJiexiView;

        public KViewHoler(View convertView) {
            currentNum = (TextView) convertView.findViewById(R.id.item_ques_current);
            questionView = (QuesWebViewSeven) convertView.findViewById(R.id.item_ques_ques);
            mAnswer = (LinearLayout) convertView.findViewById(R.id.item_ques_ans_layout);
            verLayout = (LinearLayout) convertView.findViewById(R.id.item_ques_assws);
            jiexi = (TextView) convertView.findViewById(R.id.item_ques_ans_jiexi_view);
            jieXiLayout = (LinearLayout) convertView.findViewById(R.id.item_ques_jiexi_layout);
            knowlegeView = (TextView) convertView.findViewById(R.id.item_ques_knowadge_view);
            myRightAnswer = (LinearLayout) convertView.findViewById(R.id.item_ques_myanswer);
            rightAnswer = (LinearLayout) convertView.findViewById(R.id.item_ques_answer);
            jiexieContent = (QuesWebViewSix) convertView.findViewById(R.id.item_ques_jiexi);
            noJiexiView = (TextView) convertView.findViewById(R.id.item_ques_no_jiexi);
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

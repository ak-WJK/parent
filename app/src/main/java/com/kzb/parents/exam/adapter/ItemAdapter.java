package com.kzb.parents.exam.adapter;

import android.content.Context;

import com.kzb.parents.R;
import com.kzb.parents.base.EasyAdapter.CommonAdapter;
import com.kzb.parents.base.EasyAdapter.ViewHolder;
import com.kzb.parents.exam.bean.Question;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class ItemAdapter extends CommonAdapter<Question> {

    public ItemAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convert(ViewHolder holder, Question o) {
        holder.setText(R.id.item_exam_question_text,(1+holder.getPosition())+"");
        if(o.isDone()){
            holder.setBackground(R.id.item_exam_question_text,R.drawable.answer_item_do);
            holder.setTextColor(R.id.item_exam_question_text,mContext.getResources().getColor(R.color.white));
        }else{
            holder.setBackground(R.id.item_exam_question_text,R.drawable.answer_item_un_do);
            holder.setTextColor(R.id.item_exam_question_text,mContext.getResources().getColor(R.color.theme_gray));
        }
    }
}

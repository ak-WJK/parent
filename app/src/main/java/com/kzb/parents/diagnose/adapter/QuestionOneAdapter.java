package com.kzb.parents.diagnose.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.diagnose.JiXieTwoNewActivity;
import com.kzb.parents.diagnose.model.QuestionModel;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/1/20.
 */

public class QuestionOneAdapter extends CommonAdapter<QuestionModel> {

    private int one1;
    private int two1;
    private int thr1;
    private int wrong_count1;
    private int right_count1;
    private String one;
    private String two;
    private String thr;
    private String wrong_count;
    private String right_count;

    public QuestionOneAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.question_list_item, position);
        final QuestionModel questionModel = mDatas.get(position);

        TextView titleView = viewHolder.getView(R.id.question_list_item_title);

        titleView.setText(questionModel.getName());

        View view = viewHolder.getConvertView();

//        Drawable drawable;
//
//        if(position == 0){
//            drawable= mContext.getResources().getDrawable(R.drawable.blue_round);
//        }else {
//            drawable= mContext.getResources().getDrawable(R.drawable.gray_round);
//        }
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        titleView.setCompoundDrawables(drawable,null,null,null);


//        Log.e("tttt",questionModel.toString());


            startJieXi(questionModel, view);


        return view;
    }


    private void startJieXi(final QuestionModel questionModel, View view) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClick.itemClick(questionModel);

                Map<String, String> mapVal = new HashMap<String, String>();
                mapVal.put("test_id", questionModel.getTest_id());
                mapVal.put("name", questionModel.getName());
                mapVal.put("typev", String.valueOf(questionModel.getType()));


//                LogUtils.e("TAG", "wrong_count========== "+questionModel.getWrong_count());
//                LogUtils.e("TAG", "right_count======" + questionModel.getRight_count());

                LogUtils.e("TAG", "此处跳转到题目解析页面");

//              IntentUtil.startActivity((BaseActivity)mContext,JiXieTwoActivity.class,mapVal);;

                IntentUtil.startActivity((BaseActivity) mContext, JiXieTwoNewActivity.class, mapVal);

            }
        });
    }


}

package com.kzb.parents.course.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.course.model.CourseResponse.KnowledgeModel;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.CustomTextView;

import java.text.NumberFormat;

public class GridViewAdapter extends CommonAdapter<KnowledgeModel> {


    private Context context;


    private int lnType;

    public GridViewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setLnType(int lnType) {
        this.lnType = lnType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.grid_item, position);

        CustomTextView textView = viewHolder.getView(R.id.child_view);
        TextView numView = viewHolder.getView(R.id.child_view_num);
        ImageView imgView = viewHolder.getView(R.id.star_1);


        final KnowledgeModel knowledgeModel = mDatas.get(position);

//		LogUtils.e("TAG", "position == " + position);
        LogUtils.e("TAG", "mDatas.get === " + mDatas.size());


        if (knowledgeModel.getImportance() != null) {
            switch (knowledgeModel.getImportance()) {
                case "1":
                    imgView.setImageResource(R.mipmap.report_item_img_three);
                    break;
                case "2":
                    imgView.setImageResource(R.mipmap.report_item_img_two);
                    break;
                case "3":
                    imgView.setImageResource(R.mipmap.report_item_img_one);
                    break;
            }
        }

        textView.setText(knowledgeModel.getKpoint());
        numView.setText("" + (position + 1));
        View view = viewHolder.getConvertView();

        int totalCount = 0;
        int completeCount = 0;

        if (TextUtils.isEmpty(knowledgeModel.getCount())) {
            totalCount = 0;
        } else {
            totalCount = Integer.parseInt(knowledgeModel.getCount());
        }

        if (TextUtils.isEmpty(knowledgeModel.getCan())) {
            completeCount = 0;
        } else {
            completeCount = Integer.parseInt(knowledgeModel.getCan());
        }


        if (totalCount == 0 || completeCount == 0) {
            textView.setSign(1);
//			percent.setText("0%");
        } else if (totalCount == completeCount) {
            textView.setSign(2);
//			percent.setText("100%");
        } else if (completeCount > 0 && completeCount < totalCount) {

            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) completeCount / (float) totalCount);

//			percent.setText(Double.parseDouble(result)*100+"%");

            textView.setSign(3, Float.parseFloat(result));
        }

        if (lnType == 1) {
            textView.setSign(2);
        }

//		view.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Map<String,String> val = new HashMap<String, String>();
//				val.put("kid",knowledgeModel.getKid());
//				val.put("point",knowledgeModel.getKpoint());
////				if(type.trim().equals("2")){
////					//强化提高
////
////					IntentUtil.startActivity((BaseActivity)context, ExamActivity.class,val);
////				}else {
////
////				}
//
//				//课程学习
//				IntentUtil.startActivity((BaseActivity)context, CourseDetailActivity.class,val);
////				Log.e("tttt","knowledgeModel="+knowledgeModel.toString());
//			}
//		});


        return view;
    }


}

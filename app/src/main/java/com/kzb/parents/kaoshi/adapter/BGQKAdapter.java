package com.kzb.parents.kaoshi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.kaoshi.model.KSReportQKResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class BGQKAdapter extends CommonAdapter<KSReportQKResponse.ReportQKModel> {

    private String typename;
    private onBtnClickListener listener;

    public BGQKAdapter(Context context) {
        super(context);


    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_bg_qk_kaoshi, position);


        final KSReportQKResponse.ReportQKModel questionModel = mDatas.get(position);

        typename = questionModel.getTypename();
        String score = questionModel.getScore();

        String endTime = questionModel.getAddtime();


        TextView titleView = viewHolder.getView(R.id.item_bg_qk_title);
        TextView typeViewName = viewHolder.getView(R.id.item_bg_qk_typename);
        TextView positionView = viewHolder.getView(R.id.item_bg_qk_position);
        TextView tvScore = viewHolder.getView(R.id.tv_score);
        Button findView = viewHolder.getView(R.id.item_bg_qk_find);

        findView.setTag(position);

//        tvScore.setText(score + "分");
        String timedate = timedate(endTime);
        titleView.setText(timedate);



        positionView.setText(String.valueOf(position + 1));

        View view = viewHolder.getConvertView();

        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String typename = questionModel.getTypename();


                    Integer position = (Integer) v.getTag();
                    int pos = position;
                    //根据绑定的位置得到类型
                    KSReportQKResponse.ReportQKModel model = mDatas.get(pos);
                    String typename1 = mDatas.get(pos).getTypename();
                    listener.onBtnClick(v, typename1, model);
                }
            }
        });


//        findView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LogUtils.e("TAG", "进入诊断报告");
//                LogUtils.e("TAG ==== ", typename);
//
//                Map<String, String> mapVal = new HashMap<String, String>();
//                mapVal.put("test_id", questionModel.getTest_id());
//                mapVal.put("score", questionModel.getScore());
//                mapVal.put("from", "kaoshi");
//                IntentUtil.startActivity((BaseActivity) mContext, DiagNoseDetailActivity.class, mapVal);
//            }
//        });


        return view;
    }


    public interface onBtnClickListener {
        void onBtnClick(View view, String typeName, KSReportQKResponse.ReportQKModel model);
    }

    public void setOnBtnClickListener(onBtnClickListener listener) {

        this.listener = listener;
    }


    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
}


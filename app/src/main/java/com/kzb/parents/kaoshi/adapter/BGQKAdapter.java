package com.kzb.parents.kaoshi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.kaoshi.model.KSReportQKResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class BGQKAdapter extends CommonAdapter<KSReportQKResponse.ReportQKModel> {
    public BGQKAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_bg_qk_kaoshi, position);
        final KSReportQKResponse.ReportQKModel questionModel = mDatas.get(position);


        TextView titleView = viewHolder.getView(R.id.item_bg_qk_title);
        TextView positionView = viewHolder.getView(R.id.item_bg_qk_position);
        TextView findView = viewHolder.getView(R.id.item_bg_qk_find);

        titleView.setText(questionModel.getName());
        positionView.setText(String.valueOf(position + 1));

        View view = viewHolder.getConvertView();


        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtils.e("TAG", "进入诊断报告");

                Map<String,String> mapVal = new HashMap<String, String>();
                mapVal.put("test_id",questionModel.getTest_id());
                mapVal.put("score",questionModel.getScore());
                mapVal.put("from","kaoshi");
                IntentUtil.startActivity((BaseActivity)mContext,DiagNoseDetailActivity.class,mapVal);
            }
        });

        return view;
    }

}


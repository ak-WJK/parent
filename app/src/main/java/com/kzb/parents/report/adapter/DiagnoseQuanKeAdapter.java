package com.kzb.parents.report.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.report.model.ReportListItem;
import com.kzb.parents.util.DateUtils;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class DiagnoseQuanKeAdapter extends CommonAdapter<ReportListItem>{

    public DiagnoseQuanKeAdapter(Context context){
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent, R.layout.diagnoese_quanke_list_item,position);
        final ReportListItem letterListModel = mDatas.get(position);

        TextView timeView = viewHolder.getView(R.id.item_report_list_item_time);
        TextView numberView = viewHolder.getView(R.id.item_report_list_item_num);
        Button findView = viewHolder.getView(R.id.item_report_list_item_check);

        numberView.setText(""+(position+1));
        timeView.setText(DateUtils.formatDataTimeTWO(Long.parseLong(letterListModel.getAddtime())*1000));


        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> mapVal = new HashMap<String, String>();
                mapVal.put("test_id",letterListModel.getTest_id());
                mapVal.put("from","zhenduan");
                IntentUtil.startActivity((BaseActivity)mContext, DiagNoseDetailActivity.class,mapVal);
            }
        });


        return viewHolder.getConvertView();
    }
}

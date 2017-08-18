package com.kzb.parents.diagnose.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.diagnose.model.ZhentiDiagnoseItem;
import com.kzb.parents.exam.ExamActivity;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/6/4.
 */

public class DNZTAdapter extends CommonAdapter<ZhentiDiagnoseItem> {
    public DNZTAdapter(Context context) {
        super(context);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent, R.layout.item_diagnose_zj,position);
        final ZhentiDiagnoseItem diagnoseItem = mDatas.get(position);


        TextView titleView = viewHolder.getView(R.id.item_dg_zj_title);
        TextView positionView = viewHolder.getView(R.id.item_dg_zj_position);
        TextView findView = viewHolder.getView(R.id.item_dg_zj_find);

        titleView.setText(diagnoseItem.getKemu_name());
        positionView.setText(String.valueOf(position+1));

        View view = viewHolder.getConvertView();


        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ExamActivity.class);
                intent.putExtra("id", diagnoseItem.getExam_id());
                intent.putExtra("type", "zhenti");
                intent.putExtra("from", "zhenti");
                IntentUtil.startActivity((Activity) mContext, intent);

            }
        });

        return view;
    }

}

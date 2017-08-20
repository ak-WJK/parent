package com.kzb.parents.kaoshi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.exam.ZTJXActivity;
import com.kzb.parents.kaoshi.model.KSZhangJieResponse;
import com.kzb.parents.util.IntentUtil;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class ZhangJieFinAdapter extends CommonAdapter<KSZhangJieResponse.ZhangJieModel> {
    private String mType;

    private int type;//0表示未完成，1完成

    public ZhangJieFinAdapter(Context context,String mType) {
        super(context);
        this.mType = mType;
    }

    public void setType(int type){
        this.type = type;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent, R.layout.item_zhangjie_kaoshi,position);
        final KSZhangJieResponse.ZhangJieModel questionModel = mDatas.get(position);


        TextView titleView = viewHolder.getView(R.id.item_zg_ks_title);
        TextView positionView = viewHolder.getView(R.id.item_zg_ks_position);
        TextView findView = viewHolder.getView(R.id.item_zg_ks_find);

        titleView.setText(questionModel.getName());
        positionView.setText(String.valueOf(position+1));

        View view = viewHolder.getConvertView();


        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type == 1){
                    Intent intent = new Intent(mContext, DiagNoseDetailActivity.class);
                    intent.putExtra("test_id",questionModel.getTest_id());
                    intent.putExtra("from","kaoshi");

                    IntentUtil.startActivity((Activity)mContext, intent);
                }else {
                    Intent intent = new Intent(mContext, ZTJXActivity.class);
                    intent.putExtra("from",mType);
                    intent.putExtra("test_id", questionModel.getTest_id());
                    intent.putExtra("type","jiaoshi");
                    intent.putExtra("zhangjieType","1");
                    IntentUtil.startActivity((Activity)mContext,intent);
                }



            }
        });

        return view;
    }

}

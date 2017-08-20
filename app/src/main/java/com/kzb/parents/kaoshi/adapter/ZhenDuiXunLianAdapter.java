package com.kzb.parents.kaoshi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.exam.ZTJXActivity;
import com.kzb.parents.kaoshi.model.ZhenDuiXunLianResponse;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class ZhenDuiXunLianAdapter extends CommonAdapter<ZhenDuiXunLianResponse.XunLianModel> {

    private String mType;

    public ZhenDuiXunLianAdapter(Context context) {
        super(context);
    }


    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent, R.layout.item_zhendui_xunlian,position);
        final ZhenDuiXunLianResponse.XunLianModel questionModel = mDatas.get(position);


        TextView titleView = viewHolder.getView(R.id.item_xunlian_title);
        TextView positionView = viewHolder.getView(R.id.item_xunlian_position);
        TextView findView = viewHolder.getView(R.id.item_xunlian_find);


        titleView.setText(questionModel.getName());
        positionView.setText(position+1+"");

        View view = viewHolder.getConvertView();


        findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> mapVal = new HashMap<String, String>();
                mapVal.put("test_id",questionModel.getTest_id());
                mapVal.put("from",mType);
                mapVal.put("type","zhendui");
                mapVal.put("zhangjieType","2");
                IntentUtil.startActivity((BaseActivity)mContext,ZTJXActivity.class,mapVal);
            }
        });

        return view;
    }

}

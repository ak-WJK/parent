package com.kzb.parents.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzb.parents.R;
import com.kzb.parents.VipDetailActivity;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.main.model.VipResponse;

/**
 * Created by wanghaofei on 17/7/16.
 */

public class VipAdapter extends CommonAdapter<VipResponse.MoneyModel> {
    public VipAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.vip_list_item, position);
        final VipResponse.MoneyModel moneyModel = mDatas.get(position);


        ImageView headerView = viewHolder.getView(R.id.vip_list_item_header);
        TextView nameView = viewHolder.getView(R.id.vip_list_item_name);
        TextView detailView = viewHolder.getView(R.id.vip_list_item_detail);

        Glide.with(mContext).load(moneyModel.getImg()).into(headerView);

        nameView.setText(moneyModel.getName());


        View view = viewHolder.getConvertView();

        detailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(mContext, VipDetailActivity.class);
                intent.putExtra("vip",moneyModel);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

}

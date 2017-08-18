package com.kzb.parents.msg.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.msg.model.MsgListResponse.MsgListModel;
import com.kzb.parents.util.DateUtils;
/**
 * Created by wanghaofei on 17/7/17.
 */
public class MsgAdapter extends CommonAdapter<MsgListModel> {
    public MsgAdapter(Context context) {
        super(context);
    }

    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent, R.layout.letter_list_item,position);
        final MsgListModel letterListModel = mDatas.get(position);

        TextView titleView = viewHolder.getView(R.id.letter_list_item_title);
        TextView timeView = viewHolder.getView(R.id.letter_list_item_time);

        titleView.setText(letterListModel.getTitle());
        if(null != letterListModel.getAddtime()){
            timeView.setText(DateUtils.formatDate(Long.parseLong(letterListModel.getAddtime())));
        }


        View view = viewHolder.getConvertView();

        Drawable drawable;

        if(position == 0){
            drawable= mContext.getResources().getDrawable(R.drawable.blue_round);
        }else {
            drawable= mContext.getResources().getDrawable(R.drawable.gray_round);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        titleView.setCompoundDrawables(drawable,null,null,null);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClick.itemClick(letterListModel);
//            }
//        });

        return view;
    }

    public interface ItemClick{
        void itemClick(MsgListModel moniRecord);
    }



}

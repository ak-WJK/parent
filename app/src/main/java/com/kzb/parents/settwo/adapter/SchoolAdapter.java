package com.kzb.parents.settwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.settwo.model.SchoolResponse;
import com.kzb.parents.settwo.model.SchoolResponse.SchoolModel;

import java.util.List;
/**
 * Created by wanghaofei on 17/6/12.
 */

public class SchoolAdapter extends CommonAdapter<SchoolResponse.SchoolModel> {


    private Context context;
    private List<SchoolResponse.SchoolModel> listSchools;

    private SchoolItemClick schoolItemClick;

    public SchoolAdapter(Context context, List<SchoolResponse.SchoolModel> listSchools) {
        super(context);
        this.context = context;
        this.listSchools = listSchools;
    }


    public interface SchoolItemClick{
        void sItemClick(SchoolResponse.SchoolModel schoolModel);
    }


    public void setSchoolItemClic(SchoolItemClick schoolItemClick){
        this.schoolItemClick = schoolItemClick;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.school_item, position);

        TextView titleView = viewHolder.getView(R.id.school_item_name);
        ImageView imgView = viewHolder.getView(R.id.school_item_sign);


        final SchoolResponse.SchoolModel schoolModel = mDatas.get(position);

        if(schoolModel.getType() == 0){
            imgView.setImageResource(R.mipmap.blue_uncheck_img);
        }else {
            imgView.setImageResource(R.mipmap.blue_check_img);
        }

        View view = viewHolder.getConvertView();

        titleView.setText(schoolModel.getSchoolname());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0;i<mDatas.size();i++){
                    SchoolModel temModel = mDatas.get(i);
                    if(temModel.getId() == schoolModel.getId()){
                        temModel.setType(1);
                    }else {
                        temModel.setType(0);
                    }
                }

                notifyDataSetChanged();

                schoolItemClick.sItemClick(schoolModel);
            }
        });

        return view;
    }


}

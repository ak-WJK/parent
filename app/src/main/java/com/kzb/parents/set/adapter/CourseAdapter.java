package com.kzb.parents.set.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.adapter.CommonAdapter;
import com.kzb.parents.base.adapter.ViewHolder;
import com.kzb.parents.set.model.CourseResponse.CourseModel;

/**
 * Created by wanghaofei on 17/2/28.
 */

public class CourseAdapter extends CommonAdapter<CourseModel> {
    public CourseAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.course_listview_item, position);
        final CourseModel courseModel = mDatas.get(position);


        TextView nameView = viewHolder.getView(R.id.course_item_name);
        TextView viewState = viewHolder.getView(R.id.course_item_state);
        nameView.setText(courseModel.getName());

        View view = viewHolder.getConvertView();

       if (courseModel.getType() == 0){
           viewState.setVisibility(View.GONE);
       }else {
           viewState.setVisibility(View.VISIBLE);
       }

        return view;
    }
}

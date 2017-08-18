package com.kzb.parents.report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.report.model.ReportListItem;
import com.kzb.parents.report.model.ReportZjListItem;

import java.util.List;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class ExamZhangJieAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ReportZjListItem> wZhangModelList;

    public ExamZhangJieAdapter(Context context, List<ReportZjListItem> wZhangModelList) {
        this.context = context;
        this.wZhangModelList = wZhangModelList;
    }


    @Override
    public int getGroupCount() {
        return wZhangModelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(null == wZhangModelList.get(groupPosition).getTest()){
            return 0;
        }else {
            return wZhangModelList.get(groupPosition).getTest().size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return wZhangModelList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return wZhangModelList.get(groupPosition).getTest().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        View view = (View) LayoutInflater.from(context).inflate(R.layout.item_report_list_father, null);

        TextView textView = (TextView) view.findViewById(R.id.item_report_list_father_title);
        TextView numView = (TextView) view.findViewById(R.id.item_report_list_father_num);
        ImageView imgView = (ImageView) view.findViewById(R.id.item_report_list_father_icon);
        ReportZjListItem wZhangModel = wZhangModelList.get(groupPosition);
        textView.setText(wZhangModel.getName());

        numView.setVisibility(View.VISIBLE);
        numView.setText("("+wZhangModel.getTestcount()+")");
        if(!isExpanded){
            imgView.setBackgroundResource(R.mipmap.arrow_down);
        }else {
            imgView.setBackgroundResource(R.mipmap.arrow_up);
        }

        view.setPadding(10,10,10,10);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.item_report_list_son, parent,false);

//        TextView textView = (TextView) view.findViewById(R.id.wrong_child_item_view);
//        TextView lookView = (TextView) view.findViewById(R.id.wrong_child_look_view);

        final ReportListItem wJieModel = wZhangModelList.get(groupPosition).getTest().get(childPosition);
//        textView.setText(wJieModel.getName());
//        view.setPadding(10,10,10,10);
//
//        lookView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String,String> mapVal = new HashMap<String, String>();
//                mapVal.put("id",wJieModel.getId());
//                IntentUtil.startActivity((BaseActivity)context, WrongDetailActivity.class,mapVal);
//            }
//        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

package com.kzb.parents.report.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.report.model.ReportListItem;
import com.kzb.parents.report.model.ReportZjListItem;
import com.kzb.parents.util.IntentUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class DiagnoseZhangJieAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ReportZjListItem> wZhangModelList;

    public DiagnoseZhangJieAdapter(Context context, List<ReportZjListItem> wZhangModelList) {
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
        View lineView = (View) view.findViewById(R.id.item_report_list_father_lineview);

        ReportZjListItem wZhangModel = wZhangModelList.get(groupPosition);
        textView.setText(wZhangModel.getName());


//        if(wZhangModel.getOpenState() == 0){
//            lineView.setVisibility(View.VISIBLE);
//        }else {
//            lineView.setVisibility(View.GONE);
//        }

        numView.setVisibility(View.VISIBLE);
//        if(TextUtils.isEmpty(wZhangModel.getTestcount())){
//            numView.setText("(0)");
//        }else {
//            numView.setText("("+wZhangModel.getTestcount()+")");
//        }


        if(!isExpanded){
            imgView.setBackgroundResource(R.mipmap.arrow_down);
        }else {
            imgView.setBackgroundResource(R.mipmap.arrow_up);
        }

//        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//        p.setMargins(0, 10, 0, 10);

//        view.setPadding(10,10,10,10);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.item_report_list_son, parent,false);

        TextView psView = (TextView) view.findViewById(R.id.item_report_list_son_position);
        TextView dateView = (TextView) view.findViewById(R.id.item_report_list_son_date);
        TextView scoreView = (TextView) view.findViewById(R.id.item_report_list_son_score);
        Button lookView = (Button) view.findViewById(R.id.item_report_list_son_check);




        final ReportListItem wJieModel = wZhangModelList.get(groupPosition).getTest().get(childPosition);


        String addTime = wJieModel.getAddtime();
        SimpleDateFormat df = new SimpleDateFormat();
        if (!TextUtils.isEmpty(addTime)) {
            addTime = addTime + "000";
            Date date = new Date();
            date.setTime(Long.parseLong(addTime));
            df.applyPattern("yyyy-MM-dd");
            dateView.setText(df.format(date));
        }

        psView.setText(""+(childPosition+1));
        scoreView.setText("("+wJieModel.getScore()+"分)");
        lookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> mapVal = new HashMap<String, String>();
                mapVal.put("test_id",wJieModel.getTest_id());
                mapVal.put("from","zhenduan");
                IntentUtil.startActivity((BaseActivity)context, DiagNoseDetailActivity.class,mapVal);

            }
        });



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
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        for (int i=0;i<wZhangModelList.size();i++){
            ReportZjListItem listItem = wZhangModelList.get(i);
            listItem.setOpenState(0);
        }
        notifyDataSetChanged();
    }




    //通过监听展开，关闭，控制线条是否显示
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        for (int i=0;i<wZhangModelList.size();i++){
            ReportZjListItem listItem = wZhangModelList.get(i);
            listItem.setOpenState(0);
        }
        wZhangModelList.get(groupPosition).setOpenState(1);
         notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

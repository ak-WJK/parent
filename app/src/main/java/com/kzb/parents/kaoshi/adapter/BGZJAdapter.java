package com.kzb.parents.kaoshi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.report.model.ReportListItem;
import com.kzb.parents.report.model.ReportZjListItem;
import com.kzb.parents.util.IntentUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class BGZJAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater father_Inflater = null;
    private LayoutInflater son_Inflater = null;

    private List<ReportZjListItem> datas;//父层 子层

    public BGZJAdapter(Context context, List<ReportZjListItem> datas) {
        this.context = context;
        father_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        son_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.datas = datas;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        if (datas != null) {
            return datas.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        if (datas != null && datas.size() > groupPosition && datas.get(groupPosition).getTest() != null) {
            return datas.get(groupPosition).getTest().size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        if (datas != null) {
            return datas.get(groupPosition);
        }else{
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (datas != null && datas.size() > groupPosition && datas.get(groupPosition).getTest() != null) {
            return datas.get(groupPosition).getTest().get(childPosition);
        }else{
            return null;
        }
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Father_ViewHolder father_ViewHolder = null;
        if (convertView == null) {
            convertView = father_Inflater.inflate(R.layout.item_report_list_father, parent,false);
            father_ViewHolder = new Father_ViewHolder();

            father_ViewHolder.father_title = (TextView) convertView.findViewById(R.id.item_report_list_father_title);
            father_ViewHolder.father_icon = (ImageView) convertView.findViewById(R.id.item_report_list_father_icon);
            father_ViewHolder.numTxt = (TextView) convertView.findViewById(R.id.item_report_list_father_num);
            convertView.setTag(father_ViewHolder);
        } else {
            father_ViewHolder = (Father_ViewHolder) convertView.getTag();
        }
        father_ViewHolder.father_title.setText(datas.get(groupPosition).getName());
        if (isExpanded) {
            father_ViewHolder.father_icon.setImageResource(R.mipmap.arrow_up);
        } else {
            father_ViewHolder.father_icon.setImageResource(R.mipmap.arrow_down);
        }
        father_ViewHolder.numTxt.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(datas.get(groupPosition).getTestcount())){
            father_ViewHolder.numTxt.setText("0");
        }else {
            father_ViewHolder.numTxt.setText(datas.get(groupPosition).getTestcount());
        }

//        convertView.setPadding(10,10,10,10);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Son_ViewHolder son_ViewHolder = null;
        if (convertView == null) {
            convertView = son_Inflater.inflate(R.layout.item_report_list_son, null);
            son_ViewHolder = new Son_ViewHolder();
            son_ViewHolder.son_position = (TextView) convertView.findViewById(R.id.item_report_list_son_position);
            son_ViewHolder.son_title = (TextView) convertView.findViewById(R.id.item_report_list_son_title);
            son_ViewHolder.son_date = (TextView) convertView.findViewById(R.id.item_report_list_son_date);
//            son_ViewHolder.son_time = (TextView) convertView.findViewById(R.id.item_report_list_son_time);
            son_ViewHolder.son_score = (TextView) convertView.findViewById(R.id.item_report_list_son_score);
            son_ViewHolder.son_check = (TextView) convertView.findViewById(R.id.item_report_list_son_check);
            convertView.setTag(son_ViewHolder);
        } else {
            son_ViewHolder = (Son_ViewHolder) convertView.getTag();

        }
        final ReportListItem item = datas.get(groupPosition).getTest().get(childPosition);

        son_ViewHolder.son_position.setText("" + (childPosition + 1));
        son_ViewHolder.son_title.setText(item.getName());

        son_ViewHolder.son_score.setText(item.getScore() + "分");

        String addTime = item.getAddtime();
        SimpleDateFormat df = new SimpleDateFormat();
        if (!TextUtils.isEmpty(addTime)) {
            addTime = addTime + "000";
            Date date = new Date();
            date.setTime(Long.parseLong(addTime));
            df.applyPattern("yyyy-MM-dd");
            son_ViewHolder.son_date.setText(df.format(date));
//            df.applyPattern("hh:mm");
//            son_ViewHolder.son_time.setText(df.format(date));

            son_ViewHolder.son_date.setVisibility(View.VISIBLE);
//            son_ViewHolder.son_icon.setVisibility(View.GONE);
//            son_ViewHolder.son_time.setVisibility(View.GONE);
        } else {
            son_ViewHolder.son_date.setVisibility(View.VISIBLE);
//            son_ViewHolder.son_icon.setVisibility(View.GONE);
//            son_ViewHolder.son_time.setVisibility(View.GONE);
        }

        son_ViewHolder.son_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DiagNoseDetailActivity.class);
                intent.putExtra("test_id", item.getTest_id());
                intent.putExtra("from","kaoshi");
                IntentUtil.startActivity((Activity) context, intent);
            }
        });
        convertView.setPadding(10,5,10,5);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        for (int i=0;i<datas.size();i++){
            ReportZjListItem listItem = datas.get(i);
            listItem.setOpenState(0);
        }
        notifyDataSetChanged();
    }


    //通过监听展开，关闭，控制线条是否显示
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        for (int i=0;i<datas.size();i++){
            ReportZjListItem listItem = datas.get(i);
            listItem.setOpenState(0);
        }
        datas.get(groupPosition).setOpenState(1);
        notifyDataSetChanged();
    }



    public final class Father_ViewHolder {
        private TextView father_title;
        private ImageView father_icon;
        private TextView numTxt;
    }

    public final class Son_ViewHolder {
        private TextView son_position;
        private TextView son_title;
        private TextView son_date;
        private ImageView son_icon;
        //        private TextView son_time;
        private TextView son_score;
        private TextView son_check;
    }
    public String mType;
    public void setType(String type){
        mType = type;
    }
}

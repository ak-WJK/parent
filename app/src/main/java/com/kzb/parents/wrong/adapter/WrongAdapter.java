package com.kzb.parents.wrong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.wrong.WrongDetailThreeActivity;
import com.kzb.parents.wrong.model.WrongResponse.WJieModel;
import com.kzb.parents.wrong.model.WrongResponse.WZhangModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaofei on 16/12/7.
 */

public class WrongAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<WZhangModel> wZhangModelList;

    public WrongAdapter(Context context, List<WZhangModel> wZhangModelList) {
        this.context = context;
        this.wZhangModelList = wZhangModelList;
    }


    @Override
    public int getGroupCount() {
        return wZhangModelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (null == wZhangModelList.get(groupPosition).getJie()) {
            return 0;
        } else {
            return wZhangModelList.get(groupPosition).getJie().size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return wZhangModelList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return wZhangModelList.get(groupPosition).getJie().get(childPosition);
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


        View view = (View) LayoutInflater.from(context).inflate(R.layout.wrong_group_item, null);

        TextView textView = (TextView) view.findViewById(R.id.wrong_group_item_view);
        TextView numView = (TextView) view.findViewById(R.id.wrong_group_num_view);
        ImageView imgView = (ImageView) view.findViewById(R.id.wrong_group_img);
        View lineView = (View) view.findViewById(R.id.item_report_list_father_lineview);
        WZhangModel wZhangModel = wZhangModelList.get(groupPosition);
        textView.setText(wZhangModel.getName());
        LogUtils.e("TAG", "wZhangModel.getName()=====" + wZhangModel.getName());

//        numView.setVisibility(View.VISIBLE);
        numView.setText(wZhangModel.getCount());
        if (!isExpanded) {
            imgView.setBackgroundResource(R.mipmap.arrow_down);
        } else {
            imgView.setBackgroundResource(R.mipmap.arrow_up);
        }

        if (wZhangModel.getOpenState() == 0) {
            lineView.setVisibility(View.VISIBLE);
        } else {
            lineView.setVisibility(View.GONE);
        }


//        view.setPadding(10,10,10,10);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.wrong_child_view, null);

        TextView textView = (TextView) view.findViewById(R.id.wrong_child_item_view);
        TextView lookView = (TextView) view.findViewById(R.id.wrong_child_look_view);
        TextView numView = (TextView) view.findViewById(R.id.wrong_child_item_num);


        final WJieModel wJieModel = wZhangModelList.get(groupPosition).getJie().get(childPosition);
        textView.setText(wJieModel.getName());
        view.setPadding(10, 10, 10, 10);

        numView.setText("" + (childPosition + 1));


        lookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapVal = new HashMap<String, String>();
                mapVal.put("id", wJieModel.getId());
//                IntentUtil.startActivity((BaseActivity)context, WrongDetailActivity.class,mapVal);
//                IntentUtil.startActivity((BaseActivity)context, WrongDetailTwoActivity.class,mapVal);
                IntentUtil.startActivity((BaseActivity) context, WrongDetailThreeActivity.class, mapVal);
            }
        });

        return view;
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        for (int i = 0; i < wZhangModelList.size(); i++) {
            WZhangModel listItem = wZhangModelList.get(i);
            listItem.setOpenState(0);
        }
        notifyDataSetChanged();
    }


    //通过监听展开，关闭，控制线条是否显示
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        for (int i = 0; i < wZhangModelList.size(); i++) {
            WZhangModel listItem = wZhangModelList.get(i);
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

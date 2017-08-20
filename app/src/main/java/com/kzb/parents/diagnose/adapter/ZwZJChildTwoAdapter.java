package com.kzb.parents.diagnose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.course.CourseDetailActivity;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.diagnose.model.TimuZwSon;
import com.kzb.parents.exam.ZTJXActivity;
import com.kzb.parents.util.IntentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by wanghaofei on 17/2/8.
 */

public class ZwZJChildTwoAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private ArrayList<TimuZwFather> mChilds;

    private String type;


    public ZwZJChildTwoAdapter(Context context, ArrayList<TimuZwFather> childs, String type) {
        this.mContext = context;
        this.mChilds = childs;
        this.type = type;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {

        Son_ViewHolder son_viewHolder;

        if(convertView == null){

            son_viewHolder = new Son_ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_report_timu_zw_list_son, null);
            son_viewHolder.son_position = (TextView) convertView.findViewById(R.id.item_report_list_son_position);
            son_viewHolder.son_title = (TextView) convertView.findViewById(R.id.item_report_list_son_title);
            son_viewHolder.star1 = (ImageView) convertView.findViewById(R.id.star_1);
            son_viewHolder.mLine = convertView.findViewById(R.id.timu_zw_son_line);
            convertView.setTag(son_viewHolder);
        }else {
            son_viewHolder = (Son_ViewHolder) convertView.getTag();
        }




        final TimuZwSon item = mChilds.get(groupPosition).getKnowledges().get(childPosition);

        son_viewHolder.son_position.setText("" + (childPosition + 1));
        son_viewHolder.son_title.setText(item.getKpoint());

        switch (item.getCan()){
            case 0:
                son_viewHolder.son_title.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_green));
                break;
            case 1:
                son_viewHolder.son_title.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_red));
                break;
            case 2:
                son_viewHolder.son_title.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_gray));
                break;
            default:
                son_viewHolder.son_title.setBackground(mContext.getResources().getDrawable(R.drawable.btn_click_gray));
                break;
        }

        son_viewHolder.star1.setVisibility(View.INVISIBLE);


        if (item.getImportance() != null) {
            switch (item.getImportance()){
                case "1":
                    son_viewHolder.star1.setVisibility(View.VISIBLE);
                    son_viewHolder.star1.setImageResource(R.mipmap.report_item_img_three);
                    break;
                case "2":
                    son_viewHolder.star1.setVisibility(View.VISIBLE);
                    son_viewHolder.star1.setImageResource(R.mipmap.report_item_img_two);
                    break;
                case "3":
                    son_viewHolder.star1.setVisibility(View.VISIBLE);
                    son_viewHolder.star1.setImageResource(R.mipmap.report_item_img_one);

                    break;
            }
        }

        if (childPosition >= (mChilds.get(groupPosition).getKnowledges().size() - 1)) {
            son_viewHolder.mLine.setVisibility(View.GONE);
        } else {
            son_viewHolder.mLine.setVisibility(View.VISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> val = new HashMap<String, String>();
                val.put("kid", item.getKid());
                val.put("point", item.getKpoint());


                if (type.trim().equals("2")) {
                    //强化提高
                    IntentUtil.startActivity((BaseActivity) mContext, ZTJXActivity.class, val);
                } else {
                    //课程学习
                    IntentUtil.startActivity((BaseActivity) mContext, CourseDetailActivity.class, val);
                }


            }
        });

        return convertView;
    }


    public final class Son_ViewHolder {
        private TextView son_position;
        private TextView son_title;
        private ImageView star1;
        private View mLine;
    }



    @Override
    public Object getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChilds.get(groupPosition).getKnowledges().get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).getKnowledges().size();
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_group_item_two_two,null);

            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        convertView.setPadding(0, 10, 0, 10);
        if (!isExpanded) {
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_down);
        } else {
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_up);
        }

        holder.update(mChilds.get(groupPosition));
        if (null == mChilds.get(groupPosition).getKnowledges()){
            holder.childNum.setText("(0)");
        }else {
            holder.childNum.setText("("+mChilds.get(groupPosition).getKnowledges().size()+")");
        }


        return convertView;
    }


    class GroupHolder {

        private TextView childGroupTV;
        private TextView childNum;
        private ImageView arrowImg;

        public GroupHolder(View v) {
            childGroupTV = (TextView) v.findViewById(R.id.childGroupTV);
            childNum = (TextView) v.findViewById(R.id.child_group_num);
            arrowImg = (ImageView) v.findViewById(R.id.child_group_img);
        }

        public void update(TimuZwFather model) {
            childGroupTV.setText(model.getgFather() + "\n" + model.getName());
        }
    }


    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }


}

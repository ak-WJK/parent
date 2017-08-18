package com.kzb.parents.diagnose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.diagnose.model.TimuZwSon;

import java.util.List;

/********************
 * 作者：malus
 * 日期：17/1/10
 * 时间：下午9:37
 * 注释：
 ********************/
public class ReportTimuZwTwoAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater father_Inflater = null;
    private LayoutInflater son_Inflater = null;

    private List<TimuZwFather> datas;//父层 子层

    public ReportTimuZwTwoAdapter(Context context, List<TimuZwFather> datas) {
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
        if (datas != null && datas.size() > groupPosition && datas.get(groupPosition) != null && datas.get(groupPosition).getKnowledges() != null) {
            return datas.get(groupPosition).getKnowledges().size();
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
        if (datas != null && datas.size() > groupPosition && datas.get(groupPosition).getKnowledges() != null) {
            return datas.get(groupPosition).getKnowledges().get(childPosition);
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
            convertView = father_Inflater.inflate(R.layout.item_report_timu_zw_list_father, null);
            father_ViewHolder = new Father_ViewHolder();
            father_ViewHolder.father_title = (TextView) convertView.findViewById(R.id.item_report_list_father_title);
            father_ViewHolder.father_icon = (ImageView) convertView.findViewById(R.id.item_report_list_father_icon);
            father_ViewHolder.father_num = (TextView) convertView.findViewById(R.id.item_report_list_father_num_view);
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

        father_ViewHolder.father_num.setVisibility(View.VISIBLE);

        if(null == datas.get(groupPosition).getKnowledges()){
            father_ViewHolder.father_num.setText("(0)");
        }else {
            father_ViewHolder.father_num.setText("("+datas.get(groupPosition).getKnowledges().size()+")");
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Son_ViewHolder son_ViewHolder = null;
        if (convertView == null) {
            convertView = son_Inflater.inflate(R.layout.item_report_timu_zw_list_son, null);
            son_ViewHolder = new Son_ViewHolder();
            son_ViewHolder.son_position = (TextView) convertView.findViewById(R.id.item_report_list_son_position);
            son_ViewHolder.son_title = (TextView) convertView.findViewById(R.id.item_report_list_son_title);
            son_ViewHolder.star1 = (ImageView) convertView.findViewById(R.id.star_1);
//            son_ViewHolder.star2 = (ImageView) convertView.findViewById(R.id.star_2);
//            son_ViewHolder.star3 = (ImageView) convertView.findViewById(R.id.star_3);
            son_ViewHolder.mLine = convertView.findViewById(R.id.timu_zw_son_line);
            convertView.setTag(son_ViewHolder);
        } else {
            son_ViewHolder = (Son_ViewHolder) convertView.getTag();

        }
        final TimuZwSon item = datas.get(groupPosition).getKnowledges().get(childPosition);

        son_ViewHolder.son_position.setText("" + (childPosition + 1));
        son_ViewHolder.son_title.setText(item.getKpoint());

        switch (item.getCan()){
            case 0:
                son_ViewHolder.son_title.setBackground(context.getResources().getDrawable(R.drawable.btn_click_green));
                break;
            case 1:
                son_ViewHolder.son_title.setBackground(context.getResources().getDrawable(R.drawable.btn_click_red));
                break;
            case 2:
                son_ViewHolder.son_title.setBackground(context.getResources().getDrawable(R.drawable.btn_click_gray));
                break;
            default:
                son_ViewHolder.son_title.setBackground(context.getResources().getDrawable(R.drawable.btn_click_gray));
                break;
        }

        son_ViewHolder.star1.setVisibility(View.INVISIBLE);
//        son_ViewHolder.star2.setVisibility(View.INVISIBLE);
//        son_ViewHolder.star3.setVisibility(View.INVISIBLE);
        if (item.getImportance() != null) {
            switch (item.getImportance()){
                case "1":
                    son_ViewHolder.star1.setVisibility(View.VISIBLE);
                    son_ViewHolder.star1.setImageResource(R.mipmap.report_item_img_three);
                    break;
                case "2":
                    son_ViewHolder.star1.setVisibility(View.VISIBLE);
//                    son_ViewHolder.star3.setVisibility(View.VISIBLE);
                    son_ViewHolder.star1.setImageResource(R.mipmap.report_item_img_two);
                    break;
                case "3":
                    son_ViewHolder.star1.setVisibility(View.VISIBLE);
                    son_ViewHolder.star1.setImageResource(R.mipmap.report_item_img_one);
//                    son_ViewHolder.star2.setVisibility(View.VISIBLE);
//                    son_ViewHolder.star3.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (childPosition >= (datas.get(groupPosition).getKnowledges().size() - 1)) {
            son_ViewHolder.mLine.setVisibility(View.GONE);
        } else {
            son_ViewHolder.mLine.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public final class Father_ViewHolder {
        private TextView father_title;
        private ImageView father_icon;
        private TextView father_num;
    }

    public final class Son_ViewHolder {
        private TextView son_position;
        private TextView son_title;
        private ImageView star1;
//        private ImageView star2;
//        private ImageView star3;
        private View mLine;
    }
}


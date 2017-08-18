package com.kzb.parents.strengthen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.course.adapter.GridViewAdapter;
import com.kzb.parents.course.model.CourseResponse;
import com.kzb.parents.course.model.CourseResponse.JieModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChildAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private ArrayList<JieModel> mChilds;

    private String type;

    private int lnType;//0为未学习，1为学习

    public ChildAdapter(Context context, ArrayList<JieModel> childs, String type) {
        this.mContext = context;
        this.mChilds = childs;
        this.type = type;
    }

    public void setLnType(int lnType) {
        this.lnType = lnType;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return mChilds.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return mChilds.get(groupPosition).getKnowledges().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {

        View view = (View) LayoutInflater.from(mContext).inflate(
                R.layout.child_child_item, null);

        ListView childChildTV = (ListView) view.findViewById(R.id.child_listview);
        childChildTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseResponse.KnowledgeModel knowledgeModel = mChilds.get(groupPosition).getKnowledges().get(position);
                Map<String, String> val = new HashMap<String, String>();
                val.put("kid", knowledgeModel.getKid());
                val.put("point", knowledgeModel.getKpoint());
                if (type.trim().equals("2")) {
                    //强化提高
//                    IntentUtil.startActivity((BaseActivity) mContext, ExamActivity.class, val);
                } else {
                    //课程学习
//                    IntentUtil.startActivity((BaseActivity) mContext, CourseDetailActivity.class, val);
                }

//				Log.e("tttt","knowledgeModel="+knowledgeModel.toString());
            }
        });
//		childChildTV.setNumColumns(2);// 设置每行列数
//		childChildTV.setGravity(Gravity.CENTER);// 位置居中
//		childChildTV.setVerticalSpacing(10);
//		childChildTV.setHorizontalSpacing(10);

//		SanjiListAdapter gridViewAdapter = new SanjiListAdapter(mContext,mChilds.get(groupPosition).getKnowledges(),R.layout.item_streng_three_ji);
//		childChildTV.setAdapter(gridViewAdapter);


        GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext);
        gridViewAdapter.setItems(mChilds.get(groupPosition).getKnowledges());
        childChildTV.setAdapter(gridViewAdapter);

        gridViewAdapter.setLnType(lnType);
//		holder.update(mChilds.get(groupPosition).getKnowledges());
        return view;
    }


//	class ChildHolder {
//
//		private MyGridView childChildTV;
//
//		public ChildHolder(View v) {
//			childChildTV = (MyGridView) v.findViewById(R.id.childChildTV);
//
//			childChildTV.setNumColumns(4);// 设置每行列数
//			childChildTV.setGravity(Gravity.CENTER);// 位置居中
//			childChildTV.setVerticalSpacing(10);
//
//
//
////			childChildTV.setNumColumns(3);
////			childChildTV.setGravity(Gravity.CENTER);
////			childChildTV.setHorizontalSpacing(10);
//
////			final int rowHeightDp = 40;
////			final float ROW_HEIGHT = mContext.getResources()
////					.getDisplayMetrics().density * rowHeightDp;
////			int grandChildCount = getThreeLevelCount(mFatherGroupPosition,
////					mChildGroupPosition);
////			int rowCount = (int) Math.ceil((double) grandChildCount
////					/ mThreeLevelColumns);
////			final int GRID_HEIGHT = (int) (ROW_HEIGHT * rowCount);
////			gridView.getLayoutParams().height = GRID_HEIGHT;
//
//
//		}

//		public void update(List<KnowledgeModel> knowledgeModelList) {
//
//			GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext);
//			gridViewAdapter.setItems(knowledgeModelList);
//			childChildTV.setAdapter(gridViewAdapter);
//
////			childChildTV.setText(knowledgeModelList.getKpoint());
//
////			GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext);
////			gridViewAdapter.setItems(knowledgeModelList);
////			childChildTV.setAdapter(gridViewAdapter);
//
////			childChildTV.setText(str);
////			childChildTV.setTextColor(Color.parseColor("#00ffff"));
//		}
//	}

    @Override
    public Object getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_group_item, parent, false);

            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        convertView.setPadding(10, 10, 10, 10);
        if (!isExpanded) {
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_down);
        } else {
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_up);
        }

        holder.update(mChilds.get(groupPosition));

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

        public void update(JieModel model) {
            childGroupTV.setText(model.getName());

            if (lnType == 1 || lnType == 2) {
                //,只有学习下面，已经学习才为蓝色
                childNum.setBackgroundResource(R.drawable.answer_btn_blue);
            } else {
                childNum.setBackgroundResource(R.drawable.answer_btn_red);
            }

            if (model.getCount().equals("0")) {
                childNum.setVisibility(View.GONE);
            } else {
                childNum.setVisibility(View.VISIBLE);
                childNum.setText(model.getCount());
            }


        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}

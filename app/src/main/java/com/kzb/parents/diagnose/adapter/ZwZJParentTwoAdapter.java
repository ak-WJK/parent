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
import com.kzb.parents.diagnose.JiXieThreeNewActivity;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.diagnose.model.TimuZwGFather;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaofei on 17/2/8.
 */

public class ZwZJParentTwoAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private List<TimuZwGFather> mParents;
    private String test_id;

    private OnChildTreeViewClickListener mTreeViewClickListener;

//    private String type;



    public ZwZJParentTwoAdapter(Context context, List<TimuZwGFather> parents, String type,String test_id) {
        this.mContext = context;
        this.mParents = parents;
//        this.type = type;
        this.test_id = test_id;
    }


    @Override
    public TimuZwFather getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).getJie().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mParents.get(groupPosition).getJie() != null ? mParents
                .get(groupPosition).getJie().size() : 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {


      View view = LayoutInflater.from(mContext).inflate(
                R.layout.child_group_item_three, parent,false);

        final TimuZwFather child = getChild(groupPosition, childPosition);

      TextView  childGroupTV = (TextView) view.findViewById(R.id.childGroupTV);
        TextView  childNum = (TextView) view.findViewById(R.id.child_group_num);
        ImageView arrowImg = (ImageView) view.findViewById(R.id.child_group_img);
        arrowImg.setVisibility(View.GONE);
        childNum.setVisibility(View.GONE);

        childGroupTV.setText(child.getName());
        view.setPadding(10,10,10,10);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> mapVal = new HashMap<String, String>();
                mapVal.put("id",child.getId());
                mapVal.put("test_id",test_id);
                mapVal.put("name",child.getName());
                mapVal.put("typev","4");
//                IntentUtil.startActivity((BaseActivity)mContext,JiXieThreeActivity.class,mapVal);
                IntentUtil.startActivity((BaseActivity)mContext,JiXieThreeNewActivity.class,mapVal);
            }
        });

        return view;


//        final ExpandableListView eListView = getExpandableListView();
//
//        ArrayList<TimuZwFather> childs = new ArrayList<TimuZwFather>();
//
//
//
//        childs.add(child);
//
//        final ZwZJChildAdapter childAdapter = new ZwZJChildAdapter(mContext,
//                childs,type);
//
//        eListView.setAdapter(childAdapter);
//
//
//        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView arg0, View arg1,
//                                        int groupIndex, int childIndex, long arg4) {
//
//                if (mTreeViewClickListener != null) {
//
//                    mTreeViewClickListener.onClickPosition(groupPosition,
//                            childPosition, childIndex);
//                }
//                return false;
//            }
//        });
//
//
//
//        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//                try {
//                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, (child
//                            .getKnowledges().size() + 1)
//                            * (int) mContext.getResources().getDimension(
//                            R.dimen.parent_expandable_list_height));
//                    eListView.setLayoutParams(lp);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//
//        eListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
//                        .getResources().getDimension(
//                                R.dimen.parent_expandable_list_height));
//                eListView.setLayoutParams(lp);
//            }
//        });
//        return eListView;

    }


//    public ExpandableListView getExpandableListView() {
//        ZwZJParentTwoAdapter.CustExpListview mExpandableListView = new ZwZJParentTwoAdapter.CustExpListview(
//                mContext);
//        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
//                .getResources().getDimension(
//                        R.dimen.parent_expandable_list_height));
//
//
//        mExpandableListView.setLayoutParams(lp);
//        mExpandableListView.setDividerHeight(0);
//        mExpandableListView.setChildDivider(null);
//        mExpandableListView.setGroupIndicator(null);
//        return mExpandableListView;
//    }


//    public class CustExpListview extends ExpandableListView {
//
//        public CustExpListview(Context context) {
//            super(context);
//        }
//
//        public CustExpListview(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(Application.WIDTH,
//                    MeasureSpec.AT_MOST);
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Application.HEIGHT,
//                    MeasureSpec.AT_MOST);
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
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
                    R.layout.parent_group_item, parent,false);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        if(!isExpanded){
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_down);
        }else {
            holder.arrowImg.setBackgroundResource(R.mipmap.arrow_up);
        }

        holder.update(mParents.get(groupPosition));
        convertView.setPadding(10,10,10,10);

        return convertView;
    }


    class GroupHolder {

        private TextView parentGroupTV;
        private ImageView arrowImg;
        private TextView parentNum;

        public GroupHolder(View v) {
            parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
            arrowImg = (ImageView) v.findViewById(R.id.parent_group_img);
            parentNum = (TextView) v.findViewById(R.id.parent_group_num);
            parentNum.setVisibility(View.GONE);
        }

        public void update(TimuZwGFather model) {
            parentGroupTV.setText(model.getName());

//            if(lnType == 1){
//                //,只有学习下面，已经学习才为蓝色
//                parentNum.setBackgroundResource(R.drawable.answer_btn_blue);
//            }else {
//                parentNum.setBackgroundResource(R.drawable.answer_btn_red);
//            }
//
//            if (model.getCount().equals("0")) {
//                parentNum.setVisibility(View.GONE);
//                parentNum.setText(model.getCount());
//            } else {
//                parentNum.setVisibility(View.VISIBLE);
//                parentNum.setText(model.getCount());
//            }

        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public void setOnChildTreeViewClickListener(
            OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }


    public interface OnChildTreeViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition,
                             int childPosition);
    }

}

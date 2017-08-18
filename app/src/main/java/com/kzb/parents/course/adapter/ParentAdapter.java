package com.kzb.parents.course.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.kzb.parents.course.model.CourseResponse.ZhangModel;
import com.kzb.parents.course.model.CourseResponse.JieModel;
import com.kzb.parents.R;
import com.kzb.parents.application.Application;

import java.util.ArrayList;
import java.util.List;

/*
*未强化Adapter
*
* */
public class ParentAdapter extends BaseExpandableListAdapter {

	private Context mContext;

	private List<ZhangModel> mParents;

	private OnChildTreeViewClickListener mTreeViewClickListener;

	private String type;


	private int lnType;//0为未学习，1为学习(学习的已学习)，2强化（强化的已强化）


	public ParentAdapter(Context context, List<ZhangModel> parents,String type) {
		this.mContext = context;
		this.mParents = parents;
		this.type = type;
	}



	public void setLnType(int lnType) {
		this.lnType = lnType;
	}

	@Override
	public JieModel getChild(int groupPosition, int childPosition) {
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

		final ExpandableListView eListView = getExpandableListView();

		ArrayList<JieModel> childs = new ArrayList<JieModel>();

		final JieModel child = getChild(groupPosition, childPosition);

		childs.add(child);

		final ChildAdapter childAdapter = new ChildAdapter(this.mContext,
				childs,type);

		childAdapter.setLnType(lnType);
		eListView.setAdapter(childAdapter);


		eListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupIndex, int childIndex, long arg4) {

				if (mTreeViewClickListener != null) {

					mTreeViewClickListener.onClickPosition(groupPosition,
							childPosition, childIndex);
				}
				return false;
			}
		});

		

		eListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

				try {
					LayoutParams lp = new LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, (child
							.getKnowledges().size() + 1)
							* (int) mContext.getResources().getDimension(
							R.dimen.parent_expandable_list_height));
					eListView.setLayoutParams(lp);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		});


		eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {

				LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
								.getResources().getDimension(
										R.dimen.parent_expandable_list_height));
				eListView.setLayoutParams(lp);
			}
		});
		return eListView;

	}


	public ExpandableListView getExpandableListView() {
		CustExpListview mExpandableListView = new CustExpListview(
				mContext);
		LayoutParams lp = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
						.getResources().getDimension(
								R.dimen.parent_expandable_list_height));

//		LayoutParams lp = new LayoutParams(Application.WIDTH, (int) mContext
//				.getResources().getDimension(
//						R.dimen.parent_expandable_list_height));
		mExpandableListView.setLayoutParams(lp);
		mExpandableListView.setDividerHeight(0);
		mExpandableListView.setChildDivider(null);
		mExpandableListView.setGroupIndicator(null);
		return mExpandableListView;
	}


	public class CustExpListview extends ExpandableListView {

		public CustExpListview(Context context) {
			super(context);
		}

		public CustExpListview(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(Application.WIDTH,
					MeasureSpec.AT_MOST);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(Application.HEIGHT,
					MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

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
		}

		public void update(ZhangModel model) {
			parentGroupTV.setText(model.getName());

//			if(lnType == 1 || lnType == 2){
//				//,只有学习下面，已经学习才为蓝色
//				parentNum.setBackgroundResource(R.drawable.answer_btn_blue);
//			}else {
//				parentNum.setBackgroundResource(R.drawable.answer_btn_red);
//			}

			if (model.getCount().equals("0")) {
				parentNum.setVisibility(View.GONE);
				parentNum.setText("("+model.getCount()+")");
			} else {
				parentNum.setVisibility(View.VISIBLE);
				parentNum.setText("("+model.getCount()+")");
			}




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

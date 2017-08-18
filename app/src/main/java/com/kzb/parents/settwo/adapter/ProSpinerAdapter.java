package com.kzb.parents.settwo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.settwo.model.ProResponse;
import com.kzb.parents.view.widget.AbstractSpinerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class ProSpinerAdapter extends BaseAdapter {

    public static interface IOnItemSelectListener{
        public void onItemClick(int pos);
    };

    private Context mContext;
    private List<ProResponse.ProModel> mObjects = new ArrayList<ProResponse.ProModel>();
    private int mSelectItem = 0;

    private LayoutInflater mInflater;

    public  ProSpinerAdapter(Context context){
        init(context);
    }

    public void refreshData(List<ProResponse.ProModel> objects, int selIndex){
        mObjects = objects;
        if (selIndex < 0){
            selIndex = 0;
        }
        if (selIndex >= mObjects.size()){
            selIndex = mObjects.size() - 1;
        }

        mSelectItem = selIndex;
    }

    private void init(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return mObjects.size();
    }

    @Override
    public ProResponse.ProModel getItem(int pos) {
        return mObjects.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
        AbstractSpinerAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
            viewHolder = new AbstractSpinerAdapter.ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AbstractSpinerAdapter.ViewHolder) convertView.getTag();
        }


//		YearModel item =  getItem(pos);
        ProResponse.ProModel item =  mObjects.get(pos);
        viewHolder.mTextView.setText(item.getAreaname());

        return convertView;
    }

    public static class ViewHolder
    {
        public TextView mTextView;
    }


}

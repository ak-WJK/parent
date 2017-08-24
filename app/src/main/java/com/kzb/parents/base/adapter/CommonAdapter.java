package com.kzb.parents.base.adapter;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 16/10/4.
 *
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas = new ArrayList<>();

    public CommonAdapter(Context context) {
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
        this.mContext = context;
    }


    public void setItems(List<T> listVals) {

        this.mDatas.clear();
        if (listVals != null) {
            this.mDatas.addAll(listVals);
        }
        notifyDataSetChanged();
    }


    public void addItems(List<T> listVas) {
        this.mDatas.addAll(listVas);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

package com.kzb.parents.wrong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wanghaofei on 17/3/28.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int mLayoutId;


    public CommonAdapter(Context context,List<T> datas,int layoutId) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        if(mDatas==null){
            return 0;
        }else{
            return mDatas.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if(mDatas!=null){
            return mDatas.get(position);
        }else{
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext,convertView,parent,mLayoutId,position);
        convert(holder,mDatas.get(position));
        return holder.getConvertView();
    }

    protected abstract void convert(ViewHolder holder,T t);
}

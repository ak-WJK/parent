package com.kzb.parents.settwo.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.kzb.parents.R;
import com.kzb.parents.settwo.model.ProResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class ProSpinerPopWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private ProSpinerAdapter mAdapter;
    private ProSpinerAdapter.IOnItemSelectListener mItemSelectListener;


    public ProSpinerPopWindow(Context context)
    {
        super(context);

        mContext = context;
        init();
    }


    public void setItemListener(ProSpinerAdapter.IOnItemSelectListener listener){
        mItemSelectListener = listener;
    }

    public void setAdatper(ProSpinerAdapter adapter){
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }


    public <ProModel> void refreshData(List<ProResponse.ProModel> list, int selIndex)
    {
        if (list != null && selIndex  != -1)
        {
            if (mAdapter != null){
                mAdapter.refreshData(list, selIndex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null){
            mItemSelectListener.onItemClick(pos);
        }
    }




}

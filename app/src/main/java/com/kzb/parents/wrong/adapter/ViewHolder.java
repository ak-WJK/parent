package com.kzb.parents.wrong.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzb.parents.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    protected ImageLoader imageLoader;
    protected Context mContext;
    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .displayer(new RoundedBitmapDisplayer(0)).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(options).build();
        imageLoader.init(configuration);
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public int getPosition(){
        return mPosition;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        if (text == null) {
            text = "";
        }
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public ViewHolder setBackground(int viewId, int background) {
        View view = getView(viewId);
        view.setBackgroundResource(background);
        return this;
    }


    public ViewHolder setVisibilityTwo(int viewId, boolean visible) {
        if (visible) {
            getView(viewId).setVisibility(View.VISIBLE);
        } else {
            getView(viewId).setVisibility(View.GONE);
        }
        return this;
    }

    public ViewHolder setVisibility(int viewId, boolean visible) {
        if (visible) {
            getView(viewId).setVisibility(View.VISIBLE);
        } else {
            getView(viewId).setVisibility(View.INVISIBLE);
        }
        return this;
    }
    public ViewHolder gone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }
    public ViewHolder invisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    public ViewHolder setImageResource(int viewId, String url) {
        ImageView iv = getView(viewId);
        imageLoader.displayImage(url, iv);
        return this;
    }
    public ViewHolder setImageResource(int viewId, int res) {
        ImageView iv = getView(viewId);
        iv.setImageResource(res);
        return this;
    }
    public ViewHolder setOnCLickListener(int viewId, View.OnClickListener onClickListener){
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
        return this;
    }
}

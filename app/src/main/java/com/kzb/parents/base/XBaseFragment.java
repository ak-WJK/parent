package com.kzb.parents.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wanghaofei on 17/1/3.
 */


//当默认首页首先加载时会导致viewPager的首页第一次展示时没有数据显示，
// 切换一下才会有数据。因为首页fragment的setUserVisible()在onActivityCreated() 之前调用，
// 此时isPrepared为false 导致首页fragment 没能调用onLazyLoad()方法加载数据。

public class XBaseFragment extends Fragment {


    private boolean isLazyLoaded;
    private boolean isPrepared;

    protected BaseActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad(){
        if(getUserVisibleHint() && isPrepared && !isLazyLoaded){
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    public void onLazyLoad(){

    }


}

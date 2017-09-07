package com.kzb.parents.diagnose;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;

import java.util.ArrayList;

public class WTJieXiNewActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ViewPager viewPager;

    private String path1;
    private String path2;
    private ArrayList<String> icon = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wtjie_xi_new);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        viewPager = getView(R.id.view_pager);


        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter.setText("诊断报告");

        Intent intent = getIntent();
        path1 = intent.getStringExtra("path1");
        path2 = intent.getStringExtra("path2");
        LogUtils.e("TAG", "path 1 == " + path1 + " path2 == " + path2);
        icon.add(path1);
        icon.add(path2);


    }

    @Override
    protected void initData() {


        viewPager.setAdapter(new SamplePagerAdapter(icon));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(WTJieXiNewActivity.this);

                break;
        }

    }

    static class SamplePagerAdapter extends PagerAdapter {


        private ArrayList<String> icon;

        public SamplePagerAdapter(ArrayList<String> icon) {

            this.icon = icon;
        }

        @Override
        public int getCount() {
            return icon.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {

            PhotoView photoView = new PhotoView(container.getContext());

//            photoView.setScaleType(ImageView.ScaleType.FIT_XY);

            String path = icon.get(position);

            Glide.with(container.getContext()).load(path).into(photoView);

//

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


}

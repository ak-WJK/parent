package com.kzb.parents.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.main.fragment.FirstFragment;
import com.kzb.parents.main.fragment.FourthFragment;
import com.kzb.parents.main.fragment.SecFragment;
import com.kzb.parents.main.fragment.ThirdFragment;
import com.kzb.parents.main.model.LunBoResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private LinearLayout fristLayout, secLayout,thirdLayout, fourthLayout;
    private ImageView firstImg, secImg, thirdImg,fourthImg;
    private TextView firstTxt, secTxt, thirdTxt,fourthTxt;

    private ViewPager viewPager;

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView titleView;

    public List<LunBoResponse.LunBoModel> lunboVals;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpConfig = new HttpConfig();
        initView();
        initData();

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 101);

            }
        }
    }

    @Override
    protected void initView() {

        titleView = getView(R.id.common_head_center);

        fristLayout = getView(R.id.main_first_layout);
        firstImg = getView(R.id.main_first_img);
        firstTxt = getView(R.id.main_first_txt);

        secLayout = getView(R.id.main_second_layout);
        secImg = getView(R.id.main_second_img);
        secTxt = getView(R.id.main_second_txt);

        thirdLayout = getView(R.id.main_third_layout);
        thirdImg = getView(R.id.main_third_img);
        thirdTxt = getView(R.id.main_third_txt);


        fourthLayout = getView(R.id.main_fourth_layout);
        fourthImg = getView(R.id.main_fourth_img);
        fourthTxt = getView(R.id.main_fourth_txt);

        viewPager = getView(R.id.main_viewpager);

        fristLayout.setOnClickListener(this);
        secLayout.setOnClickListener(this);
        thirdLayout.setOnClickListener(this);
        fourthLayout.setOnClickListener(this);

    }








    @Override
    protected void initData() {

        mFragments.add(new FirstFragment());
        mFragments.add(new SecFragment());
        mFragments.add(new ThirdFragment());
        mFragments.add(new FourthFragment());


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(this);
        tableFirst();


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void tableFirst() {
        titleView.setText("考之宝");
        viewPager.setCurrentItem(0);
        firstImg.setImageResource(R.mipmap.main_first_press);
        secImg.setImageResource(R.mipmap.main_sec_normal);
        thirdImg.setImageResource(R.mipmap.main_third_normal);
        fourthImg.setImageResource(R.mipmap.main_fourth_normal);

        firstTxt.setTextColor(getResources().getColor(R.color.theme_green));
        secTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        thirdTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        fourthTxt.setTextColor(getResources().getColor(R.color.theme_gray));

    }

    private void tableSec() {
        titleView.setText("报告");
        viewPager.setCurrentItem(1);
        firstImg.setImageResource(R.mipmap.main_first_normal);
        secImg.setImageResource(R.mipmap.main_sec_press);
        thirdImg.setImageResource(R.mipmap.main_third_normal);
        fourthImg.setImageResource(R.mipmap.main_fourth_normal);

        firstTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        secTxt.setTextColor(getResources().getColor(R.color.theme_green));
        thirdTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        fourthTxt.setTextColor(getResources().getColor(R.color.theme_gray));
    }

    private void tableThird() {
        titleView.setText("知识点");
        viewPager.setCurrentItem(2);
        firstImg.setImageResource(R.mipmap.main_first_normal);
        secImg.setImageResource(R.mipmap.main_sec_normal);
        thirdImg.setImageResource(R.mipmap.main_third_press);
        fourthImg.setImageResource(R.mipmap.main_fourth_normal);

        firstTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        secTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        thirdTxt.setTextColor(getResources().getColor(R.color.theme_green));
        fourthTxt.setTextColor(getResources().getColor(R.color.theme_gray));
    }


    private void tableFourth() {
        titleView.setText("我的");
        viewPager.setCurrentItem(3);
        firstImg.setImageResource(R.mipmap.main_first_normal);
        secImg.setImageResource(R.mipmap.main_sec_normal);
        thirdImg.setImageResource(R.mipmap.main_third_normal);
        fourthImg.setImageResource(R.mipmap.main_fourth_press);

        firstTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        secTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        thirdTxt.setTextColor(getResources().getColor(R.color.theme_gray));
        fourthTxt.setTextColor(getResources().getColor(R.color.theme_green));
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                tableFirst();
                break;
            case 1:
                tableSec();

                break;
            case 2:
                tableThird();

                break;
            case 3:
                tableFourth();

                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_first_layout:
                tableFirst();
                break;
            case R.id.main_second_layout:
                tableSec();
                break;
            case R.id.main_fourth_layout:
                tableFourth();
                break;
            case R.id.main_third_layout:
                tableThird();
                break;
        }
    }
}

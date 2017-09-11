package com.kzb.parents;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.main.model.VipResponse;
import com.kzb.parents.paywx.PayActivity;
import com.kzb.parents.payzfb.PayDemoActivity;
import com.kzb.parents.util.IntentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 17/6/21.
 */

public class VipDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;

    private TextView vipType;
    private TextView detailContent;
    private TextView moneyView;
    private TextView payView;

    private RelativeLayout wxLayout;
    private RelativeLayout zfbLayout;

    private ImageView wxSign;
    private ImageView zfbSign;

    private VipResponse.MoneyModel moneyModel;

    //支付类型 1 为微信支付，2 为支付宝支付
    private int payType = 1;


    private TextView vvType;
    private RelativeLayout payLayout;
    private View lineFour, lineFive, lineThree;


    private ImageView headerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail);

        moneyModel = (VipResponse.MoneyModel) getIntent().getSerializableExtra("vip");

        if (null == moneyModel) {
            return;
        }

        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        vipType = getView(R.id.vip_detail_tilte);
        detailContent = getView(R.id.vip_detail_content);
        moneyView = getView(R.id.vip_detail_money);
        payView = getView(R.id.vip_detail_pay);
        wxLayout = getView(R.id.vip_detail_wx);
        zfbLayout = getView(R.id.vip_detail_zfb);
        wxSign = getView(R.id.vip_detail_wx_sign);
        zfbSign = getView(R.id.vip_detail_zfb_sign);

        headerImg = getView(R.id.vip_detail_img);
        vvType = getView(R.id.vip_detail_type);
        payLayout = getView(R.id.vip_detail_pay_layout);
        lineFour = getView(R.id.vip_detail_line_four);
        lineFive = getView(R.id.vip_detail_line_five);
        lineThree = getView(R.id.vip_detail_line_three);


        Glide.with(VipDetailActivity.this).load(moneyModel.getImg()).into(headerImg);

        if( moneyModel == null || moneyModel.getPrice() == null || moneyModel.getPrice().trim().length() == 0){
            vvType.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
            lineFour.setVisibility(View.GONE);
            lineFive.setVisibility(View.GONE);
            lineThree.setVisibility(View.GONE);
            zfbLayout.setVisibility(View.GONE);
            wxLayout.setVisibility(View.GONE);
        }else {
            if(moneyModel != null && moneyModel.getPrice() != null && moneyModel.getPrice().trim().length() != 0){
                if(Double.parseDouble(moneyModel.getPrice()) == 0){
                    vvType.setVisibility(View.GONE);
                    payLayout.setVisibility(View.GONE);
                    lineFour.setVisibility(View.GONE);
                    lineFive.setVisibility(View.GONE);
                    lineThree.setVisibility(View.GONE);
                    zfbLayout.setVisibility(View.GONE);
                    wxLayout.setVisibility(View.GONE);
                }else {
                    vvType.setVisibility(View.VISIBLE);
                    payLayout.setVisibility(View.VISIBLE);
                    lineFour.setVisibility(View.VISIBLE);
                    lineFive.setVisibility(View.VISIBLE);
                    lineThree.setVisibility(View.VISIBLE);
                    zfbLayout.setVisibility(View.VISIBLE);
                    wxLayout.setVisibility(View.VISIBLE);
                }
            }

        }


        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");
        titleCenter.setText("确认订单");


        titleLeft.setOnClickListener(this);
        zfbLayout.setOnClickListener(this);
        wxLayout.setOnClickListener(this);
        payView.setOnClickListener(this);
        wxLayout.setOnClickListener(this);
        zfbLayout.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        vipType.setText(moneyModel.getName());
        detailContent.setText(moneyModel.getContent());
        moneyView.setText("总计" + moneyModel.getPrice());


    }


    //选择支付宝背景变更
    private void zfbChange() {

        payType = 2;
        wxSign.setImageResource(R.mipmap.pay_select_normal);
        zfbSign.setImageResource(R.mipmap.pay_select_press);
    }

    //选择微信背景变更
    private void wxChange() {

        payType = 1;
        wxSign.setImageResource(R.mipmap.pay_select_press);
        zfbSign.setImageResource(R.mipmap.pay_select_normal);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_detail_wx:
                wxChange();
                break;
            case R.id.vip_detail_zfb:
                zfbChange();
                break;
            case R.id.vip_detail_pay:

                if (payType == 2) {
                    Map<String, String> mapVal = new HashMap<>();
                    mapVal.put("id", moneyModel.getId());
                    IntentUtil.startActivity(VipDetailActivity.this, PayDemoActivity.class, mapVal);
                } else {
                    Map<String, String> mapVals = new HashMap<>();
                    mapVals.put("id", moneyModel.getId());
                    IntentUtil.startActivity(VipDetailActivity.this, PayActivity.class, mapVals);
                }

                break;
            case R.id.common_head_left:
                IntentUtil.finish(VipDetailActivity.this);
                break;

        }
    }
}

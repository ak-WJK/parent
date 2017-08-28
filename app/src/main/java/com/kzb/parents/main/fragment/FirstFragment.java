package com.kzb.parents.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.application.Application;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.common.ArticeDetailActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.CourseActivity;
import com.kzb.parents.diagnose.DiagNoseMainActivity;
import com.kzb.parents.diagnose.DiagnoseTwoActivity;
import com.kzb.parents.exer.ExerActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.kaoshi.KaoShiActivity;
import com.kzb.parents.login.model.LoginResponse;
import com.kzb.parents.main.model.LunBoResponse;
import com.kzb.parents.main.model.PayResponse;
import com.kzb.parents.msg.MsgListActivity;
import com.kzb.parents.set.SetCourseActivity;
import com.kzb.parents.strengthen.StrengthenActivity;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.wrong.WrongActivity;
import com.kzb.parents.xunlian.XunLianKaoShiActivity;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.kzb.parents.application.Application.spTimes;
import static com.kzb.parents.config.SpSetting.loadLoginInfo;

/**
 * Created by wanghaofei on 17/2/15.
 */

public class FirstFragment extends BaseFragment implements XBanner.XBannerAdapter, View.OnClickListener, Handler.Callback {

    private HttpConfig httpConfig;
//    private DialogView dialogView;

    private XBanner banner;
    List<LunBoResponse.LunBoModel> imgesUrl = new ArrayList<>();

    private TextView headTitle;

    private TextView zhenDuanLayout, courseLayout, wrongLayout, strengLayout, xuexiLayout, zhenduanBaoGao, exerLayout;

    private TextView curCourseView, msgListView;
    private TextView zhenduiXunLian;


    private boolean sign = false;

    //会员等级
    int level = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, parent, false);
        banner = (XBanner) view.findViewById(R.id.banner_1);

        LogUtils.e("TAG", "诊断详情页面");
        EventBus.getDefault().register(this);

//        dialogView = new DialogView(getActivity());

        initView(view);


        //获取会员信息(造成诊断报告 --> 知识点掌握,下的子Item崩溃)
        // getStatus();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        try {
//            level = Integer.parseInt(SpSetting.loadLoginInfo().getGood_id());
//            LogUtils.e("TAG", "会员等级 === " + level);
//
//        } catch (Exception e) {
//            level = 1;
//        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {

            if (!sign) {
                httpConfig = new HttpConfig();
                getData();
                sign = true;
            }
        }

    }

    private void initView(View view) {

        zhenDuanLayout = (TextView) view.findViewById(R.id.first_zhenduan_layout);
        courseLayout = (TextView) view.findViewById(R.id.first_course_layout);
        wrongLayout = (TextView) view.findViewById(R.id.first_wrong_layout);
        strengLayout = (TextView) view.findViewById(R.id.first_streng_layout);
        xuexiLayout = (TextView) view.findViewById(R.id.first_xuexi_layout);
        curCourseView = (TextView) view.findViewById(R.id.first_current_course);
        msgListView = (TextView) view.findViewById(R.id.first_msg_list);
        zhenduanBaoGao = (TextView) view.findViewById(R.id.first_zhenduan_baogao);
        zhenduiXunLian = (TextView) view.findViewById(R.id.first_zhendui_xunlian);
        exerLayout = (TextView) view.findViewById(R.id.first_exer_layout);


        zhenDuanLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        wrongLayout.setOnClickListener(this);
        strengLayout.setOnClickListener(this);
        xuexiLayout.setOnClickListener(this);
        zhenduanBaoGao.setOnClickListener(this);
        zhenduiXunLian.setOnClickListener(this);
        exerLayout.setOnClickListener(this);

        //登陆后加载用户退出前存储的科目
        curCourseView.setText("科目：" + SpSetting.loadLoginInfo().getSubject());
        LogUtils.e("TAG", "科目 " + SpSetting.loadLoginInfo().getSubject());


        msgListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentUtil.startActivity(getActivity(), MsgListActivity.class);

//                showShare(getActivity(), null, true);
            }
        });


        curCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(getActivity(), SetCourseActivity.class);
            }
        });

    }


    private void getData() {

        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.MAIN_LUNBO);

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<LunBoResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(LunBoResponse response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.getContent() != null) {

                    initData(response.getContent());
                    //成功
//                    IntentUtil.startActivity(SetCourseActivity.this,MainPageActivity.class);

                } else {
                }

            }
        });
    }

    //在切换tab的时候，重复创建
    private void initData(List<LunBoResponse.LunBoModel> lunboVals) {

        if (null == lunboVals) {
            return;
        }

        for (LunBoResponse.LunBoModel lunBoModel : lunboVals) {
            if (lunBoModel != null) {
                imgesUrl.add(lunBoModel);
            }
        }

        banner.setData(imgesUrl, null);
        banner.setmAdapter(FirstFragment.this);

        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {

                Map<String, String> mapv = new HashMap<String, String>();
                mapv.put("id", imgesUrl.get(position).getId());


                IntentUtil.startActivity(getActivity(), ArticeDetailActivity.class, mapv);
            }
        });
    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Glide.with(this).load(imgesUrl.get(position).getPath()).into((ImageView) view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_zhenduan_baogao:
                int count = spTimes.getInt("count", 0);


//                if (level < 3) {
//                    Toast.makeText(getActivity(), R.string.notice_val, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                count++;
//                spTimes.edit().putInt("count", count).commit();

                //报告
                IntentUtil.startActivity(getActivity(), DiagnoseTwoActivity.class);
                break;
            case R.id.first_xuexi_layout:
                //考试
                IntentUtil.startActivity(getActivity(), KaoShiActivity.class);
                break;
            case R.id.first_wrong_layout:
                IntentUtil.startActivity(getActivity(), WrongActivity.class);
                break;
            case R.id.first_course_layout:
                IntentUtil.startActivity(getActivity(), CourseActivity.class);
                break;
            case R.id.first_zhenduan_layout:

                if (level == 3) {
                    //诊断
                    IntentUtil.startActivity(getActivity(), DiagNoseMainActivity.class);
                } else {
                    Toast.makeText(getActivity(), R.string.notice_val, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.first_streng_layout:
                if (level == 3) {

                    //强化进度
                    IntentUtil.startActivity(getActivity(), StrengthenActivity.class);
                } else {
                    Toast.makeText(getActivity(), R.string.notice_val, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.first_zhendui_xunlian:

                IntentUtil.startActivity(getActivity(), XunLianKaoShiActivity.class);
                break;
            case R.id.first_exer_layout:
               IntentUtil.startActivity(getActivity(),ExerActivity.class);
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMesg(String type) {
        curCourseView.setText("科目：" + SpSetting.loadLoginInfo().getSubject());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //获取支付后的订单状态
    protected void getStatus() {

//        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.ORDER_STATUS_URL);

        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("uid", loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<PayResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(PayResponse response, int id) {
//                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {


                    LoginResponse.LoginModel loginModel = SpSetting.loadLoginInfo();
                    loginModel.setStatus(response.getContent().getStatus());
                    loginModel.setGood_id(response.getContent().getGood_id());
                    LogUtils.e("TAG", "登陆成功后会员信息==== " + response.getContent().toString());
//                    SpSetting.saveLoginInfo(loginModel);

                } else {
                    MineToast.show(Application.mContext, response.msg);
                }
            }
        });
    }
}

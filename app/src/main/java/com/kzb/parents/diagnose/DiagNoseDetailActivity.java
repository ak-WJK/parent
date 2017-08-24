package com.kzb.parents.diagnose;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.ReportFinishActivity;
import com.kzb.parents.diagnose.fragment.QuestionOneFragment;
import com.kzb.parents.diagnose.fragment.QuestionThreeFragment;
import com.kzb.parents.diagnose.fragment.QuestionTwoFragmentThree;
import com.kzb.parents.diagnose.fragment.ReportZhangWoCengduFragment;
import com.kzb.parents.diagnose.fragment.ReportZhangWoXingjiFragment;
import com.kzb.parents.diagnose.fragment.ReportZhangWoZhangjieFragmentTwo;
import com.kzb.parents.diagnose.model.Report;
import com.kzb.parents.diagnose.model.ReportContent;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.util.ShareUtil;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.chart.CircleChartView;
import com.kzb.parents.view.chart.CircleForNanduChartView;
import com.kzb.parents.view.chart.RingChartView;
import com.kzb.parents.view.chart.VerticalChart;
import com.kzb.parents.view.chart.VerticalForHorChart;
import com.kzb.parents.view.chart.VerticalForWancChart;
import com.kzb.parents.view.chart.bean.Rate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/3/18.
 * 诊断详情页面
 */

public class DiagNoseDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView headTitle, headLeft, headRight;
    //知识点ID
    private String testId;
    //来源
    private String from;

    private TextView nameview, subView, orderView;

    //知识点掌握
    private TextView kgTotalNum, kgType;
    private TextView kgspNum, kgspType;
    private TextView kgmdNum, kgmdType;
    private TextView kghdNum, kghdType;

    private TextView kgspKc, kgmdKc, kghdKc, kgTotalKc;

    VerticalForHorChart verticalForHorChart;
    VerticalChart verticalChart;
    CircleChartView circleChartView;
    RingChartView ringChartView;
    VerticalForWancChart timuWanCChartView;
    CircleForNanduChartView timuNanduChartView;

    private TextView topOneView, topTwoView, topThreeView;
    private TextView topOneViewTwo, topTwoViewTwo, topThreeViewTwo;

    private TextView chatSignView;

    private LinearLayout chatLayout;
    private int chatSign = 0;//关闭，1打开
    private int kgSign = 0;//关闭，1打开
    private int qusSign = 0;//关闭，1打开

    View kgView, qusView;
    TextView kgTxtView, qusTxtView;
    TextView scoreView;

    ImageView kaoshiView;


    private ScrollView reportScrollView;
    private String wrong_count;
    private ReportContent content;
    private String right_count;
    private String one;
    private String two;
    private String thr;
    //等级图片显示
    private ImageView scoreLevel;
    private int level;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dgno_detail);

        //会员等级
        String good_id = SpSetting.loadLoginInfo().getGood_id();
        level = Integer.parseInt(good_id);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        testId = getIntent().getExtras().getString("test_id");

        //得到(诊断报告or考试报告)
        from = getIntent().getStringExtra("from");

        if (from == null) {
            from = "";
        }
        chatSign = 0;

        initView();
        initData();
    }

    @Override
    protected void initView() {
        headTitle = getView(R.id.common_head_center);
        headLeft = getView(R.id.common_head_left);
        headRight = getView(R.id.common_head_right);
        headRight.setVisibility(View.VISIBLE);
        headRight.setText("分享");
        headLeft.setVisibility(View.VISIBLE);
        headLeft.setText("返回");

        nameview = getView(R.id.dgno_detail_name);
        subView = getView(R.id.dgno_detail_subject);
        orderView = getView(R.id.dgno_detail_order);

        kgTotalNum = getView(R.id.report_kg_num);
        kgType = getView(R.id.report_kg_type);
        kgTotalKc = getView(R.id.report_kc_num);


        kgspNum = getView(R.id.report_kg_sp_num);
        kgspType = getView(R.id.report_kg_sp_type);
        kgspKc = getView(R.id.report_kc_sp_num);


        kgmdNum = getView(R.id.report_kg_md_num);
        kgmdType = getView(R.id.report_kg_md_type);
        kgmdKc = getView(R.id.report_kc_md_num);


        kghdNum = getView(R.id.report_kg_hd_num);
        kghdType = getView(R.id.report_kg_hd_type);
        kghdKc = getView(R.id.report_kc_hd_num);


        topOneView = getView(R.id.report_top_one);
        topTwoView = getView(R.id.report_top_two);
        topThreeView = getView(R.id.report_top_three);

        topOneViewTwo = getView(R.id.report_top_one_two);
        topTwoViewTwo = getView(R.id.report_top_two_two);
        topThreeViewTwo = getView(R.id.report_top_three_two);

        chatSignView = getView(R.id.digo_chat_sign_view);
        chatLayout = getView(R.id.digo_chat_layout);


        kgView = getView(R.id.report_kg_layout);
        qusView = getView(R.id.report_qs_layout);

        kgTxtView = getView(R.id.kg_sign_view);
        qusTxtView = getView(R.id.qs_sign_view);

        scoreView = getView(R.id.report_kg_sp_score_view);
        scoreLevel = getView(R.id.iv_score_level);
        LogUtils.e("TAG", "在此处修改考试分数等级评价(优良中差(图片))");

        kaoshiView = getView(R.id.report_kg_sp_kaoshi_view);

        topOneView.setOnClickListener(this);
        topTwoView.setOnClickListener(this);
        topThreeView.setOnClickListener(this);
        topOneViewTwo.setOnClickListener(this);
        topTwoViewTwo.setOnClickListener(this);
        topThreeViewTwo.setOnClickListener(this);
        chatSignView.setOnClickListener(this);
        kgTxtView.setOnClickListener(this);
        qusTxtView.setOnClickListener(this);
        kaoshiView.setOnClickListener(this);

        reportScrollView = (ScrollView) findViewById(R.id.zdbg_scrollview);

        //分享
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://s.kaozhibao.com/index.php/Home/Test/Generalreport_mobile/tid/" + testId + ".html";
                ShareUtil.showShare(DiagNoseDetailActivity.this, null, false, url);
            }
        });


        //图表
        verticalForHorChart = getView(R.id.chart_horizontal);
        verticalChart = getView(R.id.chart_vertical);
        timuWanCChartView = getView(R.id.chart_timu_wanc);
        circleChartView = getView(R.id.chart_circle);
        timuNanduChartView = getView(R.id.chart_timu_nandu);
        ringChartView = getView(R.id.chart_ring);

        if (from.equals("zhenduan")) {
            headTitle.setText("诊断报告");
        } else {
            headTitle.setText("考试报告");
        }

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.report_knowledge_layout, ReportZhangWoCengduFragment.getInstance(testId), "ReportZhangWoZhangjieFragment");
//        transaction.commit();
//
//
//        FragmentManager managerTwo = getSupportFragmentManager();
//        FragmentTransaction transactionTwo = manager.beginTransaction();
//        transactionTwo.replace(R.id.report_knowledge_layout_two, QuestionOneFragment.getInstance(testId , wrong_count), "ReportZhangWoZhangjieFragment");
//        transactionTwo.commit();


        //返回
        headLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(DiagNoseDetailActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        getQuestion();
    }


    /**
     * 获取题目
     */
    public void getQuestion() {
        if (TextUtils.isEmpty(testId)) {
            Toast.makeText(DiagNoseDetailActivity.this, "testId为空", Toast.LENGTH_SHORT).show();
            IntentUtil.finish(DiagNoseDetailActivity.this);
            return;
        }
//        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();

        try {
            json.put("uid", SpSetting.loadLoginInfo().getUid());
            json.put("test_id", testId);
            json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.GET_TEST_INFO, json), new GenericsCallback<Report>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
//                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(Report response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0) {
                    if (response.getContent() != null) {

                        LogUtils.e("TAG", response.toString());

                        try {
                            content = response.getContent();
                            //此处内容隐藏(得到诊断类型)
                            String type = content.getType();

//                            //得到错(正确)题的个数
                            wrong_count = content.getWrong_count();
                            right_count = content.getRight_count();
                            //得到题目简单度的个数
                            one = content.getqOne();
                            two = content.getqTwo();
                            thr = content.getqThr();

                            //得到考试分数
                            String score = content.getScore();
                            int score1 = Integer.parseInt(score);
                            //设置考试分数显示等级
                            if (score1 < 60) {
                                scoreLevel.setImageResource(R.mipmap.report_img_one);

                            } else if (score1 >= 60 && score1 <= 70) {
                                scoreLevel.setImageResource(R.mipmap.report_img_four);
                            } else if (score1 > 70 && score1 <= 90) {
                                scoreLevel.setImageResource(R.mipmap.report_img_three);
                            } else {
                                scoreLevel.setImageResource(R.mipmap.report_img_two);
                            }


                            orderView.setText(Html.fromHtml("排名:<font color='#de352f'>" + content.getRank() + "</font>" + "/" + content.getTestcount()));

//                            设置顶部内容
                            subView.setText(Html.fromHtml("科目:" + content.getSubject() + ""));
                            String typeVal = "";

                            if ("zhangjie".equals(type)) {
                                typeVal = "章节诊断";
                            } else if ("quanke".equals(type)) {
                                typeVal = "全科诊断";
                            } else if ("testquanke".equals(type)) {
                                typeVal = "全科考试";
                            } else if ("zhenti".equals(type)) {
                                typeVal = "真题诊断";
                            } else if ("testzhangjie".equals(type)) {
                                typeVal = "章节考试";
                            }

//                            typeTypeView.setText(Html.fromHtml("类型:<font color='#323334'>" + typeVal + "</font>"));
//                            reportNumView.setText(Html.fromHtml("编号:<font color='#323334'>" + response.getContent().getCode() + "</font>"));
//                            scoreView.setText(Html.fromHtml("<font color='#FF2D1F'> <big><big><big><big>" + content.getScore() + "</big></big></big></big></font>"));


                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.report_knowledge_layout, ReportZhangWoCengduFragment.getInstance(testId), "ReportZhangWoZhangjieFragment");
                            transaction.commit();


                            FragmentManager managerTwo = getSupportFragmentManager();
                            FragmentTransaction transactionTwo = manager.beginTransaction();
                            transactionTwo.replace(R.id.report_knowledge_layout_two, QuestionOneFragment.getInstance(testId, wrong_count, right_count), "ReportZhangWoZhangjieFragment");
                            transactionTwo.commit();
                            LogUtils.e("TAG", "交换了Fragment的初始化位置");


                            scoreView.setText(content.getScore());

                            kgTotalNum.setText(Html.fromHtml("<font color='#de352f'>" + content.getRightk() + "</font>" + "/" + content.getAllk()));
                            kgType.setText(checkType(content.getAllkLevel()));
                            kgTotalKc.setText("" + content.getAllkq());
                            kgspNum.setText(Html.fromHtml("<font color='#de352f'>" + content.getGetOne() + "</font>" + "/" + content.getOne()));
                            kgspType.setText(checkType(content.getOneLevel()));
                            kgspKc.setText("" + content.getKqOne());
                            kgmdNum.setText(Html.fromHtml("<font color='#de352f'>" + content.getGetTwo() + "</font>" + "/" + content.getTwo()));
                            kgmdType.setText(checkType(content.getTwoLevel()));
                            kgmdKc.setText("" + content.getKqTwo());
                            kghdNum.setText(Html.fromHtml("<font color='#de352f'>" + content.getGetThr() + "</font>" + "/" + content.getThr()));
                            kghdType.setText(checkType(content.getThrqLevel()));
                            kghdKc.setText("" + content.getKqThr());
//                            resultContent.setText(content.getContent());
//
//                            if (content.getTotalLeval() == 1) {
//                                resultType.setImageResource(R.mipmap.report_img_two);
//                            } else if (content.getTotalLeval() == 2) {
//                                resultType.setImageResource(R.mipmap.report_img_three);
//                            } else if (content.getTotalLeval() == 3) {
//                                resultType.setImageResource(R.mipmap.report_img_four);
//                            } else if (content.getTotalLeval() == 4) {
//                                resultType.setImageResource(R.mipmap.report_img_one);
//                            } else {
//                                resultType.setImageResource(R.mipmap.report_img_one);
//                            }


//
                            float vscore = Float.parseFloat(content.getScore());
                            float vtopScore = Float.parseFloat(content.getTopscore());
                            float vlowScore = Float.parseFloat(content.getLowscore());
                            int[] vget = {(int) vtopScore, (int) vscore, (int) vlowScore};
                            int[] vunGet = {0, 0, 0};
                            verticalForHorChart.setNum(100, vunGet, vget);


                            float rightCount = Float.parseFloat(content.getRight_count());
                            float wrongCount = Float.parseFloat(content.getWrong_count());
                            float rateWrong = wrongCount / (rightCount + wrongCount);
                            float rateRight = rightCount / (rightCount + wrongCount);
                            List<Rate> rates = new ArrayList<Rate>();
                            Rate rate1 = new Rate(rateRight, getResources().getColor(R.color.theme_green), "正确数 " + (int) rightCount + "个", "");
                            Rate rate2 = new Rate(rateWrong, getResources().getColor(R.color.theme_red), "错误数 " + (int) wrongCount + "个", "");
                            rates.add(rate2);
                            rates.add(rate1);
                            ringChartView.setRates(rates);
                            ringChartView.setTestId(testId);


                            //星星
                            float oneCount = Float.parseFloat(content.getOne());
                            float twoCount = Float.parseFloat(content.getTwo());
                            float thrCount = Float.parseFloat(content.getThr());
                            float ontRate = oneCount / (oneCount + twoCount + thrCount);
                            float twoRate = twoCount / (oneCount + twoCount + thrCount);
                            float thrRate = thrCount / (oneCount + twoCount + thrCount);
                            List<Rate> starRate = new ArrayList<Rate>();

                            Rate rate = new Rate(ontRate, Color.GRAY, "简单 " + (int) oneCount + "个", "");
                            starRate.add(rate);
                            rate = new Rate(thrRate, getResources().getColor(R.color.theme_red), "较难" + (int) thrCount + "个", "");
                            starRate.add(rate);
                            rate = new Rate(twoRate, getResources().getColor(R.color.theme_green), "中等 " + (int) twoCount + "个", "");
                            starRate.add(rate);

                            circleChartView.setRates(starRate);
//                            circleChartView.setOnChartClick(new CircleChartView.OnChartClick() {
//                                @Override
//                                public void onChartClick(final int position) {
//                                    kgLayout.setVisibility(View.VISIBLE);
//                                    kgSign = 1;
//                                    kgHanlView.setImageResource(R.mipmap.arrow_up);
//                                    picSign = 2;
//                                    if (position == 1) {
//                                        //难
//                                        topThreeClick(1);
//                                    }else if(position == 0){
//                                        //中等
//                                        topThreeClick(2);
//                                    }else if(position == 2){
//                                        //简单
//                                        topThreeClick(3);
//                                    }
//                                    kgLayout.setVisibility(View.VISIBLE);
//                                    mapLayout.setVisibility(View.GONE);
//                                    qsLayout.setVisibility(View.GONE);
//                                    new Handler().postDelayed(runnable, 2000);
//                                }
//                            });


                            //点击定位到-->题目掌握情况-->按难易度
                            verticalChart.setOnChartClick(new CircleChartView.OnChartClick() {
                                @Override
                                public void onChartClick(int position) {
                                    topThreeClick(position + 1);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            reportScrollView.scrollTo(0, findViewById(R.id.qs_sign_view).getBottom());

                                            //设置点击(按难易度)
                                            onClick(kgTxtView);

                                        }
                                    }, 500);
                                }
                            });


                            //知识点掌握情况
                            int unget[] = new int[3];
                            int get[] = new int[3];
                            unget[0] = Integer.parseInt(content.getUnThr());
                            unget[1] = Integer.parseInt(content.getUnTwo());
                            unget[2] = Integer.parseInt(content.getUnOne());
                            get[0] = Integer.parseInt(content.getGetThr());
                            get[1] = Integer.parseInt(content.getGetTwo());
                            get[2] = Integer.parseInt(content.getGetOne());
                            int max = Math.max(unget[0] + get[0], unget[1] + get[1]);
                            max = Math.max(max, unget[2] + get[2]);
                            if (max < 6) {
                                max = 6;
                            } else {
                                max = max + (6 - max % 6);
                            }
                            verticalChart.setNum(max, unget, get);


//                            verticalChart.setOnChartClick(new CircleChartView.OnChartClick() {
//                                @Override
//                                public void onChartClick(int position) {
//
//                                    kgLayout.setVisibility(View.VISIBLE);
//                                    kgSign = 1;
//                                    kgHanlView.setImageResource(R.mipmap.arrow_up);
//                                    picSign = 2;
//                                    if (position == 0) {
//                                        //难
//                                        topThreeClick(1);
//                                    }else if(position == 1){
//                                        //中等
//                                        topThreeClick(2);
//                                    }else if(position == 2){
//                                        //简单
//                                        topThreeClick(3);
//                                    }
//                                    kgLayout.setVisibility(View.VISIBLE);
//                                    mapLayout.setVisibility(View.GONE);
//                                    qsLayout.setVisibility(View.GONE);
//                                    new Handler().postDelayed(runnable, 2000);
//
//                                }
//                            });


                            //题目难度
                            float qoneNan = Float.parseFloat(content.getqOne());
                            float qtwoNan = Float.parseFloat(content.getqTwo());
                            float qthrNan = Float.parseFloat(content.getqThr());
                            float qontRate = qoneNan / (qoneNan + qtwoNan + qthrNan);
                            float qtwoRate = qtwoNan / (qoneNan + qtwoNan + qthrNan);
                            float qthrRate = qthrNan / (qoneNan + qtwoNan + qthrNan);
                            List<Rate> qNanRate = new ArrayList<Rate>();
                            rate = new Rate(qontRate, Color.GRAY, "简单 " + (int) qoneNan + "个", "");
                            qNanRate.add(rate);
                            rate = new Rate(qthrRate, getResources().getColor(R.color.theme_red), "较难" + (int) qthrNan + "个", "");
                            qNanRate.add(rate);
                            rate = new Rate(qtwoRate, getResources().getColor(R.color.theme_green), "中等 " + (int) qtwoNan + "个", "");
                            qNanRate.add(rate);

                            timuNanduChartView.setRates(qNanRate);


//                            timuNanduChartView.setOnChartClick(new CircleForNanduChartView.OnChartClick() {
//                                @Override
//                                public void onChartClick(int position) {
//                                    Map<String, String> mapVal = new HashMap<String, String>();
//                                    mapVal.put("test_id", testId);
//                                    switch (position) {
//                                        case 2:
//                                            mapVal.put("name", "简单题目");
//                                            mapVal.put("typev", "5");
//                                            break;
//                                        case 1:
//                                            mapVal.put("name", "较难题目");
//                                            mapVal.put("typev", "7");
//                                            break;
//                                        case 0:
//                                            mapVal.put("name", "中等题目");
//                                            mapVal.put("typev", "6");
//                                            break;
//                                    }
//
//                                    LogUtils.e("TAG", "点击此处跳转到对应的题库");
////                                    IntentUtil.startActivity(DiagNoseDetailActivity.this, ReportFinishActivity.class, mapVal);
//                                }
//                            });


                            //题目完成情况
                            int qunget[] = new int[3];
                            int qget[] = new int[3];
                            qunget[0] = Integer.parseInt(content.getUnqThr());
                            qunget[1] = Integer.parseInt(content.getUnqTwo());
                            qunget[2] = Integer.parseInt(content.getUnqOne());
                            qget[0] = Integer.parseInt(content.getGetqThr());
                            qget[1] = Integer.parseInt(content.getGetqTwo());
                            qget[2] = Integer.parseInt(content.getGetqOne());
                            max = Math.max(qunget[0] + qget[0], qunget[1] + qget[1]);
                            max = Math.max(max, qunget[2] + qget[2]);
                            if (max < 6) {
                                max = 6;
                            } else {
                                max = max + (6 - max % 6);
                            }
                            timuWanCChartView.setNum(max, qunget, qget);


                            timuWanCChartView.setOnChartClick(new CircleChartView.OnChartClick() {
                                @Override
                                public void onChartClick(int position) {
                                    Map<String, String> mapVal = new HashMap<String, String>();
                                    mapVal.put("test_id", testId);
                                    switch (position) {
                                        case 2:
                                            mapVal.put("name", "简单题目");
                                            mapVal.put("typev", "5");
                                            break;
                                        case 1:
                                            mapVal.put("name", "中等题目");
                                            mapVal.put("typev", "6");
                                            break;
                                        case 0:
                                            mapVal.put("name", "较难题目");
                                            mapVal.put("typev", "7");
                                            break;
                                    }

                                    LogUtils.e("TAG", "如果添加点击,修改此处");
                                    IntentUtil.startActivity(DiagNoseDetailActivity.this, ReportFinishActivity.class, mapVal);
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }


    private String checkType(int numType) {
        String typeName;
        if (numType == 1) {
            typeName = "优";
        } else if (numType == 2) {
            typeName = "良";
        } else if (numType == 3) {
            typeName = "中";
        } else if (numType == 4) {
            typeName = "差";
        } else if (numType == 0) {
            typeName = "-";
        } else {
            typeName = "-";
        }
        return typeName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_top_one:

                topOneClick();

                break;
            case R.id.report_top_two:
                topTwoClick();
                break;
            case R.id.report_top_three:
                topThreeClick(0);
                break;
            case R.id.report_top_one_two:
                topOneTwoClick();
                break;
            case R.id.report_top_two_two:
                topTwoTwoClick();
                break;
            case R.id.report_top_three_two:
                topThreeTwoClick();
                break;
            case R.id.digo_chat_sign_view:

                if (level >= 2) {

                    if (chatSign == 0) {
                        chatLayout.setVisibility(View.VISIBLE);
                        chatSign = 1;

                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_up);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        chatSignView.setCompoundDrawables(null, null, drawable, null);

                    } else {

                        chatLayout.setVisibility(View.GONE);
                        chatSign = 0;
                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_down);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        chatSignView.setCompoundDrawables(null, null, drawable, null);
                    }

                } else {
                    Toast.makeText(DiagNoseDetailActivity.this, R.string.notice_val, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.kg_sign_view:

                if (level >= 2) {

                    if (kgSign == 0) {
                        kgView.setVisibility(View.VISIBLE);
                        kgSign = 1;

                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_up);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        kgTxtView.setCompoundDrawables(null, null, drawable, null);

                    } else {
                        kgView.setVisibility(View.GONE);
                        kgSign = 0;
                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_down);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        kgTxtView.setCompoundDrawables(null, null, drawable, null);
                    }


                } else {
                    Toast.makeText(DiagNoseDetailActivity.this, R.string.notice_val, Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.qs_sign_view:

                if (level >= 2) {

                    if (qusSign == 0) {
                        qusView.setVisibility(View.VISIBLE);
                        qusSign = 1;

                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_up);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        qusTxtView.setCompoundDrawables(null, null, drawable, null);

                    } else {
                        qusView.setVisibility(View.GONE);
                        qusSign = 0;
                        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_down);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        qusTxtView.setCompoundDrawables(null, null, drawable, null);
                    }


                } else {
                    Toast.makeText(DiagNoseDetailActivity.this, R.string.notice_val, Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.report_kg_sp_kaoshi_view:

                LogUtils.e("TAG", "诊断报告中考试的点击事件");

                Map<String, String> mapVal1 = new HashMap<String, String>();
                mapVal1.put("test_id", testId);
                mapVal1.put("name", "诊断报告");
                //IntentUtil.startActivity(DiagNoseDetailActivity.this, JiXieActivity.class, mapVal1);

                if (level >= 2) {
                    IntentUtil.startActivity(DiagNoseDetailActivity.this, JiXieNewActivity.class, mapVal1);
                } else {
                    Toast.makeText(DiagNoseDetailActivity.this, R.string.notice_val, Toast.LENGTH_SHORT).show();

                }


                break;
        }
    }


    //知识点，按是否掌握
    private void topOneClick() {

        topOneView.setBackgroundColor(getResources().getColor(R.color.white));
        topTwoView.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topThreeView.setBackgroundColor(getResources().getColor(R.color.theme_green));

        topThreeView.setTextColor(getResources().getColor(R.color.white));
        topTwoView.setTextColor(getResources().getColor(R.color.white));
        topOneView.setTextColor(getResources().getColor(R.color._333333));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.report_knowledge_layout, ReportZhangWoCengduFragment.getInstance(testId), "ReportZhangWoZhangjieFragment");
        transaction.commit();
    }

    //知识点，按章节
    private void topTwoClick() {

        topOneView.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topTwoView.setBackgroundColor(getResources().getColor(R.color.white));
        topThreeView.setBackgroundColor(getResources().getColor(R.color.theme_green));


        topThreeView.setTextColor(getResources().getColor(R.color.white));
        topTwoView.setTextColor(getResources().getColor(R.color._333333));
        topOneView.setTextColor(getResources().getColor(R.color.white));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.report_knowledge_layout, ReportZhangWoZhangjieFragmentTwo.getInstance(testId), "ReportZhangWoZhangjieFragment");
        transaction.commit();
    }

    //知识点，按难易度
    public void topThreeClick(int open) {

        topOneView.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topTwoView.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topThreeView.setBackgroundColor(getResources().getColor(R.color.white));

        topThreeView.setTextColor(getResources().getColor(R.color._333333));
        topTwoView.setTextColor(getResources().getColor(R.color.white));
        topOneView.setTextColor(getResources().getColor(R.color.white));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.report_knowledge_layout, ReportZhangWoXingjiFragment.getInstance(testId, open), "ReportZhangWoZhangjieFragment");
        transaction.commit();
    }


    //题目掌握程度，对错
    private void topOneTwoClick() {

        topOneViewTwo.setBackgroundColor(getResources().getColor(R.color.white));
        topTwoViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topThreeViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));


        topOneViewTwo.setTextColor(getResources().getColor(R.color._333333));
        topTwoViewTwo.setTextColor(getResources().getColor(R.color.white));
        topThreeViewTwo.setTextColor(getResources().getColor(R.color.white));


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.report_knowledge_layout_two, QuestionOneFragment.getInstance(testId, wrong_count, right_count), "ReportZhangWoZhangjieFragment");
        transaction.commit();
    }

    //题目掌握程度，章节
    private void topTwoTwoClick() {

        topOneViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topTwoViewTwo.setBackgroundColor(getResources().getColor(R.color.white));
        topThreeViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));


        topOneViewTwo.setTextColor(getResources().getColor(R.color.white));
        topTwoViewTwo.setTextColor(getResources().getColor(R.color._333333));
        topThreeViewTwo.setTextColor(getResources().getColor(R.color.white));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.report_knowledge_layout_two, QuestionTwoFragmentThree.getInstance(testId), "ReportZhangWoZhangjieThreeFragment");
        transaction.commit();
    }

    //题目掌握程度，难易度
    private void topThreeTwoClick() {

        topOneViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topTwoViewTwo.setBackgroundColor(getResources().getColor(R.color.theme_green));
        topThreeViewTwo.setBackgroundColor(getResources().getColor(R.color.white));

        topOneViewTwo.setTextColor(getResources().getColor(R.color.white));
        topTwoViewTwo.setTextColor(getResources().getColor(R.color.white));
        topThreeViewTwo.setTextColor(getResources().getColor(R.color._333333));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.report_knowledge_layout_two, QuestionThreeFragment.getInstance(testId, one, two, thr), "ReportZhangWoZhangjieFragment");
        transaction.commit();
    }

}

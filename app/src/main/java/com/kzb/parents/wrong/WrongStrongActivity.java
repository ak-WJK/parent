package com.kzb.parents.wrong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.exam.ExamActivity;

import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.CustomReTextView;
import com.kzb.parents.view.topbar.Topbar;
import com.kzb.parents.wrong.adapter.QuesTwoAdapter;
import com.kzb.parents.wrong.model.Answer;
import com.kzb.parents.wrong.model.ExamQuestion;
import com.kzb.parents.wrong.model.ExplainContent;
import com.kzb.parents.wrong.model.Question;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class WrongStrongActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleLeft, titleCenter;
    private ListView mQuesListView;
    private String jieId;
    private QuesTwoAdapter quesTwoAdapter;

    TextView mRight,mTotal,mWrong;
    CustomReTextView mLine;
    TextView mPoint;
    private String mDone;
    private String mAll;

    private String allper;//全部百分比
    private String thisper;//和上次对比，百分比

    private String point;
    private String kid;

    private List<ExplainContent> mQuesData = new ArrayList<>();

    private View mHeader;
    private View mFooter;


    private Topbar mTopabr;
    ExamQuestion mResponse;

    private TextView percentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_strong_detail);

        mHeader = LayoutInflater.from(this).inflate(R.layout.activity_strong_report_header,null);
        mFooter = LayoutInflater.from(this).inflate(R.layout.activity_strong_report_footer,null);


        String response = getIntent().getStringExtra("question");
        if(!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            mResponse = gson.fromJson(response,ExamQuestion.class);
        }
        mAll = getIntent().getStringExtra("all");
        mDone = getIntent().getStringExtra("done");

        allper = getIntent().getStringExtra("allper");
        thisper = getIntent().getStringExtra("thisper");
        //这里的知识点涉及到，和界面显示配合一起
        point = getIntent().getStringExtra("point");
        kid = getIntent().getStringExtra("kid");

//        httpConfig = new HttpConfig();
//        dialogView = new DialogView(this);

//        jieId = getIntent().getStringExtra("id");
        initView();
        initData();
    }

    @Override
    protected void initView() {

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);

        percentView = (TextView) mHeader.findViewById(R.id.strong_percent_view);
        percentView.setText("累计强化进度:"+allper+"%");

        MineToast.show(WrongStrongActivity.this,"对比上次强化提高了"+thisper+"%");

        mRight = (TextView)mHeader.findViewById(R.id.strong_right);
        mWrong = (TextView)mHeader.findViewById(R.id.strong_wrong);
        mLine = (CustomReTextView) mHeader.findViewById(R.id.strong_line);
        mTotal = (TextView)mHeader.findViewById(R.id.strong_total);
        mPoint = (TextView) mHeader.findViewById(R.id.strong_point);

        String point = getIntent().getStringExtra("point");
        if (!TextUtils.isEmpty(point)) {
            mPoint.setText(point);
        }
        mFooter.findViewById(R.id.strong_report_replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> val = new HashMap<String, String>();
                val.put("point", mPoint.getText().toString());
                val.put("kid", getIntent().getStringExtra("kid"));
                IntentUtil.startActivity(WrongStrongActivity.this, ExamActivity.class, val);
            }
        });
        mFooter.findViewById(R.id.strong_report_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(WrongStrongActivity.this);
                EventBus.getDefault().post("refresh");
            }
        });

        titleLeft.setText("返回");
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter.setText("强化提高");



        mQuesListView = getView(R.id.strong_listview);
        mQuesListView.addHeaderView(mHeader);
        mQuesListView.addFooterView(mFooter);
    }

    @Override
    protected void initData() {



        if (mResponse == null || mResponse.getContent() == null || mResponse.getContent().size() <= 0) {
            return;
        }
        int right = getRightCount();
        int wrong = getWrongCount();
        int total = right+wrong;
        float r = (float)right;
        float t = (float)total;
        mRight.setText("答对" + right + "题");
        mWrong.setText("答错" + wrong + "题");
        mTotal.setText("共" + total + "题");
        mLine.setSign(3, r / t);



//        ExplainContent.Knowledges knowledges = new ExplainContent.Knowledges();


        mQuesData.addAll(mResponse.getExplainContent());
        quesTwoAdapter = new QuesTwoAdapter(WrongStrongActivity.this, mQuesData);
        mQuesListView.setAdapter(quesTwoAdapter);

    }

    /**
     * 获取正确答案
     * @return
     */
    public int getRightCount(){
        int count = 0;
        if (mResponse != null && mResponse.getContent() != null) {
            for (int i = 0; i < mResponse.getContent().size(); i++) {
                Question question = mResponse.getContent().get(i);
                boolean isTrue = true;
                if (question != null && question.getAnswers() != null) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        Answer answer = question.getAnswers().get(j);
                        if (answer.isCheck()) {
                            if(answer.istrue.equals("0")){
                                isTrue = false;
                            }
                        }else{
                            if(answer.istrue.equals("1")){
                                isTrue = false;
                            }
                        }
                    }
                }
                if (isTrue) {
                    count++;
                }
            }
        }

        return count;
    }

    //获取错误答案
    public int getWrongCount(){
        int count = 0;
        if (mResponse != null && mResponse.getContent() != null) {
            for (int i = 0; i < mResponse.getContent().size(); i++) {
                Question question = mResponse.getContent().get(i);
                boolean isTrue = true;
                if (question != null && question.getAnswers() != null) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        Answer answer = question.getAnswers().get(j);
                        if (answer.isCheck()) {
                            if(answer.istrue.equals("0")){
                                isTrue = false;
                            }
                        }else{
                            if(answer.istrue.equals("1")){
                                isTrue = false;
                            }
                        }
                    }
                }
                if (!isTrue) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_left:
                IntentUtil.finish(WrongStrongActivity.this);
                break;

        }
    }
}

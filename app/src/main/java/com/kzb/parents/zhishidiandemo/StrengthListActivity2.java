package com.kzb.parents.zhishidiandemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.baselibrary.utils.MineToast;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.model.CourseResponse;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class StrengthListActivity2 extends BaseActivity implements ExpandableListView.OnGroupExpandListener {




    private ExpandableListView expandableListView;
    private int pageNo = 1;
    private int pageSize = 10;
    private List<CourseResponse.ZhangModel> listZhangs;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private ParentAdapter2 adapter;

    private TextView titleLeft, titleContent;
    private String type;//1表示未强化，2表示已强化



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_list2);

        LogUtils.e("TAG", "已(未)强化List");

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        pageNo = 1;

        type = getIntent().getStringExtra("type");

        initView();
        initData();

    }

    @Override
    protected void initView() {

        titleContent = getView(R.id.common_head_center);
        titleLeft = getView(R.id.common_head_left);
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setText("返回");

        if (type.equals("1")) {
            titleContent.setText("未强化");
        } else {
            titleContent.setText("已强化");
        }

        expandableListView = getView(R.id.course_learn_expand_listview2);

        expandableListView.setGroupIndicator(null);


        mOnItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        };


        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(StrengthListActivity2.this);
            }
        });

    }

    @Override
    protected void initData() {
        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        if (type.equals("1")) {
            //未学习
            baseRequest.setUrl(AddressConfig.STRENGTH_No_LIST_URL);
        } else {
            //已学习
            baseRequest.setUrl(AddressConfig.STRENGTH_No_LIST_URL);
        }

//        dialogView.handleDialog(true);
        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", SpSetting.loadLoginInfo().getSubject_id());
            object.put("uid", SpSetting.loadLoginInfo().getUid());
            object.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            object.put("schsystem_id", SpSetting.loadLoginInfo().getSchsystemid());

            if (type.equals("1")) {
                object.put("type", "2");
            } else {
                object.put("type", "1");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());
        baseRequest.setRequestParams(map);
        httpConfig.doPostRequest(baseRequest, new GenericsCallback<CourseResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(CourseResponse response, int id) {
                dialogView.handleDialog(false);

                if (response != null && response.errorCode == 0 && response.getContent() != null) {

                    listZhangs = response.getContent();


                    expandableListView.setOnGroupExpandListener(StrengthListActivity2.this);
                    adapter = new ParentAdapter2(StrengthListActivity2.this, listZhangs, "2");

                    expandableListView.setAdapter(adapter);

//                    Utility.setListViewHeightBasedOnChildren(expandableListView);

//                    adapter.setOnChildTreeViewClickListener(CourseListActivity.this);


//                    setVal(response.getContent());

                } else {
                    MineToast.show(StrengthListActivity2.this, response.msg);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //关闭其他项目
        for (int i = 0; i < listZhangs.size(); i++) {
            if (i != groupPosition) {
//                expandableListView.expandGroup(i);
//                expandableListView.collapseGroup(i);
            }
        }
    }
}

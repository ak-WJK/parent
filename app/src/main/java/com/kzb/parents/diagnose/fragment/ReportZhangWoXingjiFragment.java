package com.kzb.parents.diagnose.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.course.CourseDetailActivity;
import com.kzb.parents.diagnose.adapter.ReportTimuZwTwoAdapter;
import com.kzb.parents.diagnose.model.AnXingji;
import com.kzb.parents.diagnose.model.AnXingjiContent;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/********************
 * 作者：malus
 * 日期：16/12/10
 * 时间：下午9:07
 * 注释：章节考试，列表
 ********************/
public class ReportZhangWoXingjiFragment extends BaseFragment {
    View convertView;
    ExpandableListView mListView;
    ReportTimuZwTwoAdapter mAdapter;
    private String testId;
    private int open;
    private List<TimuZwFather> timuZws = new ArrayList<>();

    public static ReportZhangWoXingjiFragment getInstance(String testId, int open) {
        ReportZhangWoXingjiFragment fragment = new ReportZhangWoXingjiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("testId", testId);
        bundle.putInt("open", open);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.from(getContext()).inflate(R.layout.fragment_report_zhangwo_xingji_two, null);
        testId = getArguments().getString("testId");
        open = getArguments().getInt("open");
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getContext());
        initView();
        getTimuZw();
        return convertView;
    }

    protected void initView() {
        mListView = (ExpandableListView) convertView.findViewById(R.id.report_timu_list);
        mAdapter = new ReportTimuZwTwoAdapter(getContext(), timuZws);
        mListView.setAdapter(mAdapter);



        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //强化提高
                Map val = new HashMap();
                val.put("point", timuZws.get(groupPosition).getKnowledges().get(childPosition).getKpoint());
                val.put("kid", timuZws.get(groupPosition).getKnowledges().get(childPosition).getKid());
                IntentUtil.startActivity((BaseActivity) getContext(), CourseDetailActivity.class, val);
                return false;
            }
        });
    }

    /**
     * 获取题目掌握情况
     */
    public void getTimuZw() {
//        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("test_id", testId);
            json.put("type", "2");
            json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
            json.put("schsystem_id",SpSetting.loadLoginInfo().getSchsystemid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.GET_TEST_KNOWLEDGE, json), new GenericsCallback<AnXingji>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(AnXingji response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0) {
                    List<AnXingjiContent> contents = response.getContent();
                    for (AnXingjiContent content :
                            contents) {
                        if (content != null) {
                            switch (content.getImportance()) {
                                case 1:
                                    TimuZwFather father = new TimuZwFather();
                                    father.setName("简单知识点");
                                    father.setKnowledges(content.getCon());
                                    timuZws.add(father);
                                    break;
                                case 2:
                                    father = new TimuZwFather();
                                    father.setName("中等知识点");
                                    father.setKnowledges(content.getCon());
                                    timuZws.add(father);
                                    break;
                                case 3:
                                    father = new TimuZwFather();
                                    father.setName("较难知识点");
                                    father.setKnowledges(content.getCon());
                                    timuZws.add(father);
                                    break;
                                case 0:
                                    father = new TimuZwFather();
                                    father.setName("未出题");
                                    father.setKnowledges(content.getCon());
                                    timuZws.add(father);
                                    break;
                            }
                        }
                    }

                    mAdapter.notifyDataSetChanged();
//                    if (open == 0) {
//                        mListView.expandGroup(0);
//                    }else{
//                        mListView.expandGroup(open-1);
//                    }
                }
                mAdapter.notifyDataSetChanged();
//                EventBus.getDefault().post("scroll");
            }
        });
    }

}


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
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.diagnose.adapter.ZwZJChildTwoAdapter;
import com.kzb.parents.diagnose.model.TimuZw;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.diagnose.model.TimuZwGFather;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/********************
 * 作者：malus
 * 日期：16/12/10
 * 时间：下午9:07
 * 注释：章节考试，列表
 ********************/
public class ReportZhangWoZhangjieFragmentTwo extends BaseFragment {
    View convertView;
    ExpandableListView mListView;
    //    ReportTimuZwTwoAdapter mAdapter;
    private String testId;
    //    private ZwZJParentAdapter zwZJParentAdapter;
    ZwZJChildTwoAdapter zwZJChildAdapter;

    //    private List<TimuZwGFather> timuZws = new ArrayList<>();
    public static ReportZhangWoZhangjieFragmentTwo getInstance(String testId) {
        ReportZhangWoZhangjieFragmentTwo fragment = new ReportZhangWoZhangjieFragmentTwo();
        Bundle bundle = new Bundle();
        bundle.putString("testId", testId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.from(getContext()).inflate(R.layout.fragment_report_zhangwo_xingji_two, null);
        testId = getArguments().getString("testId");
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getContext());
        initView();
        getTimuZw();
        return convertView;
    }

    protected void initView() {
        mListView = (ExpandableListView) convertView.findViewById(R.id.report_timu_list);
        mListView.setGroupIndicator(null);

//        mAdapter = new ReportTimuZwTwoAdapter(getContext(), timuZws);
//        mListView.setAdapter(mAdapter);
//        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                //强化提高
//                Map val = new HashMap();
//                val.put("point",timuZws.get(groupPosition).getKnowledges().get(childPosition).getKpoint());
//                val.put("kid",timuZws.get(groupPosition).getKnowledges().get(childPosition).getKid());
//                IntentUtil.startActivity((BaseActivity)getContext(), CourseDetailActivity.class,val);
//                return false;
//            }
//        });
    }

    /**
     * 获取题目掌握情况
     */
    public void getTimuZw() {
//        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        try {
            json.put("test_id", testId);
            json.put("type", "1");
            json.put("version_id", SpSetting.loadLoginInfo().getVersion_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.GET_TEST_KNOWLEDGE, json), new GenericsCallback<TimuZw>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(TimuZw response, int id) {
//                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    //节集合
                    ArrayList<TimuZwFather> listValss = new ArrayList<TimuZwFather>();

                    for (int i = 0; i < response.getContent().size(); i++) {
                        //章对象
                        TimuZwGFather timuZwGFather = response.getContent().get(i);
                        for (int j = 0; j < timuZwGFather.getJie().size(); j++) {
                            TimuZwFather timuZwFather = timuZwGFather.getJie().get(j);
                            timuZwFather.setgFather(timuZwGFather.getName());
                            listValss.add(timuZwFather);
                        }
                    }
                    //上面代码意思，将每章的标题赋值给该章下面的所有节


//                    for (TimuZwFather tis: listValss) {
//
//                        Log.e("wang","child="+tis.getKnowledges().size());
//                    }


                    zwZJChildAdapter = new ZwZJChildTwoAdapter(getActivity(), listValss, "1");
                    mListView.setAdapter(zwZJChildAdapter);

                    EventBus.getDefault().post("scroll");

                }
            }
        });
    }


}


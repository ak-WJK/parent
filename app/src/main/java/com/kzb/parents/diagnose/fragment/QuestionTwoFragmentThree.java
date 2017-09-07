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
import com.kzb.parents.diagnose.adapter.ZwZJParentTwoAdapter;
import com.kzb.parents.diagnose.model.TimuZw;
import com.kzb.parents.diagnose.model.TimuZwGFather;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/********************
 * 作者：malus
 * 日期：16/12/10
 * 时间：下午9:07
 * 注释：章节考试，列表
 ********************/
public class QuestionTwoFragmentThree extends BaseFragment implements ExpandableListView.OnGroupExpandListener,ZwZJParentTwoAdapter.OnChildTreeViewClickListener{
    View convertView;
    ExpandableListView mListView;
    private String testId;
    private ZwZJParentTwoAdapter zwZJParentAdapter;

    private List<TimuZwGFather> timuZws = new ArrayList<>();
    public static QuestionTwoFragmentThree getInstance(String testId){
        QuestionTwoFragmentThree fragment = new QuestionTwoFragmentThree();
        Bundle bundle = new Bundle();
        bundle.putString("testId",testId);
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
            json.put("schsystem_id",SpSetting.loadLoginInfo().getSchsystemid());
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

                    timuZws = response.getContent();


                    mListView.setOnGroupExpandListener(QuestionTwoFragmentThree.this);
                    zwZJParentAdapter = new ZwZJParentTwoAdapter(getActivity(), timuZws,"1",testId);

                    mListView.setAdapter(zwZJParentAdapter);

                    zwZJParentAdapter.setOnChildTreeViewClickListener(QuestionTwoFragmentThree.this);

                }
                zwZJParentAdapter.notifyDataSetChanged();
                EventBus.getDefault().post("scroll");
            }
        });
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //关闭其他项目
        for (int i = 0; i < timuZws.size(); i++) {
            if (i != groupPosition) {
                mListView.collapseGroup(i);
            }
        }
    }

    @Override
    public void onClickPosition(int parentPosition, int groupPosition, int childPosition) {

    }
}

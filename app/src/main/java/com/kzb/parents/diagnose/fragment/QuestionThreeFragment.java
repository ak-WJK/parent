package com.kzb.parents.diagnose.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.diagnose.adapter.QuestionOneAdapter;
import com.kzb.parents.diagnose.model.QuestionModel;
import com.kzb.parents.diagnose.model.TimuZwFather;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/12/10
 * 时间：下午9:07
 * 注释：章节考试，列表
 ********************/
public class QuestionThreeFragment extends BaseFragment {
    View convertView;
    ListView mListView;
    private QuestionOneAdapter questionOneAdapter;
    private String testId;
    private List<TimuZwFather> timuZws = new ArrayList<>();
    public static QuestionThreeFragment getInstance(String testId){
        QuestionThreeFragment fragment = new QuestionThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("testId",testId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.from(getContext()).inflate(R.layout.fragment_report_question_layout, null);
        testId = getArguments().getString("testId");
        httpConfig = new HttpConfig();
        dialogView = new DialogView(getContext());
        initView();
        return convertView;
    }

    protected void initView() {
        mListView = (ListView) convertView.findViewById(R.id.report_listview);

        questionOneAdapter = new QuestionOneAdapter(getActivity());

        mListView.setAdapter(questionOneAdapter);

        List<QuestionModel> listVal = new ArrayList<>();


        QuestionModel questionModel = new QuestionModel();
        questionModel.setType(5);
        questionModel.setName("简单题目");
        questionModel.setTest_id(testId);

        QuestionModel questionModel1 = new QuestionModel();
        questionModel1.setType(6);
        questionModel1.setName("中等题目");
        questionModel1.setTest_id(testId);

        QuestionModel questionModel2 = new QuestionModel();
        questionModel2.setType(7);
        questionModel2.setName("较难题目");
        questionModel2.setTest_id(testId);

        listVal.add(questionModel);
        listVal.add(questionModel1);
        listVal.add(questionModel2);

        questionOneAdapter.addItems(listVal);
        EventBus.getDefault().post("scroll");


//        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                //强化提高
//                Map val = new HashMap();
//                val.put("point",timuZws.get(groupPosition).getKnowledges().get(childPosition).getKpoint());
//                val.put("kid",timuZws.get(groupPosition).getKnowledges().get(childPosition).getKid());
//                IntentUtil.startActivity((BaseActivity)getContext(), CourseDetailActivity.class,val);
//                return false;
//            }
//        });
    }



}


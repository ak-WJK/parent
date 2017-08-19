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
public class QuestionOneFragment extends BaseFragment {
    View convertView;
    ListView mListView;
    private QuestionOneAdapter questionOneAdapter;
    private String testId;
    private List<TimuZwFather> timuZws = new ArrayList<>();
    private String wrong_count;
    private String right_count;
    private int wrong_count1;
    private int right_count1;


    public static QuestionOneFragment getInstance(String testId, String wrong_count, String right_count) {
        QuestionOneFragment fragment = new QuestionOneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("testId", testId);
        bundle.putString("wrong_count", wrong_count);
        bundle.putString("right_count", right_count);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.from(getContext()).inflate(R.layout.fragment_report_question_layout, null);
        testId = getArguments().getString("testId");
        //得到错(正确)题个数
        wrong_count = getArguments().getString("wrong_count");
        right_count = getArguments().getString("right_count");

        if (wrong_count != null && right_count != null) {

            wrong_count1 = Integer.parseInt(wrong_count);
            right_count1 = Integer.parseInt(right_count);

        }


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
        questionModel.setType(1);
        questionModel.setName("错误题目");
        questionModel.setTest_id(testId);


        QuestionModel questionModel1 = new QuestionModel();
        questionModel1.setType(2);
        questionModel1.setName("正确题目");
        questionModel1.setTest_id(testId);


        //此处判断添加(错误题目)(正确题目)Item的条件
        if (wrong_count1 != 0 && right_count1 != 0) {
            listVal.add(questionModel);
            listVal.add(questionModel1);
        }
        if (wrong_count1 != 0 && right_count1 == 0) {
            listVal.add(questionModel);
        }
        if (right_count1 != 0 && wrong_count1 == 0) {
            listVal.add(questionModel1);
        }


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


package com.kzb.parents.diagnose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.diagnose.adapter.DiagListAdapter;
import com.kzb.parents.diagnose.adapter.DiagListAdapter.ItemClick;
import com.kzb.parents.diagnose.model.DiagNoseResponse;
import com.kzb.parents.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/3/16.
 */

public class DiagNoseListActivity extends BaseActivity implements ItemClick {

    private RecyclerView recyclerView;

    private DiagListAdapter diagListAdapter;

    private TextView headTitle, headLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dgno);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        recyclerView = getView(R.id.dig_recy_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        headTitle = getView(R.id.common_head_center);
        headLeft = getView(R.id.common_head_left);
        headLeft.setVisibility(View.VISIBLE);
        headLeft.setText("返回");
        headTitle.setText("诊断报告");

        headLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(DiagNoseListActivity.this);
            }
        });

    }

    @Override
    protected void initData() {
        List<DiagNoseResponse> listVals = new ArrayList<>();

        DiagNoseResponse one = new DiagNoseResponse("one",0);
        DiagNoseResponse two = new DiagNoseResponse("two",0);
        DiagNoseResponse three = new DiagNoseResponse("three",0);
        DiagNoseResponse four = new DiagNoseResponse("four",0);
        DiagNoseResponse five = new DiagNoseResponse("five",0);
        DiagNoseResponse six = new DiagNoseResponse("six",0);
        DiagNoseResponse seven = new DiagNoseResponse("seven",0);
        DiagNoseResponse eight = new DiagNoseResponse("eight",0);
        DiagNoseResponse nine = new DiagNoseResponse("nine",0);
        DiagNoseResponse twn = new DiagNoseResponse("twn",0);
        DiagNoseResponse evenl1 = new DiagNoseResponse("evenl1",0);

        listVals.add(one);
        listVals.add(two);
        listVals.add(three);
        listVals.add(four);
        listVals.add(five);
        listVals.add(six);
        listVals.add(seven);
        listVals.add(eight);
        listVals.add(nine);
        listVals.add(twn);
        listVals.add(evenl1);

        diagListAdapter = new DiagListAdapter(DiagNoseListActivity.this, listVals);
        recyclerView.setAdapter(diagListAdapter);
        diagListAdapter.setItemClic(this);
    }

    @Override
    public void itemClic(View view, int position) {

        Toast.makeText(DiagNoseListActivity.this, "" + position, Toast.LENGTH_SHORT).show();

        diagListAdapter.notifyItemRemoved(position);

    }
}

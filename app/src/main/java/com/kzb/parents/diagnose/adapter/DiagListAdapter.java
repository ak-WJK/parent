package com.kzb.parents.diagnose.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.diagnose.DiagNoseDetailActivity;
import com.kzb.parents.diagnose.model.DiagNoseResponse;
import com.kzb.parents.util.IntentUtil;

import java.util.List;


/**
 * Created by wanghaofei on 17/3/16.
 */

public class DiagListAdapter extends RecyclerView.Adapter<DiagListAdapter.DiagViewHolder> {

    private Context context;
    private List<DiagNoseResponse> datas;

    private ItemClick itemClick;

    public DiagListAdapter(Context context, List<DiagNoseResponse> datas) {
        this.context = context;
        this.datas = datas;
    }


    public void setItemClic(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public DiagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.diagnose_item_layout, null);
        DiagViewHolder diagViewHolder = new DiagViewHolder(view);
        return diagViewHolder;
    }

    @Override
    public void onBindViewHolder(final DiagViewHolder holder, final int position) {


        final DiagNoseResponse diagNoseResponse = datas.get(position);
        holder.nameView.setText(diagNoseResponse.getName());

        if (diagNoseResponse.getOpenType() == 1) {
            holder.bomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.bomLayout.setVisibility(View.GONE);
        }


        holder.handleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClick.itemClic(v,position);

                holder.bomLayout.setVisibility(View.VISIBLE);
                diagNoseResponse.setOpenType(1);

            }
        });

        holder.findView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity((BaseActivity) context, DiagNoseDetailActivity.class);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class DiagViewHolder extends ViewHolder {

        TextView scoreView, numView, timeView, findView, nameView;
        ImageView stateImg, handleImg;
        LinearLayout bomLayout;

        public DiagViewHolder(View view) {
            super(view);
            scoreView = (TextView) view.findViewById(R.id.dig_item_score_view);
            nameView = (TextView) view.findViewById(R.id.dig_item_name_view);
            numView = (TextView) view.findViewById(R.id.dig_item_score_num_view);
            timeView = (TextView) view.findViewById(R.id.dig_item_score_time_view);
            findView = (TextView) view.findViewById(R.id.dig_item_find_view);
            stateImg = (ImageView) view.findViewById(R.id.dig_item_state_view);
            handleImg = (ImageView) view.findViewById(R.id.dig_item_handle_view);
            bomLayout = (LinearLayout) view.findViewById(R.id.dig_item_bom_layout);
        }
    }


    public interface ItemClick {
        void itemClic(View view, int position);
    }


}

package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.MeetingOverviewActivity;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.utils.Constants;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by why on 2018/6/14.
 */

public class MeetingListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private int meetingType;//标识点击哪个进入到的页面

    @Override
    public boolean isTitle() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_meeting_list;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.fragment_meeting_list_RecyclerView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getActivity(), R.color.percentage_10)));

        recyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_meeting, getData()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }

            @Override
            public void convert(ViewHolder holder, String s) {
                //赋值
                holder.setText(R.id.item_meeting_name, "第一个会议");
                holder.setText(R.id.item_meeting_state, "会中");
                holder.setText(R.id.item_meeting_start_time, "6月14日 9:00-17:00");
                holder.setText(R.id.item_meeting_end_time, "6月16日 9:00-17:00");
                //R.id.item_meeting_level_icon ---V重图片
                ImageView imageView = holder.getView(R.id.item_meeting_level_icon);
                imageView.setImageResource(R.drawable.back_btn);
                holder.setText(R.id.item_meeting_room, "高级客房");
                //关注按钮点击
                TextView follow = holder.getView(R.id.item_meeting_follow);
                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                holder.setText(R.id.item_meeting_payment_state, "待付款");
                TextView paymentState = holder.getView(R.id.item_meeting_payment_state);
                paymentState.setText(getString(R.string.pending_payment));

                ConstraintLayout itemLayout = holder.getView(R.id.item_meeting_layout);
                itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), MeetingOverviewActivity.class));
                    }
                });
            }
        });
    }

    @Override
    public void requestNetwork() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            meetingType = bundle.getInt(Constants.MEETING_TYPE);
        }

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 40; i++) {
            data.add("测试数据" + i);
        }
        return data;
    }
}

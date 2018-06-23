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
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
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
                TextView nameView = holder.getView(R.id.item_meeting_name);
                setNameTextLayoutParams(nameView, s);
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

            private void setNameTextLayoutParams(TextView nameView, String content) {
                setLayoutParams(nameView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                nameView.setText(content);
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                nameView.measure(spec, spec);
                int measuredWidthTicketNum = nameView.getMeasuredWidth();
                int maxWidth = ScreenUtil.sp2px(getContext(), 270);
                if (measuredWidthTicketNum > maxWidth) {
                    setLayoutParams(nameView, maxWidth);
                }
            }

            private void setLayoutParams(TextView nameView, int value) {
                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
                layoutParams1.width = value;
                nameView.setLayoutParams(layoutParams1);
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
        String[] str = new String[]{"梅西传球数比门将还少 阿媒痛批:配不上当领袖",
                "VAR正在蚕食比赛悬念",
                "三匪徒持斧校门口“行凶”不到5分钟被制伏",
                "告诉你一个电视上看不到的世界杯",
                "教皇方济各：对中国充满敬意 中国人应获“诺贝尔耐心奖”",
                "世界杯最长球荒！梅西675分钟0进球 从11射到1射",
                "你也一定想知道的事：世界杯赛场上裁判和球员咋对话？",
                "世界杯期间交通违法真不少",
                "男子为掩人耳目男扮女装深夜偷车被民警抓获",
                "瑞斯康达破发：上市业绩变脸 投行招商证券赚4200万",
                "他信又摊上事儿了！泰国最高法院向他发出逮捕令"
                , "谁在豪赌退市昆机"};
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            data.add(str[i]);
        }
        return data;
    }
}

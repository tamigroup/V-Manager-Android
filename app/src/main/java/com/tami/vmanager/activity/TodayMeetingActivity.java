package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.AllMeetingsRequest;
import com.tami.vmanager.entity.AllMeetingsResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日会议
 * Created by why on 2018/6/26.
 */
public class TodayMeetingActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private CommonAdapter<AllMeetingsResponse.Array.Item> commonAdapter;
    private List<AllMeetingsResponse.Array.Item> listData;
    private NetworkBroker networkBroker;
    private int CurPage = 1;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_meeting_list;
    }

    @Override
    public void initView() {
        pullToRefreshLayout = findViewById(R.id.fml_PullToRefreshLayout);
        recyclerView = findViewById(R.id.fml_RecyclerView);
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                CurPage = 1;
                pullToRefreshLayout.setCanLoadMore(true);
                query(true);
            }

            @Override
            public void loadMore() {
                query(false);
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.today_meeting);

        int screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.color_EAEAEA)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<AllMeetingsResponse.Array.Item>(getApplicationContext(), R.layout.item_today_meeting, listData) {
            @Override
            protected void convert(ViewHolder holder, AllMeetingsResponse.Array.Item item, int position) {
                //名称
                TextView nameView = holder.getView(R.id.item_meeting_name);
                setNameTextLayoutParams(nameView, item.meetingName);
                //会议状态
                MeetingStateView stateView = holder.getView(R.id.item_meeting_state);
                stateView.setMeetingStateText(item.meetingStatus, 20);
                //时间
                holder.setText(R.id.item_meeting_start_time, item.autoDayTime);
//                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(item.startTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
//                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(item.endTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                //V图片
                AppCompatImageView vipImageView = holder.getView(R.id.item_meeting_level_icon);
                vipImageView.setVisibility(item.isImportant == 0 ? View.GONE : View.VISIBLE);
                vipImageView.setImageResource(getImageResId(item.isImportant));
                //智图片
                AppCompatImageView imageView1 = holder.getView(R.id.item_meeting_level_icon1);
                imageView1.setVisibility(item.isVzh == 1 ? View.VISIBLE : View.GONE);
                //房间
                holder.setText(R.id.item_meeting_room, item.meetingAddress);
                //待完善
                TextView perfected = holder.getView(R.id.item_meeting_perfected);
                perfected.setText(item.perfectStatus);
                //已取消
                TextView cancel = holder.getView(R.id.item_meeting_cancel);
                cancel.setText(item.cancelStatus);
                //ITEM点击
                ConstraintLayout itemLayout = holder.getView(R.id.item_meeting_layout);
                itemLayout.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getApplicationContext(), MeetingOverviewActivity.class);
                    intent.putExtra(Constants.KEY_MEETING_ID, item.meetingId);
                    startActivity(intent);
                });
            }

            /**
             * 获取图片资源ID
             * @param vipId
             */
            private int getImageResId(int vipId) {
                int id = R.mipmap.meeting_level_vip1;
                switch (vipId) {
                    case 1:
                        id = R.mipmap.meeting_level_vip1;
                        break;
                    case 2:
                        id = R.mipmap.meeting_level_vip2;
                        break;
                    case 3:
                        id = R.mipmap.meeting_level_vip3;
                        break;
                    case 4:
                        id = R.mipmap.meeting_level_vip4;
                        break;
                }
                return id;
            }

            /**
             * 测量会议名称的长度
             * @param nameView
             * @param content
             */
            private void setNameTextLayoutParams(TextView nameView, String content) {
                setLayoutParams(nameView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                nameView.setText(content);
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                nameView.measure(spec, spec);
                int measuredWidthTicketNum = nameView.getMeasuredWidth();
                int maxWidth = ScreenUtil.dip2px(getApplicationContext(), (screenWidth - 90));
                if (measuredWidthTicketNum > maxWidth) {
                    setLayoutParams(nameView, maxWidth);
                }
            }

            /**
             * 重新赋值长度
             * @param nameView
             * @param value
             */
            private void setLayoutParams(TextView nameView, int value) {
                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
                layoutParams1.width = value;
                nameView.setLayoutParams(layoutParams1);
            }
        };
        recyclerView.setAdapter(commonAdapter);
//        pullToRefreshLayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        CurPage = 1;
        query(false);
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }

    private void query(boolean isRefresh) {
        AllMeetingsRequest allMeetingsRequest = new AllMeetingsRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            allMeetingsRequest.setUserId(String.valueOf(item.getId()));
        }
        allMeetingsRequest.setCurPage(String.valueOf(CurPage++));
        allMeetingsRequest.setPageSize(String.valueOf(Constants.PAGE_SIZE));
        allMeetingsRequest.setRequsetUrl(HttpKey.TODAY_MEETINGS);
        networkBroker.ask(allMeetingsRequest, (ex1, res) -> {
            if (null != ex1) {
                ex1.printStackTrace();
                Logger.d(ex1.getMessage() + "-" + ex1);
                pullToRefreshLayout.finishRefresh();
                pullToRefreshLayout.finishLoadMore();
                return;
            }
            try {
                AllMeetingsResponse response = (AllMeetingsResponse) res;
                if (response.getCode() == 200) {
                    AllMeetingsResponse.Array array = response.data;
                    if (array != null && array.elements != null && array.elements.size() > 0) {
                        if (isRefresh && listData.size() > 0) {
                            listData.clear();
                        }
                        listData.addAll(array.elements);
                        commonAdapter.notifyDataSetChanged();
                    }
                    pullToRefreshLayout.finishRefresh();
                    pullToRefreshLayout.finishLoadMore();
                    if (array.lastPage) {
                        pullToRefreshLayout.setCanLoadMore(false);
                    }
                } else {
                    pullToRefreshLayout.finishRefresh();
                    pullToRefreshLayout.finishLoadMore();
                }
            } catch (Exception e) {
                pullToRefreshLayout.finishRefresh();
                pullToRefreshLayout.finishLoadMore();
                e.printStackTrace();
            }
        });
    }
}

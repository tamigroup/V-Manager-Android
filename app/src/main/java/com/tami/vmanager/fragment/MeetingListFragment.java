package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.ViewStatus;
import com.tami.vmanager.R;
import com.tami.vmanager.activity.MeetingOverviewActivity;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.AllMeetingsRequest;
import com.tami.vmanager.entity.AllMeetingsResponse;
import com.tami.vmanager.entity.FollowUserMeetingRequest;
import com.tami.vmanager.entity.FollowUserMeetingResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.message.MessageEvent;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by why on 2018/6/14.
 */
public class MeetingListFragment extends ViewPagerBaseFragment {

    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private CommonAdapter<AllMeetingsResponse.Array.Item> commonAdapter;
    private List<AllMeetingsResponse.Array.Item> listData;
    private int meetingType;//标识点击哪个进入到的页面
    private NetworkBroker networkBroker;
    private int CurPage = 1;

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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getContext(), R.color.percentage_10)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<AllMeetingsResponse.Array.Item>(getActivity(), R.layout.item_meeting, listData) {
            @Override
            protected void convert(ViewHolder holder, AllMeetingsResponse.Array.Item item, int position) {
                //名称
                TextView nameView = holder.getView(R.id.item_meeting_name);
                setNameTextLayoutParams(nameView, item.meetingName);
                //会议状态
                MeetingStateView stateView = holder.getView(R.id.item_meeting_state);
                if (TextUtils.isEmpty(item.meetingStatus)) {
                    stateView.setMeetingStateText(getString(R.string.daiban), 20);
                } else {
                    stateView.setMeetingStateText(item.meetingStatus, 20);
                }
                if (TextUtils.isEmpty(item.cancelStatus)) {
                    stateView.setVisibility(View.VISIBLE);
                } else {
                    stateView.setVisibility(View.INVISIBLE);
                }
                //时间
                StringBuilder time = new StringBuilder();
                String startTime = TimeUtils.milliseconds2String(item.startTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH);
                time.append(startTime);
                time.append(" - ");
                String endTime = TimeUtils.milliseconds2String(item.endTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH);
                time.append(endTime);
                holder.setText(R.id.item_meeting_start_time, time.toString());
//                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(item.startTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
//                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(item.endTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                //V图片
                AppCompatImageView vipImageView = holder.getView(R.id.item_meeting_level_icon);
                vipImageView.setVisibility((item.isImportant == 0||item.isImportant == 10) ? View.GONE : View.VISIBLE);
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
                //关注按钮点击
                final TextView follow = holder.getView(R.id.item_meeting_follow);
                followOnClick(follow, item.meetingStatus, item.followStatus);
                follow.setOnClickListener((View v) -> {
                    followUserMeeting(follow, item);
                });
                //ITEM点击
                ConstraintLayout itemLayout = holder.getView(R.id.item_meeting_layout);
                itemLayout.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getActivity(), MeetingOverviewActivity.class);
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
                int maxWidth = ScreenUtil.dip2px(getActivity(), (screenWidth - 160));
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            meetingType = bundle.getInt(Constants.MEETING_TYPE);
        }
        Logger.d("meetingType:" + meetingType);
        networkBroker = new NetworkBroker(getActivity());
        networkBroker.setCancelTag(getTAG());
        CurPage = 1;
        query(false);
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (listData != null) {
            listData.clear();
        }
        listData = null;
//        if (networkBroker != null) {
//            networkBroker.cancelAllRequests();
//        }
        EventBus.getDefault().unregister(this);
    }

    /**
     * 查询列表数据
     */
    private void query(boolean isRefresh) {
        AllMeetingsRequest allMeetingsRequest = new AllMeetingsRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            allMeetingsRequest.setUserId(String.valueOf(item.getId()));
        }
        allMeetingsRequest.setSearchType(String.valueOf(meetingType + 1));
        allMeetingsRequest.setCurPage(String.valueOf(CurPage++));
        allMeetingsRequest.setPageSize(String.valueOf(Constants.PAGE_SIZE));
        allMeetingsRequest.setRequsetUrl(HttpKey.USER_ALL_MEETINGS);
        networkBroker.ask(allMeetingsRequest, (ex1, res) -> {
            if (null != ex1) {
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
                    isEmptyPage();
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

    /**
     * 关注/取消关注会议
     */
    private void followUserMeeting(TextView follow, AllMeetingsResponse.Array.Item onClickItem) {
        FollowUserMeetingRequest followUserMeetingRequest = new FollowUserMeetingRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            followUserMeetingRequest.setUserId(String.valueOf(item.getId()));
        }
        followUserMeetingRequest.setMeetingId(String.valueOf(onClickItem.meetingId));
        followUserMeetingRequest.setRequsetUrl(onClickItem.followStatus == 0 ? HttpKey.FOLLOW_USER_MEETING : HttpKey.CANCEL_USER_MEETING);
        networkBroker.ask(followUserMeetingRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                FollowUserMeetingResponse response = (FollowUserMeetingResponse) res;
                if (response.getCode() == 200) {
                    if (response.data) {
                        onClickItem.followStatus = (onClickItem.followStatus == 1 ? 0 : 1);
                        followOnClick(follow, onClickItem.meetingStatus, onClickItem.followStatus);
                        if (onClickItem.followStatus == 1) {
                            showToast(getString(R.string.attention_prompt, getString(R.string.success)));
                        } else {
                            showToast(getString(R.string.attention_cancel_prompt, getString(R.string.success)));
                        }
                    }
                } else {
                    if (onClickItem.followStatus == 0) {
                        showToast(getString(R.string.attention_prompt, getString(R.string.failure)));
                    } else {
                        showToast(getString(R.string.attention_cancel_prompt, getString(R.string.failure)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 设置关注点击效果
     *
     * @param follow
     * @param status
     */
    private void followOnClick(TextView follow, String meetingStatus, int status) {
        if (getString(R.string.finished).equals(meetingStatus)) {
            follow.setEnabled(false);
            follow.setText(getString(R.string.attention));
            follow.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
            follow.setBackgroundResource(R.drawable.item_meeting_follow_unselected);
        } else {
            follow.setEnabled(true);
            if (status == 1) {
                follow.setText(getString(R.string.yi_attention));
                follow.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF5657));
                follow.setBackgroundResource(R.drawable.item_meeting_follow_selected);
            } else {
                follow.setText(getString(R.string.attention));
                follow.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
                follow.setBackgroundResource(R.drawable.item_meeting_follow_unselected1);
            }
        }
    }

    /**
     * 是否显示空页面
     */
    private void isEmptyPage() {
        if (CurPage == 2 && listData.size() == 0) {
            pullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
            TextView emptyTxt = (TextView) pullToRefreshLayout.getView(ViewStatus.EMPTY_STATUS);
            emptyTxt.setText(getString(R.string.no_meeting));
        } else {
            pullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent event) {
        if (event.isRefresh()) {
            CurPage = 1;
            query(true);
        }
    }
}

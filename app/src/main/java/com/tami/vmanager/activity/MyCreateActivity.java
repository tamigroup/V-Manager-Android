package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.ViewStatus;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.adapter.ViewHolder;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.AllMeetingsRequest;
import com.tami.vmanager.entity.AllMeetingsResponse;
import com.tami.vmanager.entity.DeleteUserMeetingRequest;
import com.tami.vmanager.entity.DeleteUserMeetingResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.view.MeetingStateView;
import com.tami.vmanager.view.swipemenu.SwipeHorizontalMenuLayout;
import com.tami.vmanager.view.swipemenu.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的创建
 * Created by why on 2018/7/5.
 */
public class MyCreateActivity extends BaseActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private SwipeMenuListView swipeMenuListView;
    private ListViewAdapter<AllMeetingsResponse.Array.Item> listViewAdapter;
    private List<AllMeetingsResponse.Array.Item> listData;
    private NetworkBroker networkBroker;
    private int CurPage = 1;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_create;
    }

    @Override
    public void initView() {
        pullToRefreshLayout = findViewById(R.id.amc_PullToRefreshLayout);
        swipeMenuListView = findViewById(R.id.listView);
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
            }

            @Override
            public void loadMore() {
                getMyCreateList(false);
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.my_create);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        listData = new ArrayList<>();

        int screenWidth = ScreenUtil.getScreenWidth(getApplicationContext());
        listViewAdapter = new ListViewAdapter<AllMeetingsResponse.Array.Item>(getApplicationContext(), listData, R.layout.item_my_create) {
            @Override
            protected void binView(ViewHolder viewHolder, AllMeetingsResponse.Array.Item item, int position) {
                SwipeHorizontalMenuLayout shmlView = viewHolder.getItemView(R.id.imc_shml);
                shmlView.setSwipeEnable(true);
                //修改按钮
                TextView modifyView = viewHolder.getItemView(R.id.menu_modify);
                modifyView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    modifyMeeting(item.meetingId);
                });
                //取消按钮
                TextView cancelView = viewHolder.getItemView(R.id.menu_cancel);
                cancelView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    deleteUserMeeting(item.meetingId, position);
                });

                //名称
                TextView nameView = viewHolder.getItemView(R.id.item_content_name);
                setNameTextLayoutParams(nameView, item.meetingName);
                //会议状态
                MeetingStateView stateView = viewHolder.getItemView(R.id.item_content_state);
                stateView.setMeetingStateText(item.meetingStatus, 20);
                if (TextUtils.isEmpty(item.cancelStatus)) {
                    stateView.setVisibility(View.VISIBLE);
                } else {
                    stateView.setVisibility(View.INVISIBLE);
                }
                //时间
                viewHolder.setTextView(R.id.item_content_start_time, item.autoDayTime);
//                viewHolder.setTextView(R.id.item_content_start_time, TimeUtils.milliseconds2String(item.startTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
//                viewHolder.setTextView(R.id.item_content_end_time, TimeUtils.milliseconds2String(item.endTime, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                //V图片
                AppCompatImageView vipImageView = viewHolder.getItemView(R.id.item_content_level_icon);
                vipImageView.setVisibility(item.isImportant == 0 ? View.GONE : View.VISIBLE);
                vipImageView.setImageResource(getImageResId(item.isImportant));
                //智图片
                AppCompatImageView imageView1 = viewHolder.getItemView(R.id.item_content_level_icon1);
                imageView1.setVisibility(item.isVzh == 1 ? View.VISIBLE : View.GONE);
                //房间
                viewHolder.setTextView(R.id.item_content_room, item.meetingAddress);
                //待完善
                TextView perfected = viewHolder.getItemView(R.id.item_content_perfected);
                perfected.setText(item.perfectStatus);
                //已取消
                TextView cancel = viewHolder.getItemView(R.id.item_content_cancel);
                cancel.setText(item.cancelStatus);

                ConstraintLayout layout = viewHolder.getItemView(R.id.smContentView);
                layout.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getApplicationContext(), MeetingOverviewActivity.class);
                    intent.putExtra(Constants.KEY_MEETING_ID, listData.get(position).meetingId);
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
        swipeMenuListView.setAdapter(listViewAdapter);
        pullToRefreshLayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        CurPage = 1;
        getMyCreateList(false);
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (listData != null) {
            listData.clear();
            listData = null;
        }
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CREATE_MEETING) {
            CurPage = 1;
            getMyCreateList(true);
        }
    }

    /**
     * 获取我的创建数据
     */
    private void getMyCreateList(boolean isRefresh) {
        AllMeetingsRequest allMeetingsRequest = new AllMeetingsRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            allMeetingsRequest.setUserId(String.valueOf(item.getId()));
        }
        allMeetingsRequest.setCurPage(String.valueOf(CurPage++));
        allMeetingsRequest.setPageSize(String.valueOf(Constants.PAGE_SIZE));
        allMeetingsRequest.setRequsetUrl(HttpKey.MY_MEETINGS);
        networkBroker.ask(allMeetingsRequest, (ex1, res) -> {
            if (null != ex1) {
                ex1.printStackTrace();
                Logger.d(ex1.getMessage() + "-" + ex1);
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
                        listViewAdapter.notifyDataSetChanged();
                    }else{
                        isEmptyPage();
                    }
                    pullToRefreshLayout.finishLoadMore();
                    if (array.lastPage) {
                        pullToRefreshLayout.setCanLoadMore(false);
                    }
                } else {
                    pullToRefreshLayout.finishLoadMore();
                }
            } catch (Exception e) {
                pullToRefreshLayout.finishLoadMore();
                e.printStackTrace();
            }
        });
    }

    /**
     * 修改会议
     *
     * @param meetingId
     */
    private void modifyMeeting(int meetingId) {
        Intent intent = new Intent(getApplicationContext(), ModifyMeetingActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        startActivityForResult(intent, Constants.CREATE_MEETING);
    }

    /**
     * 取消会议
     *
     * @param meetingId
     */
    private void deleteUserMeeting(int meetingId, int position) {
        DeleteUserMeetingRequest dmr = new DeleteUserMeetingRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            dmr.setUserId(item.getId());
        }
        dmr.setMeetingId(meetingId);
        networkBroker.ask(dmr, (ex1, res) -> {
            if (null != ex1) {
                ex1.printStackTrace();
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                DeleteUserMeetingResponse response = (DeleteUserMeetingResponse) res;
                if (response.getCode() == 200 && response.data) {
                    showToast(getString(R.string.delete_meeting, getString(R.string.success)));
                    CurPage = 1;
                    getMyCreateList(true);
//                    listData.remove(position);
//                    listViewAdapter.notifyDataSetChanged();
                } else {
                    showToast(getString(R.string.delete_meeting, getString(R.string.failure)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 是否显示空页面
     */
    private void isEmptyPage() {
        if (CurPage == 2 && listData.size() == 0) {
            pullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
            TextView emptyTxt = (TextView) pullToRefreshLayout.getView(ViewStatus.EMPTY_STATUS);
            emptyTxt.setText(getString(R.string.no_meeting_to_create_meeting));
        } else {
            pullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
        }
    }
}

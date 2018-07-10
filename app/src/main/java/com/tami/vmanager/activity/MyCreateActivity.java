package com.tami.vmanager.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.adapter.ViewHolder;
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
import com.tami.vmanager.view.MeetingStateView;
import com.tami.vmanager.view.swipemenu.SwipeHorizontalMenuLayout;
import com.tami.vmanager.view.swipemenu.SwipeMenuLayout;
import com.tami.vmanager.view.swipemenu.SwipeMenuListView;
import com.tami.vmanager.view.swipemenu.listener.SwipeRightSwitchListener;

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
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.my_create);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        listData = new ArrayList<>();

        listViewAdapter = new ListViewAdapter<AllMeetingsResponse.Array.Item>(getApplication(), listData, R.layout.item_my_create) {
            @Override
            protected void binView(ViewHolder viewHolder, AllMeetingsResponse.Array.Item item, int position) {
                SwipeHorizontalMenuLayout shmlView = viewHolder.getItemView(R.id.imc_shml);
                shmlView.setSwipeEnable(true);
                //修改按钮
                TextView modifyView = viewHolder.getItemView(R.id.menu_modify);
                modifyView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    showToast("modifyView");
                });
                //取消按钮
                TextView cancelView = viewHolder.getItemView(R.id.menu_cancel);
                cancelView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    showToast("cancelView");
                });
                //编辑按钮
                TextView editView = viewHolder.getItemView(R.id.item_content_editor);
                editView.setOnClickListener((View v) -> {
                    shmlView.smoothOpenEndMenu();
                });

                shmlView.setSwipeListener(new SwipeRightSwitchListener() {
                    @Override
                    public void endMenuClosed(SwipeMenuLayout swipeMenuLayout) {
                        editView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void endMenuOpened(SwipeMenuLayout swipeMenuLayout) {
                        editView.setVisibility(View.GONE);
                    }
                });

                //名称
                TextView nameView = viewHolder.getItemView(R.id.item_content_name);
                setNameTextLayoutParams(nameView, item.meetingName);
                //会议状态
                MeetingStateView stateView = viewHolder.getItemView(R.id.item_content_state);
                stateView.setMeetingStateText(item.meetingStatus, 20);
                //时间
                viewHolder.setTextView(R.id.item_content_start_time, item.autoDayTime);
//                holder.setText(R.id.item_content_start_time, TimeUtils.milliseconds2String(elements.getStartTime()));
//                holder.setText(R.id.item_content_end_time, TimeUtils.milliseconds2String(elements.getEndTime()));
                //V图片
                AppCompatImageView imageView = viewHolder.getItemView(R.id.item_content_level_icon);
                imageView.setVisibility(View.VISIBLE);
                //智图片
                AppCompatImageView imageView1 = viewHolder.getItemView(R.id.item_content_level_icon1);
                imageView1.setVisibility(item.fromPlat == 1 ? View.VISIBLE : View.GONE);
                viewHolder.setTextView(R.id.item_content_room, item.meetingAddress);
            }

            private void setNameTextLayoutParams(TextView nameView, String content) {
                setLayoutParams(nameView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                nameView.setText(content);
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                nameView.measure(spec, spec);
                int measuredWidthTicketNum = nameView.getMeasuredWidth();
                int maxWidth = ScreenUtil.sp2px(getApplicationContext(), 270);
                if (measuredWidthTicketNum > maxWidth) {
                    setLayoutParams(nameView, maxWidth);
                }
            }

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
        getMyCreateList();

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }


    /**
     * 获取我的创建数据
     */
    private void getMyCreateList() {
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
                        listData.addAll(array.elements);
                        listViewAdapter.notifyDataSetChanged();
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
}

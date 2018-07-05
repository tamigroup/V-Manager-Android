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
    private CommonAdapter<AllMeetingsResponse.Item.ElementElements> commonAdapter;
    private List<AllMeetingsResponse.Item.ElementElements> listData;
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
            }

            @Override
            public void loadMore() {
                query();
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.today_meeting);

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.percentage_10)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<AllMeetingsResponse.Item.ElementElements>(getApplicationContext(), R.layout.item_today_meeting, listData) {
            @Override
            protected void convert(ViewHolder holder, AllMeetingsResponse.Item.ElementElements elementElements, int position) {

            }

            @Override
            public void convert(ViewHolder holder, AllMeetingsResponse.Item.ElementElements elements) {
                //名称
                TextView nameView = holder.getView(R.id.item_meeting_name);
                setNameTextLayoutParams(nameView, elements.getMeetingName());
                //会议状态
                MeetingStateView stateView = holder.getView(R.id.item_meeting_state);
                stateView.setMeetingStateText(elements.getMeetingStatus(), 20);
                //时间
                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(elements.getStartTime()));
                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(elements.getEndTime()));
                //V图片
                AppCompatImageView imageView = holder.getView(R.id.item_meeting_level_icon);
                imageView.setVisibility(View.VISIBLE);
                //智图片
                AppCompatImageView imageView1 = holder.getView(R.id.item_meeting_level_icon1);
                imageView1.setVisibility(elements.getFromPlat() == 1 ? View.VISIBLE : View.GONE);
                holder.setText(R.id.item_meeting_room, elements.getMeetingAddress());

                //ITEM点击
                ConstraintLayout itemLayout = holder.getView(R.id.item_meeting_layout);
                itemLayout.setOnClickListener((View v) -> {
                    startActivity(new Intent(getApplicationContext(), MeetingOverviewActivity.class));
                });
            }

            private void setNameTextLayoutParams(TextView nameView, String content) {
                setLayoutParams(nameView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                nameView.setText(content);
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                nameView.measure(spec, spec);
                int measuredWidthTicketNum = nameView.getMeasuredWidth();
                int maxWidth = ScreenUtil.sp2px(getApplicationContext(), 330);
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
        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        CurPage = 1;
        query();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }

    private void query() {
        AllMeetingsRequest allMeetingsRequest = new AllMeetingsRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            allMeetingsRequest.setUserId(item.getId());
        }
        allMeetingsRequest.setCurPage(String.valueOf(CurPage++));
        allMeetingsRequest.setPageSize(String.valueOf(Constants.PAGE_SIZE));
        allMeetingsRequest.setRequsetUrl(HttpKey.TODAY_MEETINGS);
        networkBroker.ask(allMeetingsRequest, (ex1, res) -> {
            if (null != ex1) {
                ex1.printStackTrace();
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                AllMeetingsResponse response = (AllMeetingsResponse) res;
                if (response.getCode() == 200) {
                    AllMeetingsResponse.Item aItem = response.getData();
                    if (aItem != null && aItem.getElements() != null && aItem.getElements().size() > 0) {
                        listData.addAll(aItem.getElements());
                        commonAdapter.notifyDataSetChanged();
                    }
                    pullToRefreshLayout.finishLoadMore();
                    if (!aItem.getLastPage()) {
                        pullToRefreshLayout.setCanLoadMore(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}

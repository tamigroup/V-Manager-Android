package com.tami.vmanager.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.ChangeDemandRequestBean;
import com.tami.vmanager.entity.ChangeDemandResponseBean;
import com.tami.vmanager.entity.MobileMessage;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.CircleImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 反馈 活动变化
 * Created by why on 2018/6/16.
 */

public class FeedbackFragment extends ViewPagerBaseFragment {

    private int meetingId;
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private NetworkBroker networkBroker;
    private int CurPage = 1;
    List<ChangeDemandResponseBean.DataBean.ElementsBean> listData;
    private CommonAdapter<ChangeDemandResponseBean.DataBean.ElementsBean> commonAdapter;

    public FeedbackFragment() {
    }


    @SuppressLint("ValidFragment")
    public FeedbackFragment(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.notice_recycler_view);
        pullToRefreshLayout = findViewById(R.id.feedback_PullToRefreshLayout);
        networkBroker = new NetworkBroker(getContext());
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                queryData();
            }

            @Override
            public void loadMore() {
                queryData();
            }
        });
    }

    @Override
    public void initData() {
        listData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter = new CommonAdapter<ChangeDemandResponseBean.DataBean.ElementsBean>(getActivity(), R.layout.item_feedback, listData) {
            @Override
            protected void convert(ViewHolder holder, ChangeDemandResponseBean.DataBean.ElementsBean item, int position) {
                if (!item.getRequestIconUrl().trim().isEmpty()) {
                    CircleImageView circleImageView = holder.getView(R.id.in_avatar_image);
                    Picasso.get().load(item.getRequestIconUrl()).into(circleImageView);
                }
                TextView have_reply = holder.getView(R.id.have_reply);
                TextView item_reply_content = holder.getView(R.id.item_reply_content);
                TextView item_reply_name = holder.getView(R.id.item_reply_name);
                holder.setText(R.id.item_content_tv, item.getRequestContent());
                holder.setText(R.id.in_name, item.getRequestUserName());
                holder.setText(R.id.item_time, item.getRequestTime());
                if (item.getReplyUserName().trim().isEmpty() && item.getReplyContent().trim().isEmpty()) {
                    have_reply.setText(getResources().getString(R.string.no_replay));
                    have_reply.setTextColor(getResources().getColor(R.color.color_999999));
                    item_reply_content.setVisibility(View.GONE);
                    item_reply_name.setVisibility(View.GONE);
                } else {
                    item_reply_content.setVisibility(View.VISIBLE);
                    item_reply_name.setVisibility(View.VISIBLE);
                    holder.setText(R.id.item_reply_name, item.getReplyUserName());
                    holder.setText(R.id.item_reply_content, String.format(getResources().getString(R.string.replay_content), item.getReplyContent()));
                }
            }
        };
        commonAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        queryData();
    }

    private void queryData() {
        ChangeDemandRequestBean changeDemandRequestBean = new ChangeDemandRequestBean();
        changeDemandRequestBean.setMeetingId(String.valueOf(meetingId));
        changeDemandRequestBean.setCurPage(CurPage++);
        changeDemandRequestBean.setPageSize(10);
        networkBroker.ask(changeDemandRequestBean, (Exception exl, MobileMessage res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }
            try {
                ChangeDemandResponseBean response = (ChangeDemandResponseBean) res;
                if (response.getCode() == 200) {
                    ChangeDemandResponseBean.DataBean data = response.getData();
                    if (data != null){
                        if (data.getElements() != null && data.getElements().size() > 0) {
                            listData.addAll(data.getElements());
                            commonAdapter.notifyDataSetChanged();
                        }
                        if (data.isLastPage()) {
                            pullToRefreshLayout.setCanLoadMore(false);
                        }
                    }
                }
                pullToRefreshLayout.finishRefresh();
                pullToRefreshLayout.finishLoadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }
}

package com.tami.vmanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tami.pulltorefresh.BaseRefreshListener;
import com.tami.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.NoticeRequestBean;
import com.tami.vmanager.entity.NoticeResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.message.MessageEvent;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告
 * Created by why on 2018/6/16.
 */

public class NoticeFragment extends ViewPagerBaseFragment {

    private RecyclerView recyclerView;
    private NetworkBroker networkBroker;
    private int CurPage = 1;
    private PullToRefreshLayout pullToRefreshLayout;
    private CommonAdapter<NoticeResponseBean.DataBean.ElementsBean> commonAdapter;
    private List<NoticeResponseBean.DataBean.ElementsBean> listData;
    private int meetingId;
    private TextView empty_tv;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.notice_recycler_view);
        pullToRefreshLayout = findViewById(R.id.feedback_PullToRefreshLayout);
        empty_tv = findViewById(R.id.empty_tv);
        networkBroker = new NetworkBroker(getActivity());
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
            }

            @Override
            public void loadMore() {
                queryData();
            }
        });
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<NoticeResponseBean.DataBean.ElementsBean>(getActivity(), R.layout.item_notice, listData) {
            @Override
            protected void convert(ViewHolder holder, NoticeResponseBean.DataBean.ElementsBean noticeEntity, int position) {
                ImageView image = holder.getView(R.id.in_avatar_image);
                if (!TextUtils.isEmpty(noticeEntity.getIconUrl())) {
                    Picasso.get().load(noticeEntity.getIconUrl()).into(image);
                }
                holder.setText(R.id.in_name, noticeEntity.getUserName());
                holder.setText(R.id.item_content_tv, noticeEntity.getTitle());
                holder.setText(R.id.item_content, noticeEntity.getContent());
            }
        };
        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void requestNetwork() {
        if (null == networkBroker){
            networkBroker = new NetworkBroker(getActivity());
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            meetingId = (int) arguments.get(Constants.KEY_MEETING_ID);
        }
        CurPage = 1;
        queryData();
    }

    /**
     * 获取公告数据
     */
    private void queryData() {
        NoticeRequestBean noticeRequestBean = new NoticeRequestBean();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        noticeRequestBean.setSystemId(item.getSystemId());
        //        noticeRequestBean.setMeetingId(1);
        noticeRequestBean.setMeetingId(meetingId);
        noticeRequestBean.setGroupType(1);//1-V管家 2-V智会 3-VV群
        noticeRequestBean.setNoticeType(0);// 0-全部  1-系统通知  2-会务广播 3-群内公告
        noticeRequestBean.setCurPage(CurPage++);
        noticeRequestBean.setPageSize(10);

        networkBroker.ask(noticeRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                NoticeResponseBean response = (NoticeResponseBean) res;
                if (response.getCode() == 200) {
                    List<NoticeResponseBean.DataBean.ElementsBean> data = response.getData().getElements();
                    if (data != null && data.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_tv.setVisibility(View.GONE);
                        listData.addAll(data);
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        empty_tv.setVisibility(View.VISIBLE);
                        empty_tv.setText(R.string.empty_notice);
                    }
                    if (response.getData().isLastPage()){
                        pullToRefreshLayout.setCanLoadMore(false);
                    }
                    pullToRefreshLayout.finishLoadMore();
                }else{
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void removeListener() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void emptyObject() {
//        if (networkBroker != null) {
//            networkBroker.cancelAllRequests();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent event) {
        if (event.isRefresh()) {
            queryData();
        }
        EventBus.getDefault().cancelEventDelivery(event);
    }

}

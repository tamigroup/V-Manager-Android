package com.tami.vmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.NoticeRequestBean;
import com.tami.vmanager.entity.NoticeResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
            }

            @Override
            public void loadMore() {
                requestNetwork();
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
                Picasso.get().load(noticeEntity.getIconUrl()).into(image);
                holder.setText(R.id.in_name, noticeEntity.getUserName());
                holder.setText(R.id.item_content_tv, noticeEntity.getTitle());
                holder.setText(R.id.item_content, noticeEntity.getContent());
            }
        };
        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        NoticeRequestBean noticeRequestBean = new NoticeRequestBean();
        noticeRequestBean.setSystemId(4);
        noticeRequestBean.setMeetingId(1);
        noticeRequestBean.setGroupType(1);
        noticeRequestBean.setNoticeType(0);
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
                        listData.addAll(data);
                        commonAdapter.notifyDataSetChanged();
                    }
                    pullToRefreshLayout.finishLoadMore();
                }
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

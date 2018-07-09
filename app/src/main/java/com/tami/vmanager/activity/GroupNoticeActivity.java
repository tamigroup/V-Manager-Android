package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.NoticeRequestBean;
import com.tami.vmanager.entity.NoticeResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议公告 群公告
 * Created by why on 2018/7/5.
 */
public class GroupNoticeActivity extends BaseActivity {

    private NetworkBroker networkBroker;
    private int meetingId;
    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView recyclerView;
    private CommonAdapter<NoticeResponseBean.DataBean.ElementsBean> commonAdapter;
    private List<NoticeResponseBean.DataBean.ElementsBean> listData;
    private int CurPage = 1;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_notice;
    }

    @Override
    public void initView() {
        pullToRefreshLayout = findViewById(R.id.agn_PullToRefreshLayout);
        recyclerView = findViewById(R.id.agn_recyclerview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.group_notice));
        setTitleRightBtn(R.mipmap.edit_notice);

        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        initRecyclerView();
        pullToRefreshLayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        CurPage = 1;
        getNotice();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleRightBtn:
                Intent intent = new Intent(getApplicationContext(), GroupEditNoticeActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intent);
                break;
        }
    }

    private void initRecyclerView() {
        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<NoticeResponseBean.DataBean.ElementsBean>(this, R.layout.item_group_notice, listData) {
            @Override
            protected void convert(ViewHolder holder, NoticeResponseBean.DataBean.ElementsBean item, int position) {
                //赋值
                holder.setText(R.id.ign_title, item.getTitle());
                holder.setText(R.id.ign_time, item.getUserName() + " " + item.getRelativeDate());
                holder.setText(R.id.ign_content, item.getContent());
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    private void getNotice() {
        NoticeRequestBean noticeRequestBean = new NoticeRequestBean();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            noticeRequestBean.setSystemId(Integer.valueOf(item.getSystemId()));
        }
        noticeRequestBean.setMeetingId(meetingId);
        noticeRequestBean.setGroupType(1);
        noticeRequestBean.setNoticeType(3);
        noticeRequestBean.setCurPage(CurPage++);
        noticeRequestBean.setPageSize(10);

//        noticeRequestBean.setSystemId(4);
//        noticeRequestBean.setMeetingId(1);
//        noticeRequestBean.setGroupType(1);
//        noticeRequestBean.setNoticeType(0);
//        noticeRequestBean.setCurPage(CurPage++);
//        noticeRequestBean.setPageSize(10);
        networkBroker.ask(noticeRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                NoticeResponseBean response = (NoticeResponseBean) res;
                if (response.getCode() == 200) {
                    NoticeResponseBean.DataBean dataBean = response.getData();
                    if (dataBean != null) {
                        List<NoticeResponseBean.DataBean.ElementsBean> data = dataBean.getElements();
                        if (data != null && data.size() > 0) {
                            listData.addAll(data);
                            commonAdapter.notifyDataSetChanged();
                        }

                    }
                    if (dataBean.isLastPage()) {
                        pullToRefreshLayout.setCanLoadMore(false);
                    }
                }
                pullToRefreshLayout.finishLoadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

package com.tami.vmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.NoticeEntity;
import com.tami.vmanager.view.CircleImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 反馈
 * Created by why on 2018/6/16.
 */

public class FeedbackFragment extends ViewPagerBaseFragment {

    private RecyclerView recyclerView;
    private PullToRefreshLayout feedback_pullToRefreshLayout;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.notice_recycler_view);
        feedback_pullToRefreshLayout = findViewById(R.id.feedback_PullToRefreshLayout);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonAdapter<NoticeEntity>(getActivity(), R.layout.item_feedback, getData()) {
            @Override
            protected void convert(ViewHolder holder, NoticeEntity noticeEntity, int position) {

            }

            @Override
            public void convert(ViewHolder holder, NoticeEntity noticeEntity) {
                //头像
                CircleImageView circleImageView = holder.getView(R.id.in_avatar_image);
                //发送者
                TextView name = holder.getView(R.id.in_name);
                name.setText(noticeEntity.getName());
                //发送内容
                TextView content = holder.getView(R.id.item_content_tv);
                content.setText(noticeEntity.getContent());
                //发送时间
                TextView time = holder.getView(R.id.item_time);
                time.setText(noticeEntity.getTime());
            }
        });
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }

    private List<NoticeEntity> getData() {
        List<NoticeEntity> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NoticeEntity noticeEntity = new NoticeEntity();
            noticeEntity.setId(i);
            noticeEntity.setName("反馈测试名称" + i);
            noticeEntity.setContent("反馈测试内容" + i);
            noticeEntity.setTime("6月19日 15:00");
            data.add(noticeEntity);
        }
        return data;
    }
}

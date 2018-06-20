package com.tami.vmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.NoticeEntity;
import com.tami.vmanager.view.CircleImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告
 * Created by why on 2018/6/16.
 */

public class NoticeFragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Override
    public boolean isTitle() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.notice_recycler_view);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonAdapter<NoticeEntity>(getActivity(), R.layout.item_notice, getData()) {
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
                TextView content = holder.getView(R.id.in_content);
                content.setText(noticeEntity.getContent());
                //发送时间
                TextView time = holder.getView(R.id.in_time);
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
            noticeEntity.setName("公告测试数据" + i);
            noticeEntity.setContent("公告测试内容" + i);
            noticeEntity.setTime("6月19日 15:00");
            data.add(noticeEntity);
        }
        return data;
    }
}

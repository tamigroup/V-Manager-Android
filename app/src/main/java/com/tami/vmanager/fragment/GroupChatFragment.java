package com.tami.vmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * 群聊
 * Created by why on 2018/6/16.
 */
public class GroupChatFragment extends BaseFragment {

    private RecyclerView recyclerView;//聊天记录
    private EditText sendTxt;//发送文本
    private Button sendBtn;//发送按钮

    @Override
    public boolean isTitle() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_group_chat;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.fgc_recycler_view);
        sendTxt = findViewById(R.id.fgc_send_txt);
        sendBtn = findViewById(R.id.fgc_send_btn);
    }

    @Override
    public void initListener() {
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
//创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonAdapter<NoticeEntity>(getActivity(), R.layout.item_group_chat, getData()) {
            @Override
            protected void convert(ViewHolder holder, NoticeEntity noticeEntity, int position) {

            }

            @Override
            public void convert(ViewHolder holder, NoticeEntity noticeEntity) {
                //头像
                CircleImageView circleImageView = holder.getView(R.id.igc_avatar_image);
                //发送者职位和姓名
                TextView name = holder.getView(R.id.igc_position_name);
                name.setText(noticeEntity.getName());
                //发送内容
                TextView content = holder.getView(R.id.igc_left_content);
                content.setText(noticeEntity.getContent());
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fgc_send_btn:
                //发送按钮

                break;
        }
    }

    private List<NoticeEntity> getData() {
        List<NoticeEntity> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NoticeEntity noticeEntity = new NoticeEntity();
            noticeEntity.setId(i);
            noticeEntity.setName("销售-张三");
            noticeEntity.setContent("AA聚餐吃了近千元，损友相继溜走，最后留下的他这样逃单……看了好疼！");
//            noticeEntity.setTime("6月19日 15:00");
            data.add(noticeEntity);
        }
        return data;
    }
}

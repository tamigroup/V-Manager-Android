package com.tami.vmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.MsgComingItemDelagate;
import com.tami.vmanager.adapter.MsgSendItemDelagate;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.MeetingChatPageRequest;
import com.tami.vmanager.entity.MeetingChatPageResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 群聊 群消息
 * Created by why on 2018/6/16.
 */
public class GroupChatFragment extends ViewPagerBaseFragment {

    private RecyclerView recyclerView;//聊天记录
    private EditText sendTxt;//发送文本
    private Button sendBtn;//发送按钮
    private int CurPage = 1;
    private NetworkBroker networkBroker;
    private MultiItemTypeAdapter adapter;
    private List<MeetingChatPageResponse.DataBean.ElementsBean> listData;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_group_chat;
    }

    @Override
    public void initView() {
//        JMessageClient.registerEventReceiver(getContext()); 需要重写OnEvent
        recyclerView = findViewById(R.id.fgc_recycler_view);
        sendTxt = findViewById(R.id.fgc_send_txt);
        sendBtn = findViewById(R.id.fgc_send_btn);

        networkBroker = new NetworkBroker(getContext());
    }

    @Override
    public void initListener() {
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listData = new ArrayList<>();
        adapter = new MultiItemTypeAdapter(getContext(), listData);
        adapter.addItemViewDelegate(new MsgSendItemDelagate());
        adapter.addItemViewDelegate(new MsgComingItemDelagate());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void requestNetwork() {
        MeetingChatPageRequest meetingChatPageRequest = new MeetingChatPageRequest();
        meetingChatPageRequest.setMeetingId(1);
        meetingChatPageRequest.setType(1);
        meetingChatPageRequest.setCurPage(CurPage++);
        meetingChatPageRequest.setPageSize(10);
        networkBroker.ask(meetingChatPageRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                MeetingChatPageResponse response = (MeetingChatPageResponse) res;
                if (response.getCode() == 200) {
                    MeetingChatPageResponse.DataBean responseData = response.getData();
                    if (responseData != null && responseData.getElements() != null && responseData.getElements().size() > 0) {
                        listData.addAll(responseData.getElements());
                        adapter.notifyDataSetChanged();
                    }
                    //                    pullToRefreshLayout.finishLoadMore();
                    //                    if (!aItem.getLastPage()) {
                    //                        pullToRefreshLayout.setCanLoadMore(false);
                    //                    }
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fgc_send_btn:
                //发送按钮

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        JMessageClient.unRegisterEventReceiver(getContext());
    }
}

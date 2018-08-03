package com.tami.vmanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.pulltorefresh.BaseRefreshListener;
import com.tami.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.MsgComingItemDelagate;
import com.tami.vmanager.adapter.MsgSendItemDelagate;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.MeetingChatPageRequest;
import com.tami.vmanager.entity.MeetingChatPageResponse;
import com.tami.vmanager.entity.SendMsgRequest;
import com.tami.vmanager.entity.SendMsgResponse;
import com.tami.vmanager.event.EventManage;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
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
    private LoginResponse.Item item;
    private PullToRefreshLayout pullToRefreshLayout;
    private boolean isRefresh = false;
    private Bundle bundle;
    private int meetingId;
    private boolean is_invisible;
    private TextView empty_tv;
    private boolean isLoadMore = false;
    private int userId;
    private List<MeetingChatPageResponse.DataBean.ElementsBean> elements;
    private MsgSendItemDelagate msgSendItemDelagateAdapter;
    private boolean notification_receipt = false;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_group_chat;
    }

    @Override
    public void initView() {
        //        JMessageClient.registerEventReceiver(getContext()); 需要重写OnEvent
        Logger.e("EventBus注册00"+EventBus.getDefault().isRegistered(this));
        if (!EventBus.getDefault().isRegistered(this)){
            Logger.e("EventBus注册"+EventBus.getDefault().isRegistered(this));
            EventBus.getDefault().register(this);
        }
        recyclerView = findViewById(R.id.fgc_recycler_view);
        pullToRefreshLayout = findViewById(R.id.pullRL);
        sendTxt = findViewById(R.id.fgc_send_txt);
        sendBtn = findViewById(R.id.fgc_send_btn);
        empty_tv = findViewById(R.id.empty_tv);
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isRefresh = true;
                queryData();
            }

            @Override
            public void loadMore() {
                isLoadMore = true;
                CurPage = 1;
                queryData();
            }
        });
    }

    @Override
    public void initData() {
        is_invisible = (boolean) SPUtils.get(getActivity(), Constants.IS_INVISIBLE, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listData = new ArrayList<>();
        adapter = new MultiItemTypeAdapter(getContext(), listData);
        msgSendItemDelagateAdapter = new MsgSendItemDelagate();
        adapter.addItemViewDelegate(msgSendItemDelagateAdapter);
        adapter.addItemViewDelegate(new MsgComingItemDelagate());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        if (is_invisible) {
            sendTxt.setFocusable(false);
            sendBtn.setBackgroundColor(getResources().getColor(R.color.color_999999));
        } else {
            sendTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable content) {
                    sendBtn.setOnClickListener(v -> {
                        String trim = sendTxt.getText().toString().trim();
                        if (trim.isEmpty()) {
                            Toast.makeText(getContext(), R.string.not_empty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        requestNet(content.toString().trim());
                    });
                }
            });
        }
    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(getActivity());
        bundle = getArguments();
        if (bundle != null) {
            meetingId = this.bundle.getInt(Constants.KEY_MEETING_ID);
        }
        CurPage = 1;
        queryData();
    }

    private void queryData() {
        MeetingChatPageRequest meetingChatPageRequest = new MeetingChatPageRequest();
        meetingChatPageRequest.setMeetingId(meetingId);
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
                    if (responseData != null) {
                        List<MeetingChatPageResponse.DataBean.ElementsBean> elements = responseData.getElements();
                        if (elements != null && elements.size() > 0) {
                            empty_tv.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            this.elements = elements;
                            Collections.reverse(elements);
                            if (isRefresh) {
                                listData.addAll(0, elements);
                            } else if (isLoadMore) {
                                listData.clear();
                                listData.addAll(elements);
                            } else {
                                listData.addAll(elements);
                            }
                            isLoadMore = false;
                            isRefresh = false;
                            adapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(listData.size() - 1);
                        } else {
                            empty_tv.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                        if (responseData.isLastPage()) {
                            pullToRefreshLayout.setCanRefresh(false);
                        }
                    }
                    pullToRefreshLayout.finishRefresh();
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

    /**
     * 发送群聊消息
     *
     * @param content 内容
     */
    private void requestNet(String content) {
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setContent(content);
        sendMsgRequest.setMeetingId(String.valueOf(meetingId));
        sendMsgRequest.setType("1"); // 1-v管家  2-v智会 3-VV群
        item = GlobaVariable.getInstance().item;
        if (item != null) {
            sendMsgRequest.setUserId(String.valueOf(item.getId()));
            sendMsgRequest.setUserName(item.getNickName());
            sendMsgRequest.setUserIcon(item.getIconUrl());
        }

        networkBroker.ask(sendMsgRequest, (exl, res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }

            try {
                SendMsgResponse response = (SendMsgResponse) res;
                if (response.getCode() == 200) {
                    if (response.isData()) {
                        sendTxt.setText("");
                        if (notification_receipt){
                            notification_receipt = false;
                            return;
                        }
                        MeetingChatPageResponse.DataBean.ElementsBean elementsBean = new MeetingChatPageResponse.DataBean.ElementsBean();
                        elementsBean.setContent(content);
                        LoginResponse.Item userItem = GlobaVariable.getInstance().item;
                        if (userItem != null) {
                            elementsBean.setUserId(userItem.getId());
                            elementsBean.setUserName(msgSendItemDelagateAdapter.getItem().getUserName());
                            elementsBean.setUserIcon(userItem.getIconUrl());
                        }
                        elementsBean.setMeetingId(String.valueOf(meetingId));
                        elementsBean.setType("1");
                        listData.add(elementsBean);

                        empty_tv.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.notifyItemInserted(listData.size() - 1);
                        recyclerView.scrollToPosition(listData.size() - 1);
                    } else {
                        showToast(getString(R.string.send_fail) + response.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getJpush_GroupMessageEvent(EventManage.Jpush_GroupMessageEvent jpush_GroupMessageEvent) {
        String message = jpush_GroupMessageEvent.getMessage();
        if (message.equals(EventManage.JPUSH_GROUPMESSAGEEVENT)) {
            notification_receipt = true;
            CurPage = 1;
            queryData();
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //JMessageClient.unRegisterEventReceiver(getContext());
    }
}

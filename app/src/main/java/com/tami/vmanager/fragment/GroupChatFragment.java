package com.tami.vmanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.MsgComingItemDelagate;
import com.tami.vmanager.adapter.MsgSendItemDelagate;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.MeetingChatPageRequest;
import com.tami.vmanager.entity.MeetingChatPageResponse;
import com.tami.vmanager.entity.SendMsgRequest;
import com.tami.vmanager.entity.SendMsgResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

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

    @Override
    public int getContentViewId() {
        return R.layout.fragment_group_chat;
    }

    @Override
    public void initView() {
        //        JMessageClient.registerEventReceiver(getContext()); 需要重写OnEvent
        recyclerView = findViewById(R.id.fgc_recycler_view);
        pullToRefreshLayout = findViewById(R.id.pullRL);
        sendTxt = findViewById(R.id.fgc_send_txt);
        sendBtn = findViewById(R.id.fgc_send_btn);

        networkBroker = new NetworkBroker(getContext());
        bundle = getArguments();
        if (bundle != null) {
            meetingId = this.bundle.getInt(Constants.KEY_MEETING_ID);
        }
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setCanLoadMore(false);
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isRefresh = true;
                queryData();
            }

            @Override
            public void loadMore() {

            }
        });
    }

    @Override
    public void initData() {
        is_invisible = (boolean) SPUtils.get(getActivity(), Constants.IS_INVISIBLE, false);
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
        queryData();
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
                        if (responseData.getElements() != null && responseData.getElements().size() > 0) {
                            List<MeetingChatPageResponse.DataBean.ElementsBean> elements = responseData.getElements();
                            Collections.reverse(elements);
                            if (isRefresh) {
                                listData.addAll(0, elements);
                            } else {
                                listData.addAll(elements);
                            }
                            isRefresh = false;
                            adapter.notifyDataSetChanged();
                        }
                        if (responseData.isLastPage()) {
                            pullToRefreshLayout.setCanRefresh(false);
                        }
                    }
                    pullToRefreshLayout.finishRefresh();
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
        sendMsgRequest.setType("1");
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
                        MeetingChatPageResponse.DataBean.ElementsBean elementsBean = new MeetingChatPageResponse.DataBean.ElementsBean();
                        elementsBean.setContent(content);
                        if (item != null) {
                            elementsBean.setUserId(item.getId());
                            elementsBean.setUserIcon(item.getIconUrl());
                        }
                        String username = (String) SPUtils.get(getContext(), "username", "");
                        elementsBean.setUserName(username);
                        elementsBean.setMeetingId(String.valueOf(meetingId));
                        elementsBean.setType("1");
                        listData.add(elementsBean);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        JMessageClient.unRegisterEventReceiver(getContext());
    }
}

package com.tami.vmanager.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;
import com.tami.pulltorefresh.BaseRefreshListener;
import com.tami.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.ChangeDemandReplayRequestBean;
import com.tami.vmanager.entity.ChangeDemandReplayResponseBean;
import com.tami.vmanager.entity.ChangeDemandRequestBean;
import com.tami.vmanager.entity.ChangeDemandResponseBean;
import com.tami.vmanager.entity.GetMeetingRequest;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.entity.GetMeetingSellUserIdRequestBean;
import com.tami.vmanager.entity.GetMeetingSellUserIdResponeBean;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.MobileMessage;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.CircleImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反馈 活动变化
 * Created by why on 2018/6/16.
 */

public class FeedbackFragment extends ViewPagerBaseFragment {

    private GetMeetingResponse.Item item;
    private int meetingId;
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private NetworkBroker networkBroker;
    private int CurPage = 1;
    List<ChangeDemandResponseBean.DataBean.ElementsBean> listData;
    private CommonAdapter<ChangeDemandResponseBean.DataBean.ElementsBean> commonAdapter;
    private DialogPlus dialog;
    private DialogPlus fastDialog;
    private List<String> fastRepayList;
    private TextView empty_tv;
    private TextView vzhihui_tv;
    private int create_meetingId;

    public FeedbackFragment() {
    }


    @SuppressLint("ValidFragment")
    public FeedbackFragment(int meetingId, GetMeetingResponse.Item item) {
        this.meetingId = meetingId;
        this.item = item;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.notice_recycler_view);
        pullToRefreshLayout = findViewById(R.id.feedback_PullToRefreshLayout);
        networkBroker = new NetworkBroker(getActivity());
        fastRepayList = Arrays.asList(getString(R.string.fast_replay_1), getString(R.string.fast_replay_2), getString(R.string.fast_replay_3), getString(R.string.fast_replay_4));
        empty_tv = findViewById(R.id.empty_tv);
        vzhihui_tv = findViewById(R.id.Vzhihui_tv);
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
        if (null == item) {
            getMeeting();
        } else {
            initUI();
        }

        listData = new ArrayList<>();
        initRecycle();
        onItemClick();
    }

    private void initUI() {
        if (item.isVzh != 1) {
            recyclerView.setVisibility(View.GONE);
            empty_tv.setVisibility(View.GONE);
            vzhihui_tv.setVisibility(View.VISIBLE);
            return;
        }
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter = new CommonAdapter<ChangeDemandResponseBean.DataBean.ElementsBean>(getActivity(), R.layout.item_feedback, listData) {
            @Override
            protected void convert(ViewHolder holder, ChangeDemandResponseBean.DataBean.ElementsBean item, int position) {
                holder.setIsRecyclable(false);
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
                    have_reply.setText(getString(R.string.has_replay));
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

    private void onItemClick() {
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                boolean is_invisible = (boolean) SPUtils.get(view.getContext(), Constants.IS_INVISIBLE, false);
                if (is_invisible) {
                    showToast(getString(R.string.view_only));
                    return;
                }
                TextView have_reply = holder.itemView.findViewById(R.id.have_reply);
                if (have_reply.getText().equals(getString(R.string.has_replay))) {
                    showToast(getString(R.string.y_has_replay));
                    return;
                }
                //从接口获取 创建会议的ID  销售总负责 2
                LoginResponse.Item LoginItem = GlobaVariable.getInstance().item;
                if (null != LoginItem) {
                    int userId = LoginItem.getId();
                    if (userId == create_meetingId) {
                        //快速回复
                        fastReplay(view, holder, position);
                    } else {
                        showToast(R.string.no_permission_to_reply);
                    }
                }
//                if (null != LoginItem) {
//                    int userId = LoginItem.getId();
//                    List<LoginResponse.Item.UserRole> userRoleList = LoginItem.getUserRoleList();
//                    if (userRoleList != null && userRoleList.size() > 0) {
//                        for (LoginResponse.Item.UserRole userRole : userRoleList) {
//                            if (userId == create_meetingId || userRole.roleId == 2) {
//                                //快速回复
//                                fastReplay(view, holder, position);
//                            } else {
//                                showToast(R.string.no_permission_to_reply);
//                            }
//                        }
//                    }
//                }
                //弹出EditText回复
                //replay(view, holder, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 快速回复
     */
    private void fastReplay(View view, RecyclerView.ViewHolder holder, int position) {
        fastDialog = DialogPlus.newDialog(view.getContext())
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_fast_reply))
                .create();
        fastDialog.show();
        RecyclerView dialog_recyc = (RecyclerView) fastDialog.findViewById(R.id.recyc);
        dialog_recyc.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dialog_recyc.addItemDecoration(new RecycleViewDivider(view.getContext(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(view.getContext(), R.color.percentage_10)));
        CommonAdapter<String> dialog_adapter = new CommonAdapter<String>(view.getContext(), R.layout.item_dialog_fast_reply, fastRepayList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.fast_replay_tv, s);
            }
        };
        dialog_recyc.setAdapter(dialog_adapter);
        dialog_adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder fastHolder, int po) {
                requestNet(fastRepayList.get(po), listData.get(position).getId(), GlobaVariable.getInstance().item.getId(), holder);
                fastDialog.dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void replay(View view, RecyclerView.ViewHolder holder, int position) {
        dialog = DialogPlus.newDialog(view.getContext())
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_reply))
                .create();
        dialog.show();
        EditText reply_edit = (EditText) dialog.findViewById(R.id.reply_edit);
        TextView send = (TextView) dialog.findViewById(R.id.send);
        Utils.showSoftInput((Activity) view.getContext());
        reply_edit.setHint(String.format(getResources().getString(R.string.hint_replay), listData.get(position).getRequestUserName()));
        reply_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable content) {
                send.setOnClickListener(v -> {
                    String trim = reply_edit.getText().toString().trim();
                    if (trim.isEmpty()) {
                        Toast.makeText(getContext(), getString(R.string.not_empty), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requestNet(content.toString().trim(), listData.get(position).getId(), GlobaVariable.getInstance().item.getId(), holder);
                    Utils.hideSoftInput((Activity) view.getContext());
                    dialog.dismiss();
                });
            }
        });
    }

    /**
     * 获取会议信息
     */
    private void getMeeting() {
        GetMeetingRequest gmr = new GetMeetingRequest();
        gmr.setMeetingId(meetingId);
        networkBroker.ask(gmr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingResponse response = (GetMeetingResponse) res;
                if (response.getCode() == 200) {
                    this.item = response.data;
                    initUI();
                }else{
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void requestNet(String content, int meetingRequirementId, int replyUserId, RecyclerView.ViewHolder holder) {
        ChangeDemandReplayRequestBean changeDemandReplayRequestBean = new ChangeDemandReplayRequestBean();
        changeDemandReplayRequestBean.setMeetingRequirementId(meetingRequirementId);
        changeDemandReplayRequestBean.setReplyContent(content);
        changeDemandReplayRequestBean.setReplyUserId(replyUserId);
        networkBroker.ask(changeDemandReplayRequestBean, (exl, res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }
            ChangeDemandReplayResponseBean replayResponse = (ChangeDemandReplayResponseBean) res;
            if (replayResponse.getCode() == 200) {
                if (replayResponse.isData()) {
                    showToast(getString(R.string.replay_success));
                    TextView have_reply = holder.itemView.findViewById(R.id.have_reply);
                    have_reply.setText(getResources().getString(R.string.has_replay));
                    have_reply.setTextColor(getResources().getColor(R.color.color_21AE1D));
                    TextView item_reply_content = holder.itemView.findViewById(R.id.item_reply_content);
                    item_reply_content.setVisibility(View.VISIBLE);
                    item_reply_content.setText(String.format(getResources().getString(R.string.replay_content), content));
                    TextView item_reply_name = holder.itemView.findViewById(R.id.item_reply_name);
                    item_reply_name.setVisibility(View.VISIBLE);
                    item_reply_name.setText(GlobaVariable.getInstance().item.getNickName());
                } else {
                    showToast(getString(R.string.replay_fail));
                }
            }
        });
    }


    @Override
    public void requestNetwork() {
        CurPage = 1;
        if (item.isVzh == 1) {
            GetMeetingSellUserId();
            queryData();
        }
    }

    /**
     * 获取创建会议人的Id
     */
    private void GetMeetingSellUserId() {
        GetMeetingSellUserIdRequestBean getMeetingSellUserIdRequestBean = new GetMeetingSellUserIdRequestBean();
        getMeetingSellUserIdRequestBean.setMeetingId(meetingId);
        networkBroker.ask(getMeetingSellUserIdRequestBean, (Exception exl, MobileMessage res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }
            try {
                GetMeetingSellUserIdResponeBean response = (GetMeetingSellUserIdResponeBean) res;
                if (response.getCode() == 200) {
                    create_meetingId = response.getData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void queryData() {
        ChangeDemandRequestBean changeDemandRequestBean = new ChangeDemandRequestBean();
        changeDemandRequestBean.setMeetingId(String.valueOf(meetingId));
        //        changeDemandRequestBean.setMeetingId(String.valueOf(1));
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
                    if (data != null) {
                        if (data.getElements() != null && data.getElements().size() > 0) {
                            listData.addAll(data.getElements());
                            commonAdapter.notifyDataSetChanged();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            empty_tv.setVisibility(View.VISIBLE);
                            empty_tv.setText(getString(R.string.no_change_in_the_event));
                        }
                        if (data.isLastPage()) {
                            pullToRefreshLayout.setCanRefresh(false);
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

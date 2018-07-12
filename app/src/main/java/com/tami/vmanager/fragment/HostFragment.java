package com.tami.vmanager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.EvaluatePageRequestBean;
import com.tami.vmanager.entity.EvaluatePageResponseBean;
import com.tami.vmanager.entity.IdeasBoxRequestBean;
import com.tami.vmanager.entity.IdeasBoxResponsetBean;
import com.tami.vmanager.enums.IdeasBoxType;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.RatingBar.BaseRatingBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tang on 2018/7/5
 * 主办方
 */
public class HostFragment extends ViewPagerBaseFragment {

    private BaseRatingBar ratingBar;
    private RecyclerView recyclerview;
    private TextView environmental;
    private TextView service;
    private NetworkBroker networkBroker;
    private int CurPag = 1;
    private PullToRefreshLayout pulltorefreshlayout;
    private CommonAdapter<EvaluatePageResponseBean.DataBean.ElementsBean> commonAdapter;
    private List<EvaluatePageResponseBean.DataBean.ElementsBean> listData;
    private TextView comment;

    private int meetingId;//会议ID

    @Override
    public int getContentViewId() {
        return R.layout.fragment_host;
    }

    @Override
    public void initView() {
        ratingBar = findViewById(R.id.rating_bar);
        comment = findViewById(R.id.comment);
        service = findViewById(R.id.service);
        environmental = findViewById(R.id.environmental);
        recyclerview = findViewById(R.id.recyc);
        pulltorefreshlayout = findViewById(R.id.pull);
    }

    @Override
    public void initListener() {
        pulltorefreshlayout.setCanRefresh(false);
        pulltorefreshlayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

            }

            @Override
            public void loadMore() {
                getEvaluate();
            }
        });
    }

    @Override
    public void initData() {
        initRecyc();
    }

    private void initRecyc() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<EvaluatePageResponseBean.DataBean.ElementsBean>(getContext(), R.layout.item_fragment_host, listData) {

            @Override
            protected void convert(ViewHolder holder, EvaluatePageResponseBean.DataBean.ElementsBean elementsBean, int position) {
                if (!TextUtils.isEmpty(elementsBean.getIconUrl())) {
                    ImageView header_image = holder.getView(R.id.header_image);
                    Picasso.get().load(elementsBean.getIconUrl()).into(header_image);
                }
                holder.setText(R.id.header_name, elementsBean.getUserName());
                BaseRatingBar rating_bar = holder.getView(R.id.rating_bar);
                rating_bar.setRating(elementsBean.getScore());
                holder.setText(R.id.item_content, elementsBean.getContent());
            }
        };
        commonAdapter.notifyDataSetChanged();
        recyclerview.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(getContext());
        networkBroker.setCancelTag(getTAG());
        Bundle bundle = getArguments();
        if (bundle != null) {
            meetingId = bundle.getInt(Constants.KEY_MEETING_ID);
        }

        getAvg();
        getEvaluate();
    }

    /**
     * 获取评论
     */
    @SuppressLint("StringFormatMatches")
    private void getEvaluate() {
        EvaluatePageRequestBean evaluatePageRequestBean = new EvaluatePageRequestBean();
        evaluatePageRequestBean.setMeetingId(String.valueOf(meetingId));
        evaluatePageRequestBean.setType(IdeasBoxType.HOST.getType());
        evaluatePageRequestBean.setCurPage(CurPag++);
        evaluatePageRequestBean.setPageSize(Constants.PAGE_SIZE);
        networkBroker.ask(evaluatePageRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                pulltorefreshlayout.finishLoadMore();
                return;
            }
            EvaluatePageResponseBean response = (EvaluatePageResponseBean) res;
            if (response.getCode() == 200) {
                EvaluatePageResponseBean.DataBean data = response.getData();
                if (data.getElements() != null && data.getElements().size() > 0) {
                    comment.setText(getResources().getString(R.string.comment, data.getTotalElements()));
                    listData.addAll(data.getElements());
                    commonAdapter.notifyDataSetChanged();
                }
                pulltorefreshlayout.finishLoadMore();
                if (data.isLastPage()) {
                    pulltorefreshlayout.setCanLoadMore(false);
                }
            } else {
                pulltorefreshlayout.finishLoadMore();
            }
        });
    }

    /**
     * 获取综合打分
     */
    private void getAvg() {
        IdeasBoxRequestBean ideasBoxRequestBean = new IdeasBoxRequestBean();
        ideasBoxRequestBean.setMeetingId(String.valueOf(meetingId));
        ideasBoxRequestBean.setType(IdeasBoxType.HOST.getType());
        networkBroker.ask(ideasBoxRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            IdeasBoxResponsetBean response = (IdeasBoxResponsetBean) res;
            if (response.getCode() == 200) {
                IdeasBoxResponsetBean.DataBean data = response.getData();
                if (data != null) {
                    ratingBar.setRating(data.getAvg());
                    switch (data.getAvg()) {
                        case 0:
                        case 1:
                            setEvaluate(R.string.very_bad);
                            break;
                        case 2:
                            setEvaluate(R.string.bad);
                            break;
                        case 3:
                            setEvaluate(R.string.general);
                            break;
                        case 4:
                            setEvaluate(R.string.good);
                            break;
                        case 5:
                            setEvaluate(R.string.very_good);
                            break;
                    }
                }
            }
        });
    }

    /**
     * 评价服务与环境
     * @param evaluate 评价
     */
    private void setEvaluate(int evaluate) {
        environmental.setText(getResources().getString(R.string.environment, getResources().getString(evaluate)));
        service.setText(getResources().getString(R.string.service, getResources().getString(evaluate)));
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
        }
    }
}

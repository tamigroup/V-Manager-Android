package com.tami.vmanager.fragment;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.EvaluatePageRequestBean;
import com.tami.vmanager.entity.EvaluatePageResponseBean;
import com.tami.vmanager.entity.IdeasBoxRequestBean;
import com.tami.vmanager.entity.IdeasBoxResponsetBean;
import com.tami.vmanager.http.NetworkBroker;
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
public class HostFragment extends BaseFragment {

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
        networkBroker = new NetworkBroker(getContext());
    }

    @Override
    public void initListener() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getActivity(), R.color.percentage_10)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<EvaluatePageResponseBean.DataBean.ElementsBean>(getContext(), R.layout.item_fragment_host, listData) {

            @Override
            protected void convert(ViewHolder holder, EvaluatePageResponseBean.DataBean.ElementsBean elementsBean, int position) {
                ImageView header_image = holder.getView(R.id.header_image);
                if (elementsBean.getIconUrl() != null){
//                    Picasso.get().load(elementsBean.getIconUrl()).into(header_image);
                }

                holder.setText(R.id.header_name,elementsBean.getUserName());
                BaseRatingBar rating_bar = holder.getView(R.id.rating_bar);
                rating_bar.setRating(elementsBean.getScore());
                holder.setText(R.id.item_content,elementsBean.getContent());

            }
        };
        recyclerview.setAdapter(commonAdapter);
        pulltorefreshlayout.setCanRefresh(false);
    }

    @Override
    public void requestNetwork() {
        getAvg();
        getEvaluate();
    }

    /**
     * 获取评论
     */
    @SuppressLint("StringFormatMatches")
    private void getEvaluate() {
        EvaluatePageRequestBean evaluatePageRequestBean = new EvaluatePageRequestBean();
        evaluatePageRequestBean.setMeetingId(1);
        evaluatePageRequestBean.setType(2);
        evaluatePageRequestBean.setCurPage(CurPag++);
        evaluatePageRequestBean.setPageSize(10);
        networkBroker.ask(evaluatePageRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            EvaluatePageResponseBean response = (EvaluatePageResponseBean) res;
            if (response.getCode() == 200) {
                EvaluatePageResponseBean.DataBean data = response.getData();
                if (data.getElements() != null && data.getElements().size() > 0) {
                    int size = data.getElements().size();
                    comment.setText(String.format(getResources().getString(R.string.comment,data.getElements().size())));
                    listData.addAll(data.getElements());
                    commonAdapter.notifyDataSetChanged();
                }
                if (data.isLastPage()) {
                    pulltorefreshlayout.setCanLoadMore(false);
                }
            }
        });
    }

    /**
     * 获取综合打分
     */
    private void getAvg() {
        IdeasBoxRequestBean ideasBoxRequestBean = new IdeasBoxRequestBean();
        ideasBoxRequestBean.setMeetingId(1);
        ideasBoxRequestBean.setType(2);
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
                }
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

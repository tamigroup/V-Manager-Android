package com.tami.vmanager.activity;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.Database.AppDatabase;
import com.tami.vmanager.Database.dao.SearchHistoryDao;
import com.tami.vmanager.Database.entity.SearchHistoryBean;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SearchRequestBean;
import com.tami.vmanager.entity.SearchResponseBean;
import com.tami.vmanager.enums.SearchType;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by why on 2018/6/14.
 * 搜索
 */

public class SearchActivity extends BaseActivity {

    private AppCompatImageView search_back_btn;
    private SearchView searchView;
    private TextView search_make_content;
    private ConstraintLayout search_content_tv;
    private TextView search_history;
    private ConstraintLayout search_history_tv;
    private TextView search_content_tv1;
    private TextView search_content_tv2;
    private TextView search_content_tv3;
    private TextView search_content_tv4;
    private TextView search_content_tv5;
    private TextView search_content_tv6;
    private TextView search_content_tv7;
    private TextView search_content_tv8;
    private TextView search_history_tv1;
    private TextView search_history_tv2;
    private TextView search_history_tv3;
    private TextView search_history_tv4;
    private TextView search_history_tv5;
    private TextView search_history_tv6;
    private SearchHistoryDao dao;
    private SearchHistoryBean searchHistoryBean;
    private AppDatabase db;
    private View line;
    private int CurPage = 1;
    private NetworkBroker networkBroker;
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private List<SearchResponseBean.DataBean.DataListBean> listData;
    private CommonAdapter<SearchResponseBean.DataBean.DataListBean> commonAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        search_back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.searchView);
        line = findViewById(R.id.line);
        pullToRefreshLayout = findViewById(R.id.fml_PullToRefreshLayout);
        recyclerView = findViewById(R.id.fml_RecyclerView);

        search_make_content = findViewById(R.id.search_make_content);
        search_content_tv = findViewById(R.id.search_content_tv);
        search_history = findViewById(R.id.search_history);
        search_history_tv = findViewById(R.id.search_history_tv);

        search_content_tv1 = findViewById(R.id.search_content_tv1);
        search_content_tv2 = findViewById(R.id.search_content_tv2);
        search_content_tv3 = findViewById(R.id.search_content_tv3);
        search_content_tv4 = findViewById(R.id.search_content_tv4);
        search_content_tv5 = findViewById(R.id.search_content_tv5);
        search_content_tv6 = findViewById(R.id.search_content_tv6);
        search_content_tv7 = findViewById(R.id.search_content_tv7);
        search_content_tv8 = findViewById(R.id.search_content_tv8);

        search_history_tv1 = findViewById(R.id.search_history_tv1);
        search_history_tv2 = findViewById(R.id.search_history_tv2);
        search_history_tv3 = findViewById(R.id.search_history_tv3);
        search_history_tv4 = findViewById(R.id.search_history_tv4);
        search_history_tv5 = findViewById(R.id.search_history_tv5);
        search_history_tv6 = findViewById(R.id.search_history_tv6);

        db = AppDatabase.getInstance(getApplicationContext());
        dao = db.searchHistoryDao();
        searchHistoryBean = new SearchHistoryBean();
        networkBroker = new NetworkBroker(this);
    }

    @Override
    public void initListener() {
        search_back_btn.setOnClickListener(this);
        search_content_tv1.setOnClickListener(this);
        search_content_tv2.setOnClickListener(this);
        search_content_tv3.setOnClickListener(this);
        search_content_tv4.setOnClickListener(this);
        search_content_tv5.setOnClickListener(this);
        search_content_tv6.setOnClickListener(this);
        search_content_tv7.setOnClickListener(this);
        search_content_tv8.setOnClickListener(this);
    }


    @Override
    public void initData() {
        QueryData();
        searchView.setQueryHint("请输入关键字");
        searchView.setOnSearchClickListener(v -> SearchViewGone());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHistoryBean.setSearchHistory(query);
                new Thread(() -> dao.insert(searchHistoryBean)).start();
                RequestServer(query, SearchType.MEETING_NAME.getType());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        InitRecyc();

    }

    /**
     * 搜索界面隐藏
     */
    private void SearchViewGone() {
        search_make_content.setVisibility(View.GONE);
        search_content_tv.setVisibility(View.GONE);
        search_history.setVisibility(View.GONE);
        search_history_tv.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }

    /**
     * 设置recycleview
     */
    private void InitRecyc() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.percentage_10)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<SearchResponseBean.DataBean.DataListBean>(this, R.layout.item_search, listData) {

            @Override
            protected void convert(ViewHolder holder, SearchResponseBean.DataBean.DataListBean dataBean, int position) {
                holder.setText(R.id.item_meeting_name, dataBean.getMeetingName());

                MeetingStateView stateView = holder.getView(R.id.item_meeting_state);
                stateView.setMeetingStateText(dataBean.getMeetingStatus(), 20);

                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(dataBean.getStartTime()));
                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(dataBean.getStartTime()));
                holder.setText(R.id.item_meeting_room, dataBean.getMeetingAddress());

                AppCompatImageView imageView = holder.getView(R.id.item_meeting_level_icon1);
                if (dataBean.getFromPlat() == 1) {
                    Picasso.get().load(R.mipmap.meeting_level_zhi).into(imageView);
                } else {
                    Picasso.get().load(R.mipmap.meeting_level_vip1).into(imageView);
                }
            }
        };
    }

    /**
     * 请求服务器
     *
     * @param query      搜索的内容
     * @param searchType 请求的方式
     */
    private void RequestServer(String query, int searchType) {
        SearchRequestBean searchRequestBean = new SearchRequestBean();
        searchRequestBean.setKeyWord(query);
        searchRequestBean.setCurPage(CurPage++);
        searchRequestBean.setPageSize(10);
        searchRequestBean.setSearchType(searchType);
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            searchRequestBean.setUserId(String.valueOf(item.getId()));
        }
        networkBroker.ask(searchRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                RecycleViewShow();
                SearchResponseBean response = (SearchResponseBean) res;
                if (response.getCode() == 200) {
                    List<SearchResponseBean.DataBean.DataListBean> data = response.getData().getDataList();
                    if (data != null && data.size() > 0) {
                        listData.addAll(data);
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        //数据为空，显示空布局
                        Logger.e("data为空==" + data);
                    }
                    pullToRefreshLayout.finishLoadMore();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);
        pullToRefreshLayout.setCanLoadMore(false);
    }

    /**
     * 显示RecycleView
     */
    private void RecycleViewShow() {
        pullToRefreshLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏RecycleView
     */
    private void RecycleViewGone() {
        pullToRefreshLayout.setVisibility(View.GONE);
    }


    /**
     * 数据库查 搜索历史
     */
    @SuppressLint("CheckResult")
    private void QueryData() {
        dao.getSearchHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchHistoryBeans -> {
                    switch (searchHistoryBeans.size()) {
                        case 0:
                            search_history_tv.setVisibility(View.GONE);
                            break;
                        case 1:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 2:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());

                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 3:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv3.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());

                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());

                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });

                            break;
                        case 4:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv3.setVisibility(View.VISIBLE);
                            search_history_tv4.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());

                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());

                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());

                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 5:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
                                search_history_tv5.setText(searchHistoryBeans.get(4).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv3.setVisibility(View.VISIBLE);
                            search_history_tv4.setVisibility(View.VISIBLE);
                            search_history_tv5.setVisibility(View.VISIBLE);

                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());

                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());

                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());

                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv5.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(4).getSearchHistory());

                                RequestData(searchHistoryBeans.get(4).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 6:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
                                search_history_tv5.setText(searchHistoryBeans.get(4).getSearchHistory());
                                search_history_tv6.setText(searchHistoryBeans.get(5).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv3.setVisibility(View.VISIBLE);
                            search_history_tv4.setVisibility(View.VISIBLE);
                            search_history_tv5.setVisibility(View.VISIBLE);
                            search_history_tv6.setVisibility(View.VISIBLE);

                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());

                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());

                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());

                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());

                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());

                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv5.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(4).getSearchHistory());

                                RequestData(searchHistoryBeans.get(4).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv6.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(5).getSearchHistory());

                                RequestData(searchHistoryBeans.get(5).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                    }
                });
    }

    /**
     * 隐藏搜索，显示数据
     *
     * @param searchHistory 搜索的keyword
     * @param type          搜索类型
     */
    private void RequestData(String searchHistory, int type) {
        SearchViewGone();
        RecycleViewShow();
        RequestServer(searchHistory, type);
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
            case R.id.search_back_btn:
                finish();
                break;
            case R.id.search_content_tv1:
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv1, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv1);

                RequestData(SearchType.MEETING_NAME.getName(), SearchType.MEETING_NAME.getType());
                break;
            case R.id.search_content_tv2:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv2, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv2);

                RequestData(SearchType.V_MEETING.getName(), SearchType.V_MEETING.getType());
                break;
            case R.id.search_content_tv3:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv3, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv3);

                RequestData(SearchType.SALE_NAME.getName(), SearchType.SALE_NAME.getType());
                break;
            case R.id.search_content_tv4:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv4, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv4);

                RequestData(SearchType.MEETING_ADDRESS.getName(), SearchType.MEETING_ADDRESS.getType());
                break;
            case R.id.search_content_tv5:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv5, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv5);

                RequestData(SearchType.VIP1.getName(), SearchType.VIP1.getType());
                break;
            case R.id.search_content_tv6:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv6, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv6);

                RequestData(SearchType.VIP2.getName(), SearchType.VIP2.getType());
                break;
            case R.id.search_content_tv7:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
                setClickColor(search_content_tv7, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv7);

                RequestData(SearchType.VIP3.getName(), SearchType.VIP3.getType());
                break;
            case R.id.search_content_tv8:
                setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
                setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
                setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
                setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
                setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
                setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
                setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
                setClickColor(search_content_tv8, R.color.color_344266, R.drawable.shape_cricle_line, R.string.search_content_tv8);

                RequestData(SearchType.VIP4.getName(), SearchType.VIP4.getType());
                break;
        }
    }

    /**
     * 设置TextView点击颜色
     */
    private void setClickColor(TextView textView, int cor, int draw, int str) {
        setSearchView(textView, cor, draw);
        searchView.setQueryHint("搜索" + getResources().getString(str));
    }

    private void setClickColor(TextView textView, int cor, int draw, String str) {
        setSearchView(textView, cor, draw);
        searchView.setQueryHint("搜索" + str);
    }

    /**
     * 设置searchView
     */
    private void setSearchView(TextView textView, int cor, int draw) {
        textView.setTextColor(getResources().getColor(cor));
        textView.setBackgroundResource(draw);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
    }
}

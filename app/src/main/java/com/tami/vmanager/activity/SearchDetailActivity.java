package com.tami.vmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
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

/**
 * Created by Tang on 2018/7/13
 */
public class SearchDetailActivity extends BaseActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView recyclerView;
    private int CurPage =1;
    private NetworkBroker networkBroker;
    private List<SearchResponseBean.DataBean.DataListBean> listData;
    private CommonAdapter<SearchResponseBean.DataBean.DataListBean> commonAdapter;
    private static String SEARCH_QUERY = "search_query";
    private static String SEARCH_TYPE = "search_type";
    private static Intent intent;
    private ImageView search_back_btn;
    private SearchView searchView;
    private SearchHistoryBean searchHistoryBean;
    private AppDatabase db;
    private SearchHistoryDao dao;
    private TextView empty_tv;
    private String query;
    private int type;
    private boolean isLoadmore = false;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_detail;
    }

    @Override
    public void initView() {
        pullToRefreshLayout = findViewById(R.id.fml_PullToRefreshLayout);
        recyclerView = findViewById(R.id.fml_RecyclerView);
        search_back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.searchView_detail);
        empty_tv = findViewById(R.id.empty_tv);
        networkBroker = new NetworkBroker(this);
        db = AppDatabase.getInstance(getApplicationContext());
        dao = db.searchHistoryDao();
        searchHistoryBean = new SearchHistoryBean();
    }


    public static void Start(Context context, String query, int type) {
        intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra(SEARCH_QUERY, query);
        intent.putExtra(SEARCH_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public void initListener() {
        search_back_btn.setOnClickListener(v -> finish());
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

            }

            @Override
            public void loadMore() {
                isLoadmore = true;
                RequestServer(query, type);
            }
        });
    }

    @Override
    public void initData() {
        InitRecyc();
        searchView.setQueryHint(getString(R.string.enter_keyword));
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQueryHint("");
                searchHistoryBean.setSearchHistory(query);
                new Thread(() -> dao.insert(searchHistoryBean)).start();
                CurPage = 1;
                SearchDetailActivity.this.query = query;
                SearchDetailActivity.this.type = type;
                RequestServer(query, SearchType.MEETING_NAME.getType());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(dataBean.getStartTime(),TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(dataBean.getStartTime(),TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                holder.setText(R.id.item_meeting_room, dataBean.getMeetingAddress());
                holder.setText(R.id.item_meeting_perfected, dataBean.getPerfectStatus());
                holder.setText(R.id.item_meeting_cancel, dataBean.getCancelStatus());

                AppCompatImageView vipImageView = holder.getView(R.id.item_meeting_level_icon);
                vipImageView.setVisibility(dataBean.getIsImportant() == 0 ? View.GONE : View.VISIBLE);
                vipImageView.setImageResource(getImageResId(dataBean.getIsImportant()));

                AppCompatImageView imageView1 = holder.getView(R.id.item_meeting_level_icon1);
                imageView1.setVisibility(dataBean.getFromPlat() == 1 ? View.VISIBLE : View.GONE);


            }
        };
    }

    /**
     * 获取图片资源ID
     *
     * @param vipId
     */
    private int getImageResId(int vipId) {
        int id = R.mipmap.meeting_level_vip1;
        switch (vipId) {
            case 1:
                id = R.mipmap.meeting_level_vip1;
                break;
            case 2:
                id = R.mipmap.meeting_level_vip2;
                break;
            case 3:
                id = R.mipmap.meeting_level_vip3;
                break;
            case 4:
                id = R.mipmap.meeting_level_vip4;
                break;
        }
        return id;
    }

    @Override
    public void requestNetwork() {
        String query = intent.getStringExtra(SEARCH_QUERY);
        int type = intent.getIntExtra(SEARCH_TYPE, SearchType.MEETING_NAME.getType());
        searchView.setQueryHint(getString(R.string.search) + query);
        this.query = query;
        this.type = type;
        RequestServer(query, type);
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

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
        if (isLoadmore) {
            searchRequestBean.setCurPage(CurPage++);
        } else {
            searchRequestBean.setCurPage(CurPage);
        }
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
                SearchResponseBean response = (SearchResponseBean) res;
                if (response.getCode() == 200) {
                    List<SearchResponseBean.DataBean.DataListBean> data = response.getData().getDataList();
                    if (data != null && data.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_tv.setVisibility(View.GONE);
                        if (!isLoadmore) {
                            listData.clear();
                        }
                        isLoadmore = false;
                        listData.addAll(data);
                        recyclerView.scrollToPosition(data.size());
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        empty_tv.setVisibility(View.VISIBLE);
                        empty_tv.setText(String.format(getString(R.string.empty_search), query));
                    }
                }
                pullToRefreshLayout.finishLoadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);
    }
}

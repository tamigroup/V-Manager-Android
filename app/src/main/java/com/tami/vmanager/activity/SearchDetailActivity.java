package com.tami.vmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tami.pulltorefresh.BaseRefreshListener;
import com.tami.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.Database.AppDatabase;
import com.tami.vmanager.Database.dao.SearchHistoryDao;
import com.tami.vmanager.Database.entity.SearchHistoryBean;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.FollowUserMeetingRequest;
import com.tami.vmanager.entity.FollowUserMeetingResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SearchRequestBean;
import com.tami.vmanager.entity.SearchResponseBean;
import com.tami.vmanager.enums.SearchType;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.SpUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tang on 2018/7/13
 */
public class SearchDetailActivity extends BaseActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView recyclerView;
    private int CurPage = 1;
    private NetworkBroker networkBroker;
    private List<SearchResponseBean.DataBean.DataListBean> listData;
    private CommonAdapter<SearchResponseBean.DataBean.DataListBean> commonAdapter;
    private static String SEARCH_QUERY = "search_query";
    private static String SEARCH_TYPE = "search_type";
    private static String SEARCH_CONTENT = "search_content";
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
    private int userId;
    private String search_history_tv1_text;
    private String search_history_tv2_text;
    private String search_history_tv3_text;
    private String search_history_tv4_text;
    private String search_history_tv5_text;
    private String search_history_tv6_text;
    private int screenWidth;

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


    public static void Start(Context context, String query, int type, boolean isContent) {
        intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra(SEARCH_QUERY, query);
        intent.putExtra(SEARCH_TYPE, type);
        intent.putExtra(SEARCH_CONTENT, isContent);
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
                RequestServer(query, type, false);
            }
        });
    }

    @Override
    public void initData() {
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (null != item) {
            userId = item.getId();
        }
        screenWidth = ScreenUtil.getScreenWidth(this);
        List<SearchHistoryBean> searchHistoryBeans = SpUtil.getList(this, "searchList");
        search_history_tv1_text = (String) SPUtils.get(this, "search_history_tv1_text", "");
        search_history_tv2_text = (String) SPUtils.get(this, "search_history_tv2_text", "");
        search_history_tv3_text = (String) SPUtils.get(this, "search_history_tv3_text", "");
        search_history_tv4_text = (String) SPUtils.get(this, "search_history_tv4_text", "");
        search_history_tv5_text = (String) SPUtils.get(this, "search_history_tv5_text", "");
        search_history_tv6_text = (String) SPUtils.get(this, "search_history_tv6_text", "");
        InitRecyc();
        searchView.setQueryHint(getString(R.string.enter_keyword));
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQueryHint("");
                int search_type = (int) SPUtils.get(SearchDetailActivity.this, SEARCH_TYPE, SearchType.MEETING_NAME.getType());
                if (searchHistoryBeans != null) {
                    int size = searchHistoryBeans.size();
                    switch (size) {
                        case 0:
                            insertData(query,search_type);
                            break;
                        case 1:
                            if (!query.equals(SearchDetailActivity.this.search_history_tv1_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                        case 2:
                            if (!TextUtils.isEmpty(SearchDetailActivity.this.search_history_tv1_text) && !query.equals(SearchDetailActivity.this.search_history_tv1_text)
                                    && !TextUtils.isEmpty(search_history_tv2_text) && !query.equals(search_history_tv2_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                        case 3:
                            if (!TextUtils.isEmpty(SearchDetailActivity.this.search_history_tv1_text) && !query.equals(SearchDetailActivity.this.search_history_tv1_text)
                                    && !TextUtils.isEmpty(search_history_tv2_text) && !query.equals(search_history_tv2_text)
                                    && !TextUtils.isEmpty(search_history_tv3_text) && !query.equals(search_history_tv3_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                        case 4:
                            if (!TextUtils.isEmpty(SearchDetailActivity.this.search_history_tv1_text) && !query.equals(SearchDetailActivity.this.search_history_tv1_text)
                                    && !TextUtils.isEmpty(search_history_tv2_text) && !query.equals(search_history_tv2_text)
                                    && !TextUtils.isEmpty(search_history_tv3_text) && !query.equals(search_history_tv3_text)
                                    && !TextUtils.isEmpty(search_history_tv4_text) && !query.equals(search_history_tv4_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                        case 5:
                            if (!TextUtils.isEmpty(SearchDetailActivity.this.search_history_tv1_text) && !query.equals(SearchDetailActivity.this.search_history_tv1_text)
                                    && !TextUtils.isEmpty(search_history_tv2_text) && !query.equals(search_history_tv2_text)
                                    && !TextUtils.isEmpty(search_history_tv3_text) && !query.equals(search_history_tv3_text)
                                    && !TextUtils.isEmpty(search_history_tv4_text) && !query.equals(search_history_tv4_text)
                                    && !TextUtils.isEmpty(search_history_tv5_text) && !query.equals(search_history_tv5_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                        case 6:
                            if (!TextUtils.isEmpty(SearchDetailActivity.this.search_history_tv1_text) && !query.equals(SearchDetailActivity.this.search_history_tv1_text)
                                    && !TextUtils.isEmpty(search_history_tv2_text) && !query.equals(search_history_tv2_text)
                                    && !TextUtils.isEmpty(search_history_tv3_text) && !query.equals(search_history_tv3_text)
                                    && !TextUtils.isEmpty(search_history_tv4_text) && !query.equals(search_history_tv4_text)
                                    && !TextUtils.isEmpty(search_history_tv5_text) && !query.equals(search_history_tv5_text)
                                    && !TextUtils.isEmpty(search_history_tv6_text) && !query.equals(search_history_tv6_text)) {
                                insertData(query,search_type);
                            } else {
                                request(query,search_type);
                            }
                            break;
                    }
                } else {
                    insertData(query,search_type);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void insertData(String query,int searcType) {
        searchHistoryBean.setUserId(userId);
        searchHistoryBean.setSearchHistory(query);
        new Thread(() -> dao.insert(searchHistoryBean)).start();
        request(query,searcType);
    }

    private void request(String query,int searcType) {
        CurPage = 1;
//        SearchDetailActivity.this.query = query;
//        SearchDetailActivity.this.type = type;
        RequestServer(query, searcType, false);
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
                TextView nameView = holder.getView(R.id.item_meeting_name);
                setNameTextLayoutParams(nameView, dataBean.getMeetingName());

                MeetingStateView stateView = holder.getView(R.id.item_meeting_state);
                if (TextUtils.isEmpty(dataBean.getMeetingStatus())) {
                    stateView.setMeetingStateText(getString(R.string.daiban), 20);
                } else {
                    stateView.setMeetingStateText(dataBean.getMeetingStatus(), 20);
                }
                if (TextUtils.isEmpty(dataBean.getCancelStatus())) {
                    stateView.setVisibility(View.VISIBLE);
                } else {
                    stateView.setVisibility(View.INVISIBLE);
                }
                StringBuilder time = new StringBuilder();
                String startTime = TimeUtils.milliseconds2String(dataBean.getStartTime(), TimeUtils.DATE_YYYYMMDDHHMM_SLASH);
                time.append(startTime);
                time.append(" - ");
                String endTime = TimeUtils.milliseconds2String(dataBean.getEndTime(), TimeUtils.DATE_YYYYMMDDHHMM_SLASH);
                time.append(endTime);
                holder.setText(R.id.item_meeting_start_time, time.toString());
                //                holder.setText(R.id.item_meeting_start_time,dataBean.getAutoDayTime());
                //                holder.setText(R.id.item_meeting_start_time, TimeUtils.milliseconds2String(dataBean.getStartTime(),TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                //                holder.setText(R.id.item_meeting_end_time, TimeUtils.milliseconds2String(dataBean.getStartTime(),TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
                holder.setText(R.id.item_meeting_room, dataBean.getMeetingAddress());
                holder.setText(R.id.item_meeting_perfected, dataBean.getPerfectStatus());
                holder.setText(R.id.item_meeting_cancel, dataBean.getCancelStatus());

                AppCompatImageView vipImageView = holder.getView(R.id.item_meeting_level_icon);
                vipImageView.setVisibility((dataBean.getIsImportant() == 0 || dataBean.getIsImportant() == 10) ? View.GONE : View.VISIBLE);
                vipImageView.setImageResource(getImageResId(dataBean.getIsImportant()));

                AppCompatImageView imageView1 = holder.getView(R.id.item_meeting_level_icon1);
                imageView1.setVisibility(dataBean.getIsVzh() == 1 ? View.VISIBLE : View.GONE);

                //关注
                final TextView follow = holder.getView(R.id.item_meeting_follow);
                followOnClick(follow, dataBean.getMeetingStatus(), dataBean.getFollowStatus());
                follow.setOnClickListener((View v) -> {
                    followUserMeeting(follow, dataBean);
                });
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(SearchDetailActivity.this, MeetingOverviewActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, listData.get(position).getMeetingId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(commonAdapter);
        pullToRefreshLayout.setCanRefresh(false);
    }


    /**
     * 测量会议名称的长度
     * @param nameView
     * @param content
     */
    private void setNameTextLayoutParams(TextView nameView, String content) {
        setLayoutParams(nameView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        nameView.setText(content);
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        nameView.measure(spec, spec);
        int measuredWidthTicketNum = nameView.getMeasuredWidth();
        int maxWidth = ScreenUtil.dip2px(this, (screenWidth - 160));
        if (measuredWidthTicketNum > maxWidth) {
            setLayoutParams(nameView, maxWidth);
        }
    }

    /**
     * 重新赋值长度
     * @param nameView
     * @param value
     */
    private void setLayoutParams(TextView nameView, int value) {
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
        layoutParams1.width = value;
        nameView.setLayoutParams(layoutParams1);
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
        boolean isContent = intent.getBooleanExtra(SEARCH_CONTENT, false);
        searchView.setQueryHint(getString(R.string.search) + query);
        this.query = query;
        this.type = type;
        RequestServer(query, type, isContent);
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (listData != null) {
            listData.clear();
            listData = null;
        }
        searchHistoryBean = null;
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    /**
     * 请求服务器
     *
     * @param query      搜索的内容
     * @param searchType 请求的方式
     * @param isContent
     */
    private void RequestServer(String query, int searchType, boolean isContent) {
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
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        empty_tv.setVisibility(View.VISIBLE);
                        empty_tv.setText(String.format(getString(R.string.empty_search), query));
                        if (isContent) {
                            empty_tv.setVisibility(View.GONE);
                        }
                    }
                }
                pullToRefreshLayout.finishLoadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 关注/取消关注会议
     */
    private void followUserMeeting(TextView follow, SearchResponseBean.DataBean.DataListBean onClickItem) {
        FollowUserMeetingRequest followUserMeetingRequest = new FollowUserMeetingRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            followUserMeetingRequest.setUserId(String.valueOf(item.getId()));
        }
        followUserMeetingRequest.setMeetingId(String.valueOf(onClickItem.getMeetingId()));
        followUserMeetingRequest.setRequsetUrl(onClickItem.getFollowStatus() == 0 ? HttpKey.FOLLOW_USER_MEETING : HttpKey.CANCEL_USER_MEETING);
        networkBroker.ask(followUserMeetingRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                FollowUserMeetingResponse response = (FollowUserMeetingResponse) res;
                if (response.getCode() == 200) {
                    if (response.data) {
                        onClickItem.setFollowStatus(onClickItem.getFollowStatus() == 1 ? 0 : 1);
                        followOnClick(follow, onClickItem.getMeetingStatus(), onClickItem.getFollowStatus());
                        if (onClickItem.getFollowStatus() == 1) {
                            showToast(getString(R.string.attention_prompt, getString(R.string.success)));
                        } else {
                            showToast(getString(R.string.attention_cancel_prompt, getString(R.string.success)));
                        }
                    }
                } else {
                    if (onClickItem.getFollowStatus() == 0) {
                        showToast(getString(R.string.attention_prompt, getString(R.string.failure)));
                    } else {
                        showToast(getString(R.string.attention_cancel_prompt, getString(R.string.failure)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 设置关注点击效果
     *
     * @param follow
     * @param status
     */
    private void followOnClick(TextView follow, String meetingStatus, int status) {
        if (getString(R.string.finished).equals(meetingStatus)) {
            follow.setEnabled(false);
            follow.setText(getString(R.string.attention));
            follow.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
            follow.setBackgroundResource(R.drawable.item_meeting_follow_unselected);
        } else {
            follow.setEnabled(true);
            if (status == 1) {
                follow.setText(getString(R.string.yi_attention));
                follow.setTextColor(ContextCompat.getColor(this, R.color.color_FF5657));
                follow.setBackgroundResource(R.drawable.item_meeting_follow_selected);
            } else {
                follow.setText(getString(R.string.attention));
                follow.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
                follow.setBackgroundResource(R.drawable.item_meeting_follow_unselected1);
            }
        }
    }




}

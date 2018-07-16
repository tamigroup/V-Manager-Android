package com.tami.vmanager.activity;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.tami.vmanager.Database.AppDatabase;
import com.tami.vmanager.Database.dao.SearchHistoryDao;
import com.tami.vmanager.Database.entity.SearchHistoryBean;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.enums.SearchType;

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
    private ConstraintLayout search_content_tv;
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
    private Group search_group;
    private Group search_content_group;
    private List<SearchHistoryBean> searchHistoryBeans;
    private TextView search_make_content;
    private TextView search_history;
    private View line;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        search_back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.searchView);

        search_content_tv = findViewById(R.id.search_content_tv);
        search_history_tv = findViewById(R.id.search_history_tv);
        search_make_content = findViewById(R.id.search_make_content);
        search_history = findViewById(R.id.search_history);
        line = findViewById(R.id.line);

        search_content_tv1 = findViewById(R.id.search_content_tv1);
        search_content_tv2 = findViewById(R.id.search_content_tv2);
        search_content_tv3 = findViewById(R.id.search_content_tv3);
        search_content_tv4 = findViewById(R.id.search_content_tv4);
        search_content_tv5 = findViewById(R.id.search_content_tv5);
        search_content_tv6 = findViewById(R.id.search_content_tv6);
        search_content_tv7 = findViewById(R.id.search_content_tv7);
        search_content_tv8 = findViewById(R.id.search_content_tv8);
        search_content_group = findViewById(R.id.search_content_group);

        search_history_tv1 = findViewById(R.id.search_history_tv1);
        search_history_tv2 = findViewById(R.id.search_history_tv2);
        search_history_tv3 = findViewById(R.id.search_history_tv3);
        search_history_tv4 = findViewById(R.id.search_history_tv4);
        search_history_tv5 = findViewById(R.id.search_history_tv5);
        search_history_tv6 = findViewById(R.id.search_history_tv6);
        search_group = findViewById(R.id.search_group);

        db = AppDatabase.getInstance(getApplicationContext());
        dao = db.searchHistoryDao();
        searchHistoryBean = new SearchHistoryBean();
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
        searchView.setQueryHint(getString(R.string.enter_keyword));
        searchView.setOnSearchClickListener(v -> {
            search_group.setVisibility(View.GONE);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHistoryBean.setSearchHistory(query);
                new Thread(() -> dao.insert(searchHistoryBean)).start();
                SearchDetailActivity.Start(SearchActivity.this,query,SearchType.MEETING_NAME.getType());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        search_group.setVisibility(View.VISIBLE);
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
                            this.searchHistoryBeans = searchHistoryBeans;
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 2:
                            this.searchHistoryBeans = searchHistoryBeans;
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                            }
                            search_history_tv1.setVisibility(View.VISIBLE);
                            search_history_tv2.setVisibility(View.VISIBLE);
                            search_history_tv1.setOnClickListener(v -> {
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv1, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(0).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 3:
                            this.searchHistoryBeans = searchHistoryBeans;
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
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });

                            break;
                        case 4:
                            this.searchHistoryBeans = searchHistoryBeans;
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
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 5:
                            this.searchHistoryBeans = searchHistoryBeans;
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
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv5.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(4).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(4).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            break;
                        case 6:
                            this.searchHistoryBeans = searchHistoryBeans;
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
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(0).getSearchHistory(), SearchType.MEETING_NAME.getType());

                            });
                            search_history_tv2.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(1).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(1).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv3.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(2).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(2).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv4.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(3).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(3).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv5.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(4).getSearchHistory());
                                setContentClickColor();
                                RequestData(searchHistoryBeans.get(4).getSearchHistory(), SearchType.MEETING_NAME.getType());
                            });
                            search_history_tv6.setOnClickListener(v -> {
                                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                                setClickColor(search_history_tv6, R.color.color_344266, R.drawable.shape_cricle_line, searchHistoryBeans.get(5).getSearchHistory());
                                setContentClickColor();
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
        SearchDetailActivity.Start(this,searchHistory,type);
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
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
                setHistoryClickColor();
                RequestData(SearchType.VIP4.getName(), SearchType.VIP4.getType());
                break;
        }
    }

    /**
     * 设置TextView点击颜色
     */
    private void setClickColor(TextView textView, int cor, int draw, int str) {
        setSearchView(textView, cor, draw);
//        searchView.setQueryHint("搜索" + getResources().getString(str));
    }

    private void setClickColor(TextView textView, int cor, int draw, String str) {
        setSearchView(textView, cor, draw);
//        searchView.setQueryHint("搜索" + str);
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

    private void setHistoryClickColor(){
        if (searchHistoryBeans == null){
            return;
        }
        switch (searchHistoryBeans.size()) {
            case 0:
                break;
            case 1:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                break;
            case 2:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                break;
            case 3:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                break;
            case 4:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                break;
            case 5:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                break;
            case 6:
                setClickColor(search_history_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(0).getSearchHistory());
                setClickColor(search_history_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(1).getSearchHistory());
                setClickColor(search_history_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(2).getSearchHistory());
                setClickColor(search_history_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(3).getSearchHistory());
                setClickColor(search_history_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(4).getSearchHistory());
                setClickColor(search_history_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, searchHistoryBeans.get(5).getSearchHistory());
                break;
        }
    }

    private void setContentClickColor(){
        setClickColor(search_content_tv1, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv1);
        setClickColor(search_content_tv2, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv2);
        setClickColor(search_content_tv3, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv3);
        setClickColor(search_content_tv4, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv4);
        setClickColor(search_content_tv5, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv5);
        setClickColor(search_content_tv6, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv6);
        setClickColor(search_content_tv7, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv7);
        setClickColor(search_content_tv8, R.color.color_E5E5E5, R.drawable.shape_cricle_gray_line, R.string.search_content_tv8);
    }
}

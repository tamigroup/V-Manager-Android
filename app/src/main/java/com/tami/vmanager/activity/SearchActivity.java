package com.tami.vmanager.activity;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.tami.vmanager.Database.AppDatabase;
import com.tami.vmanager.Database.dao.SearchHistoryDao;
import com.tami.vmanager.Database.entity.SearchHistoryBean;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by why on 2018/6/14.
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

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        search_back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.searchView);
        line = findViewById(R.id.line);
        search_make_content = findViewById(R.id.search_make_content);
        search_content_tv = findViewById(R.id.search_content_tv);
        search_history = findViewById(R.id.search_history);
        search_history_tv = findViewById(R.id.search_history_tv);

        search_content_tv1 = findViewById(R.id.search_content_tv1);
        search_content_tv2 = findViewById(R.id.search_content_tv2);
        search_content_tv3 = findViewById(R.id.search_content_tv3);
        search_content_tv4 = findViewById(R.id.search_content_tv4);
        search_content_tv5 = findViewById(R.id.search_content_tv5);

        search_history_tv1 = findViewById(R.id.search_history_tv1);
        search_history_tv2 = findViewById(R.id.search_history_tv2);
        search_history_tv3 = findViewById(R.id.search_history_tv3);
        search_history_tv4 = findViewById(R.id.search_history_tv4);
//        search_history_tv5 = findViewById(R.id.search_history_tv5);
//        search_history_tv6 = findViewById(R.id.search_history_tv6);

        db = AppDatabase.getInstance(getApplicationContext());
        dao = db.searchHistoryDao();
        searchHistoryBean = new SearchHistoryBean();
    }

    @Override
    public void initListener() {
        search_back_btn.setOnClickListener(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
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
                            break;
                        case 2:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                            }
                            break;
                        case 3:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                            }
                            break;
                        case 4:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
                            }
                            break;
                        case 5:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
//                                search_history_tv5.setText(searchHistoryBeans.get(4).getSearchHistory());
                            }
                            break;
                        case 6:
                            for (int i = 0; i < searchHistoryBeans.size(); i++) {
                                search_history_tv1.setText(searchHistoryBeans.get(0).getSearchHistory());
                                search_history_tv2.setText(searchHistoryBeans.get(1).getSearchHistory());
                                search_history_tv3.setText(searchHistoryBeans.get(2).getSearchHistory());
                                search_history_tv4.setText(searchHistoryBeans.get(3).getSearchHistory());
//                                search_history_tv5.setText(searchHistoryBeans.get(4).getSearchHistory());
//                                search_history_tv6.setText(searchHistoryBeans.get(5).getSearchHistory());
                            }
                            break;
                    }
                });

        searchView.setOnSearchClickListener(v -> {
            search_make_content.setVisibility(View.GONE);
            search_content_tv.setVisibility(View.GONE);
            search_history.setVisibility(View.GONE);
            search_history_tv.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHistoryBean.setSearchHistory(query);
                new Thread(() -> dao.insert(searchHistoryBean)).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        }
    }
}

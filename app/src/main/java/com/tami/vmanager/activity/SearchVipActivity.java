package com.tami.vmanager.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.Database.AppDatabase;
import com.tami.vmanager.Database.dao.SearchVipHistoryDao;
import com.tami.vmanager.Database.entity.SearchVipHistoryBean;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tang on 2018/7/7
 * 搜索vip
 */
public class SearchVipActivity extends BaseActivity {

    private SearchView searchView;
    private TextView cancel;
    private RecyclerView recyclerView;
    private List<SearchVipHistoryBean> data = new ArrayList<>();
    private TextView search_history;
    private TextView no_search;
    private AppDatabase db;
    private SearchVipHistoryBean searchVipHistoryBean;
    private SearchVipHistoryDao dao;

    @Override
    public int getContentViewId() {
        return R.layout.activity_searchvip;
    }

    @Override
    public void initView() {
        searchView = findViewById(R.id.search);
        search_history = findViewById(R.id.search_history);
        cancel = findViewById(R.id.cancel);
        recyclerView = findViewById(R.id.recyc);
        no_search = findViewById(R.id.no_search);

        db = AppDatabase.getInstance(getApplicationContext());
        dao = db.searchVipHistoryDao();
        searchVipHistoryBean = new SearchVipHistoryBean();
    }

    public static void Start(Context context) {
        context.startActivity(new Intent(context, SearchVipActivity.class));
    }


    @Override
    public void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchVipHistoryBean.setSearchVipHistory(query);
                new Thread(() -> dao.insert(searchVipHistoryBean)).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void initData() {
        QueryData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(this, R.color.percentage_10)));
        //        listData = new ArrayList<>();
        CommonAdapter<SearchVipHistoryBean> commonAdapter = new CommonAdapter<SearchVipHistoryBean>(this, R.layout.item_searchvip, data) {
            @Override
            protected void convert(ViewHolder holder, SearchVipHistoryBean s, int position) {
                holder.setText(R.id.name, s.getSearchVipHistory());
            }
        };
        commonAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(commonAdapter);
    }

    /**
     * 查询数据库
     */
    @SuppressLint("CheckResult")
    private void QueryData() {
        dao.getSearchVipHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchVipHistoryBeans -> {
                    for (SearchVipHistoryBean data : searchVipHistoryBeans) {
                        Logger.e("data==" + data.getSearchVipHistory());
                    }
                    data.addAll(searchVipHistoryBeans);
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
            case R.id.cancel:
                dao.delete(searchVipHistoryBean);
                break;
        }
    }
}

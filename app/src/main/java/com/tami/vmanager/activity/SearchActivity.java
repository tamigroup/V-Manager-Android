package com.tami.vmanager.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Logger;

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

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        search_back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.searchView);
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
        search_history_tv5 = findViewById(R.id.search_history_tv5);
        search_history_tv6 = findViewById(R.id.search_history_tv6);
    }

    @Override
    public void initListener() {
        search_back_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        searchView.setOnSearchClickListener(v -> {
            search_make_content.setVisibility(View.GONE);
            search_content_tv.setVisibility(View.GONE);
            search_history.setVisibility(View.GONE);
            search_history_tv.setVisibility(View.GONE);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                //请求网络
                Logger.e("searchView is onQueryTextSubmit+++");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入框内容改变
                Logger.e("searchView is onQueryTextChange-----");
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
                this.onBackPressed();
                break;
        }
    }
}

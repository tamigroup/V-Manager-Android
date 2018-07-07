package com.tami.vmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tang on 2018/7/7
 * 搜索vip
 */
public class SearchVipActivity extends BaseActivity {

    private SearchView searchView;
    private TextView cancel;
    private RecyclerView recyclerView;
    private List<String> data = Arrays.asList("马云", "马化腾", "丽丽", "tony");

    @Override
    public int getContentViewId() {
        return R.layout.activity_searchvip;
    }

    @Override
    public void initView() {
        searchView = findViewById(R.id.search);
        cancel = findViewById(R.id.cancel);
        recyclerView = findViewById(R.id.recyc);

    }

    public static void Start(Context context) {
        context.startActivity(new Intent(context, SearchVipActivity.class));
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(this, R.color.percentage_10)));
        //        listData = new ArrayList<>();
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item_searchvip, data) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.name, s);
            }
        };
        recyclerView.setAdapter(commonAdapter);
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

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("马云");
        data.add("马化腾");
        data.add("马云");
        data.add("马云");
        return data;
    }
}

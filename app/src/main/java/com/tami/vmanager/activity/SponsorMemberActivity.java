package com.tami.vmanager.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 主办方成员
 * Created by why on 2018/6/19.
 */
public class SponsorMemberActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sponsor_member;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyc);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        setTitleName(R.string.sponsor_member);
        initRecyc();
    }

    private void initRecyc() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);

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
}

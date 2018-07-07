package com.tami.vmanager.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 主办方成员
 * Created by why on 2018/6/19.
 */
public class SponsorMemberActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<String> data = Arrays.asList("汤姆", "汤姆迅", "汤姆", "汤姆迅", "汤姆", "汤姆迅", "tom", "tony", "jack", "mayun", "laoli", "hh");

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
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(layoutManager);
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item_activity_sponsor_member, data) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_name, s);
            }
        };
        commonAdapter.notifyDataSetChanged();
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
}

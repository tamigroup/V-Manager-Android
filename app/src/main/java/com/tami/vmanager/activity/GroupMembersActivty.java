package com.tami.vmanager.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 群成员
 * Created by why on 2018/7/5.
 */
public class GroupMembersActivty extends BaseActivity {

    private RecyclerView recyclerView;
    private TextView load_more;
    private ConstraintLayout group_notice;
    private TextView out_meeting;
    private List<String> data = Arrays.asList("汤姆", "汤姆迅", "汤姆", "汤姆迅", "汤姆", "汤姆迅", "tom", "tony");
    private CommonAdapter<String> commonAdapter;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_members;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyc);
        load_more = findViewById(R.id.load_more);
        group_notice = findViewById(R.id.group_notice);
        out_meeting = findViewById(R.id.out_meeting);

    }

    @Override
    public void initListener() {
        load_more.setOnClickListener(this);
        group_notice.setOnClickListener(this);
        out_meeting.setOnClickListener(this);

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.group_members));
        initRecyc();
    }

    private void initRecyc() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        if (data.size() > 10) {
            load_more.setVisibility(View.VISIBLE);
            List<String> sub_data = data.subList(0, 10);
            setCommondAdapter(sub_data);
        } else {
            load_more.setVisibility(View.GONE);
            setCommondAdapter(data);
        }

    }

    private void setCommondAdapter(List<String> sub_data) {
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_activity_sponsor_member, sub_data) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_name, s);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.load_more:
                Logger.e("load_more==");
                load_more.setVisibility(View.GONE);
                setCommondAdapter(data);
                break;
            case R.id.out_meeting:
                finish();
                break;
            case R.id.group_notice:
                //跳转
                break;
        }
    }
}

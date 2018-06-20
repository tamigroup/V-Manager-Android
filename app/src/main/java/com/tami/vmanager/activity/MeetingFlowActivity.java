package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.TimeUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议流程
 * Created by why on 2018/6/15.
 */
public class MeetingFlowActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_flow;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.meeting_flow_recyclerview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(R.string.flow_chart);

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(this, R.color.percentage_10)));

        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_meeting_flow, getData()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }

            @Override
            public void convert(ViewHolder holder, final String s) {
                //赋值
                TextView timeView = holder.getView(R.id.meeting_flow_time);
                timeView.setText(s);
                timeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MeetingFlowEditActivity.class);
                        intent.putExtra(Constants.KEY_DAY, s);
                        startActivity(intent);
                    }
                });
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

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 5; i >= 0; i--) {
            data.add(TimeUtils.milliseconds2FormatString(System.currentTimeMillis() - (i * Constants.DAY), TimeUtils.DATE_YYYYMMHH_SLASH));
        }
        return data;
    }
}

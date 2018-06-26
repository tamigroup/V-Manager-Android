package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.adapter.ViewHolder;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建服务流程
 * Created by why on 2018/6/26.
 */
public class CreateServiceFlowActivity extends BaseActivity {

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_service_flow;
    }

    private Button submission;
    private ListView listView;
    private ListViewAdapter<String> listViewAdapter;
    private List<String> data;
    private Space space;

    @Override
    public void initView() {
        submission = findViewById(R.id.acsf_submission);
        listView = findViewById(R.id.acsf_listView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String day = null;
        Intent intent = getIntent();
        if (intent != null) {
            day = intent.getStringExtra(Constants.KEY_DAY);
        }
        setTitleName(R.string.create_service_process);
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        TextView headerView = (TextView) layoutInflater.inflate(R.layout.head_meeting_flow_edit, null, false);
        headerView.setText(day);
        listView.addHeaderView(headerView);
        ConstraintLayout footerView = (ConstraintLayout) layoutInflater.inflate(R.layout.footer_create_service_flow, null, false);
        footerView.findViewById(R.id.fcsf_add_process);
        space = footerView.findViewById(R.id.fcsf_space);
        footerView.setOnClickListener(this);
        listView.addFooterView(footerView);
        data = new ArrayList<String>();
        listViewAdapter = new ListViewAdapter<String>(getApplicationContext(), data, R.layout.item_create_service_flow) {
            @Override
            protected void binView(ViewHolder viewHolder, String item, int position) {

            }
        };
        listView.setAdapter(listViewAdapter);
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
            case R.id.fcsf_add_process_layout:
                showToast("123456");
                data.add("123456");
                listViewAdapter.notifyDataSetChanged();
                if (data.size() > 0) {
                    space.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}

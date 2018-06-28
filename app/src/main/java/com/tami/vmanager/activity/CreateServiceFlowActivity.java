package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
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
import com.tami.vmanager.entity.GetMeetingItemsResponse;
import com.tami.vmanager.utils.Constants;
import java.util.ArrayList;
import java.util.Collections;
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
    private ListViewAdapter<GetMeetingItemsResponse.ElementDataList> listViewAdapter;
    private List<GetMeetingItemsResponse.ElementDataList> data;
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
        data = new ArrayList<>();
        listViewAdapter = new ListViewAdapter<GetMeetingItemsResponse.ElementDataList>(getApplicationContext(), data, R.layout.item_create_service_flow) {
            @Override
            protected void binView(ViewHolder viewHolder, GetMeetingItemsResponse.ElementDataList item, int position) {
                viewHolder.setTextView(R.id.icsf_line_number, String.valueOf(position + 1));
                viewHolder.setTextView(R.id.icsf_name, "name" + position);
                viewHolder.setTextView(R.id.icsf_editing_time, item.getCreateOn());
                AppCompatImageView delete = viewHolder.getItemView(R.id.icsf_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("123456");
                    }
                });
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

    String[] str = new String[]{"10:10","05:20","09:10","03:20","00:10","05:21","13:20"};
    int i = 0;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fcsf_add_process_layout:
                GetMeetingItemsResponse.ElementDataList item = new GetMeetingItemsResponse.ElementDataList();
                item.setCreateOn(str[i++]);
                data.add(item);
                Collections.sort(data);
                listViewAdapter.notifyDataSetChanged();
                if (data.size() > 0) {
                    space.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}

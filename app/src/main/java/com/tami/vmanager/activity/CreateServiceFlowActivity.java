package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingItemsResponse;
import com.tami.vmanager.utils.Constants;
import com.zhy.adapter.recyclerview.CommonAdapter;

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
    private ListViewAdapter<GetMeetingItemsResponse.ElementDataList> listViewAdapter;
    private List<GetMeetingItemsResponse.ElementDataList> data;
//    private Space space;

    @Override
    public void initView() {
//        submission = findViewById(R.id.acsf_submission);
//        listView = findViewById(R.id.acsf_listView);
        topRecyclerView = findViewById(R.id.acsf_top_recyclerview);
        bottomRecyclerView = findViewById(R.id.acsf_bottom_recyclerview);
    }

    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    private CommonAdapter<GetMeetingItemsResponse.ElementDataList> topAdapter;
    private CommonAdapter<GetMeetingItemsResponse.ElementDataList> bottomAdapter;
    private List<GetMeetingItemsResponse.ElementDataList> topData;
    private List<GetMeetingItemsResponse.ElementDataList> bottomData;

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

        initRecyclerView();
//        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
//        TextView headerView = (TextView) layoutInflater.inflate(R.layout.head_meeting_flow_edit, null, false);
//        headerView.setText(day);
//        listView.addHeaderView(headerView);
//        ConstraintLayout footerView = (ConstraintLayout) layoutInflater.inflate(R.layout.footer_create_service_flow, null, false);
//        footerView.findViewById(R.id.fcsf_add_process);
//        space = footerView.findViewById(R.id.fcsf_space);
//        footerView.setOnClickListener(this);
//        listView.addFooterView(footerView);
//        data = new ArrayList<>();
//        listViewAdapter = new ListViewAdapter<GetMeetingItemsResponse.ElementDataList>(getApplicationContext(), data, R.layout.item_create_service_flow) {
//            @Override
//            protected void binView(ViewHolder viewHolder, GetMeetingItemsResponse.ElementDataList item, int position) {
//                viewHolder.setTextView(R.id.icsf_line_number, String.valueOf(position + 1));
//                viewHolder.setTextView(R.id.icsf_name, "name" + position);
//                viewHolder.setTextView(R.id.icsf_editing_time, item.getCreateOn());
//                AppCompatImageView delete = viewHolder.getItemView(R.id.icsf_delete);
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("123456");
//                    }
//                });
//            }
//        };
//        listView.setAdapter(listViewAdapter);
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

    //    String[] str = new String[]{"10:10","05:20","09:10","03:20","00:10","05:21","13:20"};
//    int i = 0;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.fcsf_add_process_layout:
//                GetMeetingItemsResponse.ElementDataList item = new GetMeetingItemsResponse.ElementDataList();
//                item.setCreateOn(str[i++]);
//                data.add(item);
//                Collections.sort(data);
//                listViewAdapter.notifyDataSetChanged();
//                if (data.size() > 0) {
//                    space.setVisibility(View.VISIBLE);
//                }
//                break;
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(layoutManager);
        bottomRecyclerView.setLayoutManager(layoutManager);

        topData = new ArrayList<>();
        bottomData = new ArrayList<>();
        setData();
        topAdapter = new CommonAdapter<GetMeetingItemsResponse.ElementDataList>(getApplicationContext(), R.layout.item_create_service_flow, topData) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, GetMeetingItemsResponse.ElementDataList elementDataList, int position) {

            }
        };
        bottomAdapter = new CommonAdapter<GetMeetingItemsResponse.ElementDataList>(getApplicationContext(), R.layout.item_create_service_flow, bottomData) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, GetMeetingItemsResponse.ElementDataList elementDataList, int position) {

            }
        };
        topRecyclerView.setAdapter(topAdapter);
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            GetMeetingItemsResponse.ElementDataList item = new GetMeetingItemsResponse.ElementDataList();
            item.setId(String.valueOf(i));
            topData.add(item);
            bottomData.add(item);
        }
    }
}

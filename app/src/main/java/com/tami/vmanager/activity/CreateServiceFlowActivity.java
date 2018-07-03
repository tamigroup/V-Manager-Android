package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingItemsRequest;
import com.tami.vmanager.entity.GetMeetingItemsResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 创建服务流程
 * Created by why on 2018/6/26.
 */
public class CreateServiceFlowActivity extends BaseActivity {

    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    private CommonAdapter<GetMeetingItemsResponse.Array.Item> topAdapter;
    private CommonAdapter<GetMeetingItemsResponse.Array.Item> bottomAdapter;
    private List<GetMeetingItemsResponse.Array.Item> topData;
    private List<GetMeetingItemsResponse.Array.Item> bottomData;
    private TextView addView;
    private Button saveBtn;

    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_service_flow;
    }


    @Override
    public void initView() {
        topRecyclerView = findViewById(R.id.acsf_top_recyclerview);
        bottomRecyclerView = findViewById(R.id.acsf_bottom_recyclerview);
        addView = findViewById(R.id.fcsf_add_process);
        saveBtn = findViewById(R.id.acsf_save_btn);
    }

    @Override
    public void initListener() {
        saveBtn.setOnClickListener(this);
        addView.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String day = null;
        Intent intent = getIntent();
        if (intent != null) {
            day = intent.getStringExtra(Constants.KEY_DAY);
        }
        setTitleName(R.string.create_service_process);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        initRecyclerView();
    }

    @Override
    public void requestNetwork() {
        getMeetingItems();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.acsf_save_btn:

                break;
            case R.id.fcsf_add_process:

                break;
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager topManager = new LinearLayoutManager(getApplicationContext());
        topManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(topManager);
        LinearLayoutManager bottomManager = new LinearLayoutManager(getApplicationContext());
        bottomManager.setOrientation(LinearLayoutManager.VERTICAL);
        bottomRecyclerView.setLayoutManager(bottomManager);

        topData = new ArrayList<>();
        bottomData = new ArrayList<>();
        topAdapter = new CommonAdapter<GetMeetingItemsResponse.Array.Item>(getApplicationContext(), R.layout.item_create_service_flow, topData) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingItemsResponse.Array.Item item, int position) {
                //流程名称
                holder.setText(R.id.icsf_name,item.name);

                findViewById(R.id.icsf_line_number);
            }
        };
        bottomAdapter = new CommonAdapter<GetMeetingItemsResponse.Array.Item>(getApplicationContext(), R.layout.item_create_service_flow, bottomData) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingItemsResponse.Array.Item item, int position) {

            }
        };
        topRecyclerView.setAdapter(topAdapter);
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    private void getMeetingItems() {
        GetMeetingItemsRequest gmir = new GetMeetingItemsRequest();
        gmir.setMeetingId(String.valueOf(29));
        gmir.setStartDate("2018-07-10");
        networkBroker.ask(gmir, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemsResponse response = (GetMeetingItemsResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        GetMeetingItemsResponse.Array array = response.data;
                        if (array != null && array.dataList != null && array.dataList.size() > 0) {
                            topData.addAll(array.dataList);
                            Collections.sort(topData);
                            topAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

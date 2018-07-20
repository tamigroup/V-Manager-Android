package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingAddressListRequest;
import com.tami.vmanager.entity.GetMeetingAddressListResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议地点选择
 * Created by why on 2018/6/27.
 */
public class MeetingPlaceSelectActivity extends BaseActivity {

    private Button confirm;
    private RecyclerView recyclerView;
    private List<GetMeetingAddressListResponse.Array.Item> listData;
    private CommonAdapter<GetMeetingAddressListResponse.Array.Item> commonAdapter;
    private int onClick = -1;
    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_place;
    }

    @Override
    public void initView() {
        confirm = findViewById(R.id.amp_confirm);
        recyclerView = findViewById(R.id.amp_recyclerView);
    }

    @Override
    public void initListener() {
        confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_place);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.color_EAEAEA)));
        recyclerView.setLayoutManager(layoutManager);
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<GetMeetingAddressListResponse.Array.Item>(getApplicationContext(), R.layout.item_meeting_place, listData) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingAddressListResponse.Array.Item item, int position) {
                holder.setText(R.id.imp_name, item.name);
                ConstraintLayout constraintLayout = holder.getView(R.id.imp_layout);
                AppCompatImageView imageView = holder.getView(R.id.imp_selected);
                imageView.setVisibility(item.isState ? View.VISIBLE : View.GONE);
                constraintLayout.setOnClickListener((View v) -> {
                    if (onClick != -1) {
                        AppCompatImageView onClickView = recyclerView.getChildAt(onClick).findViewById(R.id.imp_selected);
                        onClickView.setVisibility(View.GONE);
                    }
                    item.isState = !item.isState;
                    imageView.setVisibility(item.isState ? View.VISIBLE : View.GONE);
                    onClick = position;
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        getMeetingPlace();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (listData != null) {
            listData.clear();
            listData = null;
        }
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.amp_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        Intent intent = new Intent();
        GetMeetingAddressListResponse.Array.Item item = listData.get(onClick);
        intent.putExtra(Constants.RESULT_DIDIAN, item);
        setResult(Constants.CREATE_MEETING_DIDIAN, intent);
        finish();
    }

    /**
     * 获取会议地点列表
     */
    private void getMeetingPlace() {
        GetMeetingAddressListRequest gma = new GetMeetingAddressListRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            gma.setSystemId(String.valueOf(item.getSystemId()));
        }
        networkBroker.ask(gma, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingAddressListResponse response = (GetMeetingAddressListResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        GetMeetingAddressListResponse.Array array = response.data;
                        if (array != null && array.dataList != null && array.dataList.size() > 0) {
                            listData.addAll(array.dataList);
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}

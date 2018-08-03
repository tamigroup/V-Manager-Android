package com.tami.vmanager.activity;

import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.adapter.ViewHolder;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingItemFlowRequest;
import com.tami.vmanager.entity.GetMeetingItemFlowResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会议服务流程单
 * Created by why on 2018/7/14.
 */
public class ConferenceServiceFlowActivity extends BaseActivity {

    private TextView meetingNameView;
    private ListView listView;
    private int meetingId;
    private String meetingName;
    private List<GetMeetingItemFlowResponse.Array.Item> listData;
    private ListViewAdapter<GetMeetingItemFlowResponse.Array.Item> listViewAdapter;

    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_conference_service_flow;
    }

    @Override
    public void initView() {
        meetingNameView = findViewById(R.id.acsf_meeting_name);
        listView = findViewById(R.id.acsf_listview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(R.string.conference_service_flow);
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, -1);
            meetingName = intent.getStringExtra(Constants.KEY_MEETING_NAME);
            meetingNameView.setText(meetingName);
        }
        initListView();
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        getMeetingItemFlow();
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

    private void initListView() {
        listData = new ArrayList<>();
        listViewAdapter = new ListViewAdapter<GetMeetingItemFlowResponse.Array.Item>(getApplicationContext(), listData, R.layout.item_conference_service_flow) {
            @Override
            protected void binView(ViewHolder viewHolder, GetMeetingItemFlowResponse.Array.Item item, int position) {
                viewHolder.setTextView(R.id.icsf_time, TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH));
                viewHolder.setTextView(R.id.icsf_name, item.meetingItemName);
                TextView contactsView = viewHolder.getItemView(R.id.icsf_contacts);
                TextView mobilePhoneView = viewHolder.getItemView(R.id.icsf_mobile_phone);

                List<GetMeetingItemFlowResponse.Array.Item.UserList> userList = item.meetingItemSetFlowUserList;
                if (userList != null && userList.size() > 0) {
                    StringBuilder name = new StringBuilder();
                    StringBuilder mobilePhone = new StringBuilder();
                    int size = userList.size();
                    for (int i = 0; i < size; i++) {
                        GetMeetingItemFlowResponse.Array.Item.UserList gaiu = userList.get(i);
                        if (gaiu != null) {
                            name.append(gaiu.userName);
                            mobilePhone.append(gaiu.mobile);
                        }
                        if (i != size - 1) {
                            name.append("\n");
                            mobilePhone.append("\n");
                        }
                    }
                    contactsView.setText(name.toString());
                    mobilePhoneView.setText(mobilePhone.toString());
                }
            }
        };
        listView.setAdapter(listViewAdapter);
    }


    /**
     * 获取会议流程单*
     */
    private void getMeetingItemFlow() {
        GetMeetingItemFlowRequest gmifr = new GetMeetingItemFlowRequest();
        gmifr.setMeetingId(meetingId);
        networkBroker.ask(gmifr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemFlowResponse response = (GetMeetingItemFlowResponse) res;
                if (response.getCode() == 200) {
                    GetMeetingItemFlowResponse.Array array = response.data;
                    if (array != null && array.dataList != null && array.dataList.size() > 0) {
                        listData.addAll(array.dataList);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }else{
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

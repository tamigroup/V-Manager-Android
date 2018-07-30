package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.MeetingUserGroupPageRequest;
import com.tami.vmanager.entity.MeetingUserGroupPageResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 群成员
 * Created by why on 2018/7/5.
 */
public class GroupMembersActivty extends BaseActivity {

    private TextView notice;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private TextView load_more;
    private TextView out_meeting;
    private List<MeetingUserGroupPageResponse.Array.Item> data;
    private CommonAdapter<MeetingUserGroupPageResponse.Array.Item> commonAdapter;
    private NetworkBroker networkBroker;
    private int meetingId;
    private int saleUserId;
    private int total = 0;//传给查看全部的条数
    private int CurPage = 1;

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
        notice = findViewById(R.id.agm_notice);
        constraintLayout = findViewById(R.id.cl);
        load_more = findViewById(R.id.load_more);
        out_meeting = findViewById(R.id.out_meeting);

    }

    @Override
    public void initListener() {
        out_meeting.setOnClickListener(this);
        notice.setOnClickListener(this);
        constraintLayout.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            saleUserId = intent.getIntExtra(Constants.KEY_SALE_USER_ID, 0);
        }
        setTitleName(getString(R.string.group_members));
        initRecyc();

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    private void initRecyc() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        data = new ArrayList<>();
        setCommondAdapter(data);
    }

    private void setCommondAdapter(List<MeetingUserGroupPageResponse.Array.Item> sub_data) {
        commonAdapter = new CommonAdapter<MeetingUserGroupPageResponse.Array.Item>(this, R.layout.item_activity_sponsor_member, sub_data) {
            @Override
            protected void convert(ViewHolder holder, MeetingUserGroupPageResponse.Array.Item item, int position) {
                holder.setText(R.id.item_name, item.realName);
                if (!TextUtils.isEmpty(item.iconUrl)) {
                    ImageView imageView = holder.getView(R.id.item_header_image);
                    Picasso.get().load(item.iconUrl).into(imageView);
                }
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        meetingUserGroupPage();
    }

    @Override
    public void removeListener() {
    }

    @Override
    public void emptyObject() {
        if (data != null) {
            data.clear();
            data = null;
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
            case R.id.cl:
                Intent intentAll = new Intent(getApplicationContext(), GroupMembersAllActivity.class);
                intentAll.putExtra(Constants.TOTAL, total);
                intentAll.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intentAll);
                break;
            case R.id.out_meeting:
                //跳到会议概览
                Intent intent_meetingOverv = new Intent(getApplicationContext(), MeetingOverviewActivity.class);
                intent_meetingOverv.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_meetingOverv.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intent_meetingOverv);
                break;
            case R.id.agm_notice:
                //跳转
                Intent intent = new Intent(getApplicationContext(), GroupNoticeActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                intent.putExtra(Constants.KEY_SALE_USER_ID, saleUserId);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取会议群组中的用户成员
     */
    private void meetingUserGroupPage() {
        MeetingUserGroupPageRequest mugpr = new MeetingUserGroupPageRequest();
        mugpr.setMeetingId(meetingId);
        mugpr.setCurPage(CurPage++);
        mugpr.setPageSize(10);
//        mugpr.setMeetingId(1);
//        mugpr.setCurPage(1);
//        mugpr.setPageSize(10);
        networkBroker.ask(mugpr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                MeetingUserGroupPageResponse response = (MeetingUserGroupPageResponse) res;
                if (response.getCode() == 200) {
                    MeetingUserGroupPageResponse.Array array = response.data;
                    if (array != null) {
                        List<MeetingUserGroupPageResponse.Array.Item> item = array.elements;
                        if (item != null && item.size() > 0) {
                            if (data != null && data.size() > 0) {
                                data.clear();
                            }
                            data.addAll(item);
                            commonAdapter.notifyDataSetChanged();
                            if (!array.lastPage) {
                                load_more.setVisibility(View.VISIBLE);
                                total = array.totalElements;
                            } else {
                                load_more.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

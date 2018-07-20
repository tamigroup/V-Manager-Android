package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

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
 * 查看全部群成员
 * Created by why on 2018/7/9.
 */
public class GroupMembersAllActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<MeetingUserGroupPageResponse.Array.Item> data;
    private CommonAdapter<MeetingUserGroupPageResponse.Array.Item> commonAdapter;
    private NetworkBroker networkBroker;
    private int total = 0;
    private int meetingId;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_members_all;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.agma_recyclerview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            total = intent.getIntExtra(Constants.TOTAL, 0);
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }

        setTitleName(getString(R.string.group_members));

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        initRecyclerView();
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
        if(data!=null){
            data.clear();
            data = null;
        }
        if(networkBroker!=null){
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        data = new ArrayList<>();
        commonAdapter = new CommonAdapter<MeetingUserGroupPageResponse.Array.Item>(this, R.layout.item_activity_sponsor_member, data) {
            @Override
            protected void convert(ViewHolder holder, MeetingUserGroupPageResponse.Array.Item item, int position) {
                holder.setText(R.id.item_name, item.realName);
                if (!TextUtils.isEmpty(item.iconUrl)) {
                    AppCompatImageView imageView = holder.getView(R.id.item_header_image);
                    Picasso.get().load(item.iconUrl).into(imageView);
                }
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    /**
     * 获取会议群组中的用户成员
     */
    private void meetingUserGroupPage() {
        MeetingUserGroupPageRequest mugpr = new MeetingUserGroupPageRequest();
        mugpr.setMeetingId(meetingId);
        mugpr.setCurPage(1);
        mugpr.setPageSize(total);
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
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

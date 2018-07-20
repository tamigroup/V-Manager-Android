package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.SponsorUserListRequestBean;
import com.tami.vmanager.entity.SponsorUserListResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 主办方成员
 * Created by why on 2018/6/19.
 */
public class SponsorMemberActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<SponsorUserListResponseBean.DataBean.DataListBean> listData;
    private NetworkBroker networkBroker;
    private CommonAdapter<SponsorUserListResponseBean.DataBean.DataListBean> commonAdapter;
    private int meetingId;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sponsor_member;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyc);
        networkBroker = new NetworkBroker(getApplicationContext());
        networkBroker.setCancelTag(getTAG());
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        setTitleName(R.string.sponsor_member);
        initRecyc();
    }

    private void initRecyc() {
        listData = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(layoutManager);
        commonAdapter = new CommonAdapter<SponsorUserListResponseBean.DataBean.DataListBean>(this, R.layout.item_activity_sponsor_member, listData) {

            @Override
            protected void convert(ViewHolder holder, SponsorUserListResponseBean.DataBean.DataListBean item, int position) {
                holder.setText(R.id.item_name, item.getUserName());
            }
        };
        commonAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        SponsorUserListRequestBean sponsorUserListRequestBean = new SponsorUserListRequestBean();
        //        sponsorUserListRequestBean.setMeetingId(88);
        sponsorUserListRequestBean.setMeetingId(meetingId);
        networkBroker.ask(sponsorUserListRequestBean, (exl, res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }

            try {
                SponsorUserListResponseBean response = (SponsorUserListResponseBean) res;
                if (response.getCode() == 200) {
                    SponsorUserListResponseBean.DataBean data = response.getData();
                    if (data != null) {
                        if (data.getDataList() != null && data.getDataList().size() > 0) {
                            listData.addAll(data.getDataList());
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }
}

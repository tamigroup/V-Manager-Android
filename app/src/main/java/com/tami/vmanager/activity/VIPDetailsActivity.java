package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.VIPDetailsRequestBean;
import com.tami.vmanager.entity.VIPDetailsResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.GridSpacingItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * VIP详情
 * Created by why on 2018/7/5.
 */
public class VIPDetailsActivity extends BaseActivity {

    private TextView title;
    private RecyclerView recyclerView;
    private int CurPage = 1;
    private NetworkBroker networkBroker;
    private List<VIPDetailsResponseBean.DataBean.ElementsBean> listData;
    private CommonAdapter<VIPDetailsResponseBean.DataBean.ElementsBean> commonAdapter;
    private int meetingId;//会议ID
    private Group vip_detail_group;
    private TextView empty_tv;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_vip_details;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.avd_title);
        recyclerView = findViewById(R.id.recyc);
        vip_detail_group = findViewById(R.id.vip_detail_group);
        empty_tv = findViewById(R.id.empty_tv);
        networkBroker = new NetworkBroker(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            GetMeetingResponse.Item item = (GetMeetingResponse.Item) intent.getSerializableExtra(Constants.MEETING_INFO);
            if (item != null) {
                title.setText(item.meetingName);
                if (item.fromPlat == 1) {
                    title.setCompoundDrawables(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.meeting_level_zhi), null, null, null);
                }
            }
        }
        setTitleName(getString(R.string.vip_details));
        initRecyc();
    }

    private void initRecyc() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 50, true));

        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<VIPDetailsResponseBean.DataBean.ElementsBean>(this, R.layout.item_activity_vip_details, listData) {

            @Override
            protected void convert(ViewHolder holder, VIPDetailsResponseBean.DataBean.ElementsBean elementsBean, int position) {
                holder.setText(R.id.item_vip_name, elementsBean.getName());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                VipMessageActivity.Start(VIPDetailsActivity.this, listData.get(position).getName(), listData.get(position).getIntro());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        VIPDetailsRequestBean vipDetailsRequestBean = new VIPDetailsRequestBean();
        vipDetailsRequestBean.setMeetingId(meetingId);
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            vipDetailsRequestBean.setSystemId(item.getSystemId());
        }
        vipDetailsRequestBean.setCurPage(CurPage++);
        vipDetailsRequestBean.setPageSize(Constants.PAGE_SIZE);

//        vipDetailsRequestBean.setMeetingId(1);
//        vipDetailsRequestBean.setSystemId(4);
//        vipDetailsRequestBean.setCurPage(CurPage++);
//        vipDetailsRequestBean.setPageSize(10);
        networkBroker.ask(vipDetailsRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                VIPDetailsResponseBean response = (VIPDetailsResponseBean) res;
                if (response.getCode() == 200) {
                    VIPDetailsResponseBean.DataBean data = response.getData();
                    if (data != null && data.getElements() != null && data.getElements().size() > 0) {
                        vip_detail_group.setVisibility(View.VISIBLE);
                        empty_tv.setVisibility(View.GONE);
                        listData.addAll(data.getElements());
                        commonAdapter.notifyDataSetChanged();
                    }else {
                        vip_detail_group.setVisibility(View.GONE);
                        empty_tv.setVisibility(View.VISIBLE);
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

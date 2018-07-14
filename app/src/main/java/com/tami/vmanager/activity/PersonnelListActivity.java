package com.tami.vmanager.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.readystatesoftware.chuck.internal.support.DividerItemDecoration;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.IndexNameBean;
import com.tami.vmanager.entity.PersonnelListRequestBean;
import com.tami.vmanager.entity.PersonnelListResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.view.IndexBar.suspension.SuspensionDecoration;
import com.tami.vmanager.view.IndexBar.widget.IndexBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员名单
 * Created by why on 2018/7/5.
 */
public class PersonnelListActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private CommonAdapter<IndexNameBean> commonAdapter;
    private TextView selectTv;
    private TextView search_name;
    private int meetingId;
    private NetworkBroker networkBroker;
    private TextView indexBarTv;
    private List<IndexNameBean> mDatas;
    private SuspensionDecoration mDecoration;
    private LinearLayoutManager mManager;
    private IndexBar mIndexBar;
    private static final String INDEX_STRING_TOP = "↑";


    @Override
    public int getContentViewId() {
        return R.layout.activity_personnel_list;
    }

    @Override
    public void initView() {
        selectTv = findViewById(R.id.selectTv);
        search_name = findViewById(R.id.search_name);
        mRecyclerView = findViewById(R.id.recyc);
        mIndexBar = findViewById(R.id.indexBar);
        indexBarTv = findViewById(R.id.indexBarTv);

        networkBroker = new NetworkBroker(this);
    }

    @Override
    public void initListener() {
        selectTv.setOnClickListener(this);
        search_name.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.personnel_list));
        mDatas = new ArrayList<>();
        meetingId = getIntent().getIntExtra(Constants.KEY_MEETING_ID, 0);
        initRecyc();
    }

    private void initRecyc() {
        commonAdapter = new CommonAdapter<IndexNameBean>(this, R.layout.item_rec_car, mDatas) {

            @Override
            protected void convert(ViewHolder holder, IndexNameBean indexNameBean, int position) {
                holder.setText(R.id.item_tv, indexNameBean.getName());
            }
        };

        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(commonAdapter);
        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(PersonnelListActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mIndexBar.setmPressedShowTextView(indexBarTv)
                .setNeedRealIndex(false)
                .setmLayoutManager(mManager);
    }

    @Override
    public void requestNetwork() {
        requestData();
    }

    private void requestData() {
        PersonnelListRequestBean personnelListRequestBean = new PersonnelListRequestBean();
        //        personnelListRequestBean.setMeetingId(meetingId);
        personnelListRequestBean.setMeetingId(46);
        personnelListRequestBean.setType(0);
        personnelListRequestBean.setName("");
        networkBroker.ask(personnelListRequestBean, (exl, res) -> {
            if (null != exl) {
                return;
            }
            try {
                PersonnelListResponseBean response = (PersonnelListResponseBean) res;
                if (response.getCode() == 200) {
                    PersonnelListResponseBean.DataBean data = response.getData();
                    if (data != null) {
                        if (data.getDataList() != null && data.getDataList().size() > 0) {
                            mDatas.addAll(data.getDataList());
                            commonAdapter.notifyDataSetChanged();
                            mIndexBar.setmSourceDatas(mDatas).invalidate();
                            mDecoration.setmDatas(mDatas);
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

    }

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.selectTv:
                //pop
                break;
            case R.id.search_name:
                SearchVipActivity.Start(this);
                break;
        }
    }
}

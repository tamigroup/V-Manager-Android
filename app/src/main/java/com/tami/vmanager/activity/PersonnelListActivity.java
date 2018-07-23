package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.IndexHeaderBean;
import com.tami.vmanager.entity.IndexNameBean;
import com.tami.vmanager.entity.PersonnelListRequestBean;
import com.tami.vmanager.entity.PersonnelListResponseBean;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.view.IndexBar.bean.BaseIndexPinyinBean;
import com.tami.vmanager.view.IndexBar.suspension.SuspensionDecoration;
import com.tami.vmanager.view.IndexBar.widget.HeaderRecyclerAndFooterWrapperAdapter;
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
    private List<IndexHeaderBean> mHeaderDatas;
    private List<BaseIndexPinyinBean> mSourceDatas;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private ConstraintLayout no_v_cl;
    private Group personnel_group;
    private int isVzh;
    private ConstraintLayout empty_cl;

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
        empty_cl = findViewById(R.id.empty_cl);

        no_v_cl = findViewById(R.id.no_v_cl);
        personnel_group = findViewById(R.id.personnel_group);

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
        Intent intent = getIntent();
        if (null != intent) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            isVzh = intent.getIntExtra(Constants.IS_VZHIHUI, 0);
            //V智慧判断
            if (isVzh == 1) {
                no_v_cl.setVisibility(View.GONE);
                personnel_group.setVisibility(View.VISIBLE);
            } else {
                no_v_cl.setVisibility(View.VISIBLE);
                personnel_group.setVisibility(View.GONE);
            }
        }

        mSourceDatas = new ArrayList<>();
        mDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<IndexNameBean> hotCitys = new ArrayList<>();
        mHeaderDatas.add(new IndexHeaderBean(hotCitys, getString(R.string.vip_distinguished_guest), INDEX_STRING_TOP));
        mSourceDatas.addAll(mHeaderDatas);
        initRecyc();
    }

    private void initRecyc() {
        commonAdapter = new CommonAdapter<IndexNameBean>(this, R.layout.item_rec_car, mDatas) {

            @Override
            protected void convert(ViewHolder holder, IndexNameBean indexNameBean, int position) {
                holder.setText(R.id.item_tv, indexNameBean.getName());
            }
        };

        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(commonAdapter) {

            @Override
            protected void onBindHeaderHolder(com.tami.vmanager.view.IndexBar.widget.ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                    case R.layout.item_index_name_header:
                        final IndexHeaderBean IndexHeaderBean = (IndexHeaderBean) o;
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setLayoutManager(new LinearLayoutManager(PersonnelListActivity.this));
                        //解决左边距问题，复制布局
                        recyclerView.setAdapter(new CommonAdapter<IndexNameBean>(PersonnelListActivity.this, R.layout.item_rec_car1, IndexHeaderBean.getCityList()) {
                            @Override
                            protected void convert(ViewHolder holder, IndexNameBean item, int position) {
                                holder.setText(R.id.item_tv, item.getName());
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.item_index_name_header, mHeaderDatas.get(0));
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHeaderAdapter);
        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //        mRecyclerView.addItemDecoration(new DividerItemDecoration(PersonnelListActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mIndexBar.setmPressedShowTextView(indexBarTv)
                .setNeedRealIndex(false)
                .setmLayoutManager(mManager);
    }

    @Override
    public void requestNetwork() {
        requestData();
    }

    private void requestData() {
        List<IndexNameBean> vip_name = new ArrayList<>();
        PersonnelListRequestBean personnelListRequestBean = new PersonnelListRequestBean();
        personnelListRequestBean.setMeetingId(meetingId);
        //        personnelListRequestBean.setMeetingId(46);
        personnelListRequestBean.setType(0);//type传0-全部   1-普通   2-VIP
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
                            empty_cl.setVisibility(View.GONE);
                            for (PersonnelListResponseBean.DataBean.DataListBean list : data.getDataList()) {
                                if (list.getType() == 1) {
                                    PersonnelListResponseBean.DataBean.DataListBean dataListBean = new PersonnelListResponseBean.DataBean.DataListBean();
                                    dataListBean.setName(list.getName());
                                    vip_name.add(dataListBean);
                                }
                            }
                            mDatas.addAll(data.getDataList());
                            mIndexBar.getDataHelper().sortSourceDatas(this.mDatas);
                            commonAdapter.notifyDataSetChanged();
                            mHeaderAdapter.notifyDataSetChanged();
                            mSourceDatas.addAll(this.mDatas);
                            mIndexBar.setmSourceDatas(mSourceDatas).invalidate();
                            mDecoration.setmDatas(mSourceDatas);

                            IndexHeaderBean headerBean = mHeaderDatas.get(0);
                            headerBean.setCityList(vip_name);
                            mHeaderAdapter.notifyItemRangeChanged(0, 3);
                        } else {
                            if (isVzh == 1) {
                                empty_cl.setVisibility(View.VISIBLE);
                            } else {
                                empty_cl.setVisibility(View.GONE);
                            }
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
        if (mDatas != null) {
            mDatas.clear();
            mDatas = null;
        }
        if (mHeaderDatas != null) {
            mHeaderDatas.clear();
            mHeaderDatas = null;
        }
        if (mSourceDatas != null) {
            mSourceDatas.clear();
            mSourceDatas = null;
        }
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
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

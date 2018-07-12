package com.tami.vmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.EvaluatePageRequestBean;
import com.tami.vmanager.entity.EvaluatePageResponseBean;
import com.tami.vmanager.enums.IdeasBoxType;
import com.tami.vmanager.fragment.HostFragment;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;

/**
 * 意见箱
 * Created by why on 2018/6/27.
 */
public class IdeasBoxActivity extends BaseActivity {

    private int meetingId;//会议ID
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] arrayFragment;
    private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;
    private TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener;
    private NetworkBroker networkBroker;
    private TabItem host_tab;
    private TabItem participants_tab;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_ideas_box;
    }

    @Override
    public void initView() {
        tabLayout = findViewById(R.id.csg_tab_layout);
        viewPager = findViewById(R.id.csg_view_pager);
        host_tab = findViewById(R.id.host_tab);
        participants_tab = findViewById(R.id.participants_tab);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }

        setTitleName(R.string.complaints_box);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_MEETING_ID, meetingId);
        arrayFragment = new Fragment[2];
        arrayFragment[0] = new HostFragment();
        arrayFragment[0].setArguments(bundle);
        arrayFragment[1] = new ParticipantsFragment();
        arrayFragment[1].setArguments(bundle);

        GuidePageFragmentPagerAdapter guidePageFragmentPagerAdapter = new GuidePageFragmentPagerAdapter(getSupportFragmentManager(), arrayFragment);
        viewPager.setAdapter(guidePageFragmentPagerAdapter);
        tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
        viewPagerOnTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager);
        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);
    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        getHostEvaluate();
        getParticipants();
    }

    /**
     * 获取参会方评论
     */
    private void getParticipants() {
        EvaluatePageRequestBean evaluatePageRequestBean = new EvaluatePageRequestBean();
        evaluatePageRequestBean.setMeetingId(String.valueOf(meetingId));
        evaluatePageRequestBean.setType(IdeasBoxType.PARTICIPANTS.getType());
        evaluatePageRequestBean.setCurPage(1);
        evaluatePageRequestBean.setPageSize(Constants.PAGE_SIZE);
        networkBroker.ask(evaluatePageRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            EvaluatePageResponseBean response = (EvaluatePageResponseBean) res;
            if (response.getCode() == 200) {
                EvaluatePageResponseBean.DataBean data = response.getData();
                if (data != null) {
                    int totalElements = data.getTotalElements();
                    tabLayout.getTabAt(1).setText(String.format(getResources().getString(R.string.participants), totalElements + ""));
                }


            }
        });
    }

    /**
     * 获取主办方评论
     */
    private void getHostEvaluate() {
        EvaluatePageRequestBean evaluatePageRequestBean = new EvaluatePageRequestBean();
        evaluatePageRequestBean.setMeetingId(String.valueOf(meetingId));
        evaluatePageRequestBean.setType(IdeasBoxType.HOST.getType());
        evaluatePageRequestBean.setCurPage(1);
        evaluatePageRequestBean.setPageSize(Constants.PAGE_SIZE);
        networkBroker.ask(evaluatePageRequestBean, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            EvaluatePageResponseBean response = (EvaluatePageResponseBean) res;
            if (response.getCode() == 200) {
                EvaluatePageResponseBean.DataBean data = response.getData();
                if (data != null){
                    int totalElements = data.getTotalElements();
                    tabLayout.getTabAt(0).setText(String.format(getResources().getString(R.string.host), totalElements + ""));
                }}
        });
    }

    @Override
    public void removeListener() {
        viewPager.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
        tabLayout.removeOnTabSelectedListener(viewPagerOnTabSelectedListener);
    }

    @Override
    public void emptyObject() {
        tabLayoutOnPageChangeListener = null;
        viewPagerOnTabSelectedListener = null;
        tabLayout = null;
        viewPager = null;
        arrayFragment = null;
    }
}

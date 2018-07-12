package com.tami.vmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.fragment.HostFragment;
import com.tami.vmanager.utils.Constants;

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

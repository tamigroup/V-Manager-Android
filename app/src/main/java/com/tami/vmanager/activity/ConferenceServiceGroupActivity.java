package com.tami.vmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.fragment.ConferenceInformationFragment;
import com.tami.vmanager.fragment.FeedbackFragment;
import com.tami.vmanager.fragment.GroupChatFragment;
import com.tami.vmanager.fragment.NoticeFragment;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.SPUtils;

import cn.jiguang.api.JCoreInterface;

/**
 * 会议服务群
 * Created by why on 2018/6/16.
 */

public class ConferenceServiceGroupActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] arrayFragment;
    private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;
    private TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener;
    private int meetingId;
    private GetMeetingResponse.Item item;
    private int actualNum;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_conference_service_group;
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
//            actualNum = intent.getIntExtra(Constants.ACTUAL_NUM, 0);
            item = (GetMeetingResponse.Item) intent.getSerializableExtra(Constants.MEETING_INFO);
        }
        setTitleName(R.string.conference_service_group);
        //设置右边功能按钮图片
        Boolean is_invisible = (Boolean) SPUtils.get(this, Constants.IS_INVISIBLE, false);
        if (null != is_invisible && !is_invisible) {
            setTitleRightBtn(R.mipmap.icon_people);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_MEETING_ID, meetingId);
        arrayFragment = new Fragment[4];
        arrayFragment[0] = new ConferenceInformationFragment(meetingId, item);
        arrayFragment[1] = new GroupChatFragment();
        arrayFragment[1].setArguments(bundle);
        arrayFragment[2] = new FeedbackFragment(meetingId, item);
        arrayFragment[3] = new NoticeFragment();
        arrayFragment[3].setArguments(bundle);

        GuidePageFragmentPagerAdapter guidePageFragmentPagerAdapter = new GuidePageFragmentPagerAdapter(getSupportFragmentManager(), arrayFragment);
        viewPager.setAdapter(guidePageFragmentPagerAdapter);
        tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
//        viewPager.setOffscreenPageLimit(3);
        viewPagerOnTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager);
        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);
        viewPager.setCurrentItem(1);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleRightBtn:
                Intent intent = new Intent(getApplicationContext(), GroupMembersActivty.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                intent.putExtra(Constants.KEY_SALE_USER_ID, item.saleUserId);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        super.onResume();
    }
}

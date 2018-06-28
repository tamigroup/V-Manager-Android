package com.tami.vmanager.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.fragment.WaitMeetingsFragment;
import com.tami.vmanager.utils.Constants;

/**
 * 查询待办会议列表
 * Created by why on 2018/6/26.
 */
public class WaitMeetingsActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] arrayFragment;
    private final int[] WaitType = new int[]{Constants.FOLLOW_TYPE_WHOLE, Constants.FOLLOW_TYPE_DAY, Constants.FOLLOW_TYPE_WEEK, Constants.FOLLOW_TYPE_MONTH};
    private TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;
    private ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_wait_meetings;
    }

    @Override
    public void initView() {
        tabLayout = findViewById(R.id.awm_tablayout);
        viewPager = findViewById(R.id.awm_viewpager);
    }

    @Override
    public void initListener() {
        tabLayoutOnPageChangeListener = new TabLayoutOnPageChangeListener(tabLayout);
        viewPagerOnTabSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
        viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);
    }

    @Override
    public void initData() {
        setTitleName(R.string.pending_conference);

        arrayFragment = new Fragment[WaitType.length];
        for (int index : WaitType) {
            addFragment(new WaitMeetingsFragment(), index);
        }
        GuidePageFragmentPagerAdapter guidePageFragmentPagerAdapter = new GuidePageFragmentPagerAdapter(getSupportFragmentManager(), arrayFragment);
        viewPager.setAdapter(guidePageFragmentPagerAdapter);
    }

    /**
     * 添加每页数据
     *
     * @param fragment
     * @param index
     */
    private void addFragment(Fragment fragment, int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.WAIT_TYPE, index);
        fragment.setArguments(bundle);
        arrayFragment[index] = fragment;
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

    }
}

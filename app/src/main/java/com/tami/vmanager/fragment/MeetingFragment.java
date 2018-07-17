package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.SearchActivity;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.NoScrollViewPager;

/**
 * Created by why on 2018/6/12.
 */

public class MeetingFragment extends BaseFragment {

    private RadioGroup meetingGroup;//顶部标签
    private int onClickId = R.id.meeting_group_whole;
    private NoScrollViewPager viewPager;//列表数据
    private Fragment[] arrayFragment;
    private final int[] meetingType = new int[]{Constants.MEETING_TYPE_WHOLE, Constants.MEETING_TYPE_PERFECTED, Constants.MEETING_TYPE_DAY, Constants.MEETING_TYPE_MONTH, Constants.MEETING_TYPE_YEAR};

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_meeting;
    }

    @Override
    public void initView() {
        meetingGroup = findViewById(R.id.meeting_group);
        viewPager = findViewById(R.id.meeting_viewpager);
    }

    @Override
    public void initListener() {
        meetingGroup.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            RadioButton unselectBtn = group.findViewById(onClickId);
            unselectBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.press));
            RadioButton selectBtn = group.findViewById(checkedId);
            selectBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.nomal));
            onClickId = checkedId;
            switch (checkedId) {
                case R.id.meeting_group_whole:
                    //全部
                    groupWhole();
                    break;
                case R.id.meeting_group_perfected:
                    //待完善
                    groupPerfected();
                    break;
                case R.id.meeting_group_day:
                    //日
                    groupDay();
                    break;
                case R.id.meeting_group_month:
                    //月
                    groupMonth();
                    break;
                case R.id.meeting_group_year:
                    //年
                    groupYear();
                    break;
            }
        });

        //        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void initData() {
        //隐藏返回按钮
        setTitleLeftBtnVisibility(View.GONE);
        //设置Title名称
//        setTitleName(R.string.v_housekeeper);
        //设置右边功能按钮图片
        setTitleRightBtn(R.mipmap.home_search);
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            setTitleName(item.getSystemName());
        }

        //添充数据
        arrayFragment = new Fragment[meetingType.length];
        for (int index = 0; index < meetingType.length; index++) {
            addFragment(new MeetingListFragment(), index);
        }
        GuidePageFragmentPagerAdapter guidePageFragmentPagerAdapter = new GuidePageFragmentPagerAdapter(getFragmentManager(), arrayFragment);
        viewPager.setAdapter(guidePageFragmentPagerAdapter);
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {
        //        viewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void emptyObject() {

    }

    /**
     * 添加每页数据
     *
     * @param fragment
     * @param index
     */
    private void addFragment(Fragment fragment, int index) {
        Logger.d("addFragment ->index:" + index);
        Logger.d("addFragment ->meetingType:" + meetingType[index]);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MEETING_TYPE, meetingType[index]);
        fragment.setArguments(bundle);
        arrayFragment[index] = fragment;
    }

    /**
     * 全部
     */
    private void groupWhole() {
        viewPager.setCurrentItem(0);
    }

    private void groupPerfected() {
        viewPager.setCurrentItem(1);
    }

    /**
     * 日
     */
    private void groupDay() {
        viewPager.setCurrentItem(2);
    }

    /**
     * 月
     */
    private void groupMonth() {
        viewPager.setCurrentItem(3);
    }

    /**
     * 年
     */
    private void groupYear() {
        viewPager.setCurrentItem(4);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleRightBtn:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    //    @Override
    //    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    //    }
    //
    //    @Override
    //    public void onPageSelected(int position) {
    //        meetingGroup.check(getRadioGroupId(position));
    //    }
    //
    //    @Override
    //    public void onPageScrollStateChanged(int state) {
    //
    //    }

    //    /**
    //     * 获取RadioButton ID
    //     *
    //     * @param type
    //     * @return
    //     */
    //    private int getRadioGroupId(int type) {
    //        int id = R.id.meeting_group_whole;
    //        switch (type) {
    //            case Constants.MEETING_TYPE_WHOLE:
    //                id = R.id.meeting_group_whole;
    //                break;
    //            case Constants.MEETING_TYPE_PENDING_PAYMENT:
    //                id = R.id.meeting_group_pending_payment;
    //                break;
    //            case Constants.MEETING_TYPE_DAY:
    //                id = R.id.meeting_group_day;
    //                break;
    //            case Constants.MEETING_TYPE_MONTH:
    //                id = R.id.meeting_group_month;
    //                break;
    //            case Constants.MEETING_TYPE_YEAR:
    //                id = R.id.meeting_group_year;
    //                break;
    //
    //        }
    //        return id;
    //    }

}

package com.tami.vmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.ABaseFragment;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.view.NoScrollViewPager;

/**
 * Created by why on 2018/6/12.
 */

public class MeetingFragment extends ABaseFragment {

    private RadioGroup meetingGroup;//顶部标签
    private int onClickId = R.id.meeting_group_whole;
    private NoScrollViewPager viewPager;//列表数据
    private Fragment[] arrayFragment;
    private final int[] meetingType = new int[]{Constants.MEETING_TYPE_WHOLE, Constants.MEETING_TYPE_PENDING_PAYMENT,Constants.MEETING_TYPE_PERFECTED, Constants.MEETING_TYPE_DAY, Constants.MEETING_TYPE_MONTH, Constants.MEETING_TYPE_YEAR};

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
        meetingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton unselectBtn = group.findViewById(onClickId);
                unselectBtn.setTextColor(ContextCompat.getColor(getActivity(),R.color.press));
                RadioButton selectBtn = group.findViewById(checkedId);
                selectBtn.setTextColor(ContextCompat.getColor(getActivity(),R.color.nomal));
                onClickId = checkedId;
                switch (checkedId) {
                    case R.id.meeting_group_whole:
                        //全部
                        groupWhole();
                        break;
                    case R.id.meeting_group_pending_payment:
                        //待付款
                        groupPendingPayment();
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
            }
        });

//        viewPager.addOnPageChangeListener(this);
    }



    @Override
    public void initData() {
        //隐藏返回按钮
        setTitleLeftBtnVisibility(View.GONE);
        //设置Title名称
        setTitleName(R.string.v_housekeeper);
        //设置右边功能按钮图片
        setTitleRightBtn(R.mipmap.home_search);

        //添充数据
        arrayFragment = new Fragment[meetingType.length];
        for (int index : meetingType) {
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
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MEETING_TYPE, index);
        fragment.setArguments(bundle);
        arrayFragment[index] = fragment;
    }

    /**
     * 全部
     */
    private void groupWhole() {
        viewPager.setCurrentItem(Constants.MEETING_TYPE_WHOLE);
    }

    /**
     * 待付款
     */
    private void groupPendingPayment() {
        viewPager.setCurrentItem(Constants.MEETING_TYPE_PENDING_PAYMENT);
    }

    private void groupPerfected(){
        viewPager.setCurrentItem(Constants.MEETING_TYPE_PERFECTED);
    }

    /**
     * 日
     */
    private void groupDay() {
        viewPager.setCurrentItem(Constants.MEETING_TYPE_DAY);
    }

    /**
     * 月
     */
    private void groupMonth() {
        viewPager.setCurrentItem(Constants.MEETING_TYPE_MONTH);
    }

    /**
     * 年
     */
    private void groupYear() {
        viewPager.setCurrentItem(Constants.MEETING_TYPE_YEAR);
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

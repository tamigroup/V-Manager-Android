package com.tami.vmanager.fragment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.CreateMeetingActivity;
import com.tami.vmanager.activity.EnterMeetingActivity;
import com.tami.vmanager.activity.SearchActivity;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by why on 2018/6/12.
 */

public class HomeFragment extends BaseFragment {

    private TextView homeSelected;
    private AppCompatImageView eye;//眼睛
    private TextView contract_money;//合同款项
    private TextView receivables;//待收款项
    private TextView total;//共计场次
    private TextView have_been_held;//已举办场次
    private ConstraintLayout today_meeting_layout;//今日会议布局
    private TextView oday_meeting_num;//今日会议数
    private ConstraintLayout my_attention_layout;//我的关注布局
    private TextView my_attention_num;//我的关注数
    private ConstraintLayout conference_layout;//待办会议布局
    private TextView conference_num;//待办会议数
    private AppCompatImageView createMeeting;

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        homeSelected = findViewById(R.id.home_selected);
        eye = findViewById(R.id.home_eye);
        contract_money = findViewById(R.id.home_contract_money);
        receivables = findViewById(R.id.home_receivables);
        total = findViewById(R.id.home_total);
        have_been_held = findViewById(R.id.home_have_been_held);
        today_meeting_layout = findViewById(R.id.home_today_meeting_layout);
        oday_meeting_num = findViewById(R.id.home_today_meeting_num);
        my_attention_layout = findViewById(R.id.home_my_attention_layout);
        my_attention_num = findViewById(R.id.home_my_attention_num);
        conference_layout = findViewById(R.id.home_pending_conference_layout);
        conference_num = findViewById(R.id.home_pending_conference_num);
        createMeeting = findViewById(R.id.home_create_meeting);
    }

    @Override
    public void initListener() {
        homeSelected.setOnClickListener(this);
        eye.setOnClickListener(this);
        today_meeting_layout.setOnClickListener(this);
        my_attention_layout.setOnClickListener(this);
        conference_layout.setOnClickListener(this);
        createMeeting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //隐藏返回按钮
        setTitleLeftBtnVisibility(View.GONE);
        //设置Title名称
        setTitleName(R.string.v_housekeeper);
        //设置右边功能按钮图片
        setTitleRightBtn(R.mipmap.home_search);

        NumberFormat numberFormat = new DecimalFormat("###,###,###");
        String num = numberFormat.format(1234567);
        String resStr = getString(R.string.contract_money, num);
        int start = resStr.length() - num.length();
        int end = resStr.length();
        contract_money.setText(Utils.getSplicing(getActivity(), resStr, start, end, 32));

        String num1 = numberFormat.format(3456789);
        String resStr1 = getString(R.string.receivables, num1);
        int start1 = resStr1.length() - num1.length();
        int end1 = resStr1.length();
        receivables.setText(Utils.getSplicing(getActivity(), resStr1, start1, end1, 32));

        String num2 = String.valueOf(150);
        String resStr2 = getString(R.string.home_total, num2);
        int start2 = 0;
        int end2 = num2.length();
        total.setText(Utils.getSplicing(getActivity(), resStr2, start2, end2, 16));


        String num3 = String.valueOf(300);
        String resStr3 = getString(R.string.have_been_held, num3);
        int start3 = 0;
        int end3 = num3.length();
        have_been_held.setText(Utils.getSplicing(getActivity(), resStr3, start3, end3, 16));

        oday_meeting_num.setText(String.valueOf(10));
        my_attention_num.setText(String.valueOf(15));
        conference_num.setText(String.valueOf(20));
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog.setContentView(null);
            mBottomSheetDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.home_selected:
                //日期选择
                homeSelected();
                break;
            case R.id.titleRightBtn:
                //搜索按钮
                searchBtn();
                break;
            case R.id.home_eye:
                //眼睛
                homeEye(v);
                break;
            case R.id.home_today_meeting_layout:
                //今日会议
                todayMeetin();
                break;
            case R.id.home_my_attention_layout:
                //我的关注
                myAttention();
                break;
            case R.id.home_pending_conference_layout:
                //待办会议
                pendingConference();
                break;
            case R.id.home_create_meeting:
                //主页创建会议
                createMeeting();
                break;
            case R.id.hm_today:
                //今天
                homeSelected.setText(getString(R.string.today));
                mBottomSheetDialog.dismiss();
                break;
            case R.id.hm_this_month:
                //当月
                homeSelected.setText(getString(R.string.this_month));
                mBottomSheetDialog.dismiss();
                break;
            case R.id.hm_this_year:
                //年
                homeSelected.setText(getString(R.string.this_year));
                mBottomSheetDialog.dismiss();
                break;
            case R.id.hm_cancel:
                //取消
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    /**
     * 搜索按钮
     */
    private void searchBtn() {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    /**
     * 日期选择
     */
    private void homeSelected() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        ConstraintLayout constraintLayout = (ConstraintLayout) getActivity().getLayoutInflater().inflate(R.layout.item_home_menu, null);
        TextView hm_today = constraintLayout.findViewById(R.id.hm_today);
        TextView hm_this_month = constraintLayout.findViewById(R.id.hm_this_month);
        TextView hm_this_year = constraintLayout.findViewById(R.id.hm_this_year);
        TextView hm_cancel = constraintLayout.findViewById(R.id.hm_cancel);
        hm_today.setOnClickListener(this);
        hm_this_month.setOnClickListener(this);
        hm_this_year.setOnClickListener(this);
        hm_cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(constraintLayout);
        mBottomSheetDialog.show();
    }


    /**
     * 眼睛
     */
    private void homeEye(View v) {
        NumberFormat numberFormat = new DecimalFormat("###,###,###");
        String num = null;
        String num1 = null;
        AppCompatImageView imageView = (AppCompatImageView) v;
        if (!TextUtils.isEmpty(imageView.getTag().toString())
                && getString(R.string.view_state_display).equals(imageView.getTag().toString())) {
            imageView.setTag(getString(R.string.view_state_hide));
            imageView.setImageResource(R.mipmap.home_eye_off);
            num = "****";
            num1 = "****";
        } else {
            imageView.setTag(getString(R.string.view_state_display));
            imageView.setImageResource(R.mipmap.home_eye_on);
            num = numberFormat.format(1234567);
            num1 = numberFormat.format(3456789);
        }
        String resStr = getString(R.string.contract_money, num);
        int start = resStr.length() - num.length();
        int end = resStr.length();
        contract_money.setText(Utils.getSplicing(getActivity(), resStr, start, end, 32));

        String resStr1 = getString(R.string.receivables, num1);
        int start1 = resStr1.length() - num1.length();
        int end1 = resStr1.length();
        receivables.setText(Utils.getSplicing(getActivity(), resStr1, start1, end1, 32));
    }


    /**
     * 今日会议
     */
    private void todayMeetin() {
        startActivity(new Intent(getActivity(), EnterMeetingActivity.class));
    }

    /**
     * 我的关注
     */
    private void myAttention() {

    }

    /**
     * 待办会议
     */
    private void pendingConference() {

    }

    /**
     * 创建会议
     */
    private void createMeeting() {
        getActivity().startActivity(new Intent(getActivity(), CreateMeetingActivity.class));
    }


}

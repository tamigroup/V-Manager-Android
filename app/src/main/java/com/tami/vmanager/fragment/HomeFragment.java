package com.tami.vmanager.fragment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.CreateMeetingAndServiceActivity;
import com.tami.vmanager.activity.FollowMeetingsActivity;
import com.tami.vmanager.activity.SearchActivity;
import com.tami.vmanager.activity.TodayMeetingActivity;
import com.tami.vmanager.activity.WaitMeetingsActivity;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.GetBannerDataRequest;
import com.tami.vmanager.entity.GetBannerDataResponse;
import com.tami.vmanager.entity.GetIndexRequest;
import com.tami.vmanager.entity.GetIndexResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.enums.TimeType;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by why on 2018/6/12.
 */

public class HomeFragment extends BaseFragment {

    //    private TextView homeSelected;
    //    private AppCompatImageView eye;//眼睛
    //    private TextView contract_money;//合同款项
    //    private TextView receivables;//待收款项
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
    private NetworkBroker networkBroker;
    //顶部数据处理
    private GetBannerDataResponse.Item headItem;
    private RadioGroup time_radio_group;
    private RadioButton today_rb;
    private RadioButton month_rb;
    private RadioButton year_rb;
    private ProgressBar total_pro;
    private ProgressBar complete_pro;
    private ProgressBar no_complete_pro;
    private TextView total_num;
    private TextView complete_num;
    private TextView no_complete_num;
    private Group chief_group;
    private Group num_group;
    private TextView home_total;
    private TextView home_have_been_held;

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
        //        homeSelected = findViewById(R.id.home_selected);
        //        eye = findViewById(R.id.home_eye);
        //        contract_money = findViewById(R.id.home_contract_money);
        //        receivables = findViewById(R.id.home_receivables);
        total = findViewById(R.id.home_total);
        have_been_held = findViewById(R.id.home_have_been_held);
        today_meeting_layout = findViewById(R.id.home_today_meeting_layout);
        oday_meeting_num = findViewById(R.id.home_today_meeting_num);
        my_attention_layout = findViewById(R.id.home_my_attention_layout);
        my_attention_num = findViewById(R.id.home_my_attention_num);
        conference_layout = findViewById(R.id.home_pending_conference_layout);
        conference_num = findViewById(R.id.home_pending_conference_num);
        createMeeting = findViewById(R.id.home_create_meeting);

        //高管财务总负责人
        chief_group = findViewById(R.id.chief_group);
        time_radio_group = findViewById(R.id.time_radio_group);
        today_rb = findViewById(R.id.today_rb);
        month_rb = findViewById(R.id.month_rb);
        year_rb = findViewById(R.id.year_rb);
        total_pro = findViewById(R.id.total_pro);
        complete_pro = findViewById(R.id.complete_pro);
        no_complete_pro = findViewById(R.id.no_complete_pro);
        total_num = findViewById(R.id.total_num);
        complete_num = findViewById(R.id.complete_num);
        no_complete_num = findViewById(R.id.no_complete_num);
        //其它部门总负责人
        num_group = findViewById(R.id.num_group);
        home_total = findViewById(R.id.home_total);
        home_have_been_held = findViewById(R.id.home_have_been_held);
    }

    @Override
    public void initListener() {
        //        homeSelected.setOnClickListener(this);
        //        eye.setOnClickListener(this);
        today_meeting_layout.setOnClickListener(this);
        my_attention_layout.setOnClickListener(this);
        conference_layout.setOnClickListener(this);
        createMeeting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        networkBroker = new NetworkBroker(getActivity());
        networkBroker.setCancelTag(getTAG());

        //隐藏返回按钮
        setTitleLeftBtnVisibility(View.GONE);
        //设置Title名称
        setTitleName(R.string.v_housekeeper);
        //设置右边功能按钮图片
        setTitleRightBtn(R.mipmap.home_search);

        time_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            today_rb = group.findViewById(R.id.today_rb);
            month_rb = group.findViewById(R.id.month_rb);
            year_rb = group.findViewById(R.id.year_rb);
            today_rb.setOnClickListener(v -> {
                today_rb.setTextColor(getResources().getColor(R.color.color_364468));
                getBannerData(TimeType.TODAY.getType());
                month_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                year_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
            });
            month_rb.setOnClickListener(v -> {
                month_rb.setTextColor(getResources().getColor(R.color.color_364468));
                getBannerData(TimeType.THIS_MONTH.getType());
                today_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                year_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
            });
            year_rb.setOnClickListener(v -> {
                year_rb.setTextColor(getResources().getColor(R.color.color_364468));
                getBannerData(TimeType.THIS_YEAR.getType());
                today_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                month_rb.setTextColor(getResources().getColor(R.color.color_FFFFFF));
            });
        });


    }

    @Override
    public void requestNetwork() {
        getBannerData(TimeType.TODAY.getType());
        getIndex();
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
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //            case R.id.home_selected:
            //                //日期选择
            //                homeSelected();
            //                break;
            case R.id.titleRightBtn:
                //搜索按钮
                searchBtn();
                break;
            //            case R.id.home_eye:
            //眼睛
            //                homeEye(v, true);
            //                break;
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
                //                getBannerData();
                //                homeSelected.setText(getString(R.string.today));
                mBottomSheetDialog.dismiss();
                break;
            case R.id.hm_this_month:
                //当月
                //                getBannerData();
                //                homeSelected.setText(getString(R.string.this_month));
                mBottomSheetDialog.dismiss();
                break;
            case R.id.hm_this_year:
                //年
                //                getBannerData();
                //                homeSelected.setText(getString(R.string.this_year));
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
        ConstraintLayout constraintLayout = (ConstraintLayout) getActivity().getLayoutInflater().inflate(R.layout.show_menu_home_time, null);
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
    private void homeEye(View v, boolean flag) {
        //        LoginResponse.Item item = GlobaVariable.getInstance().item;
        //        if (item.getRoleId()) {
        //            eye.setVisibility(View.GONE);
        //            contract_money.setVisibility(View.GONE);
        //            receivables.setVisibility(View.GONE);
        //        }
        String num = String.valueOf(0);
        String num1 = String.valueOf(0);
        AppCompatImageView imageView = (AppCompatImageView) v;
        if (!TextUtils.isEmpty(imageView.getTag().toString())
                && getString(R.string.view_state_display).equals(imageView.getTag().toString())
                && flag) {
            imageView.setTag(getString(R.string.view_state_hide));
            imageView.setImageResource(R.mipmap.home_eye_off);
            num = "****";
            num1 = "****";
        } else {
            NumberFormat numberFormat = new DecimalFormat("###,###,###.##");
            imageView.setTag(getString(R.string.view_state_display));
            imageView.setImageResource(R.mipmap.home_eye_on);
            if (headItem != null) {
                if (!TextUtils.isEmpty(headItem.getAllPayCount())) {
                    BigDecimal big1 = new BigDecimal(headItem.getAllPayCount());
                    num = numberFormat.format(big1);
                }
                if (!TextUtils.isEmpty(headItem.getUnPayCount())) {
                    BigDecimal big2 = new BigDecimal(headItem.getUnPayCount());
                    num1 = numberFormat.format(big2);
                }
            }
        }
        //        String resStr = getString(R.string.contract_money, num);
        //        Logger.d("resStr:" + resStr);
        //        int start = resStr.length() - num.length();
        //        int end = resStr.length();
        //        contract_money.setText(Utils.getSplicing(getActivity(), resStr, start, end, 32));

        //        String resStr1 = getString(R.string.receivables, num1);
        //        Logger.d("resStr1:" + resStr1);
        //        int start1 = resStr1.length() - num1.length();
        //        int end1 = resStr1.length();
        //        receivables.setText(Utils.getSplicing(getActivity(), resStr1, start1, end1, 32));
    }


    /**
     * 今日会议
     */
    private void todayMeetin() {
        startActivity(new Intent(getActivity(), TodayMeetingActivity.class));
    }

    /**
     * 我的关注
     */
    private void myAttention() {
        startActivity(new Intent(getActivity(), FollowMeetingsActivity.class));
    }

    /**
     * 待办会议
     */
    private void pendingConference() {
        startActivity(new Intent(getActivity(), WaitMeetingsActivity.class));
    }

    /**
     * 创建会议
     */
    private void createMeeting() {
        startActivity(new Intent(getActivity(), CreateMeetingAndServiceActivity.class));
    }

    /**
     * 获取(合同金额/已举办/待举办)
     *
     * @param type
     */
    private void getBannerData(int type) {
        GetBannerDataRequest getBannerDataRequest = new GetBannerDataRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            getBannerDataRequest.setUserId(String.valueOf(item.getId()));
        }
        getBannerDataRequest.setType(String.valueOf(type));
        networkBroker.ask(getBannerDataRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetBannerDataResponse response = (GetBannerDataResponse) res;
                if (response.getCode() == 200) {
                    headItem = response.getData();

                    Integer all = Integer.valueOf(headItem.getAllMeetingCount());
                    total_pro.setMax(all);
                    total_pro.setProgress(all);
                    total_num.setText(headItem.getAllMeetingCount());
                    Integer over = Integer.valueOf(headItem.getOverMeetingCount());
                    complete_pro.setMax(all);
                    complete_pro.setProgress(over);
                    complete_num.setText(headItem.getOverMeetingCount());
                    int no_over = all - over;
                    no_complete_pro.setMax(all);
                    no_complete_pro.setProgress(no_over);
                    no_complete_num.setText(String.valueOf(no_over));

                    //                    setCountUI();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 场次赋值
     * setCountUI()
     */
    private void setCountUI() {
        String num2 = String.valueOf(0);
        if (headItem != null) {
            num2 = String.valueOf(headItem.getAllMeetingCount());
        }
        String resStr2 = getString(R.string.home_total, num2);
        int start2 = 0;
        int end2 = num2.length();
        total.setText(Utils.getSplicing(getActivity(), resStr2, start2, end2, 25));
        String num3 = String.valueOf(0);
        if (headItem != null) {
            num3 = String.valueOf(headItem.getOverMeetingCount());
        }
        String resStr3 = getString(R.string.have_been_held, num3);
        int start3 = 0;
        int end3 = num3.length();
        have_been_held.setText(Utils.getSplicing(getActivity(), resStr3, start3, end3, 25));
    }


    /**
     * 获取首页数据【今日会议、我的关注、待办会议】条数
     */
    private void getIndex() {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            getIndexRequest.setUserId(String.valueOf(item.getId()));
        }
        networkBroker.ask(getIndexRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetIndexResponse response = (GetIndexResponse) res;
                if (response.getCode() == 200) {
                    GetIndexResponse.Item gItem = response.getData();
                    if (gItem != null) {
                        oday_meeting_num.setText(String.valueOf(gItem.getTodayMeetingsCount()));
                        my_attention_num.setText(String.valueOf(gItem.getMyFollowMeetingsCount()));
                        conference_num.setText(String.valueOf(gItem.getWaitMeetingsCount()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}

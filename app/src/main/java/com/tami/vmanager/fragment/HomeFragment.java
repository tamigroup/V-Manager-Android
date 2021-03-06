package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.CreateMeetingRewriteActivity;
import com.tami.vmanager.activity.FollowMeetingsActivity;
import com.tami.vmanager.activity.HomeActivity;
import com.tami.vmanager.activity.MeetingOverviewActivity;
import com.tami.vmanager.activity.MyCreateActivity;
import com.tami.vmanager.activity.SearchActivity;
import com.tami.vmanager.activity.TodayMeetingActivity;
import com.tami.vmanager.activity.WaitMeetingsActivity;
import com.tami.vmanager.application.TaMiApplication;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.GetBannerDataRequest;
import com.tami.vmanager.entity.GetBannerDataResponse;
import com.tami.vmanager.entity.GetIndexRequest;
import com.tami.vmanager.entity.GetIndexResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SetUserRegistrationIdRequestBean;
import com.tami.vmanager.entity.SetUserRegistrationIdResponseBean;
import com.tami.vmanager.enums.TimeType;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;

import com.tami.vmanager.message.MessageEvent;
import com.tami.vmanager.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by why on 2018/6/12.
 */
public class HomeFragment extends BaseFragment implements HomeFragmentListener {

    private TextView total;//共计场次
    private TextView have_been_held;//已举办场次
    private ConstraintLayout today_meeting_layout;//今日会议布局
    private TextView oday_meeting_num;//今日会议数
    private ConstraintLayout my_attention_layout;//我的关注布局
    private TextView my_attention_num;//我的关注数
    private ConstraintLayout conference_layout;//待办会议布局
    private TextView conference_num;//待办会议数
    private ConstraintLayout myCreateLayout;//我的创建
    private TextView myCreateNum;//我的创建数
    private AppCompatImageView createMeeting;

    private BottomSheetDialog mBottomSheetDialog;
    private NetworkBroker networkBroker;
    //顶部数据处理
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
        total = findViewById(R.id.home_total);
        have_been_held = findViewById(R.id.home_have_been_held);
        today_meeting_layout = findViewById(R.id.home_today_meeting_layout);
        oday_meeting_num = findViewById(R.id.home_today_meeting_num);
        my_attention_layout = findViewById(R.id.home_my_attention_layout);
        my_attention_num = findViewById(R.id.home_my_attention_num);
        conference_layout = findViewById(R.id.home_pending_conference_layout);
        conference_num = findViewById(R.id.home_pending_conference_num);
        createMeeting = findViewById(R.id.home_create_meeting);

        myCreateLayout = findViewById(R.id.home_my_create_layout);
        myCreateNum = findViewById(R.id.home_my_create_num);

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
        today_meeting_layout.setOnClickListener(this);
        my_attention_layout.setOnClickListener(this);
        conference_layout.setOnClickListener(this);
        myCreateLayout.setOnClickListener(this);
        createMeeting.setOnClickListener(this);
        ((HomeActivity) getActivity()).setHomeFragmentListener(this);
    }

    @Override
    public void initData() {
        networkBroker = new NetworkBroker(getActivity());
        networkBroker.setCancelTag(getTAG());

        //隐藏返回按钮
        setTitleLeftBtnVisibility(View.GONE);
        //设置右边功能按钮图片
        setTitleRightBtn(R.mipmap.home_search);

        time_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            today_rb = group.findViewById(R.id.today_rb);
            month_rb = group.findViewById(R.id.month_rb);
            year_rb = group.findViewById(R.id.year_rb);
            today_rb.setOnClickListener(v -> {
                today_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_364468));
                getBannerData(TimeType.TODAY.getType());
                month_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
                year_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
            });
            month_rb.setOnClickListener(v -> {
                month_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_364468));
                getBannerData(TimeType.THIS_MONTH.getType());
                today_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
                year_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
            });
            year_rb.setOnClickListener(v -> {
                year_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_364468));
                getBannerData(TimeType.THIS_YEAR.getType());
                today_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
                month_rb.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
            });
        });


    }

    @Override
    public void requestNetwork() {
        getBannerData(TimeType.TODAY.getType());
        getIndex();
        handler.sendEmptyMessage(100);
    }

    @Override
    public void removeListener() {
        ((HomeActivity) getActivity()).removeHomeFragmentListener();
    }

    @Override
    public void emptyObject() {
        handler.removeCallbacksAndMessages(null);
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
            case R.id.titleRightBtn:
                //搜索按钮
                searchBtn();
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
            case R.id.home_my_create_layout:
                //我的创建
                myCreate(0);
                break;
            case R.id.home_create_meeting:
                //主页创建会议
                createMeeting();
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
     * 我的创建
     */
    private void myCreate(int meetingId) {
        Intent intent = new Intent(getActivity(), MyCreateActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        startActivity(intent);
    }


    /**
     * 创建会议
     */
    private void createMeeting() {
        Intent meetingIntent = new Intent(getActivity(), CreateMeetingRewriteActivity.class);
        startActivityForResult(meetingIntent, Constants.CREATE_MEETING);
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
                    GetBannerDataResponse.Item headItem = response.getData();
                    total_pro.setMax(headItem.getAllMeetingCount());
                    total_pro.setProgress(headItem.getAllMeetingCount());
                    total_num.setText(String.valueOf(headItem.getAllMeetingCount()));
                    complete_pro.setMax(headItem.getAllMeetingCount());
                    complete_pro.setProgress(headItem.getOverMeetingCount());
                    complete_num.setText(String.valueOf(headItem.getOverMeetingCount()));
                    int no_over = headItem.getAllMeetingCount() - headItem.getOverMeetingCount();
                    no_complete_pro.setMax(headItem.getAllMeetingCount());
                    no_complete_pro.setProgress(no_over);
                    no_complete_num.setText(String.valueOf(no_over));
                } else {
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
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
                        if (gItem.getMyCreateMeetingCount() == 0) {//如果我的创建为0不显示
                            myCreateLayout.setVisibility(View.GONE);
                        } else {
                            myCreateLayout.setVisibility(View.VISIBLE);
                            myCreateNum.setText(String.valueOf(gItem.getMyCreateMeetingCount()));
                        }
                    }
                } else {
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("HomeFragment---->requestCode:" + requestCode);
        Logger.d("HomeFragment---->resultCode:" + resultCode);
        if (requestCode == Constants.CREATE_MEETING) {
            int meetingId = 0;
            switch (resultCode) {
                case Constants.CANCEL_CREATE_FLOW:
                    refreshHome();
//                    myCreate(meetingId);
                    break;
                case Constants.RESULT_CREATE_FLOW:
                    refreshHome();
                    if (data != null) {
                        meetingId = data.getIntExtra(Constants.KEY_MEETING_ID, 0);
                    }
                    Intent intent = new Intent(getActivity(), MeetingOverviewActivity.class);
                    intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 刷新首页信息
     */
    private void refreshHome() {
        EventBus.getDefault().post(new MessageEvent(true));
        getIndex();
        getBannerData(getGroupIndex());
    }

    @Override
    public void tabOnCLick() {
        getIndex();
        getBannerData(getGroupIndex());
    }

    /**
     * 获取选中下标
     *
     * @return
     */
    private int getGroupIndex() {
        int index = 0;
        switch (time_radio_group.getCheckedRadioButtonId()) {
            case R.id.today_rb:
                index = 0;
                break;
            case R.id.month_rb:
                index = 1;
                break;
            case R.id.year_rb:
                index = 2;
                break;
        }
        return index;
    }

    /**
     * 是否显示会议创建
     */
    private void initCreateMetting() {
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            setTitleName(item.getSystemName());
            List<LoginResponse.Item.UserRole> roleList = item.getUserRoleList();
            if (roleList != null && roleList.size() > 0) {
                boolean visibility = false;
                for (LoginResponse.Item.UserRole userRole : roleList) {
                    if (userRole != null) {
                        if (userRole.roleId == 2 || userRole.roleId == 11) {
                            visibility = true;
                            break;
                        } else {
                            visibility = false;
                        }
                    }
                }
                if (visibility) {
                    createMeeting.setVisibility(View.VISIBLE);
                } else {
                    createMeeting.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initCreateMetting();
        getIndex();
        getBannerData(getGroupIndex());
    }

    /**
     * 绑定用户RegistrationId
     */
    private void bindRegistrationId() {
        String registrationID = TaMiApplication.registrationID;
        if (!TextUtils.isEmpty(registrationID)) {
            Logger.d("测试数据------------------------------------------》有");
            LoginResponse.Item item = GlobaVariable.getInstance().item;
            SetUserRegistrationIdRequestBean setUserRegistrationIdRequestBean = new SetUserRegistrationIdRequestBean();
            setUserRegistrationIdRequestBean.setUserId(item.getId());
            setUserRegistrationIdRequestBean.setRegistrationId(registrationID);
            networkBroker.ask(setUserRegistrationIdRequestBean, (exl, res) -> {
                if (null != exl) {
                    Logger.d(exl.getMessage() + "-" + exl);
                    return;
                }
                try {
                    SetUserRegistrationIdResponseBean response = (SetUserRegistrationIdResponseBean) res;
                    if (response.getCode() == 200) {
                        boolean data = response.isData();
                        if (!data) {
                            showToast("通知会收不到，请您重新登录");
                        }
                        Logger.e("绑定极光：" + data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SPUtils.save(Constants.FILE_KEY, Constants.SAVE_LOGIN_DATA, item);
                SPUtils.put(getActivity(), Constants.TOKEN, item.getToken());
                SPUtils.put(getActivity(), Constants.AUTO_LOGIN, true);
            });
        } else {
            Logger.d("测试数据------------------------------------------》无");
            handler.sendEmptyMessageDelayed(100, 1000);
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                bindRegistrationId();
            }
        }
    };
}

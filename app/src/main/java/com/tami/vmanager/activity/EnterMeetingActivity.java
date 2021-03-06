package com.tami.vmanager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.TimeLineHorizontalAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
import com.tami.vmanager.entity.CheckGroupUserRequestBean;
import com.tami.vmanager.entity.CheckGroupUserResponseBean;
import com.tami.vmanager.entity.GetActualNumRequestBean;
import com.tami.vmanager.entity.GetActualNumResponseBean;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdRequest;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.entity.IntoGroupUserRequestBean;
import com.tami.vmanager.entity.IntoGroupUserResponseBean;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.MeetingStateView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 进入会议
 * Created by why on 2018/6/16.
 */
public class EnterMeetingActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private TextView meetingName;//会议名称
    private TextView meetingTime;//会议时间
    private TextView meetingRoom;//会议房间
    private TextView memhMponsor;//主办方
    private TextView meetingPersonnel;//会议人员及岗位

    private TextView predeterminedNumber;//预定人数
    private TextView bottomNumber;//保底人数
    private TextView actualNumber;//实到人数
    private TextView lookEO;//查看EO单

    private RecyclerView recyclerView;//时间轴

    private ConstraintLayout serviceGroup;//会议服务群
    private ConstraintLayout sponsorMember; //主办方成员
    private ConstraintLayout vipDetails;//VIP详情

    private int meetingId;//会议ID
    private GetMeetingResponse.Item meetingInfo;
    private NetworkBroker networkBroker;
    private List<GetMeetingItemsByMeetingIdResponse.Array.Item> listData;
    private TimeLineHorizontalAdapter adapter;
    private MeetingStateView meeting_status;
    private ImageView sale_phone;
    private ConfirmEnterMeetingDialog confirmEnterMeetingDialog;//弹框查看会议
    private int actualNum;
    private int userId;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_enter_meeting;
    }

    @Override
    public void initView() {
        //基本数据
        meetingName = findViewById(R.id.meeting_overview_meeting_name);
        meetingTime = findViewById(R.id.meeting_overview_meeting_time);
        meetingRoom = findViewById(R.id.meeting_overview_meeting_room);
        memhMponsor = findViewById(R.id.memh_sponsor);
        meetingPersonnel = findViewById(R.id.meeting_overview_meeting_personnel);
        sale_phone = findViewById(R.id.sale_phone);

        //人数与EO查看数据
        predeterminedNumber = findViewById(R.id.meeting_overview_predetermined_number);
        bottomNumber = findViewById(R.id.meeting_overview_bottom_number);
        actualNumber = findViewById(R.id.meeting_overview_actual_number);
        lookEO = findViewById(R.id.meeting_overview_look_eo);
        meeting_status = findViewById(R.id.meeting_status);
        recyclerView = findViewById(R.id.enter_meeting_recycler_view);
        //会议服务群
        serviceGroup = findViewById(R.id.enter_meeting_service_group_layout);
        //主办方成员
        sponsorMember = findViewById(R.id.enter_meeting_sponsor_member_layout);
        //VIP详情
        vipDetails = findViewById(R.id.enter_meeting_vip_details_layout);

        confirmEnterMeetingDialog = new ConfirmEnterMeetingDialog(this);
        confirmEnterMeetingDialog.setLeftStr(getString(R.string.view_only));
        confirmEnterMeetingDialog.setContentStr(getString(R.string.confirm_enter_the_meeting));
    }

    @Override
    public void initListener() {
        lookEO.setOnClickListener(this);
        actualNumber.setOnClickListener(this);
        serviceGroup.setOnClickListener(this);
        sponsorMember.setOnClickListener(this);
        vipDetails.setOnClickListener(this);
        sale_phone.setOnClickListener(this);
    }

    @Override
    public void initData() {
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (null != item) {
            userId = item.getId();
        }

        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            meetingInfo = (GetMeetingResponse.Item) intent.getSerializableExtra(Constants.MEETING_INFO);
            actualNum = intent.getIntExtra(Constants.ACTUAL_NUM, 0);
        }

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        setTitleName(R.string.get_into_meeting);

        listData = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new TimeLineHorizontalAdapter(listData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initUIdata(meetingInfo);

        confirmEnterMeetingDialog.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void leftBtn() {
                SPUtils.put(EnterMeetingActivity.this, Constants.IS_INVISIBLE, true);
                startConferenceServiceGroupActivity();
            }

            @Override
            public void rightBtn() {
                //进入
                SPUtils.put(EnterMeetingActivity.this, Constants.IS_INVISIBLE, false);
                serviceGroup();
            }
        });
    }

    @Override
    public void requestNetwork() {
        getMeetingItemsByMeetingId();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (confirmEnterMeetingDialog != null && confirmEnterMeetingDialog.isShowing()) {
            confirmEnterMeetingDialog.dismiss();
        }
        confirmEnterMeetingDialog = null;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        meetingInfo = null;
        if (listData != null) {
            listData.clear();
            listData = null;
        }
        sale_phone.setBackgroundResource(0);
        sale_phone = null;
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.meeting_overview_look_eo:
                //查看EO单
                lookEO();
                break;
            case R.id.meeting_overview_actual_number:
                //查看人员 实到人数
                Intent intent_personnel = new Intent(getApplicationContext(), PersonnelListActivity.class);
                intent_personnel.putExtra(Constants.KEY_MEETING_ID, meetingId);
                intent_personnel.putExtra(Constants.IS_VZHIHUI, meetingInfo.isVzh);
                startActivity(intent_personnel);
                break;
            case R.id.enter_meeting_service_group_layout:
                //会议服务群
                checkGroupUser();
                break;
            case R.id.enter_meeting_sponsor_member_layout:
                //主办方成员
                sponsorMember();
                break;
            case R.id.enter_meeting_vip_details_layout:
                //VIP详情
                vipDetails();
                break;
            case R.id.sale_phone:
                ConfirmEnterMeetingDialog cemd = new ConfirmEnterMeetingDialog(this);
                cemd.setContentStr(meetingInfo.salesUserMobile);
                cemd.setRightStr(getString(R.string.confirm));
                cemd.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
                    @Override
                    public void leftBtn() {
                    }

                    @Override
                    public void rightBtn() {
                        requiresPermission();
                    }
                });
                cemd.show();
                break;
        }
    }

    /**
     * 校验用户是否在群中
     */
    private void checkGroupUser() {
        CheckGroupUserRequestBean requestBean = new CheckGroupUserRequestBean();
        requestBean.setUserId(userId);
        requestBean.setMeetingId(meetingId);
        networkBroker.ask(requestBean, (exl, res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }
            try {
                CheckGroupUserResponseBean response = (CheckGroupUserResponseBean) res;
                if (response.getCode() == 200) {
                    if (response.isData()) {
                        startConferenceServiceGroupActivity();
                    }
                } else if (response.getCode() == 400) {
                    //不在群中
                    confirmEnterMeetingDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @AfterPermissionGranted(Constants.CALL_PHONE_REQUEST_CODE)
    private void requiresPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.CALL_PHONE};
            if (EasyPermissions.hasPermissions(EnterMeetingActivity.this, mPermissionList)) {
                call();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.phone_permission), Constants.CALL_PHONE_REQUEST_CODE, mPermissionList);
            }
        } else {
            call();
        }
    }

    private void call() {
        Intent intent_phone = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + meetingInfo.salesUserMobile);
        intent_phone.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Logger.e("权限拒绝---");
            return;
        }
        startActivity(intent_phone);
    }

    /**
     * 页面UI赋值
     */
    private void initUIdata(GetMeetingResponse.Item item) {
        if (item != null) {
            meetingName.setText(item.meetingName);
//            StringBuilder time = new StringBuilder();
//            String startTime = TimeUtils.milliseconds2String(item.startTime,TimeUtils.DATE_MMDDHHMM_SLASH);
//            time.append(startTime);
//            time.append(" - ");
//            String endTime = TimeUtils.milliseconds2String(item.endTime,TimeUtils.DATE_MMDDHHMM_SLASH);
//            time.append(endTime);
//            meetingTime.setText(time.toString());
            meetingTime.setText(item.autoDayTime);
            meetingRoom.setText(item.meetingAddress);
            memhMponsor.setText(String.format(getString(R.string.host_name), item.sponsorName));
            meetingPersonnel.setText(String.format(getString(R.string.salename), item.saleUserName));
            meeting_status.setMeetingStateText(item.meetingStatus, 20);
            if (TextUtils.isEmpty(item.cancelStatus)) {
                meeting_status.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(item.meetingStatus)) {
                    meeting_status.setMeetingStateText(getString(R.string.daiban), 20);
                } else {
                    meeting_status.setMeetingStateText(item.meetingStatus, 20);
                }
            } else {
                meeting_status.setVisibility(View.GONE);
            }
            initUITxt(predeterminedNumber, String.valueOf(item.estimateNum), R.string.predetermined_number, android.R.color.white);
            initUITxt(bottomNumber, String.valueOf(item.minNum), R.string.bottom_number, android.R.color.white);

            //V智慧判断
            if (item.isVzh == 1) {
                initUITxt(actualNumber, String.valueOf(actualNum), R.string.actual_number, R.color.color_FF5657);
                handler.sendEmptyMessage(2);
            } else {
                initUITxt(actualNumber, "--", R.string.actual_number, R.color.color_FF5657);
            }

            if (TextUtils.isEmpty(item.eoUrl)) {
                lookEO.setText(getString(R.string.no_eo_single));
                lookEO.setEnabled(false);
            }
        }
    }

    /**
     * 人数控件赋值
     *
     * @param tv
     * @param num
     * @param strId
     * @param colorId
     */
    private void initUITxt(TextView tv, @NonNull String num, @StringRes int strId, @ColorRes int colorId) {
        String resStr = getString(strId, num);
        tv.setText(Utils.getSplicing(getApplicationContext(), resStr, 0, num.length(), 16, colorId));
    }

    /**
     * 查看EO单
     */
    private void lookEO() {
        Intent intent = new Intent(getApplicationContext(), LookEOActivity.class);
        intent.putExtra(Constants.KEY_EO_URL, meetingInfo.eoUrl);
        startActivity(intent);
    }

    /**
     * 会议服务群
     */
    private void serviceGroup() {
        IntoGroupUserRequestBean requestBean = new IntoGroupUserRequestBean();
        requestBean.setUserId(userId);
        requestBean.setMeetingId(meetingId);
        networkBroker.ask(requestBean, (exl, res) -> {
            if (null != exl) {
                Logger.d(exl.getMessage() + "-" + exl);
                return;
            }
            try {
                IntoGroupUserResponseBean response = (IntoGroupUserResponseBean) res;
                if (response.getCode() == 200) {
                    if (response.isData()) {
                        startConferenceServiceGroupActivity();
                    }
                } else if (response.getCode() == 400) {
                    showToast(R.string.has_in_meeting);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 跳转会议服务群
     */
    private void startConferenceServiceGroupActivity() {
        Intent intent = new Intent(getApplicationContext(), ConferenceServiceGroupActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        intent.putExtra(Constants.ACTUAL_NUM, actualNum);
        intent.putExtra(Constants.MEETING_INFO, meetingInfo);
        startActivity(intent);
    }

    /**
     * 主办方成员
     */
    private void sponsorMember() {
        Intent intent_sponsorMember = new Intent(getApplicationContext(), SponsorMemberActivity.class);
        intent_sponsorMember.putExtra(Constants.KEY_MEETING_ID, meetingId);
        intent_sponsorMember.putExtra(Constants.IS_VZHIHUI, meetingInfo.isVzh);
        startActivity(intent_sponsorMember);
    }

    /**
     * VIP详情
     */
    private void vipDetails() {
        Intent intent = new Intent(getApplicationContext(), VIPDetailsActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        intent.putExtra(Constants.MEETING_INFO, meetingInfo);
        startActivity(intent);
    }


    /**
     * 根据会议ID查询会议节点信息
     */
    private void getMeetingItemsByMeetingId() {
        GetMeetingItemsByMeetingIdRequest gmibmir = new GetMeetingItemsByMeetingIdRequest();
        gmibmir.setMeetingId(meetingId);
        networkBroker.ask(gmibmir, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemsByMeetingIdResponse response = (GetMeetingItemsByMeetingIdResponse) res;
                if (response.getCode() == 200) {
                    GetMeetingItemsByMeetingIdResponse.Array array = response.data;
                    if (array != null && array.dataList != null && array.dataList.size() > 0) {
                        //对时间轴赋值
                        if (listData.size() > 0) {
                            listData.clear();
                        }
                        listData.addAll(array.dataList);
                        adapter.notifyDataSetChanged();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        call();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                Logger.d("EnterMeetingActivity---handler---->");
                getActualNum();
            }
        }
    };

    /**
     * 获取实到人数
     */
    private void getActualNum() {
        GetActualNumRequestBean getActualNumRequestBean = new GetActualNumRequestBean();
        getActualNumRequestBean.setMeetingId(meetingId);
        networkBroker.ask(getActualNumRequestBean, false, (ex1, res) -> {
            handler.sendEmptyMessageDelayed(2, 5000);
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetActualNumResponseBean response = (GetActualNumResponseBean) res;
                if (response.getCode() == 200) {
                    GetActualNumResponseBean.DataBean data = response.getData();
                    if (data != null) {
                        actualNum = data.getActualNum();
                        initUITxt(actualNumber, String.valueOf(actualNum), R.string.actual_number, R.color.color_FF5657);
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
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMeetingItemsByMeetingId();
        if (meetingInfo != null && meetingInfo.isVzh == 1) {
            handler.sendEmptyMessage(2);
        }
    }
}

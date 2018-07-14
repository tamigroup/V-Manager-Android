package com.tami.vmanager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.TimeLineHorizontalAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetMeetingItemFlowRequest;
import com.tami.vmanager.entity.GetMeetingItemFlowResponse;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
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
    private TextView meetingDetails;//会议详情
    private TextView vipDetails;//VIP详情

    private int meetingId;//会议ID
    private GetMeetingResponse.Item item;
    private NetworkBroker networkBroker;
    private List<GetMeetingItemFlowResponse.Array.Item> listData;
    private TimeLineHorizontalAdapter adapter;
    private MeetingStateView meeting_status;
    private ImageView sale_phone;

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
        //会议详情
        meetingDetails = findViewById(R.id.enter_meeting_meeting_details);
        //VIP详情
        vipDetails = findViewById(R.id.enter_meeting_vip_details);

    }

    @Override
    public void initListener() {
        lookEO.setOnClickListener(this);
        actualNumber.setOnClickListener(this);
        serviceGroup.setOnClickListener(this);
        sponsorMember.setOnClickListener(this);
        meetingDetails.setOnClickListener(this);
        vipDetails.setOnClickListener(this);
        sale_phone.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            item = (GetMeetingResponse.Item) intent.getSerializableExtra(Constants.MEETING_INFO);
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

        initUIdata(item);
    }

    @Override
    public void requestNetwork() {
        getMeetingItemFlow();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (recyclerView != null) {
            recyclerView.removeAllViews();
            recyclerView = null;
        }
        networkBroker.cancelAllRequests();
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
                startActivity(intent_personnel);
                break;
            case R.id.enter_meeting_service_group_layout:
                //会议服务群
                serviceGroup();
                break;
            case R.id.enter_meeting_sponsor_member_layout:
                //主办方成员
                sponsorMember();
                break;
            case R.id.enter_meeting_meeting_details:
                //会议详情
                meetingDetails();
                break;
            case R.id.enter_meeting_vip_details:
                //VIP详情
                vipDetails();
                break;
            case R.id.sale_phone:

                requiresPermission();
                break;
        }
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
        Uri data = Uri.parse("tel:" + item.salesUserMobile);
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
            meetingTime.setText(item.autoDayTime);
            meetingRoom.setText(item.meetingAddress);
            memhMponsor.setText(String.format(getString(R.string.host_name), item.sponsorName));
            meetingPersonnel.setText(String.format(getString(R.string.salename), item.saleUserName));
            meeting_status.setMeetingStateText(item.meetingStatus, 20);

            initUITxt(predeterminedNumber, String.valueOf(item.estimateNum), R.string.predetermined_number, android.R.color.white);
            initUITxt(bottomNumber, String.valueOf(item.minNum), R.string.bottom_number, android.R.color.white);

            if (GlobaVariable.getInstance().item.getFromPlat() == 1) {
                initUITxt(actualNumber, String.valueOf(item.actualNum), R.string.actual_number, R.color.color_FF5657);
            } else {
                initUITxt(actualNumber, "--", R.string.actual_number, R.color.color_FF5657);
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
        intent.putExtra(Constants.KEY_EO_URL, item.eoUrl);
        startActivity(intent);
    }

    /**
     * 会议服务群
     */
    private void serviceGroup() {
        Intent intent = new Intent(getApplicationContext(), ConferenceServiceGroupActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        intent.putExtra(Constants.MEETING_INFO, item);
        startActivity(intent);
    }

    /**
     * 主办方成员
     */
    private void sponsorMember() {
        startActivity(new Intent(getApplicationContext(), SponsorMemberActivity.class));
    }

    /**
     * 会议详情
     */
    private void meetingDetails() {

    }

    /**
     * VIP详情
     */
    private void vipDetails() {
        Intent intent = new Intent(getApplicationContext(), VIPDetailsActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        intent.putExtra(Constants.MEETING_INFO, item);
        startActivity(intent);
    }

    /**
     * 获取会议流程单*
     */
    private void getMeetingItemFlow() {
        GetMeetingItemFlowRequest gmifr = new GetMeetingItemFlowRequest();
        gmifr.setMeetingId(meetingId);
        networkBroker.ask(gmifr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemFlowResponse response = (GetMeetingItemFlowResponse) res;
                if (response.getCode() == 200) {
                    GetMeetingItemFlowResponse.Array array = response.data;
                    if (array != null && array.dataList != null && array.dataList.size() > 0) {
                        listData.addAll(array.dataList);
                        adapter.notifyDataSetChanged();
                    }
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
}

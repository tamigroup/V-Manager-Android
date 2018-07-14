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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.TimeLineAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.AlreadyPaidItemDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdRequest;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.entity.GetMeetingRequest;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.CircleProgressBar;
import com.tami.vmanager.view.MeetingStateView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 会议概览
 * <p>
 * Created by why on 2018/6/14.
 */
public class MeetingOverviewActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private TextView meetingName;//会议名称
    private TextView meetingTime;//会议时间
    private TextView meetingRoom;//会议房间
    private TextView mmohSponsor;
    private TextView meetingPersonnel;//会议人员及岗位

    private TextView predeterminedNumber;//预定人数
    private TextView bottomNumber;//保底人数
    private TextView actualNumber;//实到人数
    private TextView lookEO;//查看EO单

    private CircleProgressBar alreadyPaidItem;//待付款项
    private TextView complaintsBox;//意见箱
    private TextView changeDemand;//需求变化
    private TextView vEmind;//小V提醒

    private ConstraintLayout xufuLayout;//悬浮布局
    private TextView editBtn;
    private TextView lookBtn;
    private TextView pleaseCreateConference;//提示文本

    private RecyclerView recyclerView;
    private Button functionBtn;//创建流程及进入会意按钮

    private ConfirmEnterMeetingDialog confirmEnterMeetingDialog;//弹框查看会议

    private int meetingId;//会议ID
    private GetMeetingResponse.Item item;//会议信息
    private NetworkBroker networkBroker;
    private List<GetMeetingItemsByMeetingIdResponse.Array.Item> listData;
    private TimeLineAdapter adapter;
    private MeetingStateView meeting_status;
    private ImageView sale_phone;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_overview;
    }

    @Override
    public void initView() {
        //基本数据
        meetingName = findViewById(R.id.meeting_overview_meeting_name);
        meetingTime = findViewById(R.id.meeting_overview_meeting_time);
        meetingRoom = findViewById(R.id.meeting_overview_meeting_room);
        mmohSponsor = findViewById(R.id.mmoh_sponsor);
        meetingPersonnel = findViewById(R.id.meeting_overview_meeting_personnel);
        meeting_status = findViewById(R.id.meeting_status);
        sale_phone = findViewById(R.id.sale_phone);
        //人数与EO查看数据
        predeterminedNumber = findViewById(R.id.meeting_overview_predetermined_number);
        bottomNumber = findViewById(R.id.meeting_overview_bottom_number);
        actualNumber = findViewById(R.id.meeting_overview_actual_number);
        lookEO = findViewById(R.id.meeting_overview_look_eo);
        //四个可点击图标
        alreadyPaidItem = findViewById(R.id.meeting_overview_already_paid_item);
        complaintsBox = findViewById(R.id.meeting_overview_complaints_box);
        changeDemand = findViewById(R.id.meeting_overview_change_demand);
        vEmind = findViewById(R.id.meeting_overview_v_emind);
        //悬浮布局
        xufuLayout = findViewById(R.id.meeting_overview_xuanfu_layout);
        editBtn = findViewById(R.id.meeting_overview_suspension_edit);
        lookBtn = findViewById(R.id.meeting_overview_suspension_look);
        //创建流程及进入会意按钮
        pleaseCreateConference = findViewById(R.id.please_create_conference);
        functionBtn = findViewById(R.id.meeting_overview_function_btn);

        recyclerView = findViewById(R.id.meeting_overview_recycler_view);

        confirmEnterMeetingDialog = new ConfirmEnterMeetingDialog(this);
        confirmEnterMeetingDialog.setLeftStr(getString(R.string.view_only));
        confirmEnterMeetingDialog.setContentStr(getString(R.string.confirm_enter_the_meeting));
    }

    @Override
    public void initListener() {
        lookEO.setOnClickListener(this);
        alreadyPaidItem.setOnClickListener(this);
        complaintsBox.setOnClickListener(this);
        changeDemand.setOnClickListener(this);
        vEmind.setOnClickListener(this);
        functionBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        lookBtn.setOnClickListener(this);
        actualNumber.setOnClickListener(this);
        sale_phone.setOnClickListener(this);

        confirmEnterMeetingDialog.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void leftBtn() {
                SPUtils.put(MeetingOverviewActivity.this, Constants.IS_INVISIBLE, true);
                Intent intent = new Intent(getApplicationContext(), EnterMeetingActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                intent.putExtra(Constants.MEETING_INFO, item);
                startActivity(intent);
            }

            @Override
            public void rightBtn() {
                //进入
                SPUtils.put(MeetingOverviewActivity.this, Constants.IS_INVISIBLE, false);
                Intent intent = new Intent(getApplicationContext(), EnterMeetingActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                intent.putExtra(Constants.MEETING_INFO, item);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_ganlan);

        initListView();

        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }
        meetingId = 15;

        recyclerView.setVisibility(View.VISIBLE);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        getMeeting();
        getMeetingItemsByMeetingId();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        alreadyPaidItem = null;
        if (confirmEnterMeetingDialog != null && confirmEnterMeetingDialog.isShowing()) {
            confirmEnterMeetingDialog.dismiss();
        }
        confirmEnterMeetingDialog = null;
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.meeting_overview_look_eo:
                //查看EO单
                Intent lookEOIntent = new Intent(getApplicationContext(), LookEOActivity.class);
                if (item != null) {
                    lookEOIntent.putExtra(Constants.KEY_EO_URL, item.eoUrl);
                }
                startActivity(lookEOIntent);
                break;
            case R.id.meeting_overview_actual_number:
                //实到人数
                Intent intent_personnel = new Intent(getApplicationContext(), PersonnelListActivity.class);
                intent_personnel.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intent_personnel);
                break;
            case R.id.meeting_overview_already_paid_item:
                //待付款项
                alreadyPaidItem();
                break;
            case R.id.meeting_overview_complaints_box:
                //意见箱 满意度
                Intent intent = new Intent(getApplicationContext(), IdeasBoxActivity.class);
                intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intent);
                break;
            case R.id.meeting_overview_change_demand:
                //需求变化
                Intent intent1 = new Intent(getApplicationContext(), ChangeDemandActivity.class);
                intent1.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivity(intent1);
                break;
            case R.id.meeting_overview_v_emind:
                //小V提醒
                startActivity(new Intent(getApplicationContext(), SmallVRemindingActivity.class));
                break;
            case R.id.meeting_overview_suspension_edit:
                //悬浮布局中按钮
                editFlow();
                break;
            case R.id.meeting_overview_suspension_look:
                //悬浮布局中按钮
                lookFlow();
                break;
            case R.id.meeting_overview_function_btn:
                //功能按钮
                functionBtn(v);
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
            if (EasyPermissions.hasPermissions(MeetingOverviewActivity.this, mPermissionList)) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.CREATE_FLOW:
                //创建服务流程返回
                getMeetingItemsByMeetingId();
                break;
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                call();
                break;
        }
    }

    private void lookFlow() {
        showToast("查看流程单");
    }

    /**
     * 编辑流程
     */
    private void editFlow() {
        Intent intent = new Intent(getApplicationContext(), CreateServiceFlowActivity.class);
        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
        startActivity(intent);
    }

    /**
     * 页面UI赋值
     */
    private void initUIdata(GetMeetingResponse.Item item) {
        if (item != null) {
            this.item = item;
            meetingName.setText(item.meetingName);
            meetingTime.setText(item.autoDayTime);
            meetingRoom.setText(item.meetingAddress);
            mmohSponsor.setText(String.format(getString(R.string.host_name), item.sponsorName));
            meetingPersonnel.setText(String.format(getString(R.string.salename), item.saleUserName));
            meeting_status.setMeetingStateText(item.meetingStatus, 20);
            initUITxt(predeterminedNumber, String.valueOf(item.estimateNum), R.string.predetermined_number, android.R.color.white);
            initUITxt(bottomNumber, String.valueOf(item.minNum), R.string.bottom_number, android.R.color.white);

            if (GlobaVariable.getInstance().item.getFromPlat() == 1) {
                initUITxt(actualNumber, String.valueOf(item.actualNum), R.string.actual_number, R.color.color_FF5657);
            } else {
                initUITxt(actualNumber, "--", R.string.actual_number, R.color.color_FF5657);
            }


            alreadyPaidItem.setProgress(90);
        }

        //        new AsyncTask<Void, Integer, Void>() {
        //
        //            @Override
        //            protected void onProgressUpdate(Integer... values) {
        //                super.onProgressUpdate(values);
        //                alreadyPaidItem.setProgress(values[0]);
        //            }
        //
        //            @Override
        //            protected Void doInBackground(Void... params) {
        //                for (int i = 0; i <= 90; i += 10) {
        //                    try {
        //                        Thread.sleep(50);
        //                    } catch (InterruptedException e) {
        //                        e.printStackTrace();
        //                    }
        //                    publishProgress(i);
        //                }
        //                return null;
        //            }
        //        }.execute();
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
     * 初始化时间轴
     */
    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listData = new ArrayList<>();
        adapter = new TimeLineAdapter(listData, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 待付款项
     */
    private void alreadyPaidItem() {
        AlreadyPaidItemDialog alreadyPaidItemDialog = new AlreadyPaidItemDialog(this, "4000", "1000");
        alreadyPaidItemDialog.show();
    }

    /**
     * 底部功能按钮
     *
     * @param v
     */
    private void functionBtn(View v) {
        Button button = (Button) v;
        if (!TextUtils.isEmpty(button.toString())
                && getString(R.string.create_process).equals(button.getText().toString())) {
            //创建
            Intent flowIntent = new Intent(getApplicationContext(), CreateServiceFlowActivity.class);
            flowIntent.putExtra(Constants.KEY_MEETING_ID, meetingId);
            startActivityForResult(flowIntent, Constants.CREATE_FLOW);
        } else {
            confirmEnterMeetingDialog.show();
        }
    }

    /**
     * 获取会议信息
     */
    private void getMeeting() {
        GetMeetingRequest gmr = new GetMeetingRequest();
        gmr.setMeetingId(meetingId);
        networkBroker.ask(gmr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingResponse response = (GetMeetingResponse) res;
                if (response.getCode() == 200) {
                    if (response != null) {
                        GetMeetingResponse.Item meetingItem = response.data;
                        initUIdata(meetingItem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
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
                    if (response != null) {
                        GetMeetingItemsByMeetingIdResponse.Array array = response.data;
                        if (array != null && array.dataList != null && array.dataList.size() > 0) {
                            //对时间轴赋值
                            if (listData.size() > 0) {
                                listData.clear();
                            }
                            listData.addAll(array.dataList);
                            adapter.notifyDataSetChanged();
                            pleaseCreateConference.setVisibility(View.GONE);
                            xufuLayout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            functionBtn.setText(getString(R.string.get_into_meeting));
                        } else {
                            xufuLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            pleaseCreateConference.setVisibility(View.VISIBLE);
                            functionBtn.setText(getString(R.string.create_process));
                        }
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

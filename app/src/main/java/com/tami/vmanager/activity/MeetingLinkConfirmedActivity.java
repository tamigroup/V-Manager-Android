package com.tami.vmanager.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateVipGuestRequest;
import com.tami.vmanager.entity.CreateVipGuestResponse;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.entity.GetSelectMeetingItemsUserRequest;
import com.tami.vmanager.entity.GetSelectMeetingItemsUserResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.SwitchButton;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 会议环节待确认
 * Created by why on 2018/6/15.
 */

public class MeetingLinkConfirmedActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private TextView content;
    private RecyclerView recyclerView;
    private TextView addPerson;
    private SwitchButton switchbtn;
    private TextView important;
    private Button confirmBtn;
    private List<String> listData;
    private CommonAdapter commonAdapter;
    private GetMeetingItemsByMeetingIdResponse.Array.Item item;//环节信息

    public static final int REQUEST_CALL_PHONE = 5;
    public static final String[] PERMISSIONS_CALL_PHONE = {Manifest.permission.CALL_PHONE};

    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_link_confirmed;
    }

    @Override
    public void initView() {
        content = findViewById(R.id.confirmation_content_txt);
        recyclerView = findViewById(R.id.meeting_link_confirmed_listview);
        addPerson = findViewById(R.id.mlc_add_the_person_in_charge);
        switchbtn = findViewById(R.id.mlc_switchbtn);
        important = findViewById(R.id.mlc_important);
        confirmBtn = findViewById(R.id.mlc_confirm_btn);
    }

    @Override
    public void initListener() {
        switchbtn.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
            //TODO do your job
            Toast.makeText(getApplicationContext(), String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
        });
        addPerson.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_link_confirmed);

        Intent intent = getIntent();
        if (intent != null) {
            item = (GetMeetingItemsByMeetingIdResponse.Array.Item) intent.getSerializableExtra(Constants.KEY_MEETING_LINK);
            if (item != null) {
                content.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH) + item.meetingItemName);
            }
        }

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.percentage_10)));
        listData = new ArrayList<>();
        commonAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.item_meeting_link_confirmed, listData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView distribution = holder.getView(R.id.mlc_distribution);
                AppCompatImageView phone = holder.getView(R.id.mlc_phone);
                TextView nameView = holder.getView(R.id.mlc_name);
                TextView positionView = holder.getView(R.id.mlc_position);
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    @AfterPermissionGranted(REQUEST_CALL_PHONE)
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            //权限请求与判断
                            if (EasyPermissions.hasPermissions(getApplicationContext(), PERMISSIONS_CALL_PHONE)) {
                                callPhone("15901125418");
                            } else {
                                EasyPermissions.requestPermissions(MeetingLinkConfirmedActivity.this, getString(R.string.app_name),
                                        REQUEST_CALL_PHONE, PERMISSIONS_CALL_PHONE);
                            }
                        } else {
                            callPhone("15901125418");
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void requestNetwork() {
        getSelectMeetingItemsUser();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mlc_add_the_person_in_charge:
                Intent intent = new Intent(getApplicationContext(), AddPersonChargeActivty.class);
                startActivityForResult(intent, Constants.ADD_PERSON_CHARGE);
                break;
        }
    }

    @Override
    public void emptyObject() {
        switchbtn = null;
        networkBroker.cancelAllRequests();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ADD_PERSON_CHARGE) {
            if (data != null) {
                boolean flag = data.getBooleanExtra(Constants.RESULT_JIEDAIREN, false);
                if (flag) {
                    getSelectMeetingItemsUser();
                }
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * 获取流程节点已分配人员
     */
    private void getSelectMeetingItemsUser() {
        GetSelectMeetingItemsUserRequest gsmiur = new GetSelectMeetingItemsUserRequest();
        gsmiur.setMeetingItemSetId(item.meetingItemSetId);
        networkBroker.ask(gsmiur, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetSelectMeetingItemsUserResponse response = (GetSelectMeetingItemsUserResponse) res;
                if (response.getCode() == 200) {
                    if (listData != null) {
                        listData.clear();
                    }
//                    listData.addAll();
                    commonAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

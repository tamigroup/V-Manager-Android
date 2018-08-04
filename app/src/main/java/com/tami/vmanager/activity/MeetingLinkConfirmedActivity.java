package com.tami.vmanager.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
import com.tami.vmanager.entity.CheckAddMeetingItemUserRequest;
import com.tami.vmanager.entity.CheckAddMeetingItemUserResponse;
import com.tami.vmanager.entity.DeleteMeetingItemsUserRequest;
import com.tami.vmanager.entity.DeleteMeetingItemsUserResponse;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.entity.GetSelectMeetingItemsUserRequest;
import com.tami.vmanager.entity.GetSelectMeetingItemsUserResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SetMeetingItemsStatusRequest;
import com.tami.vmanager.entity.SetMeetingItemsStatusResponse;
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
    private ConstraintLayout constraintLayout;
    private TextView addPerson;
    private SwitchButton switchbtn;
    private TextView important;
    private Button confirmBtn;
    private List<GetSelectMeetingItemsUserResponse.Array.Item> listData;
    private CommonAdapter<GetSelectMeetingItemsUserResponse.Array.Item> commonAdapter;
    private GetMeetingItemsByMeetingIdResponse.Array.Item gmibmirItem;//环节信息

    public static final int REQUEST_CALL_PHONE = 5;
    public static final String[] PERMISSIONS_CALL_PHONE = {Manifest.permission.CALL_PHONE};

    private NetworkBroker networkBroker;

    private ConfirmEnterMeetingDialog confirmEnterMeetingDialog;//弹框查看会议
    private int meetingId;//会议ID

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
        constraintLayout = findViewById(R.id.mlc_have_problem);
        switchbtn = findViewById(R.id.mlc_switchbtn);
        important = findViewById(R.id.mlc_important);
        confirmBtn = findViewById(R.id.mlc_confirm_btn);

        confirmEnterMeetingDialog = new ConfirmEnterMeetingDialog(this);
        confirmEnterMeetingDialog.setLeftStr(getString(R.string.confirm));
        confirmEnterMeetingDialog.setLeftColor(R.color.color_FF5657);
        confirmEnterMeetingDialog.setRightStr(getString(R.string.cancel));
        confirmEnterMeetingDialog.setContentStr(getString(R.string.please_confirm_service_process_completed));
    }

    @Override
    public void initListener() {
        switchbtn.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
            //TODO do your job
            important.setText(isChecked ? getString(R.string.nothing) : getString(R.string.yes));
        });
        addPerson.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        confirmEnterMeetingDialog.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void leftBtn() {
                setMeetingItemsStatus();
            }

            @Override
            public void rightBtn() {

            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_link_confirmed);

        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            gmibmirItem = (GetMeetingItemsByMeetingIdResponse.Array.Item) intent.getSerializableExtra(Constants.KEY_MEETING_LINK);
            if (gmibmirItem != null) {
                if (gmibmirItem.startOn != 0) {
                    content.setText(TimeUtils.date2String(new Date(gmibmirItem.startOn), TimeUtils.DATE_HHMM_SLASH) + gmibmirItem.meetingItemName);
                }
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
        commonAdapter = new CommonAdapter<GetSelectMeetingItemsUserResponse.Array.Item>(getApplicationContext(), R.layout.item_meeting_link_confirmed, listData) {
            @Override
            protected void convert(ViewHolder holder, GetSelectMeetingItemsUserResponse.Array.Item item, int position) {
                holder.setText(R.id.mlc_distribution, getString(R.string.distributive_personnel) + " " + (position + 1));
                holder.setText(R.id.mlc_name, item.realName);
                holder.setText(R.id.mlc_position, item.positionName);
                AppCompatImageView phone = holder.getView(R.id.mlc_phone);
                AppCompatImageView delete_person = holder.getView(R.id.delete_person_);
                if (gmibmirItem.selectStatus == 1) {
                    delete_person.setVisibility(View.GONE);
                } else {
                    delete_person.setVisibility(View.VISIBLE);
                    delete_person.setOnClickListener((View v) -> {
                        checkAddMeetingItemUser(position, item.id);
                    });
                }
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    @AfterPermissionGranted(REQUEST_CALL_PHONE)
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            //权限请求与判断
                            if (EasyPermissions.hasPermissions(getApplicationContext(), PERMISSIONS_CALL_PHONE)) {
                                callPhone(item.mobile);
                            } else {
                                EasyPermissions.requestPermissions(MeetingLinkConfirmedActivity.this, getString(R.string.phone_permission),
                                        REQUEST_CALL_PHONE, PERMISSIONS_CALL_PHONE);
                            }
                        } else {
                            callPhone(item.mobile);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //已确认隐藏功能按钮
        if (gmibmirItem.selectStatus == 1) {
            setTitleName(R.string.the_meeting_has_been_confirmed);
            constraintLayout.setVisibility(View.GONE);
            addPerson.setVisibility(View.GONE);
            confirmBtn.setVisibility(View.GONE);
        } else if (gmibmirItem.selectStatus == 2) {
            switchbtn.setChecked(false);
            important.setText(getString(R.string.yes));
            confirmBtn.setText(getString(R.string.again_identification));
        }
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
                checkAddMeetingItemUser(-1, 0);
                break;
            case R.id.mlc_confirm_btn:
                confirmEnterMeetingDialog.show();
                break;
        }
    }

    @Override
    public void emptyObject() {
        if (confirmEnterMeetingDialog != null && confirmEnterMeetingDialog.isShowing()) {
            confirmEnterMeetingDialog.dismiss();
        }
        confirmEnterMeetingDialog = null;
        switchbtn = null;
        if (listData != null) {
            listData.clear();
            listData = null;
        }
        gmibmirItem = null;
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
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
        gsmiur.setMeetingItemSetId(gmibmirItem.meetingItemSetId);
        networkBroker.ask(gsmiur, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetSelectMeetingItemsUserResponse response = (GetSelectMeetingItemsUserResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null && response.data.dataList != null && response.data.dataList.size() > 0) {
                        if (listData != null) {
                            listData.clear();
                        }
                        listData.addAll(response.data.dataList);
                        commonAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 验证用户是否有删除权限
     *
     * @param index
     */
    public void checkAddMeetingItemUser(int index, int userId) {
        CheckAddMeetingItemUserRequest camiur = new CheckAddMeetingItemUserRequest();
        LoginResponse.Item userItem = GlobaVariable.getInstance().item;
        if (userItem != null) {
            camiur.setUserId(userItem.getId());
        }
        camiur.setMeetingItemSetId(gmibmirItem.meetingItemSetId);
        networkBroker.ask(camiur, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                CheckAddMeetingItemUserResponse response = (CheckAddMeetingItemUserResponse) res;
                if (response.getCode() == 200 && response.data) {
                    if (index == -1) {
                        //添加负责人页面
                        Intent intent = new Intent(getApplicationContext(), AddPersonChargeActivty.class);
                        intent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                        intent.putExtra(Constants.KEY_MEETING_ITEM_SETID, gmibmirItem.meetingItemSetId);
                        startActivityForResult(intent, Constants.ADD_PERSON_CHARGE);
                    } else {
                        //删除负责人功能
                        deleteMeetingItemsUser(index, userId);
                    }
                } else {
                    showToast(response.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


    /**
     * 删除负责人
     *
     * @param index
     */
    public void deleteMeetingItemsUser(int index, int userId) {
        DeleteMeetingItemsUserRequest dmiur = new DeleteMeetingItemsUserRequest();
        dmiur.setUserId(userId);
        dmiur.setMeetingItemSetId(gmibmirItem.meetingItemSetId);
        networkBroker.ask(dmiur, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                DeleteMeetingItemsUserResponse response = (DeleteMeetingItemsUserResponse) res;
                if (response.getCode() == 200 && response.data) {
                    showToast(getString(R.string.delete_toast, getString(R.string.success)));
                    listData.remove(index);
                    commonAdapter.notifyDataSetChanged();
                } else {
                    showToast(getString(R.string.delete_toast, getString(R.string.failure)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 设置单个会议流程节点的状态
     */
    public void setMeetingItemsStatus() {
        SetMeetingItemsStatusRequest smisr = new SetMeetingItemsStatusRequest();
        LoginResponse.Item userItem = GlobaVariable.getInstance().item;
        if (userItem != null) {
            smisr.setUserId(userItem.getId());
        }
        smisr.setMeetingItemSetId(gmibmirItem.meetingItemSetId);
        smisr.setStatus(switchbtn.isChecked() ? 1 : 2);
        networkBroker.ask(smisr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SetMeetingItemsStatusResponse response = (SetMeetingItemsStatusResponse) res;
                if (response.getCode() == 200) {
                    finish();
                }
                showToast(response.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

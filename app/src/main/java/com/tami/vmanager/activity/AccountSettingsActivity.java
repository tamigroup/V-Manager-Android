package com.tami.vmanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetUserNoticeConfigRequest;
import com.tami.vmanager.entity.GetUserNoticeConfigResponse;
import com.tami.vmanager.entity.LoginOutRequest;
import com.tami.vmanager.entity.LoginOutResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SetUserNoticeConfigRequest;
import com.tami.vmanager.entity.SetUserNoticeConfigResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.SwitchButton;

/**
 * 帐号设置
 * Created by why on 2018/6/20.
 */
public class AccountSettingsActivity extends BaseActivity {

    private SwitchButton newMessage;//新消息
    private SwitchButton changeDemand;//需求变化
    private TextView changeDemandState;
    private SwitchButton groupMessage;//群消息
    private TextView groupMessageState;
    private SwitchButton satisfaction;//满意度
    private TextView satisfactionState;
    private SwitchButton taskAssignment;//任务分配通知
    private TextView taskAssignmentState;
    private TextView changePassword;
    private Button exitBtn;
    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_account_settings;
    }

    @Override
    public void initView() {
        newMessage = findViewById(R.id.aas_new_message_notice_sb);
        changeDemand = findViewById(R.id.aas_change_demand_notice_sb);
        changeDemandState = findViewById(R.id.aas_change_demand_notice_state);
        groupMessage = findViewById(R.id.aas_group_message_notice_sb);
        groupMessageState = findViewById(R.id.aas_group_message_notice_state);
        satisfaction = findViewById(R.id.ass_satisfaction_notice_sb);
        satisfactionState = findViewById(R.id.ass_satisfaction_notice_state);
        taskAssignment = findViewById(R.id.ass_task_assignment_notice_sb);
        taskAssignmentState = findViewById(R.id.ass_task_assignment_notice_state);
        changePassword = findViewById(R.id.aas_change_the_password);
        exitBtn = findViewById(R.id.aas_exit_login);
    }

    @Override
    public void initListener() {
        newMessage.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
            if (isChecked) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("app_package", getPackageName());
                    intent.putExtra("app_uid", getApplicationInfo().uid);
                    startActivity(intent);
                } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            }
        });
        changeDemand.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
//            changeDemandState.setText(isChecked ? getString(R.string.on) : getString(R.string.off));
            setUserNoticeConfig();
        });
        groupMessage.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
//            groupMessageState.setText(isChecked ? getString(R.string.on) : getString(R.string.off));
            setUserNoticeConfig();
        });
        satisfaction.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
//            satisfactionState.setText(isChecked ? getString(R.string.on) : getString(R.string.off));
            setUserNoticeConfig();
        });
        taskAssignment.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
//            taskAssignmentState.setText(isChecked ? getString(R.string.on) : getString(R.string.off));
            setUserNoticeConfig();
        });

        changePassword.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.account_settings);
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        getUserNoticeConfig();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.aas_change_the_password:
                changePassword();
                break;
            case R.id.aas_exit_login:
                editLogin();
                break;
        }
    }

    /**
     * 更改密码
     */
    private void changePassword() {
        startActivity(new Intent(getApplicationContext(), ModifyPasswordActivity.class));
    }

    /**
     * 退出登录
     */
    private void editLogin() {
        LoginOutRequest loginOutRequest = new LoginOutRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        loginOutRequest.setUserId(item.getId());
        networkBroker.ask(loginOutRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                LoginOutResponse response = (LoginOutResponse) res;
                if (response.getCode() == 200) {
                    GlobaVariable.getInstance().item = null;
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取提醒消息设置
     */
    private void getUserNoticeConfig() {
        GetUserNoticeConfigRequest getUserNoticeConfigRequest = new GetUserNoticeConfigRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            getUserNoticeConfigRequest.setUserId(item.getId());
        }
        networkBroker.ask(getUserNoticeConfigRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetUserNoticeConfigResponse response = (GetUserNoticeConfigResponse) res;
                if (response.getCode() == 200) {
                    if (response.data == null) {
                        changeDemand.setChecked(false);
                        groupMessage.setChecked(false);
                        satisfaction.setChecked(false);
                        taskAssignment.setChecked(false);
                    } else {
                        GetUserNoticeConfigResponse.Item noticeItem = response.data;
//                        changeDemand.setChecked(noticeItem. == 1 ? true : false);
                        groupMessage.setChecked(noticeItem.groupchatNotice == 1 ? true : false);
                        satisfaction.setChecked(noticeItem.satisfactionNotice == 1 ? true : false);
//                        taskAssignment.setChecked(noticeItem. == 1 ? true : false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 保存配置
     */
    public void setUserNoticeConfig() {
        SetUserNoticeConfigRequest setUserNoticeConfigRequest = new SetUserNoticeConfigRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            setUserNoticeConfigRequest.setUserId(item.getId());
        }
//        setUserNoticeConfigRequest.setSystemNotice(String.valueOf(0));
//        setUserNoticeConfigRequest.setHostNotice(String.valueOf(changeDemand.isChecked() ? 1 : 0));
//        setUserNoticeConfigRequest.setMeetingNotice(String.valueOf(0));
//        setUserNoticeConfigRequest.setSatisfactionNotice(String.valueOf(groupMessage.isChecked() ? 1 : 0));
//        setUserNoticeConfigRequest.setsatisfactionNotice(String.valueOf(satisfaction.isChecked() ? 1 : 0));
        networkBroker.ask(setUserNoticeConfigRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SetUserNoticeConfigResponse response = (SetUserNoticeConfigResponse) res;
                if (response.getCode() == 200) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

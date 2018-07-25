package com.tami.vmanager.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetUserNoticeConfigRequest;
import com.tami.vmanager.entity.GetUserNoticeConfigResponse;
import com.tami.vmanager.entity.LoginOutRequest;
import com.tami.vmanager.entity.LoginOutResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SetUserNoticeConfigRequest;
import com.tami.vmanager.entity.SetUserNoticeConfigResponse;
import com.tami.vmanager.entity.UpdateBean;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.ActivityManager;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.CheckUpdate;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.SwitchButton;
import com.zhy.http.okhttp.utils.L;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private ConstraintLayout version_update_cl;
    private TextView version_name;
    private UIData uiData = null;
    private UpdateBean.DataBean data;
    private String requestUrl = NetworkBroker.BASE_URI + HttpKey.UPDATE;
    private DownloadBuilder builder;
    private ConstraintLayout aas_new_message_notice_layout;

    private boolean messageFlag = true;//开关的状态
    private boolean returnFlag = false;//从系统设置返回记录

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
        aas_new_message_notice_layout = findViewById(R.id.aas_new_message_notice_layout);
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
        version_update_cl = findViewById(R.id.version_update_cl);
        version_name = findViewById(R.id.version_name);
    }


    @Override
    protected void onResume() {
        super.onResume();
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isChecked = notification.areNotificationsEnabled();
        newMessage.setChecked(isChecked);
        //(如果开关为打开状态再次设置打开不调用setOnCheckedChangeListener监听)需在系统设置返回并且做开关状态改变后需要把系统返回状态置成false
        if (returnFlag && messageFlag == isChecked) {
            returnFlag = false;
        }
        Logger.e("通知是否开启==" + isChecked);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        returnFlag = true;
    }

    @Override
    public void initListener() {
        newMessage.setOnCheckedChangeListener((view, isChecked) -> {
            //TODO: 2018/7/25 会跳转2次
            if (returnFlag) {//如果是系统返回且改变状态时调用
                returnFlag = false;
            } else {//点击开关时调用
                jumpSystemSetup();
            }
            messageFlag = isChecked;
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
        version_update_cl.setOnClickListener(this);
        aas_new_message_notice_layout.setOnClickListener(this);
    }

    /**
     * 跳转系统设置
     */
    private void jumpSystemSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity(intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
            startActivity(intent);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    @Override
    public void initData() {
        setTitleName(R.string.account_settings);
        version_name.setText(String.format(getString(R.string.version_name), Utils.getLocalVersionName(this)));
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        newMessage.setChecked(!isNotificationEnabled());
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
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.aas_new_message_notice_layout:
                jumpSystemSetup();
                break;
            case R.id.aas_change_the_password:
                changePassword();
                break;
            case R.id.aas_exit_login:
                editLogin();
                break;
            case R.id.version_update_cl:
                CheckUpdate.getInstance(this, 1);
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
        loginOutRequest.setUserId(String.valueOf(item.getId()));
        networkBroker.ask(loginOutRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                LoginOutResponse response = (LoginOutResponse) res;
                if (response.getCode() == 200) {
                    GlobaVariable.getInstance().item = null;
                    ActivityManager.getInstance().finishAllActivity();
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
            getUserNoticeConfigRequest.setUserId(String.valueOf(item.getId()));
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
                        changeDemand.setChecked(noticeItem.hostNotice == 1 ? true : false);
                        groupMessage.setChecked(noticeItem.groupchatNotice == 1 ? true : false);
                        satisfaction.setChecked(noticeItem.satisfactionNotice == 1 ? true : false);
                        taskAssignment.setChecked(noticeItem.taskDistribution == 1 ? true : false);
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
        //        setUserNoticeConfigRequest.setSystemNotice(0);
        setUserNoticeConfigRequest.setHostNotice(changeDemand.isChecked() ? 1 : 0);
        setUserNoticeConfigRequest.setGroupChatNotice(groupMessage.isChecked() ? 1 : 0);
        setUserNoticeConfigRequest.setSatisfactionNotice(satisfaction.isChecked() ? 1 : 0);
        setUserNoticeConfigRequest.setTaskDistribution(taskAssignment.isChecked() ? 1 : 0);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled() {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = getApplicationInfo();
        String pkg = getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}

package com.tami.vmanager.activity;

import android.content.Intent;
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

    private SwitchButton sponsor;//主办方
    private SwitchButton satisfaction;//满意度
    private SwitchButton groupChat;//
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
        sponsor = findViewById(R.id.aas_sponsor_notification_sb);
        satisfaction = findViewById(R.id.aas_satisfaction_notification_sb);
        groupChat = findViewById(R.id.ass_group_chat_notification_sb);
        changePassword = findViewById(R.id.aas_change_the_password);
        exitBtn = findViewById(R.id.aas_exit_login);
    }

    @Override
    public void initListener() {
        sponsor.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        satisfaction.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        groupChat.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
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
        getUserNoticeConfigRequest.setUserId(item.getId());
        networkBroker.ask(getUserNoticeConfigRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetUserNoticeConfigResponse response = (GetUserNoticeConfigResponse) res;
                if (response.getCode() == 200) {

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
        setUserNoticeConfigRequest.setUserId(item.getId());
        setUserNoticeConfigRequest.setSystemNotice(String.valueOf(1));
        setUserNoticeConfigRequest.setHostNotice(String.valueOf(1));
        setUserNoticeConfigRequest.setMeetingNotice(String.valueOf(1));
        setUserNoticeConfigRequest.setSatisfactionNotice(String.valueOf(1));
        setUserNoticeConfigRequest.setGroupChatNotice(String.valueOf(1));
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

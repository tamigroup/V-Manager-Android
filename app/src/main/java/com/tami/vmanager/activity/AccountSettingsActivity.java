package com.tami.vmanager.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
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
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.aas_change_the_password:

                break;
            case R.id.aas_exit_login:

                break;
        }
    }
}

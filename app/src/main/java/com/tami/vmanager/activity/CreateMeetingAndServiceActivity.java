package com.tami.vmanager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 创建会议与服务流程
 * Created by why on 2018/6/25.
 */
public class CreateMeetingAndServiceActivity extends BaseActivity {

    private TextView createMeeting;
    private TextView createMeeting1;
    private TextView createService;
    private TextView createService1;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_meeting_and_service;
    }

    @Override
    public void initView() {
        createMeeting = findViewById(R.id.acmas_create_meeting);
        createMeeting1 = findViewById(R.id.acmas_please_create_1);
        createService = findViewById(R.id.acmas_create_service_process);
        createService1 = findViewById(R.id.acmas_please_create_2);
    }

    @Override
    public void initListener() {
        createMeeting.setOnClickListener(this);
        createMeeting1.setOnClickListener(this);
        createService.setOnClickListener(this);
        createService1.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.create_meeting_and_service_process));
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
        switch (v.getId()) {
            case R.id.acmas_create_meeting:
            case R.id.acmas_please_create_1:
                //创建会议

                break;
            case R.id.acmas_create_service_process:
            case R.id.acmas_please_create_2:
                //创建服务
                startActivity(new Intent(getApplicationContext(), MeetingFlowActivity.class));
                break;
        }
    }
}

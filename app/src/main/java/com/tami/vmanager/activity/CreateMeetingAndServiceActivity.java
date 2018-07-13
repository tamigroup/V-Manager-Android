package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Constants;

/**
 * 创建会议与服务流程
 * Created by why on 2018/6/25.
 */
@Deprecated
public class CreateMeetingAndServiceActivity extends BaseActivity {

    private TextView createMeeting;
    private TextView createMeeting1;
    private TextView createService;
    private TextView createService1;
    private int meetingId = -1;
    private String meetingName = null;

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
                Intent meetingIntent = new Intent(getApplicationContext(), CreateMeetingRewriteActivity.class);
                startActivityForResult(meetingIntent, Constants.CREATE_MEETING);
                break;
            case R.id.acmas_create_service_process:
            case R.id.acmas_please_create_2:
                //创建服务
                if (meetingId == -1) {
                    showToast(getString(R.string.please_create_meeting));
                } else {
                    Intent flowIntent = new Intent(getApplicationContext(), CreateServiceFlowActivity.class);
                    flowIntent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                    startActivity(flowIntent);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == Constants.CREATE_MEETING) {
            //创建会议
            meetingId = data.getIntExtra(Constants.KEY_MEETING_ID, -1);
            meetingName = data.getStringExtra(Constants.KEY_MEETING_NAME);
            if (!TextUtils.isEmpty(meetingName)) {
                createMeeting1.setText(meetingName);
                createMeeting1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
            }
        }
    }
}

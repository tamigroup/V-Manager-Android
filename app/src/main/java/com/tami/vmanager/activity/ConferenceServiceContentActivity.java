package com.tami.vmanager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.view.SwitchButton;

/**
 * 会议服务内容流程
 * Created by why on 2018/6/15.
 */
public class ConferenceServiceContentActivity extends BaseActivity {

    private TextView cntractAmount;
    private TextView numberOfParticipants;
    private TextView flowChart;
    private TextView vipReceptionInformation;
    private SwitchButton switchButton;
    private TextView uploadEoList;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_conference_service_content;
    }

    @Override
    public void initView() {
        cntractAmount = findViewById(R.id.csc_contract_amount);
        numberOfParticipants = findViewById(R.id.csc_number_of_participants);
        flowChart = findViewById(R.id.csc_flow_chart);
        vipReceptionInformation = findViewById(R.id.csc_vip_reception_information);
        switchButton = findViewById(R.id.csc_switchbtn);
        uploadEoList = findViewById(R.id.csc_upload_eo_list);
    }

    @Override
    public void initListener() {
        cntractAmount.setOnClickListener(this);
        numberOfParticipants.setOnClickListener(this);
        flowChart.setOnClickListener(this);
        vipReceptionInformation.setOnClickListener(this);
        uploadEoList.setOnClickListener(this);

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                Toast.makeText(getApplicationContext(), String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.conference_service_content_flow);
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
            case R.id.csc_contract_amount:
                //合同金额

                break;
            case R.id.csc_number_of_participants:
                //会议人数

                break;
            case R.id.csc_flow_chart:
                //会议流程
                flowChart();
                break;
            case R.id.csc_vip_reception_information:
                //VIP接待

                break;
            case R.id.csc_upload_eo_list:
                //查看EO单

                break;
        }
    }

    private void flowChart() {
        startActivity(new Intent(getApplicationContext(), MeetingFlowActivity.class));
    }
}

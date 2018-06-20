package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.TimeLineHorizontalAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.TimeLine;
import com.tami.vmanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 进入会议
 * Created by why on 2018/6/16.
 */
public class EnterMeetingActivity extends BaseActivity {

    private TextView meetingName;//会议名称
    private TextView meetingTime;//会议时间
    private TextView meetingRoom;//会议房间
    private TextView meetingPersonnel;//会议人员及岗位

    private TextView predeterminedNumber;//预定人数
    private TextView bottomNumber;//保底人数
    private TextView actualNumber;//实到人数
    private TextView lookEO;//查看EO单

    private RecyclerView recyclerView;//时间轴

    private ConstraintLayout serviceGroup;//会议服务群
    private ConstraintLayout sponsorMember; //主办方成员
    private TextView meetingDetails;//会议详情
    private TextView vipDetails;//VIP详情

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_enter_meeting;
    }

    @Override
    public void initView() {
        //基本数据
        meetingName = findViewById(R.id.meeting_overview_meeting_name);
        meetingTime = findViewById(R.id.meeting_overview_meeting_time);
        meetingRoom = findViewById(R.id.meeting_overview_meeting_room);
        meetingPersonnel = findViewById(R.id.meeting_overview_meeting_personnel);
        //人数与EO查看数据
        predeterminedNumber = findViewById(R.id.meeting_overview_predetermined_number);
        bottomNumber = findViewById(R.id.meeting_overview_bottom_number);
        actualNumber = findViewById(R.id.meeting_overview_actual_number);
        lookEO = findViewById(R.id.meeting_overview_look_eo);

        recyclerView = findViewById(R.id.enter_meeting_recycler_view);
        //会议服务群
        serviceGroup = findViewById(R.id.enter_meeting_service_group_layout);
        //主办方成员
        sponsorMember = findViewById(R.id.enter_meeting_sponsor_member_layout);
        //会议详情
        meetingDetails = findViewById(R.id.enter_meeting_meeting_details);
        //VIP详情
        vipDetails = findViewById(R.id.enter_meeting_vip_details);

    }

    @Override
    public void initListener() {
        lookEO.setOnClickListener(this);
        serviceGroup.setOnClickListener(this);
        sponsorMember.setOnClickListener(this);
        meetingDetails.setOnClickListener(this);
        vipDetails.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.get_into_meeting);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        TimeLineHorizontalAdapter adapter = new TimeLineHorizontalAdapter(getData());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initUIdata();
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
            case R.id.meeting_overview_look_eo:
                //查看EO单
                lookEO();
                break;
            case R.id.enter_meeting_service_group_layout:
                //会议服务群
                serviceGroup();
                break;
            case R.id.enter_meeting_sponsor_member_layout:
                //主办方成员
                sponsorMember();
                break;
            case R.id.enter_meeting_meeting_details:
                //会议详情
                meetingDetails();
                break;
            case R.id.enter_meeting_vip_details:
                //VIP详情
                vipDetails();
                break;
        }
    }

    /**
     * 页面UI赋值
     */
    private void initUIdata() {
        meetingName.setText("夏普8K黑科技，改变你看世界的方式！");
        meetingTime.setText("6月14日 9:00-17:00");
        meetingRoom.setText("高级VIP房间");
        meetingPersonnel.setText("销售：张三");

        initUITxt(predeterminedNumber, String.valueOf(100), R.string.predetermined_number, R.color.color_303030);
        initUITxt(bottomNumber, String.valueOf(60), R.string.bottom_number, R.color.color_303030);
        initUITxt(actualNumber, String.valueOf(88), R.string.actual_number, R.color.color_E27676);
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

    private List<TimeLine> getData() {
        List<TimeLine> models = new ArrayList<TimeLine>();
        TimeLine timeLine = null;
        for (int i = 0; i < 8; i++) {
            timeLine = new TimeLine();
            timeLine.setConetnt("上午会场\n就绪");
            models.add(timeLine);
        }
        return models;
    }

    /**
     * 查看EO单
     */
    private void lookEO() {

    }

    /**
     * 会议服务群
     */
    private void serviceGroup() {
        startActivity(new Intent(getApplicationContext(), ConferenceServiceGroupActivity.class));
    }

    /**
     * 主办方成员
     */
    private void sponsorMember() {
        startActivity(new Intent(getApplicationContext(), SponsorMemberActivity.class));
    }

    /**
     * 会议详情
     */
    private void meetingDetails() {

    }

    /**
     * VIP详情
     */
    private void vipDetails() {

    }

}

package com.tami.vmanager.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 首页中的创建会议
 * Created by why on 2018/6/13.
 */
public class CreateMeetingActivity extends BaseActivity {

    private ConstraintLayout nameLayout;//会议名称
    private TextView name;
    private ConstraintLayout saleNameLayout;//销售名称
    private TextView saleName;
    private ConstraintLayout meetingPlaceLayout;//会议地点
    private TextView meetingPlace;
    private ConstraintLayout startTimeLayout;//开始时间
    private TextView startTime;
    private ConstraintLayout endTimeLayout;//结束时间
    private TextView endTime;
    private TextView createMeetingBtn;//确认按钮

    private Date recordStartDate;//记录开始时间
    private Date recordEndDate;//记录结束时间

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_meeting;
    }

    @Override
    public void initView() {
        nameLayout = findViewById(R.id.create_meeting_name_layout);
        name = findViewById(R.id.create_meeting_name_onclick);
        saleNameLayout = findViewById(R.id.create_meeting_sale_name_layout);
        saleName = findViewById(R.id.create_meeting_sale_name_onclick);
        meetingPlaceLayout = findViewById(R.id.create_meeting_meeting_place_layout);
        meetingPlace = findViewById(R.id.create_meeting_meeting_place_onclick);
        startTimeLayout = findViewById(R.id.create_meeting_start_time_layout);
        startTime = findViewById(R.id.create_meeting_start_time_onclick);
        endTimeLayout = findViewById(R.id.create_meeting_end_time_layout);
        endTime = findViewById(R.id.create_meeting_end_time_onclick);
        createMeetingBtn = findViewById(R.id.create_meeting_btn);

    }

    @Override
    public void initListener() {
        nameLayout.setOnClickListener(this);
        saleNameLayout.setOnClickListener(this);
        meetingPlaceLayout.setOnClickListener(this);
        startTimeLayout.setOnClickListener(this);
        endTimeLayout.setOnClickListener(this);
        createMeetingBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.new_meeting);
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
            case R.id.create_meeting_name_layout:
                //会议名称

                break;
            case R.id.create_meeting_sale_name_layout:
                //销售名称
                break;
            case R.id.create_meeting_meeting_place_layout:
                //会议地点
                break;
            case R.id.create_meeting_start_time_layout:
                //开始时间
                createTime(startTime, true, recordStartDate);
                break;
            case R.id.create_meeting_end_time_layout:
                //结束时间
                createTime(endTime, false, recordEndDate);
                break;
            case R.id.create_meeting_btn:
                //确认按钮
                createMeetingBtnOnClick();
                break;

        }
    }

    private void createTime(final TextView view, final boolean dataFlag, Date date) {
        Calendar selectedDate = Calendar.getInstance();
        if (date != null) {
            selectedDate.setTime(date);
        }
        final Calendar startDate = Calendar.getInstance();
        final Calendar endDate = Calendar.getInstance();
//        startDate.set(2018, 0, 1);
        endDate.set(2020, 11, 31);
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (dataFlag) {
                    recordStartDate = date;
                } else {
                    recordEndDate = date;
                }
                view.setText(TimeUtils.date2String(date, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isDialog(true)//是否显示为对话框样式
                .isCyclic(true)//是否循环滚动
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show(view);
    }


    /**
     * 跳转会议概览
     */
    private void createMeetingBtnOnClick() {
        startActivity(new Intent(getApplicationContext(), MeetingOverviewActivity.class));
    }
}

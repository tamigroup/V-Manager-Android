package com.tami.vmanager.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.TimeLineAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.AlreadyPaidItemDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
import com.tami.vmanager.entity.TimeLine;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * 会议概览
 * <p>
 * Created by why on 2018/6/14.
 */
public class MeetingOverviewActivity extends BaseActivity {

    private TextView meetingName;//会议名称
    private TextView meetingTime;//会议时间
    private TextView meetingRoom;//会议房间
    private TextView meetingPersonnel;//会议人员及岗位

    private TextView predeterminedNumber;//预定人数
    private TextView bottomNumber;//保底人数
    private TextView actualNumber;//实到人数
    private TextView lookEO;//查看EO单

    private CircleProgressBar alreadyPaidItem;//待付款项
    private TextView complaintsBox;//意见箱
    private TextView changeDemand;//需求变化
    private TextView vEmind;//小V提醒

    private ConstraintLayout xufuLayout;//悬浮布局
    private TextView suspensionOnclick;
    private TextView pleaseCreateConference;//提示文本

    private RecyclerView recyclerView;
    private Button functionBtn;//创建流程及进入会意按钮

    private ConfirmEnterMeetingDialog confirmEnterMeetingDialog;//弹框查看会议

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_overview;
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
        //四个可点击图标
        alreadyPaidItem = findViewById(R.id.meeting_overview_already_paid_item);
        complaintsBox = findViewById(R.id.meeting_overview_complaints_box);
        changeDemand = findViewById(R.id.meeting_overview_change_demand);
        vEmind = findViewById(R.id.meeting_overview_v_emind);
        //悬浮布局
        xufuLayout = findViewById(R.id.meeting_overview_xuanfu_layout);
        suspensionOnclick = findViewById(R.id.meeting_overview_suspension_onclick);
        //创建流程及进入会意按钮
        pleaseCreateConference = findViewById(R.id.please_create_conference);
        functionBtn = findViewById(R.id.meeting_overview_function_btn);

        recyclerView = findViewById(R.id.meeting_overview_recycler_view);

        confirmEnterMeetingDialog = new ConfirmEnterMeetingDialog(this);
    }

    @Override
    public void initListener() {
        lookEO.setOnClickListener(this);
        alreadyPaidItem.setOnClickListener(this);
        complaintsBox.setOnClickListener(this);
        changeDemand.setOnClickListener(this);
        vEmind.setOnClickListener(this);
        xufuLayout.setOnClickListener(this);
        functionBtn.setOnClickListener(this);

        confirmEnterMeetingDialog.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void viewOnly() {
                Toast.makeText(getApplicationContext(), "仅查看", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void confirm() {
                Toast.makeText(getApplicationContext(), "确定", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_ganlan);

        initUIdata();
        initListView();

//        pleaseCreateConference.setVisibility(View.GONE);
//        xufuLayout.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (confirmEnterMeetingDialog != null && confirmEnterMeetingDialog.isShowing()) {
            confirmEnterMeetingDialog.dismiss();
        }
        confirmEnterMeetingDialog = null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.meeting_overview_look_eo:
                //查看EO单

                break;
            case R.id.meeting_overview_already_paid_item:
                //待付款项
                alreadyPaidItem();
                break;
            case R.id.meeting_overview_complaints_box:
                //意见箱

                break;
            case R.id.meeting_overview_change_demand:
                //需求变化

                break;
            case R.id.meeting_overview_v_emind:
                //小V提醒

                break;
            case R.id.meeting_overview_xuanfu_layout:
                //悬浮布局

                break;
            case R.id.meeting_overview_suspension_onclick:
                //悬浮布局中按钮

                break;
            case R.id.meeting_overview_function_btn:
                //功能按钮
                functionBtn(v);
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

        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                alreadyPaidItem.setProgress(values[0]);
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i <= 90; i += 10) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
                return null;
            }
        }.execute();

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

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        TimeLineAdapter adapter = new TimeLineAdapter(getData(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<TimeLine> getData() {
        List<TimeLine> models = new ArrayList<TimeLine>();
        TimeLine timeLine = null;
        for (int i = 0; i < 8; i++) {
            timeLine = new TimeLine();
            timeLine.setConetnt("参数名" + i);
            models.add(timeLine);
        }
        return models;
    }

    /**
     * 待付款项
     */
    private void alreadyPaidItem() {
        AlreadyPaidItemDialog alreadyPaidItemDialog = new AlreadyPaidItemDialog(this, "4000", "1000");
        alreadyPaidItemDialog.show();
    }

    /**
     * 底部功能按钮
     *
     * @param v
     */
    private void functionBtn(View v) {
//        confirmEnterMeetingDialog.show();

        Button button = (Button) v;
        if (!TextUtils.isEmpty(button.toString())
                && getString(R.string.create_process).equals(button.getText().toString())) {
            //创建
            startActivity(new Intent(getApplicationContext(), ConferenceServiceContentActivity.class));
        } else {
            //进入

        }
    }
}

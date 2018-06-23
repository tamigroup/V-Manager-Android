package com.tami.vmanager.activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.MeetingFlowEditAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Constants;

import java.util.Calendar;

/**
 * 会议流程编辑
 * Created by why on 2018/6/15.
 */

public class MeetingFlowEditActivity extends BaseActivity {

    private ExpandableListView listView;
    private Button confirmBtn;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_flow_edit;
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.meeting_flow_edit_listview);
        confirmBtn = findViewById(R.id.confirm_btn);
    }

    @Override
    public void initListener() {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                AppCompatImageView appCompatImageView = v.findViewById(R.id.mfe_group_img);
                if ("open".equals(appCompatImageView.getTag().toString())) {
                    appCompatImageView.setTag("close");
                    appCompatImageView.setImageResource(android.R.drawable.arrow_down_float);
                } else {
                    appCompatImageView.setTag("open");
                    appCompatImageView.setImageResource(android.R.drawable.arrow_up_float);
                }
                return false;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TextView textView = v.findViewById(R.id.mfe_child_time);
                showTimePicker(textView, groupPosition, childPosition);
                return false;
            }
        });

        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String day = null;
        Intent intent = getIntent();
        if (intent != null) {
            day = intent.getStringExtra(Constants.KEY_DAY);
        }
        setTitleName(R.string.edit_process);
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        TextView textView = (TextView) layoutInflater.inflate(R.layout.head_meeting_flow_edit, null, false);
        textView.setText(day);
        listView.setAdapter(new MeetingFlowEditAdapter(getApplicationContext()));
        listView.setGroupIndicator(null);
        //遍历所有group,将所有项设置成默认展开
        for (int i = 0; i < 4; i++) {
            listView.expandGroup(i);
        }
        listView.addHeaderView(textView);
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (listView != null) {
            listView.removeAllViews();
            listView = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.confirm_btn:
                //确定按钮

                break;
        }
    }

    /**
     * 弹出时间框
     *
     * @param timeView
     * @param groupPosition
     * @param childPosition
     */
    private void showTimePicker(final TextView timeView, final int groupPosition, final int childPosition) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //AlertDialog.THEME_HOLO_DARK黑色
        //AlertDialog.THEME_HOLO_LIGHT白色
        TimePickerDialog timePicker = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeStr = hourOfDay + ":" + minute;
                timeView.setText(timeStr);
            }
        }, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
        timePicker.show();
    }
}

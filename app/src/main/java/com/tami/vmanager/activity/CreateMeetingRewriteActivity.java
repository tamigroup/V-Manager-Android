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
public class CreateMeetingRewriteActivity extends BaseActivity {


    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_meeting_rewrite;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        setTitleName(R.string.create_meeting);
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
    }

}

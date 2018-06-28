package com.tami.vmanager.activity;

import android.view.View;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

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

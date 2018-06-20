package com.tami.vmanager.activity;

import android.view.View;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 主办方成员
 * Created by why on 2018/6/19.
 */
public class SponsorMemberActivity extends BaseActivity {

    private TextView expandAll;//展开全部
    private TextView tabNotice;//会告
    private TextView outMeeting;//退出会议

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sponsor_member;
    }

    @Override
    public void initView() {
        expandAll = findViewById(R.id.sm_expand_all);
        tabNotice = findViewById(R.id.sm_tab_notice);
        outMeeting = findViewById(R.id.sm_sign_out_meeting);
    }

    @Override
    public void initListener() {
        expandAll.setOnClickListener(this);
        tabNotice.setOnClickListener(this);
        outMeeting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.sponsor_member);
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
            case R.id.sm_expand_all:
                //展开全部
                expandAll();
                break;
            case R.id.sm_tab_notice:
                tabNotice();
                //会告
                break;
            case R.id.sm_sign_out_meeting:
                outMeeting();
                //退出会议
                break;
        }
    }

    /**
     * 展开全部
     */
    private void expandAll() {

    }

    /**
     * 会告
     */
    private void tabNotice() {

    }

    /**
     * 退出会议
     */
    private void outMeeting() {

    }
}

package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * Created by Tang on 2018/7/7
 * 编辑群公告
 */
public class GroupEditNoticeActivity extends BaseActivity {
    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_edit_notice;
    }

    @Override
    public void initView() {
        setTitleName(R.string.group_edit);
        setTitleRightTxt(R.string.group_release);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

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
}

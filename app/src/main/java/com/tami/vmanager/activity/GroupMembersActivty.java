package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetSaleUserByRoleRequest;

/**
 * 群成员
 * Created by why on 2018/7/5.
 */
public class GroupMembersActivty extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_members;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.group_members));
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

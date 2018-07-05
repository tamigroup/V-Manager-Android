package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 人员名单
 * Created by why on 2018/7/5.
 */
public class PersonnelListActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_personnel_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.personnel_list));
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

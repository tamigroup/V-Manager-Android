package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * VIP详情
 * Created by why on 2018/7/5.
 */
public class VIPDetailsActivity extends BaseActivity {

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_vip_details;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.vip_details));
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

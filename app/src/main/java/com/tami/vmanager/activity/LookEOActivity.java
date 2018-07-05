package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 查看EO单
 * Created by why on 2018/7/5.
 */
public class LookEOActivity extends BaseActivity {


    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_look_eo;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.look_eo));
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

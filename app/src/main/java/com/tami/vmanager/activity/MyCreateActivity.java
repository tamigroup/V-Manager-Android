package com.tami.vmanager.activity;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的创建
 * Created by why on 2018/7/5.
 */
public class MyCreateActivity extends BaseActivity {


    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_create;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {
        // 菜单点击监听。
    }

    @Override
    public void initData() {
        setTitleName(R.string.my_create);
        List<String> list = new ArrayList<>();
        list.add("测试");
        list.add("测试");
        list.add("测试");
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

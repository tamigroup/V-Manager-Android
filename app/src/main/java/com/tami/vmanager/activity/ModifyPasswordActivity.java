package com.tami.vmanager.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * 更改密码
 * Created by why on 2018/7/5.
 */
public class ModifyPasswordActivity extends BaseActivity {

    private EditText oldPassWord;//旧密码
    private EditText newPassWord;//新密码
    private EditText newPassWord1;//确认新密码
    private TextView forgetOldCode;//忘记密码

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void initView() {
        findViewById(R.id.amp_old_password_edit);
        findViewById(R.id.amp_new_password_edit);
        findViewById(R.id.amp_new_password_edit1);
        findViewById(R.id.amp_forget_old_code);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.modify_password));
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

package com.tami.vmanager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.ChangePasswordRequest;
import com.tami.vmanager.entity.ChangePasswordResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;

/**
 * 更改密码
 * Created by why on 2018/7/5.
 */
public class ModifyPasswordActivity extends BaseActivity {

    private EditText oldPassWord;//旧密码
    private EditText newPassWord;//新密码
    private EditText newPassWord1;//确认新密码
    private TextView forgetOldCode;//忘记密码
    private NetworkBroker networkBroker;

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
        oldPassWord = findViewById(R.id.amp_old_password_edit);
        newPassWord = findViewById(R.id.amp_new_password_edit);
        newPassWord1 = findViewById(R.id.amp_new_password_edit1);
        forgetOldCode = findViewById(R.id.amp_forget_old_code);
    }

    @Override
    public void initListener() {
        forgetOldCode.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.modify_password));
        setTitleRightTxt(getString(R.string.save));

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        networkBroker.cancelAllRequests();
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
            case R.id.amp_forget_old_code:
                startActivity(new Intent(getApplicationContext(), ForgetThePasswordActivity.class));
                break;
            case R.id.titleRightTxt:
                save();
                break;
        }
    }

    /**
     * 保存密码
     */
    private void save() {
        if (isEmpty()) {
            ChangePasswordRequest cpr = new ChangePasswordRequest();
            LoginResponse.Item item = GlobaVariable.getInstance().item;
            if (item != null) {
                cpr.setUserId(String.valueOf(item.getId()));
            }
            cpr.setOldPassWord(oldPassWord.getText().toString());
            cpr.setNewPassWord(newPassWord.getText().toString());
            networkBroker.ask(cpr, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    return;
                }
                try {
                    ChangePasswordResponse response = (ChangePasswordResponse) res;
                    if (response.getCode() == 200) {
                        if (response.data) {
                            showToast(getString(R.string.modify_password_true));
                            finish();
                        } else {
                            showToast(getString(R.string.modify_password_false));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 页面输入项判断
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(oldPassWord.getText())) {
            showToast(R.string.please_add_old_password);
            return false;
        }
        if (TextUtils.isEmpty(newPassWord.getText())) {
            showToast(R.string.please_add_new_password);
            return false;
        }
        if (TextUtils.isEmpty(newPassWord.getText())) {
            showToast(R.string.please_add_new_password);
            return false;
        }
        return true;
    }
}

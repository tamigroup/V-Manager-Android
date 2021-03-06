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
import com.tami.vmanager.entity.LoginOutRequest;
import com.tami.vmanager.entity.LoginOutResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.ActivityManager;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;

/**
 * 更改密码
 * Created by why on 2018/7/5.
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnFocusChangeListener {

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
        oldPassWord.setOnFocusChangeListener(this);
        newPassWord.setOnFocusChangeListener(this);
        newPassWord1.setOnFocusChangeListener(this);
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
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
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
                            editLogin();
                        } else {
                            showToast(getString(R.string.modify_password_false));
                        }
                    }else{
                        showToast(response.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 退出登录
     */
    private void editLogin() {
        LoginOutRequest loginOutRequest = new LoginOutRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        loginOutRequest.setUserId(String.valueOf(item.getId()));
        networkBroker.ask(loginOutRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                LoginOutResponse response = (LoginOutResponse) res;
                if (response.getCode() == 200) {
                    SPUtils.remove(ModifyPasswordActivity.this, Constants.SAVE_LOGIN_DATA);
                    SPUtils.remove(ModifyPasswordActivity.this,Constants.TOKEN);
                    SPUtils.remove(ModifyPasswordActivity.this,Constants.AUTO_LOGIN);
                    GlobaVariable.getInstance().item = null;
                    ActivityManager.getInstance().finishAllActivity();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 页面输入项判断
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(oldPassWord.getText())) {
            showToast(R.string.please_enter_the_old_password);
            return false;
        }
        if (TextUtils.isEmpty(newPassWord.getText())) {
            showToast(R.string.please_enter_a_new_password);
            return false;
        }
        if (TextUtils.isEmpty(newPassWord1.getText())) {
            showToast(R.string.please_enter_the_password_again);
            return false;
        }
        if (checkPassWord(newPassWord.getText().toString())
                || checkPassWord(newPassWord1.getText().toString())) {
            showToast(R.string.cryptographic_format);
            return false;
        }
        if (!newPassWord.getText().toString().equals(newPassWord1.getText().toString())) {
            showToast(getString(R.string.two_inconsistencies_in_cipher_input));
            return false;
        }
        return true;
    }

    private boolean checkPassWord(String passwrod) {
        if (passwrod.length() < 6) {
            return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.amp_old_password_edit:
                    if (TextUtils.isEmpty(oldPassWord.getText())) {
                        showToast(R.string.please_enter_the_old_password);
                    } else if (oldPassWord.getText().toString().length() < 6) {
                        showToast(R.string.please_enter_the_correct_password);
                    }
                    break;
                case R.id.amp_new_password_edit:
                    if (TextUtils.isEmpty(newPassWord.getText())) {
                        showToast(R.string.please_enter_a_new_password);
                    } else if (oldPassWord.getText().toString().length() < 6) {
                        showToast(R.string.please_enter_the_correct_password);
                    }
                    break;
                case R.id.amp_new_password_edit1:
                    if (TextUtils.isEmpty(newPassWord1.getText())) {
                        showToast(R.string.please_enter_the_password_again);
                    } else if (oldPassWord.getText().toString().length() < 6) {
                        showToast(R.string.please_enter_the_correct_password);
                    }
                    break;
            }
        }
    }
}

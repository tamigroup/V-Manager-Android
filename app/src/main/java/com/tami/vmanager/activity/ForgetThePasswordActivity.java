package com.tami.vmanager.activity;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.ResetPwdRequest;
import com.tami.vmanager.entity.SendVerifyCodeRequest;
import com.tami.vmanager.entity.SendVerifyCodeResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.VerificationCode;

/**
 * 忘记密码
 * Created by why on 2018/6/12.
 */

public class ForgetThePasswordActivity extends BaseActivity {

    private TextView title;
    private TextView prompt;
    private TextView name;
    private EditText value;
    private TextView againIdentification;
    private EditText againPassword;
    private Button confirmBtn;
    private TextView codeView;
    private VerificationCode verificationCode;

    private String mobile = null;
    private String smsCode = null;
    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.forget_password_title);
        prompt = findViewById(R.id.forget_password_prompt);
        name = findViewById(R.id.forget_password_name);
        value = findViewById(R.id.forget_password_value);
        againIdentification = findViewById(R.id.forget_password_again_identification);
        againPassword = findViewById(R.id.forget_password_again_password);
        confirmBtn = findViewById(R.id.forget_password_confirm_btn);
        codeView = findViewById(R.id.forget_password_recapture);
    }

    @Override
    public void initListener() {
        confirmBtn.setOnClickListener(this);
        codeView.setOnClickListener(this);
    }

    @Override
    public void initData() {
        verificationCode = new VerificationCode.Build()
                .setContext(getApplicationContext())
                .setCodeView(codeView)
                .setExecuteTxt("s")
                .setExecuteTxtColor(R.color.color_FF5657)
                .setEndTxt("重新获取")
                .setEndTxtColor(R.color.color_333333)
                .build();

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (verificationCode != null) {
            verificationCode.stop();
            verificationCode.clear();
        }
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.forget_password_recapture:
                verificationCode.start();
                break;
            case R.id.forget_password_confirm_btn:
                Button btn = (Button) v;
                if (getString(R.string.next_step).equals(btn.getText().toString())) {
                    sentVerificationCode();
                } else if (getString(R.string.confirm).equals(btn.getText().toString())) {
                    confirm();
                } else if (getString(R.string.identification).equals(btn.getText().toString())) {
                    identification();
                } else if (getString(R.string.go_login).equals(btn.getText().toString())) {
                    goLogin();
                }
                break;
        }
    }

    /**
     * 发送验证码点击
     */
    private void sentVerificationCode() {
        if (TextUtils.isEmpty(value.getText())) {
            showToast("请输入手机号");
            return;
        }
        mobile = value.getText().toString();
        SendVerifyCodeRequest sendVerifyCodeRequest = new SendVerifyCodeRequest();
        sendVerifyCodeRequest.setMobile(value.getText().toString());
        sendVerifyCodeRequest.setRequestUrl(HttpKey.USER_SEND_VERIFY_CODE);
        networkBroker.ask(sendVerifyCodeRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SendVerifyCodeResponse response = (SendVerifyCodeResponse) res;
                if (response.getCode() == 200) {
                    title.setText(getString(R.string.input_authentication_code));
                    prompt.setVisibility(View.INVISIBLE);
                    name.setText(getString(R.string.authentication_code));
                    value.setInputType(InputType.TYPE_CLASS_NUMBER);
                    value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    value.setText(null);
                    value.setFocusable(true);
                    value.setFocusableInTouchMode(true);
                    value.requestFocus();
                    codeView.setVisibility(View.VISIBLE);
                    verificationCode.start();
                    confirmBtn.setText(getString(R.string.confirm));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 确定按钮点击
     */
    private void confirm() {
        if (TextUtils.isEmpty(value.getText())) {
            showToast("请输入验证码！");
            return;
        }
        smsCode = value.getText().toString();
        title.setText(getString(R.string.new_password));
        prompt.setVisibility(View.INVISIBLE);
        verificationCode.stop();
        codeView.setVisibility(View.GONE);
        name.setText(getString(R.string.new_password));
        value.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
        value.setText(null);
        value.setFocusable(true);
        value.setFocusableInTouchMode(true);
        value.requestFocus();
        againIdentification.setVisibility(View.VISIBLE);
        againPassword.setVisibility(View.VISIBLE);
        confirmBtn.setText(getString(R.string.identification));
    }

    /**
     * 确认按钮点击
     */
    private void identification() {
        ResetPwdRequest resetPwdRequest = new ResetPwdRequest();
        resetPwdRequest.setMobile(mobile);
        resetPwdRequest.setNewPassWord(againPassword.getText().toString());
        resetPwdRequest.setSmsCode(smsCode);
        networkBroker.ask(resetPwdRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SendVerifyCodeResponse response = (SendVerifyCodeResponse) res;
                if (response.getCode() == 200) {
                    title.setText(getString(R.string.congratulations));
                    prompt.setVisibility(View.VISIBLE);
                    prompt.setText(getString(R.string.password_resetting_success));
                    name.setVisibility(View.INVISIBLE);
                    value.setVisibility(View.INVISIBLE);
                    againIdentification.setVisibility(View.INVISIBLE);
                    againPassword.setVisibility(View.INVISIBLE);
                    confirmBtn.setBackgroundResource(R.color.color_FF5657);
                    confirmBtn.setText(getString(R.string.go_login));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 去登陆
     */
    private void goLogin() {
        finish();
    }
}

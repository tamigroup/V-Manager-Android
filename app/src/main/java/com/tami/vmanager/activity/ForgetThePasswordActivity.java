package com.tami.vmanager.activity;

import android.support.v7.widget.AppCompatImageView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.ResetPwdRequest;
import com.tami.vmanager.entity.ResetPwdResponse;
import com.tami.vmanager.entity.SendVerifyCodeRequest;
import com.tami.vmanager.entity.SendVerifyCodeResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.utils.VerificationCode;

/**
 * 忘记密码
 * Created by why on 2018/6/12.
 */

public class ForgetThePasswordActivity extends BaseActivity {

    private AppCompatImageView titleLeftBtn;
    private TextView title;
    private TextView prompt;
    private TextView name;
    private TextView codeView;
    private EditText value;
    private EditText password;
    private TextView againIdentification;
    private EditText againPassword;
    private Button confirmBtn;
    private VerificationCode verificationCode;

    private String mobile = null;
    private String smsCode = null;
    private NetworkBroker networkBroker;
    private int stepIndex = 0;


    @Override
    public int getStartBarColor() {
        return android.R.color.transparent;
    }

    @Override
    public int getNavigationBarColor() {
        return android.R.color.transparent;
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        titleLeftBtn = findViewById(R.id.titleLeftBtn);

        title = findViewById(R.id.forget_password_title);
        prompt = findViewById(R.id.forget_password_prompt);
        name = findViewById(R.id.forget_password_name);
        codeView = findViewById(R.id.forget_password_recapture);
        value = findViewById(R.id.forget_password_value);
        password = findViewById(R.id.forget_password_password);
        againIdentification = findViewById(R.id.forget_password_again_identification);
        againPassword = findViewById(R.id.forget_password_again_password);
        confirmBtn = findViewById(R.id.forget_password_confirm_btn);

    }

    @Override
    public void initListener() {
        titleLeftBtn.setOnClickListener(this);
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

        step1(getString(R.string.next_step), 1);
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
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleLeftBtn:
                back();
                break;
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
        if (!Utils.isPhone(value.getText().toString())) {
            showToast(R.string.cell_phone_number_error);
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
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
                        step2(getString(R.string.confirm), 2);
                        title.setText(getString(R.string.input_authentication_code));
                        name.setText(getString(R.string.authentication_code));
                        value.setInputType(InputType.TYPE_CLASS_NUMBER);
                        value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                        value.setText(null);
                        value.setFocusable(true);
                        value.setFocusableInTouchMode(true);
                        value.requestFocus();
                        verificationCode.start();
                    }else{
                        showToast(response.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } else {
            title.setText(getString(R.string.input_authentication_code));
            name.setText(getString(R.string.authentication_code));
            value.setInputType(InputType.TYPE_CLASS_NUMBER);
            value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            value.setText(null);
            value.setFocusable(true);
            value.setFocusableInTouchMode(true);
            value.requestFocus();
            step2(getString(R.string.confirm), 2);
        }
    }

    /**
     * 确定按钮点击
     */
    private void confirm() {
        if (TextUtils.isEmpty(value.getText())) {
            showToast(getString(R.string.please_input_authentication_code));
            return;
        }
        if (value.getText().length() < 6) {
            showToast(getString(R.string.please_enter_the_correct_verification_code));
            return;
        }
        verificationCode.stop();
        smsCode = value.getText().toString();
        step3(getString(R.string.identification), 3);
        title.setText(getString(R.string.new_password));
        name.setText(getString(R.string.new_password));
        confirmBtn.setText(getString(R.string.identification));
    }

    /**
     * 确认按钮点击
     */
    private void identification() {
        if (isEmpty()) {
            ResetPwdRequest resetPwdRequest = new ResetPwdRequest();
            resetPwdRequest.setMobile(mobile);
            resetPwdRequest.setNewPassWord(password.getText().toString());
            resetPwdRequest.setSmsCode(smsCode);
            networkBroker.ask(resetPwdRequest, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    return;
                }
                try {
                    ResetPwdResponse response = (ResetPwdResponse) res;
                    showToast(res.getMessage());
                    if (response.getCode() == 200) {
                        step4(getString(R.string.go_login));
                        title.setText(getString(R.string.congratulations));
                        prompt.setText(getString(R.string.password_resetting_success));
                        confirmBtn.setBackgroundResource(R.color.color_FF5657);
                        confirmBtn.setText(getString(R.string.go_login));
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
        if (TextUtils.isEmpty(password.getText())) {
            showToast(R.string.please_enter_a_new_password);
            return false;
        }
        if (TextUtils.isEmpty(againPassword.getText())) {
            showToast(R.string.please_enter_the_password_again);
            return false;
        }
        if (checkPassWord(password.getText().toString())
                || checkPassWord(againPassword.getText().toString())) {
            showToast(R.string.cryptographic_format);
            return false;
        }
        if (!password.getText().toString().equals(againPassword.getText().toString())) {
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


    /**
     * 去登陆
     */
    private void goLogin() {
        finish();
    }


    private void step1(String btnTxt, int index) {
        stepIndex = index;
        prompt.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        codeView.setVisibility(View.GONE);
        value.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mobile)) {
            value.setText(mobile);
        }
        password.setVisibility(View.GONE);
        againIdentification.setVisibility(View.GONE);
        againPassword.setVisibility(View.GONE);
        confirmBtn.setText(btnTxt);
    }

    private void step2(String btnTxt, int index) {
        stepIndex = index;
        prompt.setVisibility(View.INVISIBLE);
        name.setVisibility(View.VISIBLE);
        codeView.setVisibility(View.VISIBLE);
        value.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(smsCode)) {
            value.setText(smsCode);
        }
        password.setVisibility(View.GONE);
        againIdentification.setVisibility(View.GONE);
        againPassword.setVisibility(View.GONE);
        confirmBtn.setText(btnTxt);
    }

    private void step3(String btnTxt, int index) {
        stepIndex = index;
        prompt.setVisibility(View.INVISIBLE);
        name.setVisibility(View.VISIBLE);
        codeView.setVisibility(View.GONE);
        value.setVisibility(View.GONE);
        password.setVisibility(View.VISIBLE);
        againIdentification.setVisibility(View.VISIBLE);
        againPassword.setVisibility(View.VISIBLE);
        confirmBtn.setText(btnTxt);
    }

    private void step4(String btnTxt) {
        stepIndex = 0;
        prompt.setVisibility(View.VISIBLE);
        name.setVisibility(View.INVISIBLE);
        codeView.setVisibility(View.GONE);
        value.setVisibility(View.INVISIBLE);
        password.setVisibility(View.GONE);
        againIdentification.setVisibility(View.GONE);
        againPassword.setVisibility(View.GONE);
        confirmBtn.setText(btnTxt);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        switch (stepIndex) {
            case 2:
                title.setText(getString(R.string.forget_the_password));
                name.setText(getString(R.string.phone_number));
                value.setInputType(InputType.TYPE_CLASS_NUMBER);
                value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                value.setText(null);
                value.setFocusable(true);
                value.setFocusableInTouchMode(true);
                value.requestFocus();
                step1(getString(R.string.next_step), 1);
                break;
            case 3:
                title.setText(getString(R.string.input_authentication_code));
                name.setText(getString(R.string.authentication_code));
                step2(getString(R.string.confirm), 2);
                break;
            default:
                finish();
                break;
        }
    }
}

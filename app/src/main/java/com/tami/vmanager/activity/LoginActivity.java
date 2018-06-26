package com.tami.vmanager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginRequest;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SendVerifyCodeRequest;
import com.tami.vmanager.entity.SendVerifyCodeResponse;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.VerificationCode;

/**
 * 登陆
 * Created by why on 2018/6/11.
 */
public class LoginActivity extends BaseActivity {

    private EditText logoin_phone;
    private TextView login_password_txt;
    private EditText login_password;
    private TextView get_verification_code;
    private TextView verification_code_prompt;
    private boolean promptFlag = false;//验证码发送提示
    private Button login_btn;
    private TextView authentication_code_login;
    private TextView forget_the_password;
    private VerificationCode verificationCode;
    private NetworkBroker networkBroker;

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
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //设置主题颜色
        logoin_phone = findViewById(R.id.logoin_phone);
        login_password_txt = findViewById(R.id.login_password_txt);
        login_password = findViewById(R.id.login_password);
        get_verification_code = findViewById(R.id.get_verification_code);
        verification_code_prompt = findViewById(R.id.verification_code_prompt);
        authentication_code_login = findViewById(R.id.authentication_code_login);
        forget_the_password = findViewById(R.id.forget_the_password);
        login_btn = findViewById(R.id.login_btn);
    }

    @Override
    public void initListener() {
        login_btn.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        authentication_code_login.setOnClickListener(this);
        forget_the_password.setOnClickListener(this);
    }

    @Override
    public void initData() {
        verificationCode = new VerificationCode.Build()
                .setContext(getApplicationContext())
                .setCodeView(get_verification_code)
                .setExecuteTxt("秒后重新获取")
                .setEndTxt("获取验证码")
                .build();

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        logoin_phone.setText("15901125418");
//        logoin_phone.setText("13800138000");
        login_password.setText("111111");
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
            case R.id.get_verification_code:
                getVerificationCode();
                break;
            case R.id.login_btn:
                loginBtn();
                break;
            case R.id.authentication_code_login:
                authenticationCodeLogin(v);
                break;
            case R.id.forget_the_password:
                forgetThePassword();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerificationCode() {
        SendVerifyCodeRequest sendVerifyCodeRequest = new SendVerifyCodeRequest();
        sendVerifyCodeRequest.setMobile(logoin_phone.getText().toString());
        networkBroker.ask(sendVerifyCodeRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SendVerifyCodeResponse response = (SendVerifyCodeResponse) res;
                if (response.getCode() == 200) {
                    verificationCode.start();
                    promptFlag = true;
                    verification_code_prompt.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 密码登录与验证码登录切换
     */
    private void authenticationCodeLogin(View v) {
        TextView tv = (TextView) v;
        if (getString(R.string.accounts_login).equals(tv.getText().toString())) {
            verification_code_prompt.setVisibility(View.INVISIBLE);
            get_verification_code.setVisibility(View.GONE);
            login_password_txt.setText(getString(R.string.password_txt));
            login_password.setText(null);
            login_password.setHint(getString(R.string.please_input_password));
            tv.setText(getString(R.string.authentication_code_login));
        } else {
            if (promptFlag) {
                verification_code_prompt.setVisibility(View.VISIBLE);
            }
            get_verification_code.setVisibility(View.VISIBLE);
            login_password_txt.setText(getString(R.string.authentication_code));
            login_password.setText(null);
            login_password.setHint(getString(R.string.input_authentication_code));
            tv.setText(getString(R.string.accounts_login));
        }
    }

    /**
     * 跳转忘记密码
     */
    private void forgetThePassword() {
        startActivity(new Intent(getApplicationContext(), ForgetThePasswordActivity.class));
    }

    /**
     * 登陆
     */
    private void loginBtn() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobile(logoin_phone.getText().toString());
        if (getString(R.string.authentication_code_login).equals(authentication_code_login.getText())) {
            loginRequest.setRequestUrl(HttpKey.USER_LOGIN);
            loginRequest.setPassWord(login_password.getText().toString());
        } else {
            loginRequest.setRequestUrl(HttpKey.USER_LOGIN_SMS);
            loginRequest.setSmsCode(login_password.getText().toString());
        }
        networkBroker.ask(loginRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                LoginResponse response = (LoginResponse) res;
                if (response.getCode() == 200) {
                    GlobaVariable.getInstance().item = response.getData();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

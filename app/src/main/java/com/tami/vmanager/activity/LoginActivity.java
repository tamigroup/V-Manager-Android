package com.tami.vmanager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.callback.RequestCallback;
import com.tami.vmanager.entity.LoginEntity;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.HttpManager;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.VerificationCode;

import java.util.HashMap;
import java.util.Map;

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
    private boolean promptFlag = false;
    private Button login_btn;
    private TextView authentication_code_login;
    private TextView forget_the_password;
    private VerificationCode verificationCode;

    @Override
    public int getStartBarColor() {
        return android.R.color.transparent;
    }

    @Override
    public int getNavigationBarColor() {
        return android.R.color.transparent;
    }

    @Override
    public boolean isTitle() {
        return false;
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
        verificationCode.start();
        promptFlag = true;
        verification_code_prompt.setVisibility(View.VISIBLE);
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
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

//        String url = "http://192.168.103.196/yundao-api/wxapp/login";
//        Map<String, String> params = new HashMap<>();
//        params.put("mobile", "13800138000");
//        params.put("passWord", "111111");
//        Gson gson = new Gson();
//        OkHttpUtils
//                .postString()
//                .url(url)
//                .content(gson.toJson(params))
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new RequestCallback<LoginEntity>() {
//
//                    @Override
//                    public void onSuccess(LoginEntity response) {
//                        Logger.d("onResponse:" + response.message);
//                        Logger.d("onResponse:" + response.data.headImgUrl);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });


//        Map<String, String> serviceParams = new HashMap<>();
//        serviceParams.put(HttpKey.KEY_MOBILE, "13800138000");
//        serviceParams.put(HttpKey.KEY_PASSWORD, "111111");
//        HttpManager.initPostString(HttpKey.USER_LOGIN, serviceParams)
//                .build().execute(new RequestCallback<LoginEntity>() {
//
//            @Override
//            public void onSuccess(LoginEntity response) {
//                Logger.d("onResponse:" + response.message);
//                Logger.d("onResponse:" + response.data.headImgUrl);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
    }
}

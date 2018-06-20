package com.tami.vmanager.activity;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

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
    }

    @Override
    public void initListener() {
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.forget_password_confirm_btn) {
            Button btn = (Button) v;
            if (getString(R.string.sent_verification_code).equals(btn.getText().toString())) {
                sentVerificationCode();
            } else if (getString(R.string.confirm).equals(btn.getText().toString())) {
                confirm();
            } else if (getString(R.string.identification).equals(btn.getText().toString())) {
                identification();
            } else if (getString(R.string.go_login).equals(btn.getText().toString())) {
                goLogin();
            }
        }
    }

    /**
     * 发送验证码点击
     */
    private void sentVerificationCode() {
        title.setText(getString(R.string.input_authentication_code));
        prompt.setVisibility(View.INVISIBLE);
        name.setText(getString(R.string.authentication_code));
        value.setInputType(InputType.TYPE_NULL);
        value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        value.setText(null);
        confirmBtn.setText(getString(R.string.confirm));
    }

    /**
     * 确定按钮点击
     */
    private void confirm() {
        title.setText(getString(R.string.new_password));
        prompt.setVisibility(View.INVISIBLE);
        name.setText(getString(R.string.new_password));
        value.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        value.setText(null);
        againIdentification.setVisibility(View.VISIBLE);
        againPassword.setVisibility(View.VISIBLE);
        confirmBtn.setText(getString(R.string.identification));
    }

    /**
     * 确认按钮点击
     */
    private void identification() {
        title.setText(getString(R.string.congratulations));
        prompt.setVisibility(View.VISIBLE);
        prompt.setText(getString(R.string.password_resetting_success));
        name.setVisibility(View.INVISIBLE);
        value.setVisibility(View.INVISIBLE);
        againIdentification.setVisibility(View.INVISIBLE);
        againPassword.setVisibility(View.INVISIBLE);
        confirmBtn.setText(getString(R.string.go_login));
    }

    /**
     * 去登陆
     */
    private void goLogin() {
        finish();
    }
}

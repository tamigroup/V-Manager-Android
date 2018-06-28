package com.tami.vmanager.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateVipGuestRequest;
import com.tami.vmanager.entity.CreateVipGuestResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;

/**
 * VIP人物介绍
 * Created by why on 2018/6/27.
 */
public class VIPPersonageIntroductionActivity extends BaseActivity implements TextWatcher {

    private EditText avpi_name;
    private EditText avpi_introduction;
    // 最多可输入文本数
    private final int num = 500;
    // 输入框现有文本
    private CharSequence temp;
    // 现有文本启始位置
    private int editStart;
    // 现有文本终止位置
    private int editEnd;

    private Button avpi_submission;

    private NetworkBroker networkBroker;


    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_view_personage_introduction;
    }

    @Override
    public void initView() {
        avpi_name = findViewById(R.id.avpi_name);
        avpi_introduction = findViewById(R.id.avpi_introduction);
        avpi_submission = findViewById(R.id.avpi_submission);
    }

    @Override
    public void initListener() {
        avpi_submission.setOnClickListener(this);
        avpi_introduction.addTextChangedListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.vip_personage_introduction);
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {
        avpi_introduction.removeTextChangedListener(this);
    }

    @Override
    public void emptyObject() {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        temp = charSequence;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        editStart = avpi_introduction.getSelectionStart();
        editEnd = avpi_introduction.getSelectionEnd();
        // 判断是否到最大文字数
        if (temp.length() > num) {
            editable.delete(editStart - 1, editEnd);
            avpi_introduction.setText(editable);
            avpi_introduction.setSelection(editable.length());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.avpi_submission:
                submission();
                break;
        }
    }

    private void submission() {
        if (!isCheck()) {
            return;
        }
        CreateVipGuestRequest createVipGuestRequest = new CreateVipGuestRequest();
//        createVipGuestRequest.setMeetingId();
//        createVipGuestRequest.setSystemId();
        createVipGuestRequest.setName(avpi_name.getText().toString());
        createVipGuestRequest.setIntro(avpi_introduction.getText().toString());
        networkBroker.ask(createVipGuestRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                CreateVipGuestResponse response = (CreateVipGuestResponse) res;
                if (response.getCode() == 200 && response.data) {
                    backLastPage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(avpi_name.getText())) {
            showToast("请输入姓名");
            return false;
        } else if (TextUtils.isEmpty(avpi_introduction.getText())) {
            showToast("请输入介绍");
            return false;
        }
        return true;
    }

    private void backLastPage(){

        setResult(0);
    }
}

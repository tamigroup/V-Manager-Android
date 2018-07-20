package com.tami.vmanager.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateVipGuestRequest;
import com.tami.vmanager.entity.CreateVipGuestResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.MeetingPlaceSelectResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;

/**
 * VIP人物介绍
 * Created by why on 2018/6/27.
 */
public class VIPPersonageIntroductionActivity extends BaseActivity implements TextWatcher {

    private EditText avpi_name;
    private EditText avpi_introduction;
    private TextView wordCount;
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

    private int meetingId = -1;


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
        wordCount = findViewById(R.id.avpi_word);
    }

    @Override
    public void initListener() {
        avpi_submission.setOnClickListener(this);
        avpi_introduction.addTextChangedListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, -1);
        }

        setTitleName(R.string.add_vip_personage_introduction);
        networkBroker = new NetworkBroker(getApplicationContext());
        networkBroker.setCancelTag(getTAG());

        setWords(0);
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
        temp = null;
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        temp = charSequence;
        setWords(charSequence.length());
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
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            createVipGuestRequest.setSystemId(item.getSystemId());
        }
        createVipGuestRequest.setName(avpi_name.getText().toString());
        createVipGuestRequest.setIntro(avpi_introduction.getText().toString());
        backLastPage(createVipGuestRequest);
//        if (meetingId != -1) {
//            createVipGuestRequest.setMeetingId(meetingId);
//            backLastPage(createVipGuestRequest);
//            networkBroker.ask(createVipGuestRequest, (ex1, res) -> {
//                if (null != ex1) {
//                    Logger.d(ex1.getMessage() + "-" + ex1);
//                    return;
//                }
//                try {
//                    CreateVipGuestResponse response = (CreateVipGuestResponse) res;
//                    if (response.getCode() == 200 && response.data) {
//                        backLastPage(createVipGuestRequest);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        } else {
//            backLastPage(createVipGuestRequest);
//        }
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

    private void backLastPage(CreateVipGuestRequest item) {
        Intent intent = new Intent();
        intent.putExtra(Constants.RESULT_VIP, item);
        setResult(Constants.CREATE_MEETING_VIP, intent);
        finish();
    }

    private void setWords(int number) {
        wordCount.setText(getString(R.string.word_count_500, number));
    }
}

package com.tami.vmanager.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateNoticeRequest;
import com.tami.vmanager.entity.CreateNoticeResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.message.MessageEvent;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.SwitchButton;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Tang on 2018/7/7
 * 编辑群公告
 */
public class GroupEditNoticeActivity extends BaseActivity {

    private NetworkBroker networkBroker;
    private int meetingId;
    private EditText title;
    private EditText content;
    private SwitchButton switchButton;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_edit_notice;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.title_edit);
        content = findViewById(R.id.content_edit);
        switchButton = findViewById(R.id.agen_switchButton);
    }

    @Override
    public void initListener() {
//        switchButton.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
//
//        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
        }
        setTitleName(R.string.group_edit);

        setTitleRightTxt(R.string.group_release);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        title.addTextChangedListener(titleTextWatcher);

        content.addTextChangedListener(contentTextWatcher);
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {
        title.removeTextChangedListener(titleTextWatcher);
        content.removeTextChangedListener(contentTextWatcher);
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
            case R.id.titleRightTxt:
                createNotice();
                break;
        }
    }

    /**
     * 创建公告
     */
    private void createNotice() {
        if (isEmpty()) {
            CreateNoticeRequest cnr = new CreateNoticeRequest();
            LoginResponse.Item item = GlobaVariable.getInstance().item;
            if (item != null) {
                cnr.setUserId(item.getId());
                cnr.setSystemId(item.getSystemId());
            }
            cnr.setMeetingId(meetingId);
            cnr.setTitle(title.getText().toString());
            cnr.setContent(content.getText().toString());
            cnr.setGroupType(1);//群组类型  1-V管家 2-V智会 3-VV群
            cnr.setNoticeType(3);//1-系统通知  2-会务广播 3-群内公告
            cnr.setIsTop(switchButton.isChecked() ? 1 : 0);// 0 正常    1 置顶

//            cnr.setSystemId(4);
//            cnr.setMeetingId(1);
//            cnr.setGroupType(1);
//            cnr.setUserId(1);
//            cnr.setNoticeType(3);
//            cnr.setTitle("测试");
//            cnr.setContent("测试一下");
//            cnr.setIsTop(1);

            networkBroker.ask(cnr, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    return;
                }
                try {
                    CreateNoticeResponse response = (CreateNoticeResponse) res;
                    if (response.getCode() == 200 && response.data) {
                        EventBus.getDefault().post(new MessageEvent(true));
                        showToast(getString(R.string.announcements, getString(R.string.success)));
                        setResult(Constants.GROUP_EDIT_NOTICE);
                        finish();
                    } else {
                        showToast(getString(R.string.announcements, getString(R.string.failure)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 空判断
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            showToast(getString(R.string.input_title));
            return false;
        }
        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            showToast(getString(R.string.input_content));
            return false;
        }
        return true;
    }

    public TextWatcher titleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence title_content, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable title_content) {
            if (title_content.toString().trim().length() > 40) {
                title.setText(title_content.toString().substring(0, 40));
                title.setSelection(40);
                showToast(getString(R.string.input_title_length));
            }
        }
    };


    public TextWatcher contentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable content_con) {
            if (content_con.toString().trim().length() > 300) {
                content.setText(content_con.toString().substring(0, 300));
                content.setSelection(300);
                showToast(getString(R.string.input_content_length));
            }
        }
    };
}

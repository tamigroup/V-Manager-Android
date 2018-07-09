package com.tami.vmanager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateNoticeRequest;
import com.tami.vmanager.entity.CreateNoticeResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;

/**
 * Created by Tang on 2018/7/7
 * 编辑群公告
 */
public class GroupEditNoticeActivity extends BaseActivity {

    private NetworkBroker networkBroker;
    private int meetingId;
    private EditText title;
    private EditText content;

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
    }

    @Override
    public void initListener() {

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
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
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
            cnr.setIsTop(0);// 0 正常    1 置顶

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
                        showToast(getString(R.string.announcements, getString(R.string.success)));
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
        if (TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(title.getText())) {
            showToast(getString(R.string.must_add_item_not_empty));
            return false;
        }
        return true;
    }
}

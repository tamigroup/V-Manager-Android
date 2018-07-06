package com.tami.vmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;

/**
 * Created by Tang on 2018/7/6
 */
public class VipMessageActivity extends BaseActivity {

    private TextView message;
    private TextView vip_name;
    private static Intent intent;
    private static String NAME_EXTRA = "name_extra";
    private static String MESSAGE_EXTRA = "message_extra";

    @Override
    public int getContentViewId() {
        return R.layout.activity_vip_message;
    }


    public static void Start(Context context, String name, String message) {
        intent = new Intent(context, VipMessageActivity.class);
        intent.putExtra(NAME_EXTRA,name);
        intent.putExtra(MESSAGE_EXTRA,message);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        setTitle(R.string.vip_details);
        vip_name = findViewById(R.id.vip_name);
        message = findViewById(R.id.message);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String name = intent.getStringExtra(NAME_EXTRA);
        String content = intent.getStringExtra(MESSAGE_EXTRA);
        vip_name.setText(name);
        message.setText(content);

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
}

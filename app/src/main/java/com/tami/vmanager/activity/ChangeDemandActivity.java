package com.tami.vmanager.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 需求变化 活动变化
 * Created by why on 2018/6/27.
 */
public class ChangeDemandActivity extends BaseActivity {

    private RecyclerView recyclerView;
    List<String> data = Arrays.asList("dadda", "sadadad", "sdasda");
    private DialogPlus dialog;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_demand;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyc);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(R.string.change_demand);
        initRecyc();
    }

    private void initRecyc() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item_feedback, data) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_content_tv, s);
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                dialog = DialogPlus.newDialog(ChangeDemandActivity.this)
                        .setCancelable(true)
                        .setGravity(Gravity.BOTTOM)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_reply))
                        .create();
                dialog.show();
                EditText reply_edit = (EditText) dialog.findViewById(R.id.reply_edit);
                TextView send = (TextView) dialog.findViewById(R.id.send);
                Utils.showSoftInput(ChangeDemandActivity.this);
                reply_edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                send.setOnClickListener(v -> {
                    Utils.hideSoftInput(ChangeDemandActivity.this);
                    dialog.dismiss();
                });
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(commonAdapter);
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
    public void onBackPressed() {
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}

package com.tami.vmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;

/**
 * Created by why on 2018/7/4.
 */
public class ServiceFlowDialog extends Dialog implements View.OnClickListener, TextWatcher {

    private ServiceFlowListener serviceFlowListener;
    private Context context;
    private EditText flowNameView;
    private TextView wordCount;
    // 最多可输入文本数
    private final int num = 25;
    // 输入框现有文本
    private CharSequence temp;
    // 现有文本启始位置
    private int editStart;
    // 现有文本终止位置
    private int editEnd;

    public ServiceFlowDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_dialog_adding_custom_processes, null);
        view.findViewById(R.id.sdacp_confirm).setOnClickListener(this);
        view.findViewById(R.id.sdacp_cancel).setOnClickListener(this);
        flowNameView = view.findViewById(R.id.sdacp_edit);
        flowNameView.addTextChangedListener(this);
        wordCount = view.findViewById(R.id.sdacp_word_count);
        setWords(0);
        setCanceledOnTouchOutside(false);
//        setCancelable(false);//对话框以后地方点击不消息包含返回键
        setContentView(view);
    }

    public void setServiceFlowListener(ServiceFlowListener serviceFlowListener) {
        this.serviceFlowListener = serviceFlowListener;
    }

    @Override
    public void onClick(View v) {
        if (serviceFlowListener != null) {
            switch (v.getId()) {
                case R.id.sdacp_confirm:
                    if (!TextUtils.isEmpty(flowNameView.getText())) {
                        dismiss();
                        serviceFlowListener.confirm(flowNameView.getText().toString());
                    } else {
                        Toast.makeText(context, context.getString(R.string.please_enter_process_title), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.sdacp_cancel:
                    dismiss();
                    break;
            }
        } else {
            Toast.makeText(context, context.getString(R.string.please_seting_listener), Toast.LENGTH_SHORT).show();
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
        editStart = flowNameView.getSelectionStart();
        editEnd = flowNameView.getSelectionEnd();
        // 判断是否到最大文字数
        if (temp.length() > num) {
            editable.delete(editStart - 1, editEnd);
            flowNameView.setText(editable);
            flowNameView.setSelection(editable.length());
        }
    }

    private void setWords(int number) {
        wordCount.setText(context.getString(R.string.word_count, number));
    }

    public void removeListener() {
        if (flowNameView != null) {
            flowNameView.removeTextChangedListener(this);
        }
    }

    @Override
    public void show() {
        if (flowNameView != null) {
            flowNameView.setText(null);
        }
        super.show();
    }
}


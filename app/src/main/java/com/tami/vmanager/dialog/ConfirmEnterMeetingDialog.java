package com.tami.vmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;

/**
 * Created by why on 2018/6/19.
 */

public class ConfirmEnterMeetingDialog extends Dialog implements View.OnClickListener {

    private ConfirmEnterMeetingListener confirmEnterMeetingListener;
    private Context context;

    public ConfirmEnterMeetingDialog(Context context) {
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
        View view = inflater.inflate(R.layout.show_dialog_meeting_overview, null);
        TextView leftBtn = view.findViewById(R.id.sdmo_view_left_btn);
        leftBtn.setOnClickListener(this);
        if (!TextUtils.isEmpty(leftStr)) {
            leftBtn.setText(leftStr);
        }
        if (leftColor != 0) {
            leftBtn.setTextColor(ContextCompat.getColor(context, leftColor));
        }
        TextView rightBtn = view.findViewById(R.id.sdmo_view_rigth_btn);
        rightBtn.setOnClickListener(this);
        if (!TextUtils.isEmpty(rightStr)) {
            rightBtn.setText(rightStr);
        }
        if (rightColor != 0) {
            leftBtn.setTextColor(ContextCompat.getColor(context, rightColor));
        }
        TextView contentView = view.findViewById(R.id.sdmo_content);
        if (!TextUtils.isEmpty(contentStr)) {
            contentView.setText(contentStr);
        }
        setCanceledOnTouchOutside(false);
//        setCancelable(false);//对话框以后地方点击不消息包含返回键
        setContentView(view);
    }

    public void setConfirmEnterMeetingListener(ConfirmEnterMeetingListener confirmEnterMeetingListener) {
        this.confirmEnterMeetingListener = confirmEnterMeetingListener;
    }

    @Override
    public void onClick(View v) {
        if (confirmEnterMeetingListener != null) {
            switch (v.getId()) {
                case R.id.sdmo_view_left_btn:
                    dismiss();
                    confirmEnterMeetingListener.leftBtn();
                    break;
                case R.id.sdmo_view_rigth_btn:
                    dismiss();
                    confirmEnterMeetingListener.rightBtn();
                    break;
            }
        } else {
            Toast.makeText(context, "请设置监听", Toast.LENGTH_SHORT).show();
        }
    }

    private String leftStr;
    private String rightStr;
    private String contentStr;

    public void setLeftStr(String leftStr) {
        this.leftStr = leftStr;
    }

    public void setRightStr(String rightStr) {
        this.rightStr = rightStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public void setLeftRes(int res) {
        this.leftStr = context.getString(res);
    }

    public void setRightRes(int res) {
        this.rightStr = context.getString(res);
    }

    public void setContentRes(int res) {
        this.contentStr = context.getString(res);
    }

    public void init(String leftStr, String rightStr, String contentStr) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.contentStr = contentStr;
    }

    public void init(int leftRes, int rightRes, int contentRes) {
        init(context.getString(leftRes), context.getString(rightRes), context.getString(contentRes));
    }

    private int leftColor;
    private int rightColor;

    public void setLeftColor(@ColorRes int leftColor) {
        this.leftColor = leftColor;
    }

    public void setRightColo(@ColorRes int rightColor) {
        this.rightColor = rightColor;
    }
}

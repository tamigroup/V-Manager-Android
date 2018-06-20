package com.tami.vmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        view.findViewById(R.id.sdmo_view_only).setOnClickListener(this);
        view.findViewById(R.id.sdmo_confirm).setOnClickListener(this);
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
                case R.id.sdmo_view_only:
                    dismiss();
                    confirmEnterMeetingListener.viewOnly();
                    break;
                case R.id.sdmo_confirm:
                    dismiss();
                    confirmEnterMeetingListener.confirm();
                    break;
            }
        } else {
            Toast.makeText(context, "请设置监听", Toast.LENGTH_SHORT).show();
        }
    }

}

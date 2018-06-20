package com.tami.vmanager.utils;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.tami.vmanager.R;

/**
 * Description：验证码
 * User: lxs
 */
public class VerificationCode {

    private Context context;
    private TextView code;
    private TimeHandler timeHandler;
    private long startTime;
    private final String second = " S";

    public VerificationCode(Context context, TextView code) {
        this.context = context;
        this.code = code;
        timeHandler = new TimeHandler();
    }

    public void start() {
        code.setEnabled(false);
        startTime = System.currentTimeMillis();
        timeHandler.sendEmptyMessage(1);
    }

    public void stop() {
        startTime = 0;
        timeHandler.sendEmptyMessage(0);
    }

    public void clear() {
        code = null;
        timeHandler = null;
        context = null;
    }

    private class TimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                int time = 59 - (int) ((System.currentTimeMillis() - startTime) / 1000);
                String show = time + second;
                code.setText(show);
                sendEmptyMessageDelayed(time, 100);
            } else {
                code.setText(context.getResources().getString(R.string.get_verification_code));
                code.setEnabled(true);
                timeHandler.removeCallbacksAndMessages(null);
            }
        }
    }
}

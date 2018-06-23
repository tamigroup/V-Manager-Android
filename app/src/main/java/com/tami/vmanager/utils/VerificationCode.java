package com.tami.vmanager.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Description：验证码
 * User: lxs
 */
public class VerificationCode {

    private Context context;
    private TextView codeView;
    private TimeHandler timeHandler;
    private long startTime;
    private String executeTxt;
    private int executeTxtColor;
    private String endTxt;
    private int endTxtColor;

    public static class Build {

        private Context context;
        private TextView codeView;
        private String executeTxt;
        private String endTxt;
        private int executeTxtColor;
        private int endTxtColor;

        public Build setContext(Context context) {
            this.context = context;
            return this;
        }

        public Build setCodeView(TextView codeView) {
            this.codeView = codeView;
            return this;
        }

        public Build setExecuteTxt(String executeTxt) {
            this.executeTxt = executeTxt;
            return this;
        }

        public Build setEndTxt(String endTxt) {
            this.endTxt = endTxt;
            return this;
        }

        public Build setExecuteTxtColor(@ColorRes int executeTxtColor) {
            this.executeTxtColor = executeTxtColor;
            return this;
        }

        public Build setEndTxtColor(@ColorRes int endTxtColor) {
            this.endTxtColor = endTxtColor;
            return this;
        }

        public VerificationCode build() {
            return new VerificationCode(this);
        }
    }

    private VerificationCode(Build build) {
        this.context = build.context;
        this.codeView = build.codeView;
        this.executeTxt = build.executeTxt;
        this.endTxt = build.endTxt;
        this.executeTxtColor = build.executeTxtColor;
        this.endTxtColor = build.endTxtColor;
        timeHandler = new TimeHandler();
    }

    public void start() {
        codeView.setEnabled(false);
        if (executeTxtColor != 0) {
            codeView.setTextColor(ContextCompat.getColor(context, executeTxtColor));
        }
        startTime = System.currentTimeMillis();
        timeHandler.sendEmptyMessage(1);
    }

    public void stop() {
        startTime = 0;
        codeView.setText(endTxt);
        if (endTxtColor != 0) {
            codeView.setTextColor(ContextCompat.getColor(context, endTxtColor));
        }
        codeView.setEnabled(true);
        timeHandler.removeCallbacksAndMessages(null);
    }

    public void clear() {
        context = null;
        codeView = null;
        timeHandler = null;
    }

    private class TimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                int time = 59 - (int) ((System.currentTimeMillis() - startTime) / 1000);
                String show = time + executeTxt;
                codeView.setText(show);
                sendEmptyMessageDelayed(time, 100);
            } else {
                codeView.setText(endTxt);
                if (endTxtColor != 0) {
                    codeView.setTextColor(ContextCompat.getColor(context, endTxtColor));
                }
                codeView.setEnabled(true);
                timeHandler.removeCallbacksAndMessages(null);
            }
        }
    }
}

package com.tami.vmanager.utils;

import android.util.Log;

/**
 * 日志类
 * Created by why on 2018/6/11.
 */

public class Logger {


    public static void d(String msg) {
        if (Constants.LOG_SWITCH) {
            Log.d(Constants.LOG_TAG, msg);
        }
    }

    public static void v(String msg) {
        if (Constants.LOG_SWITCH) {
            Log.v(Constants.LOG_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (Constants.LOG_SWITCH) {
            Log.i(Constants.LOG_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (Constants.LOG_SWITCH) {
            Log.e(Constants.LOG_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (Constants.LOG_SWITCH) {
            Log.w(Constants.LOG_TAG, msg);
        }
    }
}


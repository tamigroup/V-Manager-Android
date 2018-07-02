package com.tami.vmanager.utils;

/**
 * 常量类
 * Created by why on 2018/6/11.
 */

public class Constants {

    //偏好存储
    public static final String PREFERENCE_DEFAULT_NAME = "tami";
    //日志开关及LOG标签
    public static boolean LOG_SWITCH = true;
    public static final String LOG_TAG = "TaMiLog";

    public static final String IMAGE_KEY = "image_key";
    public static final String SKIP_BTN = "skip_btn";
    public static final String EXPERIENCE = "experience";
    public static final String IMAGE_A = "a";
    public static final String IMAGE_B = "b";
    public static final String IMAGE_C = "c";

    //进入会议页面的TYPE
    public static final String MEETING_TYPE = "meeting_type";
    public static final int MEETING_TYPE_WHOLE = 0;
    public static final int MEETING_TYPE_PENDING_PAYMENT = 1;
    public static final int MEETING_TYPE_PERFECTED = 2;
    public static final int MEETING_TYPE_DAY = 3;
    public static final int MEETING_TYPE_MONTH = 4;
    public static final int MEETING_TYPE_YEAR = 5;

    public static final String FOLLOW_TYPE = "follow_type";
    public static final int FOLLOW_TYPE_WHOLE = 0;
    public static final int FOLLOW_TYPE_DAY = 1;
    public static final int FOLLOW_TYPE_WEEK = 2;
    public static final int FOLLOW_TYPE_MONTH = 3;
    public static final String WAIT_TYPE = "wait_type";
    /******************** 时间相关常量 ********************/
    //秒与毫秒的倍数
    public static final int MSEC = 1;
    //秒与毫秒的倍数
    public static final int SEC = 1000;
    //分与毫秒的倍数
    public static final int MIN = 60000;
    //时与毫秒的倍数
    public static final int HOUR = 3600000;
    //天与毫秒的倍数
    public static final int DAY = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    public static final String KEY_DAY = "key_day";

    public static final String FIRST_LANDING = "firstLanding";
    //每页记录条数
    public static final int PAGE_SIZE = 10;

    public static final int CREATE_MEETING_DIDIAN = 0x10;
    public static final String RESULT_DIDIAN = "didian";
    public static final int CREATE_MEETING_JIEDAIREN = 0x11;
    public static final String RESULT_JIEDAIREN = "jiedairen";
    public static final int CREATE_MEETING_VIP = 0x12;
    public static final String RESULT_VIP = "vip";
}

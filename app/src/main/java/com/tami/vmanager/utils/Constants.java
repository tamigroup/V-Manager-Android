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
    public static final int MEETING_TYPE_DAY = 2;
    public static final int MEETING_TYPE_MONTH = 3;
    public static final int MEETING_TYPE_YEAR = 4;

    /******************** 时间相关常量 ********************/
    //秒与毫秒的倍数
    public static final int MSEC = 1;
    //秒与毫秒的倍数
    public static final int SEC  = 1000;
    //分与毫秒的倍数
    public static final int MIN  = 60000;
    //时与毫秒的倍数
    public static final int HOUR = 3600000;
    //天与毫秒的倍数
    public static final int DAY  = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }
    public static final String KEY_DAY = "key_day";

}

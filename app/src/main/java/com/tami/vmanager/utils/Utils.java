package com.tami.vmanager.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Created by why on 2018/6/11.
 */
public class Utils {


    /**
     * 改变文本颜色及大小
     *
     * @param context
     * @param str
     * @param start
     * @param end
     * @param size
     * @param color
     * @return
     */
    public static SpannableStringBuilder getSplicing(Context context, String str, int start, int end, int size, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new AbsoluteSizeSpan(ScreenUtil.sp2px(context, size)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 只改字体大小
     *
     * @param context
     * @param str
     * @param start
     * @param end
     * @param size
     * @return
     */
    public static SpannableStringBuilder getSplicing(Context context, String str, int start, int end, int size) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new AbsoluteSizeSpan(ScreenUtil.sp2px(context, size)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}

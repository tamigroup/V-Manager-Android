package com.tami.vmanager.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.tami.vmanager.R;

/**
 * Created by why on 2018/7/4.
 */
public class MeetingStateView extends android.support.v7.widget.AppCompatTextView {

    public MeetingStateView(Context context) {
        super(context);
    }

    public MeetingStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeetingStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 非圆角调用
     *
     * @param text
     */
    public void setMeetingStateText(String text) {
        setMeetingStateText(text, 0);
    }

    /**
     * 圆角调用
     *
     * @param text
     */
    public void setMeetingStateText(String text, float redius) {
        if (getContext().getString(R.string.before_meeting).equals(text)) {
            //会前
//            setTextStyle(text, R.color.color_5197FF, R.color.color_5990FF, redius);
            setTextStyle(text, 0, R.color.color_5990FF, redius);
        } else if (getContext().getString(R.string.in_meeting).equals(text)) {
            //会中
//            setTextStyle(text, R.color.color_FFB636, R.color.color_FF8838, redius);
            setTextStyle(text, 0, R.color.color_FF8838, redius);
        } else if (getContext().getString(R.string.perfected).equals(text)) {
            //待完善
//            setTextStyle(text, R.color.color_FFC9C9, R.color.color_FF8F8F, redius);
            setTextStyle(text, 0, R.color.color_FF8F8F, redius);
        } else if (getContext().getString(R.string.stay_execute).equals(text)) {
            //待执行
//            setTextStyle(text, R.color.color_D7CBFF, R.color.color_8467FF, redius);
            setTextStyle(text, 0, R.color.color_8467FF, redius);
        } else if (getContext().getString(R.string.finished).equals(text)) {
            //已结束
//            setTextStyle(text, R.color.color_EBEBEB, R.color.color_888888, redius);
            setTextStyle(text, 0, R.color.color_888888, redius);
        } else if (getContext().getString(R.string.daiban).equals(text)) {
            //待办
//            setTextStyle(text, R.color.color_7B53FF, R.color.color_8467FF, redius);
            setTextStyle(text, 0, R.color.color_8467FF, redius);
        }
    }

    public void setTextBgStyle(String text, int solidColor, float radius) {
        setTextStyle(text, 0, solidColor, radius);
    }

    public void setTextBgStyle(String text, @ColorRes int strokeColor, int strokeWidth, float radius) {
        setTextStyle(text, strokeColor, strokeWidth, radius);
    }

    public void setTextStyle(String text, @ColorRes int textColor) {
        setTextStyle(text, textColor, textColor, 0);
    }

    public void setTextStyle(String text, @ColorRes int textColor, float radius) {
        setTextStyle(text, textColor, textColor, radius);
    }

    public void setTextStyle(String text, @ColorRes int textColor, @ColorRes int solidColor, float radius) {
        setTextStyle(text, textColor, solidColor, 0, 0, radius);
    }

    public void setTextStyle(String text, @ColorRes int textColor, @ColorRes int strokeColor, int strokeWidth, float radius) {
        setTextStyle(text, textColor, 0, strokeColor, strokeWidth, radius);
    }


    public void setTextStyle(String text, @ColorRes int textColor, @ColorRes int solidColor, @ColorRes int strokeColor, int strokeWidth, float radius) {
        if (!TextUtils.isEmpty(text)) {
            setText(text);
            if (textColor != 0) {
                setTextColor(ContextCompat.getColor(getContext(), textColor));
            }
            getBackgroundDrawable(solidColor, strokeColor, strokeWidth, radius);
        }
    }

    public void getBackgroundDrawable(int solidColor, int strokeColor, int strokeWidth, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        if (solidColor != 0) {
            drawable.setColor(ContextCompat.getColor(getContext(), solidColor));
        }
        if (strokeColor != 0 && strokeWidth != 0) {
            drawable.setStroke(strokeWidth, ContextCompat.getColor(getContext(), strokeColor));
        }
        if (radius != 0) {
            drawable.setCornerRadius(radius);
        }
        setBackground(drawable);
    }

}

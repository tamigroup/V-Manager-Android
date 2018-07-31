package com.tami.pulltorefresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ViewStatus {

    @IntDef({CONTENT_STATUS, LOADING_STATUS,EMPTY_STATUS,ERROR_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VIEW_STATUS {

    }

    public static final int CONTENT_STATUS = 0;
    public static final int LOADING_STATUS = 1;
    public static final int EMPTY_STATUS = 2;
    public static final int ERROR_STATUS = 3;

}

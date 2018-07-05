package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/5
 * 反馈 请求服务器参数
 */
public class FeedbackRequestBean extends MobileMessage implements Serializable{

    /**
     * meetingId : 9
     * toType : 1
     * curPage : 1
     * pageSize : 10
     */

    private int meetingId;
    private int toType;
    private int curPage;
    private int pageSize;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Class getResponseClass() {
        return FeedbackResponseBean.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.FEED_BACK;
    }
}

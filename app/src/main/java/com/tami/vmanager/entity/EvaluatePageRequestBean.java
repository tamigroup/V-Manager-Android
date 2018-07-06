package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/6
 * 意见箱 评论列表
 */
public class EvaluatePageRequestBean extends MobileMessage implements Serializable {

    /**
     * meetingId : 1
     * type : 2
     * curPage : 1
     * pageSize : 10
     */

    private int meetingId;
    private int type;
    private int curPage;
    private int pageSize;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
    public String getRequestUrl() {
        return HttpKey.GET_EVALUATEPAGE_LIST;
    }

    @Override
    public Class getResponseClass() {
        return EvaluatePageResponseBean.class;
    }
}

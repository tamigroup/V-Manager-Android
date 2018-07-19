package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/10
 * 需求变化 活动变化 客户端
 */
public class ChangeDemandRequestBean extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -2024853707151616920L;
    /**
     * meetingId : 6
     * curPage : 1
     * pageSize : 10
     */

    private String meetingId;
    private int curPage;
    private int pageSize;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
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
        return HttpKey.GET_MEETING_REQUIREMENT_PAGE;
    }

    @Override
    public Class getResponseClass() {
        return ChangeDemandResponseBean.class;
    }
}

package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/6
 * VIP 详情
 */
public class VIPDetailsRequestBean extends MobileMessage implements Serializable {


    /**
     * systemId : 4
     * meetingId : 1
     * curPage : 1
     * pageSize : 10
     */

    private int systemId;
    private int meetingId;
    private int curPage;
    private int pageSize;

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
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
        return HttpKey.VIP_DETAIL;
    }

    @Override
    public Class getResponseClass() {
        return VIPDetailsResponseBean.class;
    }
}

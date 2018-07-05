package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/5
 * 公告
 */
public class NoticeRequestBean extends MobileMessage implements Serializable {

    /**
     * systemId : 4  ----酒店ID
     * meetingId : 1 ----会议ID
     * groupType : 1 ----群组类型	 1-V管家 2-V智会 3-VV群
     * noticeType : 0 ----公告类型    0-全部  1-系统通知  2-会务广播 3-群内公告
     * curPage : 1 ----页码
     * pageSize : 10 ----每页大小
     */

    private int systemId;
    private int meetingId;
    private int groupType;
    private int noticeType;
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

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
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
        return NoticeResponseBean.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_NOTICE_LIST;
    }
}

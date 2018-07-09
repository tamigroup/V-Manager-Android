package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取会议群组中的用户成员.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class MeetingUserGroupPageRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -4867280010470397182L;
    private int curPage;
    private int meetingId;
    private int pageSize;

    public MeetingUserGroupPageRequest() {
        super();
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.MEETING_USER_GROUP_PAGE;
    }

    @Override
    public Class getResponseClass() {
        return MeetingUserGroupPageResponse.class;
    }
}
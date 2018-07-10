package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 取消会议.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class DeleteUserMeetingRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -6689339922461979754L;

    public DeleteUserMeetingRequest() {
        super();
    }

    private int meetingId;
    private int userId;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.DELETE_USER_MEETING;
    }

    @Override
    public Class getResponseClass() {
        return DeleteUserMeetingResponse.class;
    }
}
package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by why on 2018/8/1.
 */

public class GetUserDepartmentRequest extends MobileMessage implements Serializable {
    private static final long serialVersionUID = -597573195844276470L;

    private int userId;
    private int meetingItemSetId;

    public GetUserDepartmentRequest() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public int getMeetingItemSetId() {
        return meetingItemSetId;
    }

    public void setMeetingItemSetId(int meetingItemSetId) {
        this.meetingItemSetId = meetingItemSetId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_USER_DEPARTMENT;
    }

    @Override
    public Class getResponseClass() {
        return GetUserDepartmentResponse.class;
    }
}

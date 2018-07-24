package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by why on 2018/7/10.
 */
public class CreateUserMeetingItemRequest extends MobileMessage implements Serializable {
    private static final long serialVersionUID = 7517861795727711470L;

    public CreateUserMeetingItemRequest() {
        super();
    }

    private int meetingId;
    private int meetingItemId;
    private String meetingItemName;
    private int roleId;


    public int getMeetingItemId() {
        return meetingItemId;
    }

    public void setMeetingItemId(int meetingItemId) {
        this.meetingItemId = meetingItemId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingItemName() {
        return meetingItemName;
    }

    public void setMeetingItemName(String meetingItemName) {
        this.meetingItemName = meetingItemName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public Class getResponseClass() {
        return CreateUserMeetingItemResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.CREATE_USER_MEETING_ITEM;
    }
}

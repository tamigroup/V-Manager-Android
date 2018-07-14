package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 会议流程节点分配人员.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class SetMeetingItemsUserRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = 8855300368956737262L;

    public SetMeetingItemsUserRequest() {
        super();
    }

    private int meetingItemSetId;
    private String userJsonStr;

    public int getMeetingItemSetId() {
        return meetingItemSetId;
    }

    public void setMeetingItemSetId(int meetingItemSetId) {
        this.meetingItemSetId = meetingItemSetId;
    }

    public String getUserJsonStr() {
        return userJsonStr;
    }

    public void setUserJsonStr(String userJsonStr) {
        this.userJsonStr = userJsonStr;
    }

    @Override
    public Class getResponseClass() {
        return SetMeetingItemsUserResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.SET_MEETING_ITEM_USER;
    }
}
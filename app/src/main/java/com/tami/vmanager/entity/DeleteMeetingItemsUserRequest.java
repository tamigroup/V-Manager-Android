package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 会议流程节点删除人员操作权限.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class DeleteMeetingItemsUserRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = 7070593014424747758L;

    private int userId;
    private int meetingItemSetId;

    public DeleteMeetingItemsUserRequest() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMeetingItemSetId() {
        return meetingItemSetId;
    }

    public void setMeetingItemSetId(int meetingItemSetId) {
        this.meetingItemSetId = meetingItemSetId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.DELETE_MEETING_ITEMS_USER;
    }

    @Override
    public Class getResponseClass() {
        return DeleteMeetingItemsUserResponse.class;
    }
}
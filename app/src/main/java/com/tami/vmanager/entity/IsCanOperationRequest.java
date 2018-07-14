package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 验证用户是否已被分配了某节点权限.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class IsCanOperationRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IsCanOperationRequest() {
        super();
    }

    private int userId;
    private int meetingItemSetId;

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
        return HttpKey.IS_CAN_OPERATION;
    }

    @Override
    public Class getResponseClass() {
        return IsCanOperationResponse.class;
    }
}
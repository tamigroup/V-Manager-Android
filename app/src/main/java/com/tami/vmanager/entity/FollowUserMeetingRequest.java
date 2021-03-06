package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 关注某一个会议.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class FollowUserMeetingRequest extends MobileMessage implements Serializable {


    private static final long serialVersionUID = -8722401984565724363L;
    private String meetingId;
    private String userId;

    public FollowUserMeetingRequest() {
        super();
    }

    /**
     * @return 会议ID
     */
    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    /**
     * @return 用户ID
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    private String requsetUrl;

    @Override
    public String getRequestUrl() {
        return requsetUrl;
    }

    public void setRequsetUrl(String requsetUrl) {
        this.requsetUrl = requsetUrl;
    }

    @Override
    public Class getResponseClass() {
        return FollowUserMeetingResponse.class;
    }
}
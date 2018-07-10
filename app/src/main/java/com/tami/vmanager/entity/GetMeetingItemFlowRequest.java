package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取会议流程单.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class GetMeetingItemFlowRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GetMeetingItemFlowRequest() {
        super();
    }

    private int meetingId;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public Class getResponseClass() {
        return GetMeetingItemFlowResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_MEETING_ITEM_FLOW;
    }
}
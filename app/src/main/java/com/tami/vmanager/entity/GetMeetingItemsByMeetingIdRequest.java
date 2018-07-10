package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;
import java.io.Serializable;

/**
 * 根据会议ID查询会议节点信息.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class GetMeetingItemsByMeetingIdRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GetMeetingItemsByMeetingIdRequest() {
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
    public String getRequestUrl() {
        return HttpKey.GET_MEETING_ITEMS_BY_MEETING_ID;
    }

    @Override
    public Class getResponseClass() {
        return GetMeetingItemsByMeetingIdResponse.class;
    }
}
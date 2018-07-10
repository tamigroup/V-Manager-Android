package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;
import java.io.Serializable;

/**
 * Created by why on 2018/7/10.
 */
public class GetMeetingDayRequest extends MobileMessage implements Serializable {

    public GetMeetingDayRequest() {
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
        return GetMeetingDayResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_MEETING_DAYS;
    }
}

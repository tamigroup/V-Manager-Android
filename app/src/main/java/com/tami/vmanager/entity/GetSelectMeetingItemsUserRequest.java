package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;
import java.io.Serializable;

/**
 * Created by why on 2018/7/14.
 */
public class GetSelectMeetingItemsUserRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -1679013629185960382L;

    public GetSelectMeetingItemsUserRequest() {
        super();
    }

    private int meetingItemSetId;

    public int getMeetingItemSetId() {
        return meetingItemSetId;
    }

    public void setMeetingItemSetId(int meetingItemSetId) {
        this.meetingItemSetId = meetingItemSetId;
    }

    @Override
    public Class getResponseClass() {
        return GetSelectMeetingItemsUserResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_SELECT_MEETING_ITEMS_USER;
    }
}

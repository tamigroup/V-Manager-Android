package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/14
 */
public class SponsorUserListRequestBean extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -6472068110943538568L;
    /**
     * meetingId : 88
     */

    private int meetingId;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_SPONSOR_USER_LIST;
    }

    @Override
    public Class getResponseClass() {
        return SponsorUserListResponseBean.class;
    }
}

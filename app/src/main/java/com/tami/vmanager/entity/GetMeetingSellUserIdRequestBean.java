package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/8/2
 * 获取创建会议的Id
 */
public class GetMeetingSellUserIdRequestBean extends MobileMessage implements Serializable {

    /**
     * meetingId : 20
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
        return HttpKey.GETMEETINGSELL_USERID;
    }

    @Override
    public Class getResponseClass() {
        return GetMeetingSellUserIdResponeBean.class;
    }
}

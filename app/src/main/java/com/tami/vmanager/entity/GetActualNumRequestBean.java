package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/16
 *  获取会议实到人数
 */
public class GetActualNumRequestBean extends MobileMessage implements Serializable{

    /**
     * meetingId : 46
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
        return HttpKey.GET_ACTUAL_NUM;
    }

    @Override
    public Class getResponseClass() {
        return GetActualNumResponseBean.class;
    }
}

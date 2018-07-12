package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/6
 * 意见箱
 */
public class IdeasBoxRequestBean extends MobileMessage implements Serializable {

    /**
     * meetingId : 1     -------   会议ID
     * type : 2          --------  2-举办方  3-参会方   0-取全部
     */

    private String meetingId;
    private int type;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_AVG;
    }

    @Override
    public Class getResponseClass() {
        return IdeasBoxResponsetBean.class;
    }
}

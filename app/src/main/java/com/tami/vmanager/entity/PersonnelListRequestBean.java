package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/13
 * 人员名单 客户端
 */
public class PersonnelListRequestBean extends MobileMessage implements Serializable {

    /**
     * meetingId : 46
     * type : 0
     * name :
     */

    private int meetingId;
    private int type;
    private String name;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_ACTUALLIST;
    }

    @Override
    public Class getResponseClass() {
        return PersonnelListResponseBean.class;
    }
}

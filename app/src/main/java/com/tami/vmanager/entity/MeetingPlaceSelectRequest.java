package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by why on 2018/6/29.
 */
public class MeetingPlaceSelectRequest extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -1214198018181528231L;

    public MeetingPlaceSelectRequest() {
        super();
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getRequestUrl() {
        return super.getRequestUrl();
    }

    @Override
    public Class getResponseClass() {
        return MeetingPlaceSelectResponse.class;
    }
}

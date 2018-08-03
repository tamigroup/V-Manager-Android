package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/8/3
 * 清除极光registrationId
 */
public class LongOutForRegistrationIdRequestBean extends MobileMessage implements Serializable {


    /**
     * registrationId : 1
     */

    private String registrationId;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.longOutForRegistrationId;
    }

    @Override
    public Class getResponseClass() {
        return LongOutForRegistrationIdResponseBean.class;
    }
}

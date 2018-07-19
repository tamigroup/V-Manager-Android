package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/16
 * 绑定用户RegistrationId
 */
public class SetUserRegistrationIdRequestBean extends MobileMessage implements Serializable{
    private static final long serialVersionUID = 2486573866671214788L;
    private int userId;
    private String registrationId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.USER_REGISTRATION_ID;
    }

    @Override
    public Class getResponseClass() {
        return SetUserRegistrationIdResponseBean.class;
    }
}

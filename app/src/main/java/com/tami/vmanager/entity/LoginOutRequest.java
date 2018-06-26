package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 注销登陆.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class LoginOutRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String userId;

    public LoginOutRequest() {
        super();
    }

    /**
     * @return 用户ID
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String getRequsetUrl() {
        return HttpKey.USER_LOGIN_OUT;
    }

    @Override
    public Class getResponseClass() {
        return LoginOutResponse.class;
    }
}
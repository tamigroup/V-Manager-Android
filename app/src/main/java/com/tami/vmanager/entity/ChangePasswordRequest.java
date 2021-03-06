package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 更改密码.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class ChangePasswordRequest extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 2737442766726372856L;
    private String newPassWord;
    private String oldPassWord;
    private String userId;

    public ChangePasswordRequest() {
        super();
    }

    /**
     * @return 新密码
     */
    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    /**
     * @return 旧密码
     */
    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
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
    public String getRequestUrl() {
        return HttpKey.USER_CHANGE_PASSWORD;
    }

    @Override
    public Class getResponseClass() {
        return ChangePasswordResponse.class;
    }
}
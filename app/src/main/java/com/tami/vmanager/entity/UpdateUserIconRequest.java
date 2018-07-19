package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 修改用户头像.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class UpdateUserIconRequest extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 3961418904085959995L;
    private String iconUrl;
    private String userId;

    public UpdateUserIconRequest() {
        super();
    }

    /**
     * @return 头像地址
     */
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
        return HttpKey.USER_UPDATE_USER_ICON;
    }

    @Override
    public Class getResponseClass() {
        return UpdateUserIconResponse.class;
    }
}
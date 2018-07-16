package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/16
 * 绑定用户RegistrationId 服务器端返回
 */
public class SetUserRegistrationIdResponseBean extends MobileMessage implements Serializable {

    /**
     * data : true
     */

    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}

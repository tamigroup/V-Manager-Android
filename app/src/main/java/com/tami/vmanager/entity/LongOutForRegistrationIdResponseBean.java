package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by Tang on 2018/8/3
 * 清除极光registrationId
 */
public class LongOutForRegistrationIdResponseBean extends MobileMessage implements Serializable {


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

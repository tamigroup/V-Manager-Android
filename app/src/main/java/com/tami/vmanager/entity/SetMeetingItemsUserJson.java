package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by why on 2018/7/16.
 */

public class SetMeetingItemsUserJson implements Serializable{

    private static final long serialVersionUID = 2326943758236139037L;
    private int id;
    private String realName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

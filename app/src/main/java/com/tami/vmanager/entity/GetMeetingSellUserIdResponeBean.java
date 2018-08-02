package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by Tang on 2018/8/2
 * 获取创建会议的Id
 */
public class GetMeetingSellUserIdResponeBean extends MobileMessage implements Serializable{


    /**
     * data : 1
     */

    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}

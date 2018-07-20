package com.tami.vmanager.enums;

/**
 * Created by Tang on 2018/7/20
 * 服务流程
 */
public enum  ServiceFlowType {
    TOBECONFIRMED_0("待确认", 0), CONFIRMED("已确认", 1), RECONFIRMED("再确认", 2),TOBECONFIRMED_3("待确认", 3);

    private String name;
    private int type;


    ServiceFlowType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

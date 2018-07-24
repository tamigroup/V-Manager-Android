package com.tami.vmanager.enums;

/**
 * Created by Tang on 2018/7/11
 */
public enum UpdateType {
    noUpdate("不升级", 0), update("升级", 1), forceUpdate("强制升级", 2);

    private String name;
    private int type;


    UpdateType(String name, int type) {
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

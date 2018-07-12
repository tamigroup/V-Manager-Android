package com.tami.vmanager.enums;

/**
 * Created by Tang on 2018/7/11
 */
public enum IdeasBoxType {
    ALL("全部", 0), HOST("主办方", 2), PARTICIPANTS("参会方", 3);

    private String name;
    private int type;


    IdeasBoxType(String name, int type) {
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

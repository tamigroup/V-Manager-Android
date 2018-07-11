package com.tami.vmanager.enums;

/**
 * Created by Tang on 2018/7/11
 */
public enum TimeType {
    TODAY("今天", 0), THIS_MONTH("本月", 1), THIS_YEAR("本年", 2);

    private String name;
    private int type;


    TimeType(String name, int type) {
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

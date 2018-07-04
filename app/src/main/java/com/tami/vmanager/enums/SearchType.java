package com.tami.vmanager.enums;

/**
 * Created by Tang on 2018/7/4
 * 搜索类型
 * searchType=1 会议名称 searchType=2 V智会 searchType=3 销售姓名 searchType=4 会议地点 searchType=5 Vip1 searchType=6 Vip2 searchType=7 Vip3 searchType=8 Vip4
 */
public enum SearchType {

    MEETING_NAME("会议名称",1),V_MEETING("V智会",2),SALE_NAME("销售姓名",3), MEETING_ADDRESS("会议地点",4),
    VIP1("vip1",5),VIP2("vip2",6),VIP3("vip3",7),VIP4("vip4",8)
    ;

    private String name;
    private int type;


    SearchType(String name, int type) {
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

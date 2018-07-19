package com.tami.vmanager.entity;

import com.tami.vmanager.view.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/14
 */
public class IndexNameBean extends BaseIndexPinyinBean implements Serializable{
    private static final long serialVersionUID = 3345060212649818038L;
    private String name;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的
    private int type;
    private int meetingId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public IndexNameBean() {
    }

    public IndexNameBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public IndexNameBean setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isTop() {
        return isTop;
    }

    public IndexNameBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}

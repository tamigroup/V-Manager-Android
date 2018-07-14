package com.tami.vmanager.entity;

import com.tami.vmanager.view.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * Created by Tang on 2018/7/14
 */
public class IndexHeaderBean extends BaseIndexPinyinBean {
    private List<String> cityList;
    //悬停ItemDecoration显示的Tag
    private String suspensionTag;

    public IndexHeaderBean() {
    }

    public IndexHeaderBean(List<String> cityList, String suspensionTag, String indexBarTag) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
        this.setBaseIndexTag(indexBarTag);
    }

    public List<String> getCityList() {
        return cityList;
    }

    public IndexHeaderBean setCityList(List<String> cityList) {
        this.cityList = cityList;
        return this;
    }

    public IndexHeaderBean setSuspensionTag(String suspensionTag) {
        this.suspensionTag = suspensionTag;
        return this;
    }

    @Override
    public String getTarget() {
        return null;
    }

    @Override
    public boolean isNeedToPinyin() {
        return false;
    }

    @Override
    public String getSuspensionTag() {
        return suspensionTag;
    }
}

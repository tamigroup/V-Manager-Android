package com.tami.vmanager.entity;

import com.tami.vmanager.view.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/14
 */
public class IndexHeaderBean extends BaseIndexPinyinBean implements Serializable{
    private static final long serialVersionUID = -8221887198888668211L;
    private List<IndexNameBean> vipNameList;
    //悬停ItemDecoration显示的Tag
    private String suspensionTag;

    public IndexHeaderBean() {
    }

    public IndexHeaderBean(List<IndexNameBean> vipNameList, String suspensionTag, String indexBarTag) {
        this.vipNameList = vipNameList;
        this.suspensionTag = suspensionTag;
        this.setBaseIndexTag(indexBarTag);
    }

    public List<IndexNameBean> getCityList() {
        return vipNameList;
    }

    public IndexHeaderBean setCityList(List<IndexNameBean> vipNameList) {
        this.vipNameList = vipNameList;
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

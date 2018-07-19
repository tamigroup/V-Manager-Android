package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/4
 * 客户端请求 向服务器传递参数
 */
public class SearchRequestBean extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 4465853287643258883L;
    /**
     * keyWord : 塔
     * searchType : 1
     * userId : 1
     * curPage : 1
     * pageSize : 2
     */

    private String keyWord;
    private int searchType;
    private String userId;
    private int curPage;
    private int pageSize;

    public SearchRequestBean() {
        super();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Class getResponseClass() {
        return SearchResponseBean.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.SEARCH_MEETING;
    }
}

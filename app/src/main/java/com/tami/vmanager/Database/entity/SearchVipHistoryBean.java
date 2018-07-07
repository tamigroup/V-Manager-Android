package com.tami.vmanager.Database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Tang on 2018/7/3
 * vip 历史搜索
 */
@Entity
public class SearchVipHistoryBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String SearchVipHistory;
    private String header_imageUrl;


    public String getHeader_imageUrl() {
        return header_imageUrl;
    }

    public void setHeader_imageUrl(String header_imageUrl) {
        this.header_imageUrl = header_imageUrl;
    }

    public String getSearchVipHistory() {
        return SearchVipHistory;
    }

    public void setSearchVipHistory(String searchHistory) {
        SearchVipHistory = searchHistory;
    }
}

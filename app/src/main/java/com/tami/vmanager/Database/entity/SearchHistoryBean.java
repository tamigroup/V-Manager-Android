package com.tami.vmanager.Database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Tang on 2018/7/3
 * 会议历史搜索
 */
@Entity
public class SearchHistoryBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String SearchHistory;

    public String getSearchHistory() {
        return SearchHistory;
    }

    public void setSearchHistory(String searchHistory) {
        SearchHistory = searchHistory;
    }
}

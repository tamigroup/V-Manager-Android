package com.tami.vmanager.Database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/3
 * 会议历史搜索
 */
@Entity
public class SearchHistoryBean implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public int id;

    private int UserId;
    private String SearchHistory;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getSearchHistory() {
        return SearchHistory;
    }

    public void setSearchHistory(String searchHistory) {
        SearchHistory = searchHistory;
    }
}

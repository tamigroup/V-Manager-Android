package com.tami.vmanager.Database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tami.vmanager.Database.entity.SearchHistoryBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tang on 2018/7/3
 */
@Dao
public interface SearchHistoryDao {

    @Insert
    void insert(SearchHistoryBean ... SearchHistory);

    @Query("SELECT * FROM SearchHistoryBean where UserId = :userId order by id desc limit 6")
    Flowable<List<SearchHistoryBean>> getSearchHistory(int userId);




}

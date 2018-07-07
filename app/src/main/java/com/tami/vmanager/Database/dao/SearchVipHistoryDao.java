package com.tami.vmanager.Database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tami.vmanager.Database.entity.SearchVipHistoryBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tang on 2018/7/3
 * vip 历史搜索
 */
@Dao
public interface SearchVipHistoryDao {

    @Insert
    void insert(SearchVipHistoryBean... searchVipHistoryBeans);

    @Query("SELECT * FROM SearchVipHistoryBean order by id desc")
    Flowable<List<SearchVipHistoryBean>> getSearchVipHistory();

    @Delete
    void delete(SearchVipHistoryBean... searchVipHistoryBeans);
}

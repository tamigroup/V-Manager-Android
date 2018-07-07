package com.tami.vmanager.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.tami.vmanager.Database.dao.SearchHistoryDao;
import com.tami.vmanager.Database.dao.SearchVipHistoryDao;
import com.tami.vmanager.Database.entity.SearchHistoryBean;
import com.tami.vmanager.Database.entity.SearchVipHistoryBean;
import com.tami.vmanager.utils.Logger;

/**
 * Created by Tang on 2018/7/3
 * room
 */
@Database(entities = {SearchHistoryBean.class, SearchVipHistoryBean.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "tami";
    private static final int DB_VERSION = 1;
    private static String DB_FILE_NAME = DB_NAME + DB_VERSION + ".db";

    public abstract SearchHistoryDao searchHistoryDao();
    public abstract SearchVipHistoryDao searchVipHistoryDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_FILE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 关闭链接
     */
    public void RoomClose() {
        try {
            if (INSTANCE != null) {
                INSTANCE.close();
            }
        } catch (Exception e) {
            Logger.e("数据库关闭异常");
        }
    }
}

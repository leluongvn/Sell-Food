package com.example.maket.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maket.DAO.DaoBuy;
import com.example.maket.DAO.DaoFeedBack;
import com.example.maket.Entity.FeedBack;

@Database(entities = FeedBack.class, version = 1)
public abstract class FeedBackDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "feed_back";

    public abstract DaoFeedBack daoFeedBack();

    private static FeedBackDatabase instance;

    public static synchronized FeedBackDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FeedBackDatabase.class
                    , DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

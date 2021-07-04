package com.example.maket.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.maket.Entity.FeedBack;
import com.example.maket.Entity.Foody;

import java.util.List;

@Dao
public interface DaoFeedBack {

    @Query("Select * from feed_back")
    List<FeedBack> FEED_BACK();
    @Insert
    void insertFoody(FeedBack foody);

}

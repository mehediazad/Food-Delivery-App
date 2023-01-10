package com.example.food_app.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.food_app.Model.Popular;

import java.util.List;

@Dao
public interface PopularDao {

    @Insert
    void insertPopular(Popular... popular);

    @Query("SELECT * FROM popular_table")
    LiveData<List<Popular>> getAllPopular();

}

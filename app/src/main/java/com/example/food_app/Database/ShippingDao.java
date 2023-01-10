package com.example.food_app.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.food_app.Model.ShippingAddress;

import java.util.List;

@Dao
public interface ShippingDao {
    @Insert
    void insertShipping(ShippingAddress... shoppingAddress);

    @Update
    void updateShipping(ShippingAddress shippingAddress);

    @Query("SELECT * FROM shoppingAddress_table")
    LiveData<List<ShippingAddress>> getAllShippingAddress();
}

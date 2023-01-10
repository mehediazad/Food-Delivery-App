package com.example.food_app.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_app.Model.ShippingAddress;

@Database(entities = {ShippingAddress.class}, version = 1, exportSchema = false)
public abstract class ShippingDatabase extends RoomDatabase {

    private static volatile ShippingDatabase INSTANCE;

    public abstract ShippingDao shippingDao();

    public synchronized static ShippingDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , ShippingDatabase.class, "shoppingAddress_table")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    //  .addCallback(roomCallback)
                    .build();
        }

        return INSTANCE;
    }
}

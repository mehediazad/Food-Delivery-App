package com.example.food_app.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_app.Model.Popular;

@Database(entities = {Popular.class}, version = 1, exportSchema = false)
public abstract class PopularDatabase extends RoomDatabase {

    private static volatile PopularDatabase INSTANCE;

    public abstract PopularDao popularDao();

    public synchronized static PopularDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , PopularDatabase.class, "popular_table")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    //  .addCallback(roomCallback)
                    .build();
        }

        return INSTANCE;
    }

}

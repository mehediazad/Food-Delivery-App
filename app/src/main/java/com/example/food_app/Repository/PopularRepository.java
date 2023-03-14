package com.example.food_app.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.food_app.Database.PopularDao;
import com.example.food_app.Database.PopularDatabase;
import com.example.food_app.Model.Popular;

import java.util.List;

public class PopularRepository {
    public PopularDao popularDao;
    public LiveData<List<Popular>> getAllPopular;

    public PopularRepository(Application application) {
        PopularDatabase database = PopularDatabase.getInstance(application);
        popularDao = database.popularDao();
        getAllPopular = popularDao.getAllPopular();
    }



    public  void insertPopular(Popular popular){
        popularDao.insertPopular(popular);
    }
}


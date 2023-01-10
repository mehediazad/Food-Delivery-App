package com.example.food_app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.food_app.Model.Popular;
import com.example.food_app.Repository.PopularRepository;

import java.util.List;

public class PopularViewModel extends AndroidViewModel {
    public PopularRepository repository;
    public LiveData<List<Popular>> getAllPopular;

    public PopularViewModel(@NonNull Application application) {
        super(application);
        repository = new PopularRepository(application);
        getAllPopular = repository.getAllPopular;
    }

    public void insertPopular(Popular popular) {
        repository.insertPopular(popular);
    }
}
